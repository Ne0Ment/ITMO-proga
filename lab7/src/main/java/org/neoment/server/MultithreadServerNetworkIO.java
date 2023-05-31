package org.neoment.server;

import org.neoment.shared.Params;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultithreadServerNetworkIO {
    public static RequestHandler requestHandler;
    public static ResponseHandler responseHandler;
    private DatagramChannel serverChannel;
    static Logger logger = Logger.getLogger(MultithreadServerNetworkIO.class.getName());
    public MultithreadServerNetworkIO(ServerCommandExecutor commandExecutor, InetSocketAddress addr) {
        try {
            this.serverChannel = DatagramChannel.open();
            this.serverChannel.socket().bind(addr);
            this.serverChannel.configureBlocking(false);


            responseHandler = new ResponseHandler(this.serverChannel);
            requestHandler = new RequestHandler(commandExecutor, responseHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void startLoop() throws IOException {
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(Params.BUFFER_SIZE);
            var sender =  (InetSocketAddress) this.serverChannel.receive(buffer);
            if (sender==null) continue;
            requestHandler.handleRequest(sender, buffer);
        }
    }
}
