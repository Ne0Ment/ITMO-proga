package org.neoment.server;

import org.neoment.ServerMain;
import org.neoment.shared.Params;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseHandler {
    static Logger logger = Logger.getLogger(ResponseHandler.class.getName());
    public void HandleResponse(SelectionKey key) throws IOException {
        ResponseAttachment response = (ResponseAttachment) key.attachment();
        DatagramChannel datagramChannel = (DatagramChannel) key.channel();

        try {
            for (var sendData : response.data) {
                ByteBuffer buffer = ByteBuffer.allocate(Params.BUFFER_SIZE);
                buffer.put(sendData);
                buffer.flip();
                datagramChannel.send(buffer, response.addr);
                Thread.sleep(5);
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "interrupted while sending packets");
        }


        key.interestOps(SelectionKey.OP_READ);
        logger.log(Level.INFO, "Responded to " + response.addr);
    }
}
