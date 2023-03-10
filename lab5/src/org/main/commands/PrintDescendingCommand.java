package org.main.commands;

import org.main.CollectionManager;
import org.main.data.Worker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class PrintDescendingCommand extends ManagerCommand{

    public PrintDescendingCommand(PrintWriter writer, CollectionManager manager) {
        super(writer, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        ArrayList<Worker> workers = this.manager.getWorkerList();
        Collections.reverse(workers);
        for (Worker w : workers) writer.println(w.toString());
        return false;
    }
}
