package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import ca.qc.johnabbott.cs406.serialization.util.String;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * DESCRIPTION HERE
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 * @since 2018-04-29
 */
public class Grade implements Serializable {

    public static final byte SERIAL_ID = 0x23;

    private java.lang.String name;
    private int result;
    private Date date;

    public Grade(java.lang.String name, int result, Date date) {
        this.name = name;
        this.result = result;
        this.date = date;
    }

    public Grade() {
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public java.lang.String toString() {
        return "Grade{" +
                "name='" + name + '\'' +
                ", result=" + result +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return result == grade.result &&
                Objects.equals(name, grade.name) &&
                Objects.equals(date, grade.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, result, date);
    }


    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    /***
     * Serialization by writing each value.
     * @param serializer serializer
     * @throws IOException
     */
    @Override
    public void serialize(Serializer serializer) throws IOException {
        String tmpStr = new String(this.name);
        serializer.write(tmpStr);
        serializer.write(result);
        ca.qc.johnabbott.cs406.serialization.util.Date tmpDate = new ca.qc.johnabbott.cs406.serialization.util.Date(this.date);
        serializer.write(tmpDate);
    }

    /***
     * Deserialization by reading each value.
     * @param serializer serializer
     * @throws IOException
     * @throws SerializationException
     */
    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        this.name = ((String) serializer.readSerializable()).get();
        this.result = serializer.readInt();
        ca.qc.johnabbott.cs406.serialization.util.Date tmp = (ca.qc.johnabbott.cs406.serialization.util.Date) serializer.readSerializable();
        this.date = new Date(tmp.get().getTime());
    }
}
