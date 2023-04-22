package org.neoment.server.servercommands;

import org.neoment.server.CollectionManager;
import org.neoment.server.FileHandler;

import java.io.IOException;
import java.io.PrintWriter;

public class ServerSaveCommand extends  ServerWriterCommand {
    private CollectionManager manager;
    private FileHandler fileHandler;

    public ServerSaveCommand(PrintWriter writer, CollectionManager manager, FileHandler fileHandler) {
        super(writer);
        this.manager = manager;
        this.fileHandler = fileHandler;
    }

    @Override
    public boolean execute() {
        try {
            this.fileHandler.saveToXml(this.manager);
            this.writer.println("Saved data.");
        } catch (IOException e) {
            this.writer.println("Couldn't save data" + e.getMessage());
        }
        return false;
    }
}
