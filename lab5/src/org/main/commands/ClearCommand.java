package org.main.commands;

import org.main.CollectionManager;

import java.io.IOException;
import java.io.PrintWriter;

public class ClearCommand extends ManagerCommand{

    public ClearCommand(PrintWriter writer, CollectionManager manager) {
        super(writer, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        this.manager.clear();
        return false;
    }
}
