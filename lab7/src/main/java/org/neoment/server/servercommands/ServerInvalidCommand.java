package org.neoment.server.servercommands;

import java.io.PrintWriter;

public class ServerInvalidCommand extends ServerWriterCommand {

    public ServerInvalidCommand(PrintWriter writer) {
        super(writer);
    }

    @Override
    public boolean execute() {
        this.writer.println("Invalid Command.");
        return false;
    }
}
