/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections.set;

import java.util.Iterator;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import java.io.IOException;

/**
 * An implementation of the Set API using a binary search tree.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class TreeSet<T extends Comparable<T> & Serializable> implements Set<T>, Serializable {

    public static final byte SERIAL_ID = 0x28;

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    /**
     * The TreeSet is serialized by writing each node value,
     * recursively moving down the left branches whenever possible
     * and moving up the right when an entire left is exhausted and we move back up to the root.
     * @param serializer serializer
     * @throws IOException
     */
    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(size);

        if(root != null){
            serialize(root, serializer);
        }
    }

    // Byte representations of boolean flags since Serializer can't write booleans otherwise.
    private static final byte B_TRUE = 0x01;
    private static final byte B_FALSE = 0x00;
    public void serialize(Node<T> current, Serializer serializer) throws IOException {
        serializer.write(current.element);

        // Marker for whether or not there are branches to current Node.
        boolean hasLeft = current.left != null;
        boolean hasRight = current.right != null;

        // Why doesn't the serializer have a way to write Booleans?
        if(hasLeft) {
            serializer.write(B_TRUE);
        } else {
            serializer.write(B_FALSE);
        }
        if(hasRight) {
            serializer.write(B_TRUE);
        } else {
            serializer.write(B_FALSE);
        }

        // There has to be a second check because the hasLeft and hasRight markers should follow immediately after a Node's value.
        // This if statement controls the recursion.
        if(hasLeft) {
            serialize(current.left, serializer);
        }
        if (hasRight) {
            serialize(current.right, serializer);
        }
    }

    /**
     * The TreeSet is deserialized by reading each node value from the binary and putting them into new Node<T>() objects.
     * This is done by recursively following each possible left branch from the root, and moving on to each right on the way back up to root.
     * @param serializer serializer
     * @throws IOException
     * @throws SerializationException
     */
    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        this.size = serializer.readInt();

        if(size != 0){
            root = deserialize(root, serializer);
        }
    }

    public Node<T> deserialize(Node<T> current, Serializer serializer) throws IOException, SerializationException {
        T value = (T) serializer.readSerializable();

        current = new Node<>(value);

        // Storing a byte as a flag and converting it to a boolean should be more efficient than trying to serialize Nodes themselves or trying to infer/compute their branch.
        boolean hasLeft = serializer.read() == B_TRUE;
        boolean hasRight = serializer.read() == B_TRUE;

        // Controlled recursion, moving down left branches when possible, then taking any rights on the way back up.
        if(hasLeft) {
            current.left = deserialize(current.left, serializer);
        }
        if(hasRight) {
            current.right =deserialize(current.right, serializer);
        }

        return current;
    }

    private class Node<T> {
        public T element;
        public Node<T> left;
        public Node<T> right;

        public Node(T element) {
            this.element = element;
        }
    }

    // fields: store the root of ths bst and the size.
    private Node<T> root;
    private int size;

    // Default constructor
    public TreeSet() {
        this.size = 0;
        this.root = null;
    }

    @Override
    public boolean add(T elem) {
        if(root == null) {
            root = new Node<>(elem);
            size++;
            return true;
        }
        else
            return add(root, elem);
    }

    /**
     * Recursive helper method to add elem as a leaf in the BST.
     * - precondition: current != null
     * @param current
     * @param elem
     * @return true if elem is added, false if it is already in the tree.
     */
    private boolean add(Node<T> current, T elem) {
        int cmp = elem.compareTo(current.element);
        if(cmp == 0)
            return false;
        if(cmp < 0) {  // elem is in left subtree
            if(current.left == null) {
                current.left = new Node<>(elem);
                size++;
                return true;
            }
            else
               return add(current.left, elem);
        }
        else { // elem is in right subtree
            if(current.right == null) {
                current.right = new Node<>(elem);
                size++;
                return true;
            }
            else
                return add(current.right, elem);
        }
    }

    @Override
    public boolean contains(T elem) {
        return contains(root, elem);
    }

    /**
     * Recursively search the tree to check for the element.
     * - uses BST property to optimize the search: check only the subtree that
     *   can possibly have the element.
     * @param current
     * @param elem
     * @return
     */
    private boolean contains(Node<T> current, T elem) {
        if(current == null)
            return false;

        int cmp = elem.compareTo(current.element);
        if(cmp == 0)
            return true;
        if(cmp < 0)
            return contains(current.left, elem);
        else
            return contains(current.right, elem);
    }

    @Override
    public boolean containsAll(Set<T> rhs) {
        return false;
    }

    @Override
    public T floor(T value) {
        return floor(root, value);
    }

    private T floor(Node<T> current, T value) {
        if (current == null)
            return null;
        int c = value.compareTo(current.element);
        if (c == 0)
            return current.element;
        if (c > 0) {
            T elem = floor(current.right, value);
            if(elem == null)
                return current.element;
            else
                return elem;
        }
        else {
            T elem = floor(current.left, value);
            if(elem == null)
                return null;
            else
                return elem;
        }
    }

    @Override
    public boolean remove(T elem) {
        return removeHelper(root, null, elem);
    }

    /**
     * Recurive helper method to remove an element.
     * @param current the current node.
     * @param parent the parent of the current node (null implies current == root)
     * @param elem the element t
     * @return the value removed
     */
    private boolean removeHelper(Node<T> current, Node<T> parent, T elem) {

        // binary search is unsuccessful
        if(current == null)
            return false;

        int cmp = elem.compareTo(current.element);

        // node found:
        if(cmp == 0) {

            // if we need to remove an internal node with two children
            // find the successor in the left subtree and replace the current entry
            // with the successor's entry.
            if(current.left != null && current.right != null) {

                Node<T> tmp = current; // store the current node to replace it's entry

                // Ensure that `current` is the successor and that `parent` is it's parent
                // so that we remove this node below
                current = current.left;
                while(current.right != null) {
                    parent = current;
                    current = current.right;
                }
                tmp.element = current.element;
            }

            removeNode(current, parent);
            size--;
            return true;
        }

        // descend into the subtree that could contain the node
        if(cmp < 0)
            return removeHelper(current.left, current, elem);
        else
            return removeHelper(current.right, current, elem);
    }

    private void removeNode(Node<T> current, Node<T> parent) {

        // case 1: root
        if(current == root) {
            if(current.left == null)
                root = current.right;
            else
                root = current.left;
        }

        // case 2: left subtree of parent
        else if(current == parent.left) {
            if(current.left == null)
                parent.left = current.right;
            else
                parent.left = current.left;
        }

        // case 3: right subtree of parent
        else {
            if(current.right == null)
                parent.right = current.left;
            else
                parent.right = current.right;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        toStringHelper(root, builder);
        return builder.toString();
    }

    private void toStringHelper(Node<T> current, StringBuilder builder) {
        if(current == null)
            return;
        toStringHelper(current.left, builder);
        builder.append(current.element);
        toStringHelper(current.right, builder);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public T[] toArray() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        throw new RuntimeException("Not implemented.");
    }

}
