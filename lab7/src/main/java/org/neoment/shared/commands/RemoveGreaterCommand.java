package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Parser;
import org.neoment.shared.Worker;
import org.neoment.shared.exceptions.NotLoggedInException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Objects;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class RemoveGreaterCommand extends ModifiableCommand {

    public RemoveGreaterCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public RemoveGreaterCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        checkLogin();
        try {
            Float maxSalary = (Float) Parser.parseString(Float.class, commandArgs[1]);
            this.clientIO.sendObjects(CommandType.REMOVE_GREATER, getName(), getPass(), maxSalary);
        } catch (ParseException e) {
            this.writer.println("Wrong salary format. ");
        }
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException, SQLException {
        if (!verifyUser((String) in[0], (String) in[1])) return wrap(CommandStatus.NOT_OK, "Couldn't verify user.");
        var user = this.manager.dbManager.getUserByName((String) in[0]);
        var maxSalary = (Float) in[2];
        this.manager.filterWorkers((Worker worker) -> (worker.getSalary() <= maxSalary) || !Objects.equals(worker.getCreatorId(), user.id));
        return wrap(CommandStatus.OK);
    }
}
