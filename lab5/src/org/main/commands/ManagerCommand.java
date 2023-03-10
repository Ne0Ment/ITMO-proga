package org.main.commands;

import org.main.CollectionManager;

import java.io.IOException;
import java.io.PrintWriter;

public class ManagerCommand implements Command {
    public PrintWriter writer;
    public CollectionManager manager;

    public ManagerCommand(PrintWriter writer, CollectionManager manager) {
        this.writer = writer;
        this.manager = manager;
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        return true;
    }

    public Boolean enoughArgs(int minArgs, String[] args) {
        if (args.length < minArgs) {
            this.writer.println("Invalid command.");
        }
        return (args.length >= minArgs);
    }
}
