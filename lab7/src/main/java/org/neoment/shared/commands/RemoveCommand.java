package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.shared.exceptions.NotLoggedInException;
import org.neoment.shared.exceptions.WorkerDoesntExistException;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Parser;
import org.neoment.shared.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Objects;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class RemoveCommand extends ModifiableCommand {

    public RemoveCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public RemoveCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        checkLogin();
        try {
            var id = (Long) Parser.parseString(Long.class, commandArgs[1]);
            var response = this.clientIO.sendObjects(CommandType.REMOVE_BY_ID, getName(), getPass(), id);

            var status = (CommandStatus) response[0];
            if (status == CommandStatus.OK) return false;
            writer.println((String) response[1]);
        } catch (ParseException | NumberFormatException e) {
            this.writer.println("Couldn't parse worker id.");
        }
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException, SQLException {
        if (!verifyUser((String) in[0], (String) in[1])) return wrap(CommandStatus.NOT_OK, "Couldn't verify user.");
        var user = this.manager.dbManager.getUserByName((String) in[0]);
        var id = (Long) in[2];
        try {
            var removeWorker = this.manager.getWorkerById(id);
            if (!Objects.equals(removeWorker.getCreatorId(), user.id)) return wrap(CommandStatus.NOT_OK, "You don't have permission to remove this worker.");
            this.manager.pop(removeWorker, user.id);
            return wrap(CommandStatus.OK, "");
        } catch (NumberFormatException e) {return wrap(CommandStatus.NOT_OK, "Invalid worker id.");
        } catch (WorkerDoesntExistException e) { return wrap(CommandStatus.NOT_OK, e.getMessage()); }
    }
}
