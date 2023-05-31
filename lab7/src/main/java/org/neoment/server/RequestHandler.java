package org.neoment.server;

import org.neoment.shared.NetworkObjectEncoder;
import org.neoment.shared.commands.CommandType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestHandler {
    static Logger logger = Logger.getLogger(RequestHandler.class.getName());
    private final ServerCommandExecutor commandExecutor;
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(16);
    private final ResponseHandler responseHandler;

    public RequestHandler(ServerCommandExecutor commandExecutor, ResponseHandler responseHandler) {
        this.commandExecutor = commandExecutor;
        this.responseHandler = responseHandler;
    }

    public void handleRequest(InetSocketAddress clientAddress, ByteBuffer buffer) {
        this.executor.execute(() -> {
            try {
                buffer.flip();
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
                List<Object> data = new ArrayList<>(List.of(NetworkObjectEncoder.decodeObjects(ois)));
                CommandType commandType = (CommandType) data.get(0);
                data.remove(0);
                logger.log(Level.INFO, "Got request from " + clientAddress + ": " + commandType.toString());

                ServerCommandExecutor.logging = true;
                commandExecutor.run(commandType, data.toArray(), clientAddress);
            } catch (IOException e) {
                logger.log(Level.WARNING, "IO error: " + e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
            } catch (ClassNotFoundException e) {
                logger.log(Level.WARNING, "Broken commandInfo: " + e.getMessage());
            }
        });
    }
}
