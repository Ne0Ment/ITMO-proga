package org.main.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.main.CollectionManager;
import org.main.FileHandler;

import java.io.IOException;
import java.io.PrintWriter;

public class SaveCommand extends FileCommand {

    public SaveCommand(PrintWriter writer, CollectionManager manager, FileHandler fileHandler) {
        super(writer, manager, fileHandler);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        try {
            this.fileHandler.saveToXml(this.manager);
        } catch (JsonProcessingException e) {
            writer.println("Failed to serialize xml. " + e.getMessage());
        } catch (Exception e) {
            writer.println(e.getMessage());
        }
        return false;
    }
}
