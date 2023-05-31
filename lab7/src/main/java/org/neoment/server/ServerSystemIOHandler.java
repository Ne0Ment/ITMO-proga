package org.neoment.server;

import org.neoment.server.servercommands.ServerCommand;
import org.neoment.server.servercommands.ServerExitCommand;
import org.neoment.server.servercommands.ServerInvalidCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for IO operations and input loop.
 * @author neoment
 * @version 0.1
 */
public class ServerSystemIOHandler {
    static Logger logger = Logger.getLogger(ServerSystemIOHandler.class.getName());
    private final BufferedReader reader;
    private final PrintWriter writer;

    private CollectionManager manager;
    private Map<String, ServerCommand> serverCommandMap;
    public ServerSystemIOHandler(BufferedReader reader, PrintWriter writer, CollectionManager manager) {
        this.reader = reader;
        this.writer = writer;
        this.manager = manager;

        this.serverCommandMap = new HashMap<>();
        this.serverCommandMap.put("exit", new ServerExitCommand());
    }

    public void exitMessage(){
        this.writer.println("Shutting down");
    }

    public void startMessage() {
        this.writer.println("Starting...");
    }

    public void startLoop() {
        Thread inputThread = new Thread(() -> {
            this.startMessage();
            String input;
            try {
                while ((input = this.reader.readLine()) != null) {
                    var inArgs = input.split("\\s+");
                    if (inArgs.length==0) continue;

                    boolean ex = this.serverCommandMap.getOrDefault(inArgs[0], new ServerInvalidCommand(this.writer)).execute();
                    if (ex) {
                        this.exitMessage();
                        exit();
                    }

                }
            } catch (IOException e) {
                logger.log(Level.WARNING , e.getMessage());
            }
            exitMessage();
            exit();
        });
        inputThread.start();
    }

    public static void exit() {
        System.exit(0);
    }

}
