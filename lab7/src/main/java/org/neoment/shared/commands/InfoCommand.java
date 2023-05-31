package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.User;
import org.neoment.shared.exceptions.NotLoggedInException;

import java.io.*;
import java.sql.SQLException;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class InfoCommand extends ManagerCommand {

    public InfoCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    public InfoCommand(CollectionManager manager) { super(manager); }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException {
        checkLogin();
        var response = this.clientIO.sendObjects(CommandType.INFO, getName(), getPass());
        String infoText = (String) response[0];
        var user = (User) response[1];
        this.writer.println("Username: " + user.username + ", id: " + user.id);
        this.writer.println(infoText);
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, SQLException {
        var name = (String) in[0];
        var account = this.manager.dbManager.getUserByName(name);
        return wrap(this.manager.getWorkersInfo(), account);
    }
}
