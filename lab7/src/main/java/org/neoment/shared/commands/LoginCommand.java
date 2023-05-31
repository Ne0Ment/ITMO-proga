package org.neoment.shared.commands;

import org.neoment.client.ClientAccountHandler;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class LoginCommand extends ReaderCommand {
    public LoginCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public LoginCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        var username = inputLogin();
        var password = inputPassword();

        var response = this.clientIO.sendObjects(CommandType.LOGIN, username, password);
        var status = (CommandStatus) response[0];
        if (status == CommandStatus.NOT_OK) {
            this.writer.println("Wrong username or password.");
            return false;
        }

        ClientAccountHandler.setAccount(username, password);
        this.writer.println("Logged in with user id=" + response[1]);
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException, SQLException {
        var username = (String) in[0];
        var password = (String) in[1];
        var res = this.manager.dbManager.validateUser(username, password);
        if (!res) return wrap(CommandStatus.NOT_OK);
        var user = this.manager.dbManager.getUserByName(username);
        return wrap(CommandStatus.OK, user.id);
    }
}
