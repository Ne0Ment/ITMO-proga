package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIO;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.NetworkObjectEncoder;
import org.neoment.shared.Organization;

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
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        var response = this.clientIO.sendObjects(CommandType.PRINT_UNIQUE_ORG);
        this.writer.println((String) response[0]);
        return super.clientExecute(commandArgs);
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        var outStr = new StringBuilder();
        Set<Organization> organizations = this.manager.getUniqueOrganizations();
        for (Organization org : organizations)
            outStr.append(org.toString()).append("\n");
        return wrap(outStr.toString());
    }
}
