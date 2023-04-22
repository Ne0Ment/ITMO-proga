package org.neoment.shared.commands;

import java.io.IOException;

public interface Command {
    boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException;
    Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException;
}
