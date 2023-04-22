package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIO;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.NetworkObjectEncoder;
import org.neoment.shared.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class PrintDescendingCommand extends ManagerCommand {

    public PrintDescendingCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    public PrintDescendingCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        this.clientIO.sendOneWayObjects(CommandType.PRINT_DESC);
        return handleBatchResponse();
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        var workersList = this.manager.getWorkerList();
        Collections.reverse(workersList);

        return getObjects(workersList, BATCH_SIZE);
    }

    private static final int BATCH_SIZE = 20;
}
