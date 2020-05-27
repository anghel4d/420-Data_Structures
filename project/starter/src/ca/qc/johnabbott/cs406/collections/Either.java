/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections;


import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import java.io.IOException;

/**
 * Represents an value that can be one of two generic data types: either a "left" value of type S, or a "right" value of type T.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface Either<S, T> {

    enum Type { LEFT, RIGHT }

    /**
     * Determine if the object is a left or right Either.
     * @return
     */
    Type getType();

    /**
     * Get the left value of the either.
     * @return
     */
    S getLeft();

    /**
     * Get the right value of the either.
     * @return
     */
    T getRight();


    /**
     * Generate a left Either object.
     * @param val
     * @param <S>
     * @param <T>
     * @return
     */
    static <S extends Serializable, T extends Serializable> Either<S, T> left(S val) {
        return new LeftEither<S, T>(val);
    }

    static <S extends Serializable, T extends Serializable> Either<S, T> right(T val) {
        return new RightEither<S, T>(val);
    }

    /**
     * Class to store left Either values.
     * @param <S>
     * @param <T>
     */
    class LeftEither<S extends Serializable, T extends Serializable> implements Either<S,T>, Serializable {

        public static final byte SERIAL_ID = 0x24;

        private S val;

        public LeftEither(S val) {
            this.val = val;
        }

        public LeftEither() { }

        @Override
        public Type getType() {
            return Type.LEFT;
        }

        @Override
        public S getLeft() {
            return val;
        }

        @Override
        public T getRight() {
            throw new RuntimeException();
        }

        @Override
        public byte getSerialId() {
            return SERIAL_ID;
        }

        @Override
        public void serialize(Serializer serializer) throws IOException {
            serializer.write(val);
        }

        @Override
        public void deserialize(Serializer serializer) throws IOException, SerializationException {
            val = (S) serializer.readSerializable();
        }
    }

    /**
     * Class to store right Either values.
     * @param <S>
     * @param <T>
     */
    class RightEither<S extends Serializable, T extends Serializable> implements Either<S,T>, Serializable {

        public static final byte SERIAL_ID = 0x25;

        private T val;

        public RightEither(T val) {
            this.val = val;
        }

        public RightEither() { }

        @Override
        public Type getType() {
            return Type.RIGHT;
        }

        @Override
        public S getLeft() {
            throw new RuntimeException();
        }

        @Override
        public T getRight() {
            return val;
        }

        @Override
        public byte getSerialId() {
            return SERIAL_ID;
        }

        @Override
        public void serialize(Serializer serializer) throws IOException {
            serializer.write(val);
        }

        @Override
        public void deserialize(Serializer serializer) throws IOException, SerializationException {
            val = (T) serializer.readSerializable();
        }
    }
}
