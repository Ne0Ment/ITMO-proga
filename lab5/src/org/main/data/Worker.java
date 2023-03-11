package org.main.data;

import org.main.Parser;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class for handling worker info.
 * @author neoment
 * @version 0.1
 */
public class Worker implements Comparable<Worker>{
    private final Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float salary; //Поле не может быть null, Значение поля должно быть больше 0
    private java.util.Date startDate; //Поле не может быть null
    private java.time.LocalDateTime endDate; //Поле может быть null
    private Position position; //Поле может быть null
    private Organization organization; //Поле не может быть null

    public interface Setter {
        void set(Object v);
    }

    public record Input (
        String description,
        Class<?> cl,
        Setter setter
    ) {}

    public Input[] inputs = new Input[] {
            new Input("name", String.class, (v) -> this.setName((String) v)),
            new Input("X coordinate", Integer.class, (v) -> this.coordinates.setX((Integer) v)),
            new Input("Y coordinate", Integer.class, (v) -> this.coordinates.setY((Integer) v)),
            new Input("salary", BigDecimal.class, (v) -> this.setSalaryBigDecimal((BigDecimal) v)),
            new Input("start date and time", Date.class, (v) -> this.setStartDate((Date) v)),
            new Input("end date and time", LocalDateTime.class, (v) -> this.setEndDate((LocalDateTime) v)),
            new Input("company position", Position.class, (v) -> this.setPosition((Position) v)),
            new Input("organization name", String.class, (v) -> this.organization.setFullName((String) v)),
            new Input("organization type", OrganizationType.class, (v) -> this.organization.setType((OrganizationType) v))
    };

    public Worker(Long id) {
        this.id = id;
        this.coordinates = new Coordinates();
        this.organization = new Organization();
        this.creationDate = LocalDateTime.now();
    }

    @Override
    public int compareTo(Worker otherWorker) {
        if (Objects.equals(otherWorker.getSalary(), this.getSalary())) return 0;
        return this.getSalary() > otherWorker.getSalary() ? 1 : -1;
    }

    @Override
    public String toString() {
        return  "Id: " + this.id + ", " +
                "Name: " + this.name + ", " +
                "Salary: " + this.salary + "\n" +
                "Coordinates: " + this.coordinates.getX() + ", " + this.coordinates.getY() + "\n" +
                "Creation Date: " + Parser.genString(this.creationDate) + ", " +
                "Start Date: " + Parser.genString(this.startDate) + ", " +
                ("End Date: " + (this.endDate == null ? "Not set" : Parser.genString(this.endDate)) + "\n") +
                "Position: " + this.position + ", " +
                "Organization: " + this.organization.getFullName() + ", " + this.organization.getType() + "\n";
    }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null) return false;
        if (!this.getClass().equals(otherObj.getClass())) return false;
        Worker other = (Worker) otherObj;
        return Objects.equals(this.getId(), other.getId());
    }

    private void checkNull(Object v) {
        if (v==null) throw new IllegalArgumentException("Value can't be null.");
    }
    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name can't be an empty string.");
        this.name = name;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setSalary(Float salary) {
        checkNull(salary);
        if (salary == Float.POSITIVE_INFINITY) throw new IllegalArgumentException("Value too large.");
        if (salary == Float.NEGATIVE_INFINITY) throw new IllegalArgumentException("Salary should be >= 0.");
        if (salary <= 0.) throw new IllegalArgumentException("Salary should be >= 0");
        this.salary = salary;
    }

    public void setSalaryBigDecimal(BigDecimal salary) {
        Float floatSalary = salary.floatValue();
        if (floatSalary == 0 && !salary.equals(new BigDecimal(0))) {
            throw new IllegalArgumentException("Value to small.");
        }
        this.setSalary(floatSalary);
    }

    public void setStartDate(Date startDate) {
        checkNull(startDate);
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setPosition(Position position) {
        checkNull(position);
        this.position = position;
    }

    public Long getId() {
        return this.id;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public Float getSalary() {
        return this.salary;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public Position getPosition() {
        return this.position;
    }
}