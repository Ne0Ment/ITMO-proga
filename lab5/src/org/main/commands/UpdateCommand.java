package org.main.commands;

import org.main.CollectionManager;
import org.main.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

public class UpdateCommand extends ModifiableCommand {

    public UpdateCommand(PrintWriter writer, BufferedReader reader, CollectionManager manager) {
        super(writer, reader, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        if (!this.enoughArgs(3, commandArgs)) return false;
        try {
            Long id = (Long) Parser.parseString(Long.class, commandArgs[2]);
            Boolean removed = this.removeWorker(id);
            if (!removed) return false;
            this.addWorker(id);
        } catch (ParseException e) {
            this.writer.println("Couldn't parse worker id.");
        }
        return false;
    }
}
