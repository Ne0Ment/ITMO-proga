package org.main.commands;

import org.main.CollectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AddCommand extends ModifiableCommand{
    public AddCommand(PrintWriter writer, BufferedReader reader, CollectionManager manager) {
        super(writer, reader, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        this.addWorker();
        return false;
    }
}
