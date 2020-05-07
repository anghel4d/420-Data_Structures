package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import ca.qc.johnabbott.cs406.serialization.io.BufferedChannel;
import ca.qc.johnabbott.cs406.serialization.util.Integer;
import ca.qc.johnabbott.cs406.serialization.util.String;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Serialization example.
 */
public class MainSerialize {


    public static void main(java.lang.String arg[]) throws IOException, SerializationException {

        BufferedChannel channel = new BufferedChannel(new RandomAccessFile("foo.bin", "rw").getChannel(), BufferedChannel.Mode.WRITE);

        Serializer serializer = new Serializer(null, channel);

        serializer.register(Integer.SERIAL_ID, Integer::new);
        serializer.register(String.SERIAL_ID, String::new);
        serializer.register(Box.SERIAL_ID, Box::new);

        Integer i = new Integer(123);
        String s = new String("hello,");
        Box<String> bs = new Box<>(new String("world."));

        serializer.write(i);
        serializer.write(s);
        serializer.write(bs);

        channel.close();

    }
}
