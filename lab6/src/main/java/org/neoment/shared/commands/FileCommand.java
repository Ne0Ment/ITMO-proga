package org.neoment.shared.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.neoment.client.ClientNetworkIO;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.server.FileHandler;

import java.io.IOException;
import java.io.PrintWriter;


public class FileCommand extends ManagerCommand {
    public FileHandler fileHandler;

    public FileCommand(CollectionManager manager, FileHandler fileHandler) {
        super(manager);
        this.fileHandler = fileHandler;
    }

    public FileCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    void saveFile() throws IOException {
        this.fileHandler.saveToXml(this.manager);
    }
}
