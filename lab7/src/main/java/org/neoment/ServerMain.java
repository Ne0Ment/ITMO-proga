package org.neoment;

import org.neoment.server.*;
import org.neoment.shared.Params;

import java.io.*;
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
        try {
            var propertiesManager = new PropertiesManager("settings.properties");
            var dbManager = new DBManager(propertiesManager.getUser(), propertiesManager.getPass(), propertiesManager.getUri());
            var collectionManager = dbManager.loadCollection();
            var systemIOHandler = new ServerSystemIOHandler(reader, writer, collectionManager);
            systemIOHandler.startLoop();

            var commandExecutor = new ServerCommandExecutor(collectionManager);
            var serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), Params.SERVER_PORT);
            var serverNetworkIO = new MultithreadServerNetworkIO(commandExecutor, serverAddress);
            logger.log( Level.INFO, "Started server at " + serverAddress.getAddress() + ":" + serverAddress.getPort());
            serverNetworkIO.startLoop();
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "Server failed to start due to a network issue: " + e.getMessage());
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Error while loading properties: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Network error:" + e.getMessage());
        }
    }
}