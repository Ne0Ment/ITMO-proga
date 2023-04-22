package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIO;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.NetworkObjectEncoder;

import java.io.*;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class InfoCommand extends ManagerCommand {

    public InfoCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    public InfoCommand(CollectionManager manager) { super(manager); }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        var response = this.clientIO.sendObjects(CommandType.INFO);
        String infoText = (String) response[0];

        this.writer.println(infoText);
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException {
        return wrap(this.manager.getWorkersInfo());
    }
}
