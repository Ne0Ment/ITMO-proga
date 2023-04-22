package org.neoment;

import org.neoment.server.*;
import org.neoment.shared.Params;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
    static Logger logger = Logger.getLogger(ServerMain.class.getName());

    public static void main(String[] args) {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var writer = new PrintWriter(new OutputStreamWriter(System.out), true);
        var fileHandler = new FileHandler(args.length > 0 ? args[0] : null, writer);

        var collectionManager = fileHandler.loadFromXml();

        var systemIOHandler = new ServerSystemIOHandler(reader, writer, fileHandler, collectionManager);
        systemIOHandler.startLoop();

        try {
            var commandExecutor = new ServerCommandExecutor(collectionManager, fileHandler);
            var serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), Params.SERVER_PORT);
            var serverNetworkIO = new ServerNetworkIO(commandExecutor, serverAddress);
            logger.log( Level.INFO, "Started server at " + serverAddress.getAddress() + ":" + serverAddress.getPort());
            serverNetworkIO.startLoop();
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "Server failed to start due to a network issue: " + e.getMessage());
        }
    }
}