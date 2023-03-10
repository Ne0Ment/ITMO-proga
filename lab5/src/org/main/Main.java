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
        if (List.of(args).contains("--logs")) logs=true; //print more exception info

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);
        FileHandler fileHandler = new FileHandler(args.length > 0 ? args[0] : null, writer);

        CollectionManager manager = fileHandler.loadFromXml();

        CommandExecutor commandExecutor = new CommandExecutor(reader, writer, manager, fileHandler, new RecursionChecker());
        IOHandler iOHandler = new IOHandler(reader, writer, commandExecutor, false);

        Runtime.getRuntime().addShutdownHook(new Thread(iOHandler::exitMessage));
        iOHandler.start();
    }

    public static void exit() {
        System.exit(0);
    }
}