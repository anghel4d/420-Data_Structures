package ca.qc.johnabbott.cs406.serialization.util;


import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;

import java.io.IOException;

/**
 * TODO: 1. Implement date Serializable methods.
 */
public class Date  implements Serializable {
    @Override
    public byte getSerialId() {
        return 0x08;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {

    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {

    }
}
