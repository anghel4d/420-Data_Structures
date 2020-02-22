package ca.qc.johnabbott.cs406.collections;

import java.util.Arrays;

/**
 * TODO
 */
public class SortedSet<T extends Comparable<T>> implements Set<T> {

    private static final int DEFAULT_CAPACITY = 100;

    private T[] elements;   // Collection of elements contained in the set.
    private int size;       // Current number of elements in the set.
    private int index;      // Index of the element currently being pointed to by the traversal.

    public SortedSet() {
        this(DEFAULT_CAPACITY);
    }

    public SortedSet(int capacity) {
        this.size = 0;
        this.elements = (T[]) new Comparable[capacity];

    }

    @Override
    /**
     * @param elem
     * Looks for elem parameter in elements between index 0 and current size.
     * Returns true if elem matches an element in the set, otherwise returns false.
     */
    public boolean contains(T elem) {
        // elements is sorted, so we can binary search for the element.
        return Arrays.binarySearch(elements, 0, size, elem) >= 0;
    }

    @Override
    /**
     * @param rhs
     * Determine if rhs is a subset of the current set,
     * ie: this.elements contains at least all of the items in rhs.elements.
     */
    public boolean containsAll(Set<T> rhs) {
        for (T element : elements) {
            if(Arrays.binarySearch(elements, 0, size, element) < 0){
                return false;
            }
        }
        return true;
    }

    @Override
    /**
     * @param elem
     * Insert elem into the set and sort
     */
    public boolean add(T elem) {
        if(size >= elements.length){
            throw new FullSetException();
        }
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public boolean remove(T elem) {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public int size() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public boolean isEmpty() {
        throw new RuntimeException("Not implemented.");
    }

    /**
     * TODO
     * @return
     */
    public T min() {
        throw new RuntimeException("Not implemented.");
    }

    /**
     * TODO
     * @return
     */
    public T max() {
        throw new RuntimeException("Not implemented.");
    }

    /**
     * TODO
     * @param first
     * @param last
     * @return
     */
    public SortedSet<T> subset(T first, T last) {
        throw new RuntimeException("Not implemented.");
    }


    @Override
    public boolean isFull() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public String toString() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public void reset() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public T next() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public boolean hasNext() {
        throw new RuntimeException("Not implemented.");
    }
}
