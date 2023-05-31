package org.neoment.shared.commands;

import org.neoment.shared.exceptions.NotLoggedInException;

import java.io.IOException;
import java.sql.SQLException;

public interface Command {
    boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException;
    Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException, SQLException;
}
