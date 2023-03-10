package org.main.commands;

import org.main.CollectionManager;
import org.main.FileHandler;

import java.io.PrintWriter;

public class FileCommand extends ManagerCommand{
    public FileHandler fileHandler;

    public FileCommand(PrintWriter writer, CollectionManager manager, FileHandler fileHandler) {
        super(writer, manager);
        this.fileHandler = fileHandler;
    }
}
