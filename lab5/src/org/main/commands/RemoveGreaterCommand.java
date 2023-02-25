package org.main.commands;

import org.main.BetterBufferedWriter;
import org.main.CollectionManager;
import org.main.Parser;
import org.main.data.Worker;

import java.io.BufferedReader;
import java.io.IOException;

import static org.main.Main.logs;

public class RemoveGreaterCommand extends ModifiableCommand{

    public RemoveGreaterCommand(BetterBufferedWriter writer, BufferedReader reader, CollectionManager manager) {
        super(writer, reader, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        try {
            Long maxId = (Long) Parser.parseString(Long.class, commandArgs[1]);
            this.manager.filterWorkers((Worker worker) -> worker.getId() <= maxId);
        } catch (Exception e) {
            this.writer.printLn("Wrong id format. " + (logs ? e : ""));
        }
        return false;
    }
}
