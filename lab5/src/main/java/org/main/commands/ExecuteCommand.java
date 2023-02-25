package org.main.commands;

import org.main.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteCommand extends FileCommand {
    public ExecuteCommand(BetterBufferedWriter writer, CollectionManager manager, FileHandler fileHandler) {
        super(writer, manager, fileHandler);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        try {
            BufferedReader localReader = new BufferedReader(new InputStreamReader(new FileInputStream(commandArgs[1])));
            CommandExecutor localExecutor = new CommandExecutor(localReader, this.writer, this.manager, this.fileHandler);
            IOHandler localHandler = new IOHandler(localReader, this.writer, localExecutor, true);
            localHandler.start();
        } catch (Exception e) {
            writer.printLn(e.getMessage());
        }
        return false;
    }
}
