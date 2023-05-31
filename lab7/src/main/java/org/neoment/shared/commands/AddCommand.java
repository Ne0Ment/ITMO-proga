package org.neoment.shared.commands;


import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Worker;
import org.neoment.shared.exceptions.NotLoggedInException;
import org.neoment.shared.exceptions.WorkerExistsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class AddCommand extends ModifiableCommand {

    public AddCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public AddCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException {
        checkLogin();
        var worker = this.constructWorker();
        var response = this.clientIO.sendObjects(CommandType.ADD, getName(), getPass(),  worker);

        var responseState = (CommandStatus) response[0];
        if (responseState == CommandStatus.NOT_OK) writer.println((String) response[1]);
        if (responseState == CommandStatus.OK) writer.println("Added worker with id=" + response[1]);

        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException, SQLException {
        if (!verifyUser((String) in[0], (String) in[1])) return wrap(CommandStatus.NOT_OK, "Couldn't verify user.");
        var user = this.manager.dbManager.getUserByName((String) in[0]);
        var worker = (Worker) in[2];
        worker.setCreatorId(user.id);
        worker.setId(this.manager.nextId());
        try {
            this.manager.add(worker, user.id);
            return wrap(CommandStatus.OK, worker.getId());
        } catch (WorkerExistsException e) {
            return wrap(CommandStatus.NOT_OK, e.getMessage());
        }

    }

}
