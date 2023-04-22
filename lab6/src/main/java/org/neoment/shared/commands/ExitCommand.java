package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.server.FileHandler;

import java.io.IOException;
import java.io.PrintWriter;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class ExitCommand extends FileCommand {

    public ExitCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    public ExitCommand(CollectionManager manager, FileHandler fileHandler) {
        super(manager, fileHandler);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        this.clientIO.sendObjects(CommandType.EXIT);
        return true;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        try { this.saveFile(); } catch (IOException e) {
            return wrap(CommandStatus.NOT_OK);
        }
        return wrap(CommandStatus.OK);
    }
}
