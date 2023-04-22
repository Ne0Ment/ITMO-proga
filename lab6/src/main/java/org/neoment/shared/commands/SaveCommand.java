package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIO;
import org.neoment.server.CollectionManager;
import org.neoment.server.FileHandler;

import java.io.IOException;
import java.io.PrintWriter;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class SaveCommand extends FileCommand {

    public SaveCommand(CollectionManager manager, FileHandler fileHandler) {
        super(manager, fileHandler);
    }

    public SaveCommand(PrintWriter writer, ClientNetworkIO clientIO) {
        super(writer, clientIO);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        this.clientIO.sendObjects(CommandType.SAVE);
        return super.clientExecute(commandArgs);
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        this.saveFile();
        return wrap(CommandStatus.OK);
    }
}
