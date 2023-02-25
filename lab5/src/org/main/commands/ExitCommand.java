package org.main.commands;

import java.io.IOException;

public class ExitCommand implements Command{
    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        return true;
    }
}
