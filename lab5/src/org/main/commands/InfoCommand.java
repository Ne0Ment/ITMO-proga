package org.main.commands;

import org.main.CollectionManager;

import java.io.PrintWriter;

public class InfoCommand extends ManagerCommand {
    public InfoCommand(PrintWriter writer, CollectionManager manager) {
        super(writer, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) {
        this.writer.println(this.manager.getWorkersInfo());
        return false;
    }
}
