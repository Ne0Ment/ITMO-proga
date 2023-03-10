package org.main.commands;

import org.main.CollectionManager;
import org.main.data.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AddIfMaxCommand extends ModifiableCommand{

    public AddIfMaxCommand(PrintWriter writer, BufferedReader reader, CollectionManager manager) {
        super(writer, reader, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        Worker worker = this.constructWorker();
        Worker maxWorker = this.manager.getWorkers().last();
        if (worker.compareTo(maxWorker) > 0) {
            this.manager.add(worker);
        }
        else {
            writer.println("Worker isn't max.");
        }
        return false;
    }
}
