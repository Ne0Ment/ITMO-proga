package org.neoment.shared.commands;


import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class AddCommand extends ModifiableCommand {

    public AddCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public AddCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        var worker = this.constructWorker();
        var response = this.clientIO.sendObjects(CommandType.ADD, worker);

        var responseState = (CommandStatus) response[0];
        if (responseState == CommandStatus.NOT_OK) writer.println("Server couldn't add Worker.");

        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        var worker = (Worker) in[0];
        worker.setId(this.manager.nextId());
        this.manager.add(worker);
        return wrap(CommandStatus.OK);
    }

}
