package org.main.commands;

import org.main.CollectionManager;
import org.main.data.Worker;

import java.io.IOException;
import java.io.PrintWriter;

public class ShowCommand extends ManagerCommand{

    public ShowCommand(PrintWriter writer, CollectionManager manager) {
        super(writer, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) {
        if (this.manager.getLength()==0) { writer.println("No workers."); return false; }
        for (Worker worker : this.manager.getWorkers())
            writer.println(worker.toString());
        return false;
    }
}
