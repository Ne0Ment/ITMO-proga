package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Worker;
import org.neoment.shared.exceptions.NotLoggedInException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class AddIfMaxCommand extends ModifiableCommand{
    public AddIfMaxCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public AddIfMaxCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException {
        checkLogin();
        var worker = this.constructWorker();
        var response = this.clientIO.sendObjects(CommandType.ADD_IF_MAX, getName(), getPass(), worker);

        var status = (CommandStatus) response[0];
        if (status == CommandStatus.OK) return false;
        writer.println((String) response[1]);
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException, SQLException {
        if (!verifyUser((String) in[0], (String) in[1])) return wrap(CommandStatus.NOT_OK, "Couldn't verify user.");
        var user = this.manager.dbManager.getUserByName((String) in[0]);
        var worker = (Worker) in[2];
        worker.setId(this.manager.nextId());
        Worker maxWorker = this.manager.getWorkers().last();

        if (worker.compareTo(maxWorker) > 0) {
            this.manager.add(worker, user.id);
            return wrap(CommandStatus.OK);
        }

        return wrap(CommandStatus.NOT_OK, "Worker isn't max.");
    }
}
