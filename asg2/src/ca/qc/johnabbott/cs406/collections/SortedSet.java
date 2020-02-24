package ca.qc.johnabbott.cs406.collections;

import java.util.Arrays;

/**
 * TODO: Add more comments with javadoc at the start of each method.
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
        for (int i = 0; i < size; i++) {
            if(Arrays.binarySearch(elements, 0, size, elements[i]) < 0){
                return false;
            }
        }
        return true;
    }

    @Override
    /**
     * @param elem
     * Insert elem into the set while keeping it sorted.
     */
    public boolean add(T elem) {
        if(this.isFull()){
            throw new FullSetException();
        }

        // Don't add element to set if it's already there.
        if (this.contains(elem)){
        	return false;
        }

        // Shift all elements greater than elem to the right.
        int i;
        for (i = size; (i >= 0 && (elements[i].compareTo(elem) > 0)); i--){
        	elements[i + 1] = elements[i];
        }

        // Set new size of the set.
        this.size = this.size + 1;

        // Insert elem.
        elements[i + 1] = elem;

        return true;
    }

    @Override
    public boolean remove(T elem) {
        if(this.isEmpty()){
        	throw new EmptySetException();
        }

        // Get the index of the element to remove.
        rmIndex = Arrays.binarySearch(elements, 0, size, element);
        
        // If it doesn't exist, exit function and return false.
        if(rmIndex < 0){
        	return false;
        }

        // If it exists, shift elements back to the left.
        for(int i = rmIndex; i < elements.length - 1; i++){
        	elements[i] = elements[i + 1];
        }

        // Set new size of the set.
        this.size = this.size - 1;

        // Confirm that an element was removed from the set.
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    /**
     * TODO
     * @return
     */
    public T min() {
    	if(this.isEmpty()){
    		throw EmptySetException;
    	}

    	T min = elements[0];

        for (int i = 0; i < size; i++) {
        	T element = elements[i];
            if(element.compareTo(min) < 0){
                min = element;
            }
        }

        return min;
    }

    /**
     * TODO
     * @return
     */
    public T max() {
        if(this.isEmpty()){
    		throw EmptySetException;
    	}

    	T max = elements[0];

        for (int i = 0; i < size; i++) {
        	element = elements[i];
            if(element.compareTo(max) > 0){
                max = element;
            }
        }

        return max;
    }

    /**
     * TODO
     * @param first
     * @param last
     * @return
     */
    public SortedSet<T> subset(T first, T last) {
    	int firstIndex = Arrays.binarySearch(elements, 0, size, first);
    	if(firstIndex < 0){
    		throw new RunTimeException("First element is not in the array.");
    	}
    	int lastIndex = Arrays.binarySearch(elements, 0, size, last) - 1;
    	if(lastIndex < 0){
    		throw new RunTimeException("Last element is not in the array.");
    	}
    	if(firstIndex.compareTo(lastIndex) < 0){
    		throw new RunTimeException("First element must be smaller than the last element of the subset.");
    	}

    	int subsetSize = (lastIndex - firstIndex) == 0 ? 1 : lastIndex - firstIndex;
    	SortedSet<T> subSet = new SortedSet(subsetSize);
    	for(int i = firstIndex; i < lastIndex; i++){
    		subSet.add(this.elements[i]);
    	}

        return subSet;
    }


    @Override
    public boolean isFull() {
    	return size >= elements.length;
    }

    @Override
    public String toString() {
        if(this.isEmpty()){
        	throw new EmptySetException();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i = 0; i < size; i++){
        	sb.append(elements[i].toString());
        	if(i < size - 1)
        		sb.append(", ");
        }
        sb.append("}");

    }

    @Override
    public void reset() {
    	index = 0;
    }

    @Override
    public T next() {
    	if(!hasNext()){
    		throw new FullSetException();
    	}
        return elements[++index];
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }
}
