package org.neoment.server.servercommands;

import org.neoment.server.ServerSystemIOHandler;

public class ServerExitCommand implements ServerCommand {
    @Override
    public boolean execute() {
        return true;
    }
}
