package ca.qc.johnabbott.cs406.serialization.util;


import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;

import java.io.IOException;

/**
 * A Serializable wrapper class for java.util.Date
 */
public class Date  implements Serializable {

    public static final byte SERIAL_ID = 0x09;
    private java.util.Date value;

    // Empty constructor necessary for use with the serializer.
    public Date() {
    }

    public Date(java.util.Date date){
        this.value = date;
    }

    public java.util.Date get(){
        return value;
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    /***
     * Serializing the `long` representation of the current time.
     * @param serializer serializer
     * @throws IOException
     */
    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(value.getTime());
    }

    /***
     * Deserializing the Date by reading the long value representation and creating a new instance.
     * @param serializer serializer
     * @throws IOException
     * @throws SerializationException
     */
    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        value = new java.util.Date((serializer.readLong()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date rhs = (Date) o;
        return value == rhs.value;
    }

    @Override
    public java.lang.String toString() {
        return "Date{"+
                value.toString() +
                '}';
    }
}
