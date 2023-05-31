package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIO;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.NetworkObjectEncoder;
import org.neoment.shared.Organization;
import org.neoment.shared.exceptions.NotLoggedInException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Set;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class UniqueOrgCommand extends ManagerCommand {

    public UniqueOrgCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    public UniqueOrgCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException {
        checkLogin();
        var response = this.clientIO.sendObjects(CommandType.PRINT_UNIQUE_ORG, getName(), getPass());
        this.writer.println((String) response[0]);
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        if (!verifyUser((String) in[0], (String) in[1])) return wrap(CommandStatus.NOT_OK, "Couldn't verify user.");
        var outStr = new StringBuilder();
        Set<Organization> organizations = this.manager.getUniqueOrganizations();
        for (Organization org : organizations)
            outStr.append(org.toString()).append("\n");
        return wrap(outStr.toString());
    }
}
