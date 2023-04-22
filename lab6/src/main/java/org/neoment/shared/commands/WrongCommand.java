package org.neoment.shared.commands;

import java.io.ObjectInputStream;
import java.io.PrintWriter;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class WrongCommand implements Command {
    private PrintWriter writer;

    public WrongCommand(PrintWriter writer) {
        this.writer = writer;
    }

    public WrongCommand() {}

    @Override
    public boolean clientExecute(String[] commandArgs) {
        this.writer.println("Invalid command.");
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) {
        return wrap("");
    }
}