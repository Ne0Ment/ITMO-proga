package org.neoment.server;

import org.neoment.shared.Params;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerNetworkIO {
    private Selector selector;
    private RequestHandler requestHandler;
    private ResponseHandler responseHandler;
    private DatagramChannel serverChannel;
    static Logger logger = Logger.getLogger(ServerNetworkIO.class.getName());

    private ServerCommandExecutor commandExecutor;
    public ServerNetworkIO(ServerCommandExecutor commandExecutor, InetSocketAddress addr) {
        try {
            this.commandExecutor = commandExecutor;

            this.selector = Selector.open();
            this.serverChannel = DatagramChannel.open();
            this.serverChannel.socket().bind(addr);
            this.serverChannel.configureBlocking(false);
            this.serverChannel.register(selector, SelectionKey.OP_READ);

            this.requestHandler = new RequestHandler(commandExecutor);
            this.responseHandler = new ResponseHandler();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void startLoop() {
        while (true) {
            Select();

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (key.channel() == serverChannel)
                    HandleUDP(key);
            }
        }
    }

    private void HandleUDP(SelectionKey key) {
        try {
            if (key.isReadable()) requestHandler.HandleRequest(key);
            if (key.isWritable()) responseHandler.HandleResponse(key);
        } catch (IOException e) { System.out.println(e.getMessage()); }
    }

    private void Select() {
        try {
            selector.select();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
