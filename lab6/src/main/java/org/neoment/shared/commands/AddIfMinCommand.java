package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class AddIfMinCommand extends ModifiableCommand {

    public AddIfMinCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public AddIfMinCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        var worker = this.constructWorker();
        var response = this.clientIO.sendObjects(CommandType.ADD_IF_MIN, worker);

        var status = (CommandStatus) response[0];
        if (status == CommandStatus.OK) return false;
        writer.println((String) response[1]);
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        var worker = (Worker) in[0];
        worker.setId(this.manager.nextId());
        Worker minWorker = this.manager.getWorkers().first();

        if (worker.compareTo(minWorker) < 0) {
            this.manager.add(worker);
            return wrap(CommandStatus.OK);
        }

        return wrap(CommandStatus.NOT_OK, "Worker isn't min.");
    }
}
