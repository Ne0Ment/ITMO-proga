package org.main.commands;

import org.main.CollectionManager;
import org.main.data.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AddIfMinCommand extends ModifiableCommand {

    public AddIfMinCommand(PrintWriter writer, BufferedReader reader, CollectionManager manager) {
        super(writer, reader, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        Worker worker = this.constructWorker();
        Worker minWorker = this.manager.getWorkers().first();
        if (worker.compareTo(minWorker) < 0) {
            this.manager.add(worker);
        }
        else {
            writer.println("Worker isn't min.");
        }
        return false;
    }
}