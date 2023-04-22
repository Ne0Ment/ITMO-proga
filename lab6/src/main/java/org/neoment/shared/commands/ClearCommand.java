package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;

import java.io.IOException;
import java.io.PrintWriter;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class ClearCommand extends ManagerCommand {
    public ClearCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    public ClearCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        this.clientIO.sendObjects(CommandType.CLEAR);
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        this.manager.clear();
        return wrap(CommandStatus.OK);
    }
}
