package org.neoment.server;

import org.neoment.shared.*;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {
    static Logger logger = Logger.getLogger(DBManager.class.getName());
    private MessageDigest md;
    private Connection conn;

    public DBManager(String user, String passwd, String uri) throws FileNotFoundException {
        if (user == null || passwd == null || uri == null)
            throw new FileNotFoundException("Incorrect db properties file.");
        try {
            this.conn = DriverManager.getConnection(uri, user, passwd);
            this.md = MessageDigest.getInstance("MD5");
        } catch (SQLException | NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Couldn't init DBHandler: " + e.getMessage());
            System.exit(0);
        }
    }

    public void addWorker(Worker w, Long creatorId) throws SQLException {
        var stat = this.conn.prepareStatement("INSERT INTO COORDINATES (id, x, y) VALUES (DEFAULT,?,?);", Statement.RETURN_GENERATED_KEYS);
        stat.setInt(1, w.getCoordinates().getX());
        stat.setInt(2, w.getCoordinates().getY());
        stat.execute();

        var rs = stat.getGeneratedKeys();
        rs.next();
        int coordId = rs.getInt(1);

        stat = this.conn.prepareStatement("INSERT INTO ORGANIZATIONS (id, name, type) VALUES (DEFAULT,?,?)", Statement.RETURN_GENERATED_KEYS);
        stat.setString(1, w.getOrganization().getFullName());
        stat.setString(2, w.getOrganization().getType().toString());
        stat.execute();

        rs = stat.getGeneratedKeys();
        rs.next();
        int orgId = rs.getInt(1);


        stat = this.conn.prepareStatement("INSERT INTO WORKERS (id, creator_id, name, coordinate_id, creation_date, start_date, end_date, salary, position_, org_id) VALUES (?,?,?,?,?,?,?,?,?,?)");
        stat.setLong(1, w.getId());
        stat.setLong(2, creatorId);
        stat.setString(3, w.getName());
        stat.setInt(4, coordId);
        stat.setTimestamp(5, localDateToTimestamp(w.getCreationDate()));
        stat.setTimestamp(6, Timestamp.from(w.getStartDate().toInstant()));
        stat.setTimestamp(7, localDateToTimestamp(w.getEndDate()));
        stat.setFloat(8, w.getSalary());
        stat.setString(9, w.getPosition().toString());
        stat.setInt(10, orgId);
        stat.execute();
    }

    public void removeWorker(Long workerId) throws SQLException {
        var stat = this.conn.prepareStatement("DELETE FROM WORKERS WHERE id = ?");
        stat.setLong(1, workerId);
        stat.execute();
    }

    private Timestamp localDateToTimestamp(LocalDateTime t) {
        if (t == null) return null;
        return Timestamp.from(t.toInstant(ZoneId.systemDefault().getRules().getOffset(t)));
    }

    private String encodePass(String passwd) {
        return String.format("%032X", new BigInteger(1, this.md.digest(passwd.getBytes(StandardCharsets.UTF_8))));
    }

    public User getUserByName(String username) throws SQLException {
        var stat = this.conn.prepareStatement("SELECT * FROM USERS WHERE USERS.name = ? LIMIT 1");
        stat.setString(1, username);
        var res = stat.executeQuery();
        if (!res.next()) return null;

        return new User(res.getLong("id"), res.getString("name"), res.getString("passwd"));
    }

    public boolean validateUser(String username, String passwd) throws SQLException {
        if (username == null || passwd == null) return false;
        var dbUser = getUserByName(username);
        if (dbUser == null) return false;
        return (Objects.equals(dbUser.username, username) && Objects.equals(dbUser.passwd, encodePass(passwd)));
    }

    public boolean registerUser(String username, String passwd) throws SQLException {
        if (username == null || passwd == null) return false;
        var dbUser = getUserByName(username);
        if (dbUser != null) return false; // user already exists

        var stat = this.conn.prepareStatement("INSERT INTO USERS (id, name, passwd) VALUES (default, ?, ?)");
        stat.setString(1, username);
        stat.setString(2, this.encodePass(passwd));
        stat.execute();
        return true;
    }

    public CollectionManager loadCollection() {
        try {
            var creationDate = getCreationDate();
            if (creationDate == null)
                this.initCollection();
            creationDate = getCreationDate();

            var workers = new TreeSet<>(getAllWorkers());
            return new CollectionManager(workers, creationDate, this);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to load collection: " + e.getMessage());
            return new CollectionManager(this);
        }
    }

    private void initCollection() throws SQLException {
        var stat = this.conn.prepareStatement("INSERT INTO COLLECTION_INFO(id, creation_date) VALUES (default, ?)");
        stat.setTimestamp(1, Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        stat.execute();
        logger.log(Level.INFO, "Initialized new collection.");
    }

    private LocalDateTime getCreationDate() throws SQLException {
        var stat = this.conn.prepareStatement("SELECT * FROM COLLECTION_INFO");
        var res = stat.executeQuery();
        LocalDateTime creationDate = null;
        while (res.next()) creationDate = res.getTimestamp("creation_date").toLocalDateTime();
        return creationDate;
    }

    private List<Worker> getAllWorkers() throws SQLException {
        List<Worker> workers = new ArrayList<>();
        var stat = this.conn.prepareStatement("SELECT w.id, w.creator_id, w.name, w.salary, w.position_, w.creation_date, w.start_date, w.end_date, c.x as cx, c.y as cy, o.name as oname, o.type as otype FROM workers w JOIN coordinates c on w.coordinate_id = c.id JOIN organizations o on w.org_id = o.id;");
        var res = stat.executeQuery();
        while (res.next()) {
            var w = new Worker();
            w.setId(res.getLong("id"));
            w.setCreatorId(res.getLong("creator_id"));
            w.setName(res.getString("name"));
            w.setSalary(res.getFloat("salary"));
            w.setPosition(Position.valueOf(res.getString("position_")));
            w.setCreationDate(toLocal(res.getTimestamp("creation_date")));
            w.setStartDate(res.getTimestamp("start_date"));
            w.setEndDate(toLocal(res.getTimestamp("end_date")));
            w.setCoordinates(new Coordinates(res.getInt("cx"), res.getInt("cy")));

            w.setOrganization(new Organization(res.getString("oname"), OrganizationType.valueOf(res.getString("otype"))));
            workers.add(w);
        }

        return workers;
    }

    private LocalDateTime toLocal(Timestamp t) {
        if (t == null) return null;
        return t.toLocalDateTime();
    }

    public Long nextId() {
        try {
            var stat = this.conn.prepareStatement("select nextval('workers_id_seq')");
            var res = stat.executeQuery();
            res.next();
            return res.getLong(1);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch next id for worker: " + e.getMessage());
            return 0L;
        }
    }
}
