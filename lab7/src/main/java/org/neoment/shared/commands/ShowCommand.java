package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIO;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.NetworkObjectEncoder;
import org.neoment.shared.exceptions.NotLoggedInException;

import java.io.IOException;
import java.io.PrintWriter;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class ShowCommand extends ManagerCommand {

    public ShowCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    public ShowCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException {
        checkLogin();
        this.clientIO.sendOneWayObjects(CommandType.SHOW, getName(), getPass());
        return handleBatchResponse();
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        if (!verifyUser((String) in[0], (String) in[1])) return wrap(CommandStatus.NOT_OK, "Couldn't verify user.");
        var workersList = this.manager.getWorkerList();
        return getObjects(workersList, BATCH_SIZE);
    }

    private static final int BATCH_SIZE = 20;
}
