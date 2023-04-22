package org.neoment.server;

import org.neoment.shared.NetworkObjectEncoder;
import org.neoment.shared.PacketData;
import org.neoment.shared.Params;
import org.neoment.shared.commands.CommandType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestHandler {
    static Logger logger = Logger.getLogger(RequestHandler.class.getName());
    private final ServerCommandExecutor commandExecutor;

    public RequestHandler(ServerCommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }
    public void HandleRequest(SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Params.BUFFER_SIZE);
        DatagramChannel datagramChannel = (DatagramChannel) key.channel();
        InetSocketAddress clientAddress = (InetSocketAddress) datagramChannel.receive(buffer);
        buffer.flip();
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            List<Object> data = new ArrayList<>(List.of(NetworkObjectEncoder.decodeObjects(ois)));
            CommandType commandType = (CommandType) data.get(0);
            data.remove(0);
            logger.log(Level.INFO, "Got request from " + clientAddress + ": " + commandType.toString());

            ResponseAttachment response = new ResponseAttachment(clientAddress);
            ServerCommandExecutor.logging = true;
            Object[][] responsesObjects = commandExecutor.run(commandType, data.toArray());
            for (Object[] responseObjects : responsesObjects) {
                byte[] returnData = NetworkObjectEncoder.encodeObjects(responseObjects);
                response.data.add(returnData);
            }

            key.attach(response);
            key.interestOps(SelectionKey.OP_WRITE);
        } catch (ClassNotFoundException | InvalidClassException e) {
            logger.log(Level.WARNING,  "Broken commandInfo: " + e.getMessage());
        }
    }
}
