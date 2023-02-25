package org.main.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.main.BetterBufferedWriter;
import org.main.CollectionManager;
import org.main.FileHandler;

import java.io.IOException;

public class FileCommand extends ManagerCommand{
    public FileHandler fileHandler;

    public FileCommand(BetterBufferedWriter writer, CollectionManager manager, FileHandler fileHandler) {
        super(writer, manager);
        this.fileHandler = fileHandler;
    }
}
