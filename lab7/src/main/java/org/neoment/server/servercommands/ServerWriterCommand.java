package org.neoment.server.servercommands;

import org.neoment.shared.commands.Command;

import java.io.PrintWriter;

public class ServerWriterCommand implements ServerCommand {
    PrintWriter writer;

    public ServerWriterCommand(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public boolean execute() {
        return false;
    }
}
