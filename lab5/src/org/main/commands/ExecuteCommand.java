package org.main.commands;

import org.main.*;

import java.io.*;

public class ExecuteCommand extends FileCommand {
    private RecursionChecker recursionChecker;
    public ExecuteCommand(PrintWriter writer, CollectionManager manager, FileHandler fileHandler, RecursionChecker recursionChecker) {
        super(writer, manager, fileHandler);
        this.recursionChecker = recursionChecker;
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        String runFilePath = commandArgs[1];
        try {
            if (!recursionChecker.startFile(runFilePath)) return false;
            BufferedReader localReader = new BufferedReader(new InputStreamReader(new FileInputStream(runFilePath)));
            CommandExecutor localExecutor = new CommandExecutor(localReader, this.writer, this.manager, this.fileHandler, this.recursionChecker);
            IOHandler localHandler = new IOHandler(localReader, this.writer, localExecutor, true);
            localHandler.start();
            recursionChecker.endFile(runFilePath);
        } catch (FileNotFoundException e) {
            writer.println("Script not found.");
        } catch (SecurityException e) {
            writer.println("Script not accessible.");
        } catch (Exception e) {
            writer.println(e.getMessage());
        }
        return false;
    }
}
