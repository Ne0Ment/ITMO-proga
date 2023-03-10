package org.main.commands;

import java.io.PrintWriter;

public class WrongCommand implements Command {

    private final PrintWriter writer;

    public WrongCommand(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public boolean execute(String[] commandArgs) {
        this.writer.println("Invalid command.");
        return false;
    }
}
