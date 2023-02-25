package org.main.commands;

import org.main.BetterBufferedWriter;
import org.main.CollectionManager;

import java.io.IOException;

public class InfoCommand extends ManagerCommand {
    public InfoCommand(BetterBufferedWriter writer, CollectionManager manager) {
        super(writer, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        this.writer.printLn(this.manager.getWorkersInfo());
        return false;
    }
}
