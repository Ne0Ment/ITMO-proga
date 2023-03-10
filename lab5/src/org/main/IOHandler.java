package org.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.main.Main.exit;

/**
 * Responsible for IO operations and input loop.
 * @author neoment
 * @version 0.1
 */
public class IOHandler {
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final CommandExecutor executor;
    private final Boolean isLocal;

    public IOHandler(BufferedReader reader, PrintWriter writer, CommandExecutor executor, Boolean isLocal) {
        this.reader = reader;
        this.writer = writer;
        this.executor = executor;
        this.isLocal = isLocal;
    }

    public void prompt() {
        this.writer.print(!isLocal ? "> " : "");
        this.writer.flush();
    }

    public void exitMessage(){
        this.writer.println(!isLocal ? "Shutting down" : "");
    }

    public void startMessage() {
        this.writer.println(!isLocal ? "Starting..." : "");
    }

    public void start() {
        String line;
        this.startMessage();
        this.prompt();
        try {
            while ((line = this.reader.readLine()) != null) {
                Boolean shallExit = executor.run(line);
                if (!shallExit) {
                    this.prompt();
                } else {
                    exit();
                }
            }
        } catch (IOException e) {
            this.writer.println("IO interrupted.");
        }

    }

}
