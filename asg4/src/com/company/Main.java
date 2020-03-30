package com.company;

public class Main {

    public static void main(String[] args) {

        CircleLink(5, 0, 4);
        System.out.println("\nDone.");
    }


    // Testing of circle link stepping and removal.
    private static void CircleLink(int n, int m, int o){
        if(n <=  0 || m < 0 || o < 0){
            throw new RuntimeException("Invalid Input.");
        }

        // Create a circular link of length n.
        DoubleLink<Integer> current = CreateCircleLink(n);

        // Step as long as there are disparate elements in the chain.
        while(current != current.prev){
            // Clockwise steps and removals.
            if(m != 0){
                for(int i = 1; i < m; i++){ // Step.
                    current = current.next;
                }
                RemoveFromCircleLink(current);
            }

            // Counterclockwise steps and removals.
            if(o != 0){
                for(int i = 1; i < o; i++){
                    current = current.prev; // Step.
                }
                RemoveFromCircleLink(current);
            }

            // Move cursor off of the element that was just removed.
            current = current.next;
        }
        // Remove the last and final element of the linked list.
        RemoveFromCircleLink(current);
    }

    // Creates a circular link chain structure.
    private static DoubleLink<Integer> CreateCircleLink(int n){
        // Head of the chain
        DoubleLink<Integer> head = new DoubleLink<>();
        DoubleLink<Integer> current = head;

        // Middle elements of the chain
        for(int i = 1; i <= n-1; i++){
            current.element = i;
            current.next = new DoubleLink<>();
            current.next.prev = current;
            current = current.next;
        }

        // Tail being eaten by the head, Ouroboros.
        current.element = n;
        current.next = head;
        head.prev = current;

        return head;
    }

    // Removes an element from any point in a circular link-chain.
    private static void RemoveFromCircleLink(DoubleLink<Integer> current){
        current.next.prev = current.prev;
        current.prev.next = current.next;
        System.out.printf(current.element + " ");
    }

    // Taken from Ian Clement's Assignment IV: Links instruction pdf.
    private static class DoubleLink<T> {
        public T element;
        public DoubleLink<T> next;
        public DoubleLink<T> prev;

        public DoubleLink() {}
        public DoubleLink(T element) {
            this.element = element;
        }
    }
}
