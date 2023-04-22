package org.neoment;

import org.neoment.client.*;
import org.neoment.shared.Worker;

import java.io.*;
import java.net.InetSocketAddress;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var writer = new PrintWriter(new OutputStreamWriter(System.out), true);


        var serverAddress = AddressParser.parseAddress(writer, args);
        var clientNetworkIO = new ClientNetworkIO(serverAddress);

        var executor = new ClientCommandExecutor(reader, writer, clientNetworkIO);
        var ioHandler = new ClientIOHandler(reader, writer, executor, false);

        Runtime.getRuntime().addShutdownHook(new Thread(ioHandler::exitMessage));

        ioHandler.start();
//        var a = new Worker();
//        a.setId(1L);
//        var b = new Worker();
//        b.setId(2L);
//        System.out.println(a.equals(b));
    }
}