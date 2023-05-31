package org.neoment.shared;

import org.neoment.shared.commands.CommandType;

public class PacketData {
    public CommandType commandType;
    public Object[] data;
    private Class<?>[] dataFormat;

    public PacketData(CommandType commandType, Object[] data, Class<?>[] dataFormat) {
        this.commandType = commandType;
        this.data = data;
        this.dataFormat = dataFormat;
    }

//    public byte[] encode() {
//
//    }
//
//    public static PacketData decode(byte[] data) {
//
//    }


}