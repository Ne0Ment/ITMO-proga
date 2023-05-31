package org.neoment.shared.commands;

import org.neoment.client.ClientAccountHandler;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.shared.exceptions.NotLoggedInException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LogoutCommand extends ReaderCommand {
    public LogoutCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException {
        if (!ClientAccountHandler.isLoggedIn()) {
            this.writer.println("Not logged in.");
            return false;
        }
        ClientAccountHandler.clearAccount();
        this.writer.println("Logged out");
        return false;
    }
}
