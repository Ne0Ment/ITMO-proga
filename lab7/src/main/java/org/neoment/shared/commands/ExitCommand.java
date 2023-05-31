package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;

import java.io.IOException;
import java.io.PrintWriter;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class ExitCommand extends ManagerCommand {
    public ExitCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        return true;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        return wrap(CommandStatus.OK);
    }
}
