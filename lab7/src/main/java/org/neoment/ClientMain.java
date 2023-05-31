package org.neoment;

import org.neoment.client.*;

import java.io.*;

public class ClientMain {
    public static void main(String[] args) {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var writer = new PrintWriter(new OutputStreamWriter(System.out), true);

        var serverAddress = AddressParser.parseAddress(writer, args);
        var clientNetworkIO = new ClientNetworkIO(serverAddress);

        var executor = new ClientCommandExecutor(reader, writer, clientNetworkIO);
        var ioHandler = new ClientIOHandler(reader, writer, executor, false);

        Runtime.getRuntime().addShutdownHook(new Thread(ioHandler::exitMessage));

        ioHandler.start();
    }
}