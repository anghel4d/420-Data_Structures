package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.collections.Either;
import ca.qc.johnabbott.cs406.collections.list.LinkedList;
import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import ca.qc.johnabbott.cs406.serialization.io.BufferedChannel;
import ca.qc.johnabbott.cs406.serialization.util.Date;
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

        // In
        serializeLinkedList(serializer);

        channel.close();
    }

    private static void serializeTuple(Serializer serializer) throws IOException, SerializationException {
        Tuple<Integer, String> tuple = new Tuple<>(new Integer(123), new String("ABC"));
        System.out.println(tuple);
        serializer.write(tuple);
    }

    private static void serializeDate(Serializer serializer) throws IOException, SerializationException {
        Date date = new Date(new java.util.Date());
        System.out.println(date);
        serializer.write(date);
    }

    private static void serializeIP(Serializer serializer) throws IOException, SerializationException {
        IPAddress ip = new IPAddress("12.34.56.78");
        System.out.println(ip);
        serializer.write(ip);
    }

    private static void serializeGrade(Serializer serializer) throws IOException, SerializationException {
        java.util.Date date = new java.util.Date();
        Grade grade = new Grade("Ian", 100, date);
        System.out.println(grade);
        serializer.write(grade);
    }

    private static void serializeEitherLeft(Serializer serializer) throws IOException, SerializationException {
        Either<Integer, String> e = Either.left(new Integer(123));
        System.out.println(e.getLeft());
        serializer.write((Serializable) e);
    }

    private static void serializeEitherRight(Serializer serializer) throws IOException, SerializationException {
        Either<Integer, String> e = Either.right(new String("ABC"));
        System.out.println(e.getRight());
        serializer.write((Serializable) e);
    }

    private static void serializeLinkedList(Serializer serializer) throws IOException, SerializationException {
        LinkedList<Integer> l = new LinkedList<>();
        l.add(new Integer(1));
        l.add(new Integer(2));
        l.add(new Integer(3));
        System.out.println(l.toString());
        serializer.write(l);
    }
}
