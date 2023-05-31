package org.neoment.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ResponseAttachment {
    public InetSocketAddress addr;
    public List<byte[]> data;

    public ResponseAttachment(InetSocketAddress addr, List<byte[]> data) {
        this.addr = addr;
        this.data = data;
    }

    public ResponseAttachment(InetSocketAddress addr) {
        this.addr = addr;
        this.data = new ArrayList<>();
    }
}
