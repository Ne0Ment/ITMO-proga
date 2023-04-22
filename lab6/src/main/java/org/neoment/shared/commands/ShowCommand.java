package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIO;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.NetworkObjectEncoder;

import java.io.IOException;
import java.io.PrintWriter;

public class ShowCommand extends ManagerCommand {

    public ShowCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    public ShowCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        this.clientIO.sendOneWayObjects(CommandType.SHOW);
        return handleBatchResponse();
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        var workersList = this.manager.getWorkerList();
        return getObjects(workersList, BATCH_SIZE);
    }

    private static final int BATCH_SIZE = 20;
}
