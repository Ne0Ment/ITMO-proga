package org.neoment.server;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.shared.commands.CommandType;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ClientNetworkIOEmulator implements ClientNetworkIOInterface {
    private Object[] request;
    private Object[][] response;

    private final ServerCommandExecutor serverCommandExecutor;

    public ClientNetworkIOEmulator(ServerCommandExecutor serverCommandExecutor) {
        this.serverCommandExecutor = serverCommandExecutor;
    }

    @Override
    public void clearReceivingObjects() {
        this.response = new Object[][] {};
    }


    @Override
    public Object[] sendObjects(Object... objects) throws IOException, SocketTimeoutException, ClassNotFoundException {
        this.request = objects;
        this.runRequest();
        return this.popResponse();
    }

    @Override
    public void sendOneWayObjects(Object... objects) throws IOException {
        this.request = objects;
        this.runRequest();
    }

    @Override
    public Object[] receiveObjects() throws IOException, SocketTimeoutException, ClassNotFoundException {
        return this.popResponse();
    }

    private void runRequest() throws IOException {
        var requestList = new ArrayList<>(List.of(this.request));
        var commandType = (CommandType) requestList.get(0);
        requestList.remove(0);
        ServerCommandExecutor.logging = false;
        this.response = this.serverCommandExecutor.run(commandType, requestList.toArray());
    }

    private Object[] popResponse() {
        var top = this.response[0];
        var a = new ArrayList<>(List.of(this.response));
        a.remove(0);
        this.response = a.toArray(Object[][]::new);
        return top;
    }
}
