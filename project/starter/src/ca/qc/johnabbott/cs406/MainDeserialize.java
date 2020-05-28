package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.collections.Either;
import ca.qc.johnabbott.cs406.collections.list.LinkedList;
import ca.qc.johnabbott.cs406.collections.map.HashMap;
import ca.qc.johnabbott.cs406.collections.set.TreeSet;
import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.io.BufferedChannel;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import ca.qc.johnabbott.cs406.serialization.util.Date;
import ca.qc.johnabbott.cs406.serialization.util.Integer;
import ca.qc.johnabbott.cs406.serialization.util.Long;
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
                ), null, true);

        // Out
        deserializeTreeSet(serializer);

        serializer.close();
    }

    private static void deserializeTuple(Serializer serializer) throws IOException, SerializationException {
        serializer.register(Integer.SERIAL_ID, Integer::new);
        serializer.register(String.SERIAL_ID, String::new);
        serializer.register(Tuple.SERIAL_ID, Tuple::new);

        Tuple<Integer, String> tuple = (Tuple) serializer.readSerializable();
        Tuple<Integer, String> tupleRef = (Tuple) serializer.readSerializable();
        tuple.setFirst(new Integer(345));
        System.out.println(tuple);
        System.out.println(tupleRef);
    }

    private static void deserializeDate(Serializer serializer) throws IOException, SerializationException {
        serializer.register(Date.SERIAL_ID, Date::new);

        Date date = (Date) serializer.readSerializable();
        System.out.println(date);
    }

    private static void deserializeIP(Serializer serializer) throws IOException, SerializationException {
        serializer.register(IPAddress.SERIAL_ID, IPAddress::new);

        IPAddress ip = (IPAddress) serializer.readSerializable();
        System.out.println(ip);
    }

    private static void deserializeGrade(Serializer serializer) throws IOException, SerializationException {
        serializer.register(Grade.SERIAL_ID, Grade::new);
        serializer.register(String.SERIAL_ID, String::new);
        serializer.register(Date.SERIAL_ID, Date::new);

        Grade grade = (Grade) serializer.readSerializable();
        System.out.println(grade);
    }

    private static void deserializeEitherLeft(Serializer serializer) throws IOException, SerializationException {
        serializer.register(Integer.SERIAL_ID, Integer::new);
        serializer.register(Either.LeftEither.SERIAL_ID, Either.LeftEither::new);

        Either<Integer, String> e = (Either.LeftEither) serializer.readSerializable();
        System.out.println(e.getLeft());
    }

    private static void deserializeEitherRight(Serializer serializer) throws IOException, SerializationException {
        serializer.register(String.SERIAL_ID, String::new);
        serializer.register(Either.RightEither.SERIAL_ID, Either.RightEither::new);

        Either<Integer, String> e = (Either.RightEither) serializer.readSerializable();
        System.out.println(e.getRight());
    }

    private static void deserializeLinkedList(Serializer serializer) throws IOException, SerializationException {
        serializer.register(LinkedList.SERIAL_ID, LinkedList::new);
        serializer.register(Integer.SERIAL_ID, Integer::new);

        LinkedList<Integer> l = (LinkedList<Integer>) serializer.readSerializable();
        System.out.println(l.toString());
    }

    private static void deserializeHashMap(Serializer serializer) throws IOException, SerializationException {
        serializer.register(HashMap.SERIAL_ID, HashMap::new);
        serializer.register(Integer.SERIAL_ID, Integer::new);
        serializer.register(String.SERIAL_ID, String::new);

        HashMap<Integer, String> hashMap = (HashMap<Integer, String>) serializer.readSerializable();
        System.out.println(hashMap.toString());
    }

    private static void deserializeTreeSet(Serializer serializer) throws IOException, SerializationException {
        serializer.register(Integer.SERIAL_ID, Integer::new);
        serializer.register(TreeSet.SERIAL_ID, TreeSet::new);

        TreeSet<Integer> treeSet = (TreeSet<Integer>) serializer.readSerializable();
        TreeSet<Integer> treeSetRef = (TreeSet<Integer>) serializer.readSerializable();
        treeSet.add(new Integer(777));
        System.out.println(treeSet.toString());
        System.out.println(treeSetRef.toString());
    }
}
