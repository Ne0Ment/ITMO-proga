package org.main;

import java.io.BufferedReader;
import java.io.IOException;

import static org.main.Main.exit;

/**
 * Responsible for IO operations and input loop.
 * @author neoment
 * @version 0.1
 */
public class IOHandler {
    private final BufferedReader reader;
    private final BetterBufferedWriter writer;
    private final CommandExecutor executor;
    private final Boolean isLocal;

    public IOHandler(BufferedReader reader, BetterBufferedWriter writer, CommandExecutor executor, Boolean isLocal) {
        this.reader = reader;
        this.writer = writer;
        this.executor = executor;
        this.isLocal = isLocal;
    }

    public void prompt() throws IOException {
        this.writer.print(!isLocal ? "> " : "");
    }

    public void exitMessage(){
        try {
            this.writer.printLn(!isLocal ? "Shutting down" : "");
        } catch (IOException ignored) {}

    }

    public void startMessage() throws IOException {
        this.writer.printLn(!isLocal ? "Starting..." : "");
    }

    public void start() throws IOException{
        String line;
        this.startMessage();
        this.prompt();
        while ((line = this.reader.readLine()) != null) {
            Boolean shallExit = executor.run(line);
            if (!shallExit) {
                this.prompt();
            } else {
                exit();
            }
        }
    }

}
