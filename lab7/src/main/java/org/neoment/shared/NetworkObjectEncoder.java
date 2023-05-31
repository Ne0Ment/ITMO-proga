package org.neoment.shared;

import org.neoment.shared.commands.CommandType;

import java.io.*;

public class NetworkObjectEncoder {

    public static Object DEFAULT = new Object();
    public static byte[] encodeObjects(Object... objects) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(Params.BUFFER_SIZE);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(objects.length);
        for (var obj : objects)
            oos.writeObject(obj);

        return baos.toByteArray();
    }

    public static Object[] decodeObjects(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        int objCount = (int) ois.readObject();

        Object[] objects = new Object[objCount];
        for (int i=0; i<objCount; i++){
            objects[i] = ois.readObject();
        }
        return objects;
    }

    public static Object[] decodeObjects(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return NetworkObjectEncoder.decodeObjects(ois);
    }
}
