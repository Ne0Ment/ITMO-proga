package org.neoment.client;

import org.neoment.shared.NetworkObjectEncoder;
import org.neoment.shared.Params;

import java.io.IOException;
import java.net.*;

public class ClientNetworkIO implements ClientNetworkIOInterface {
    private DatagramSocket socket;
    private final InetSocketAddress serverAddress;

    private final byte[] buffer;
    private static int defaultTimeOut = 3000;

    public ClientNetworkIO(InetSocketAddress serverAddress) {
        this.serverAddress = serverAddress;
        buffer = new byte[Params.BUFFER_SIZE];
        try { socket = new DatagramSocket(); socket.setSoTimeout(defaultTimeOut);
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public void clearReceivingObjects() {
        try {
            socket.setSoTimeout(50);
            while (true) {
                this.receivePacket();
            }
        } catch (Exception ignored) {}
        try {socket.setSoTimeout(3000);} catch (Exception ignored) {}

    }

    private DatagramPacket sendPacket(byte[] sendData) throws IOException, SocketTimeoutException {
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, serverAddress.getAddress(), serverAddress.getPort());
        socket.send(packet);
        return receivePacket();
    }

    private void sendOneWayPacket(byte[] sendData) throws IOException {
        var packet = new DatagramPacket(sendData, sendData.length, serverAddress.getAddress(), serverAddress.getPort());
        socket.send(packet);
    }

    private DatagramPacket receivePacket() throws IOException, SocketTimeoutException {
        var packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return packet;
    }

    public Object[] sendObjects(Object... objects) throws IOException, SocketTimeoutException, ClassNotFoundException {
        return NetworkObjectEncoder.decodeObjects(this.sendPacket(NetworkObjectEncoder.encodeObjects(objects)).getData());
    }

    public void sendOneWayObjects(Object... objects) throws IOException {
        this.sendOneWayPacket(NetworkObjectEncoder.encodeObjects(objects));
    }

    public Object[] receiveObjects() throws IOException, SocketTimeoutException, ClassNotFoundException {
        return NetworkObjectEncoder.decodeObjects(this.receivePacket().getData());
    }

    public static InetSocketAddress getServerAddr() {
        try {
            var local = InetAddress.getLocalHost();
            return new InetSocketAddress(local, Params.SERVER_PORT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
