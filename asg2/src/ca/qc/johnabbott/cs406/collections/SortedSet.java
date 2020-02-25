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
    private boolean isSet;

    public SortedSet() {
        this(DEFAULT_CAPACITY);
    }

    public SortedSet(int capacity) {
        this.size = 0;
        this.elements = (T[]) new Comparable[capacity];
        this.isSet = false;
    }

    /**
     * @param elem
     * Looks for elem parameter in elements between index 0 and current size.
     * Returns true if elem matches an element in the set, otherwise returns false.
     */
    @Override
    public boolean contains(T elem) {
        // elements is sorted, so we can binary search for the element.
        return Arrays.binarySearch(elements, 0, size, elem) >= 0;
    }

    /**
     * @param rhs
     * Determine if rhs is a subset of the current set,
     * ie: this.elements contains at least all of the items in rhs.elements.
     */
    @Override
    public boolean containsAll(Set<T> rhs) {
        SortedSet<T> workingSet = (SortedSet)rhs;   // Set<T> itself doesn't have any elements to check for, so it must be interpreted as SortedSet<T>.
        for (int i = 0; i < workingSet.size; i++) {
            if (Arrays.binarySearch(this.elements, 0, size, workingSet.elements[i]) < 0){
                return false;
            }
        }
        return true;
    }

    /**
     * @param elem
     * Insert elem into the set while keeping it sorted.
     */
    @Override
    public boolean add(T elem) {
        // Adding an element during traversal is really bad.
        isSet = false;

        // Don't add element to set if it's already there.
        if (this.contains(elem)){
        	return false;
        }

        // Don't allow user to bloat internal array beyond capacity.
        if(this.isFull()){
            throw new FullSetException();
        }

        // Shift all elements greater than elem to the right.
        int i;
        for (i = size - 1; (i >= 0 && (elements[i].compareTo(elem) > 0)); i--){
            elements[i + 1] = elements[i];
        }

        // Set new size of the set.
        this.size = this.size + 1;

        // Insert elem.
        elements[i + 1] = elem;

        return true;
    }

    /**
     * @param elem
     * Remove an element from the set while keeping it sorted.
     */
    @Override
    public boolean remove(T elem) {
        // Removing during traversal is also really bad.
        isSet = false;

        if(this.isEmpty()){
        	//throw new EmptySetException();
            return false;
        }

        // Get the index of the element to remove.
        int rmIndex = Arrays.binarySearch(elements, 0, size, elem);
        
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
    		throw new EmptySetException();
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
    		throw new EmptySetException();
    	}

    	T max = elements[0];

        for (int i = 0; i < size; i++) {
        	T element = elements[i];
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
        if(first.compareTo(last) > 0){
            throw new IllegalArgumentException();
        }

        SortedSet<T> subSet = new SortedSet();
        for(int i = 0; i < size; i++){
            T elem = elements[i];
            if(elem.compareTo(last) >= 0){
                break;
            }
            if(elem.compareTo(first) >= 0){
                subSet.add(elem);
            }
        }

        return subSet;
    }
    /*
    public SortedSet<T> subset(T first, T last) {
    	int firstIndex = Arrays.binarySearch(elements, 0, size, first);
        int lastIndex = Arrays.binarySearch(elements, 0, size, last);
    	if(firstIndex < 0 || lastIndex < 0 || firstIndex > lastIndex){
    		throw new IllegalArgumentException();
    	}

    	int subsetSize = (lastIndex - firstIndex) == 0 ? 1 : lastIndex - firstIndex;
    	SortedSet<T> subSet = new SortedSet(subsetSize);
    	for(int i = firstIndex; i < lastIndex; i++){
    		subSet.add(this.elements[i]);
    	}

        return subSet;
    }
    */

    @Override
    public boolean isFull() {
    	return size >= elements.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i = 0; i < size; i++){
        	sb.append(elements[i].toString());
        	if(i < size - 1)
        		sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public void reset() {
    	index = 0;
    	isSet = true;
    }

    @Override
    public T next() {
    	if(!hasNext() || !isSet){
    		throw new TraversalException();
    	}
        return elements[index++];
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }
}
