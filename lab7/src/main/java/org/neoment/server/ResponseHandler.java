package org.neoment.server;

import org.neoment.shared.NetworkObjectEncoder;
import org.neoment.shared.Params;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseHandler {
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(16);
    static Logger logger = Logger.getLogger(ResponseHandler.class.getName());

    private final DatagramChannel datagramChannel;

    public ResponseHandler(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public void handleResponse(ResponseAttachment response) {
        executor.execute(() -> {
            try {
                for (var sendData : response.data) {
                    ByteBuffer buffer = ByteBuffer.allocate(Params.BUFFER_SIZE);
                    buffer.put(sendData);
                    buffer.flip();
                    this.datagramChannel.send(buffer, response.addr);
                    Thread.sleep(5);
                }
                logger.log(Level.INFO, "Responded to " + response.addr);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "interrupted while sending packets");
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    public void handleObjectResponse(Object[][] responsesObjects, InetSocketAddress clientAddress) throws IOException {
        ResponseAttachment response = new ResponseAttachment(clientAddress);
        for (Object[] responseObjects : responsesObjects) {
            byte[] returnData = NetworkObjectEncoder.encodeObjects(responseObjects);
            response.data.add(returnData);
        }
        handleResponse(response);
    }
}
