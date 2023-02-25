package org.main.commands;

import org.main.BetterBufferedWriter;
import org.main.CollectionManager;

import java.io.BufferedReader;
import java.io.IOException;

public class AddIfMinCommand extends ModifiableCommand {

    public AddIfMinCommand(BetterBufferedWriter writer, BufferedReader reader, CollectionManager manager) {
        super(writer, reader, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        if (this.manager.getLength()==0) {
            this.addWorker();
        } else {
            writer.printLn("New workers always have a higher id.");
        }
        return false;
    }
}
