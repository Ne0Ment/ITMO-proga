package org.main.commands;

import org.main.BetterBufferedWriter;
import org.main.CollectionManager;
import org.main.data.Worker;

import java.io.IOException;
import java.util.Iterator;

public class ShowCommand extends ManagerCommand{

    public ShowCommand(BetterBufferedWriter writer, CollectionManager manager) {
        super(writer, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        if (this.manager.getLength()==0) { writer.printLn("No workers."); return false; }
        for (Worker worker : this.manager.getWorkers())
            writer.printLn(worker.toString());
        return false;
    }
}
