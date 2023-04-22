package org.neoment.client;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressParser {
    public static InetSocketAddress parseAddress(PrintWriter writer, String[] args) {
        if (args.length<1) {
            writer.println("Using localhost with default port.");
            return ClientNetworkIO.getServerAddr();
        }
        Pattern p = Pattern.compile("^\\s*(.*?):(\\d+)\\s*$");
        Matcher m = p.matcher(args[0]);
        if (m.matches()) {
            String host = m.group(1);
            int port = Integer.parseInt(m.group(2));
            return new InetSocketAddress(host, port);
        } else {
            writer.println("Inputted address is invalid, using localhost with default port.");
            return ClientNetworkIO.getServerAddr();
        }
    }
}
