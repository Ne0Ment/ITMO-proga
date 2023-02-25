package org.main.commands;

import org.main.CollectionManager;

import java.io.IOException;

public interface Command {
    boolean execute(String[] commandArgs) throws IOException;
}