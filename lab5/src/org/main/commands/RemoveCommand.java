package org.main.commands;

import org.main.BetterBufferedWriter;
import org.main.CollectionManager;
import org.main.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

public class RemoveCommand extends ModifiableCommand {
    public RemoveCommand(BetterBufferedWriter writer, BufferedReader reader, CollectionManager manager) {
        super(writer, reader, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        try {
            removeWorker((Long) Parser.parseString(Long.class, commandArgs[1]));
        } catch (ParseException e) {
            this.writer.printLn("Couldn't parse worker id.");
        }
        return false;
    }
}
