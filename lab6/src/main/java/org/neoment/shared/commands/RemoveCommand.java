package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.shared.exceptions.WorkerDoesntExistException;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Parser;
import org.neoment.shared.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class RemoveCommand extends ModifiableCommand {

    public RemoveCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public RemoveCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        try {
            var id = (Long) Parser.parseString(Long.class, commandArgs[1]);
            var response = this.clientIO.sendObjects(CommandType.REMOVE_BY_ID, id);

            var status = (CommandStatus) response[0];
            if (status == CommandStatus.OK) return false;
            writer.println((String) response[1]);
        } catch (ParseException | NumberFormatException e) {
            this.writer.println("Couldn't parse worker id.");
        }
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        var id = (Long) in[0];
        try {
            this.manager.pop(new Worker(id));
            return wrap(CommandStatus.OK, "");
        } catch (NumberFormatException e) {return wrap(CommandStatus.NOT_OK, "Invalid worker id.");
        } catch (WorkerDoesntExistException e) { return wrap(CommandStatus.NOT_OK, e.getMessage()); }
    }
}
