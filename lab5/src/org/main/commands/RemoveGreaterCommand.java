package org.main.commands;

import org.main.CollectionManager;
import org.main.Parser;
import org.main.data.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.main.Main.logs;

public class RemoveGreaterCommand extends ModifiableCommand{

    public RemoveGreaterCommand(PrintWriter writer, BufferedReader reader, CollectionManager manager) {
        super(writer, reader, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        try {
            Float maxSalary = (Float) Parser.parseString(Float.class, commandArgs[1]);
            this.manager.filterWorkers((Worker worker) -> worker.getSalary() <= maxSalary);
        } catch (Exception e) {
            this.writer.println("Wrong salary format. " + (logs ? e : ""));
        }
        return false;
    }
}
