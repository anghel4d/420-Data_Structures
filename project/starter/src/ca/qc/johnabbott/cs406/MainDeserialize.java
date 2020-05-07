package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.serialization.io.BufferedChannel;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import ca.qc.johnabbott.cs406.serialization.util.Integer;
import ca.qc.johnabbott.cs406.serialization.util.String;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Deserialize example.
 */
public class MainDeserialize {
    public static void main(java.lang.String arg[]) throws IOException, SerializationException {


        Serializer serializer = new Serializer(
                new BufferedChannel(
                        new RandomAccessFile("foo.bin", "rw").getChannel()
                        , BufferedChannel.Mode.READ
                ), null);

        serializer.register(Integer.SERIAL_ID, Integer::new);
        serializer.register(String.SERIAL_ID, String::new);
        serializer.register(Box.SERIAL_ID, Box::new);

        Integer i = (Integer) serializer.readSerializable();
        String s = (String) serializer.readSerializable();
        Box<String> bs = (Box<String>) serializer.readSerializable();

        System.out.println(i);
        System.out.println(s);
        System.out.println(bs);

        serializer.close();

    }
}
