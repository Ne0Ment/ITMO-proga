package org.main.commands;

import org.main.BetterBufferedWriter;
import org.main.CollectionManager;

import java.io.IOException;

public class ManagerCommand implements Command {
    public BetterBufferedWriter writer;
    public CollectionManager manager;

    public ManagerCommand(BetterBufferedWriter writer, CollectionManager manager) {
        this.writer = writer;
        this.manager = manager;
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        return true;
    }

    public Boolean enoughArgs(int minArgs, String[] args) throws IOException {
        if (args.length < minArgs) {
            this.writer.printLn("Invalid command.");
        }
        return (args.length >= minArgs);
    }
}
