package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.AccountVerifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class ReaderCommand extends ManagerCommand {
    public BufferedReader reader;
    public ReaderCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
        this.reader = reader;
    }

    public String inputLogin() throws IOException {
        String username = "";
        do {
            writer.print("Username: "); writer.flush();
            username = this.reader.readLine().trim();
        } while (!AccountVerifier.checkLoginInput(username));
        return username;
    }

    public String inputPassword() throws IOException {
        String pass = "";
        do {
            writer.print("Password: "); writer.flush();
            pass = this.reader.readLine().trim();
        } while (!AccountVerifier.checkPassInput(pass));
        return pass;
    }

    public ReaderCommand(CollectionManager manager) {
        super(manager);
    }
}
