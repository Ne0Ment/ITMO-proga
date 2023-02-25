package org.main.commands;

import org.main.BetterBufferedWriter;
import org.main.CollectionManager;

import java.io.IOException;

public class ClearCommand extends ManagerCommand{

    public ClearCommand(BetterBufferedWriter writer, CollectionManager manager) {
        super(writer, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        this.manager.clear();
        return false;
    }
}
