package org.main.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.main.BetterBufferedWriter;
import org.main.CollectionManager;
import org.main.FileHandler;

import java.io.IOException;

public class SaveCommand extends FileCommand {

    public SaveCommand(BetterBufferedWriter writer, CollectionManager manager, FileHandler fileHandler) {
        super(writer, manager, fileHandler);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        try {
            this.fileHandler.saveToXml(this.manager);
        } catch (JsonProcessingException e) {
            writer.printLn("Failed to serialize xml. " + e.getMessage());
        } catch (Exception e) {
            writer.printLn(e.getMessage());
        }
        return false;
    }
}
