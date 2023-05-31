package org.neoment.client;

import java.io.IOException;
import java.net.SocketTimeoutException;

public interface ClientNetworkIOInterface {
    Object[] sendObjects(Object... objects) throws IOException, SocketTimeoutException, ClassNotFoundException;
    void sendOneWayObjects(Object... objects) throws IOException;
    Object[] receiveObjects() throws IOException, SocketTimeoutException, ClassNotFoundException;

    void clearReceivingObjects();
}
