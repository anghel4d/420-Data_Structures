package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;

import java.io.IOException;

public class Tuple<S extends Serializable, T extends Serializable> implements Serializable {

    public static final byte SERIAL_ID = 0x21;
    private S first;
    private T second;

    public Tuple() {
    }

    public Tuple(S first, T second) {
        this.first = first;
        this.second = second;
    }

    public S getFirst(){
        return first;
    }

    public void setFirst(S first){
        this.first = first;
    }

    public T getSecond(){
        return second;
    }

    public void setSecond(T second){
        this.second = second;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(first);
        serializer.write(second);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        first = (S) serializer.readSerializable();
        second = (T) serializer.readSerializable();
    }
}
