package org.main;

import java.io.*;
import java.util.List;

/**
 * Initiates IO components, loads data, and starts input polling.
 * @author neoment
 * @version 0.1
 */
public class Main {
    public static boolean logs = false;

    public static void main(String[] args) {
        if (List.of(args).contains("--logs")) logs=true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BetterBufferedWriter writer = new BetterBufferedWriter(new OutputStreamWriter(System.out));
        FileHandler fileHandler = new FileHandler(args.length > 0 ? args[0] : null);

        CollectionManager manager = new CollectionManager();
        try {
            manager = fileHandler.readFromXml();
            try { writer.printLn("Loaded XML."); } catch (IOException ignored) {};
        } catch (Exception e) {
            try { writer.printLn("Unable to parse XML." + (logs ? e.getMessage() : "")); } catch (IOException ignored) {};
        }

        CommandExecutor commandExecutor = new CommandExecutor(reader, writer, manager, fileHandler);
        IOHandler iOHandler = new IOHandler(reader, writer, commandExecutor, false);

        Runtime.getRuntime().addShutdownHook(new Thread(iOHandler::exitMessage));

        try {
            iOHandler.start();
        } catch (IOException e) {
            try { writer.printLn("IO interrupted."); } catch (IOException ignored) {}
        }
    }

    public static void exit() {
        System.exit(0);
    }
}