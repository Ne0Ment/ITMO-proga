package org.main.commands;

import org.main.CollectionManager;
import org.main.data.Organization;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class UniqueOrgCommand extends ManagerCommand {

    public UniqueOrgCommand(PrintWriter writer, CollectionManager manager) {
        super(writer, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        Set<Organization> organizations = this.manager.getUniqueOrganizations();
        for (Organization org : organizations)
            writer.println(org.toString());
        return false;
    }
}
