package org.main.commands;

import org.main.BetterBufferedWriter;

import java.io.IOException;

public class WrongCommand implements Command {

    private final BetterBufferedWriter writer;

    public WrongCommand(BetterBufferedWriter writer) {
        this.writer = writer;
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        this.writer.printLn("Invalid command.");
        return false;
    }
}
