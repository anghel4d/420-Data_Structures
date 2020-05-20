package ca.qc.johnabbott.cs406;

import java.util.HashMap;
import java.util.Random;

/**
 * Represents a Trie.
 */
public class Trie implements Lexicon {

    // Internal Class
    private class Node {
        public HashMap<Character, Node> content;
        public boolean isEndOfWord;

        public Node(){
            this.content = new HashMap<>();
            this.isEndOfWord = false;
        }
    }

    // Class Properties
    private Node root;                  // Empty starting Node.
    private final int alphabetLength;   // The possible characters a Node can contain.

    public Trie(int alphabetLength) {
        this.alphabetLength = alphabetLength;
        this.root = new Node();
    }

    @Override
    public void add(String word) {
        word = word.toLowerCase();
        addHelper(word, root);
    }

    /***
     * Recursive Function for adding words to the Trie.
     * @param word
     * @param currentNode
     */
    private void addHelper(String word, Node currentNode) {
        Character letter = word.charAt(0);    // Take first character from the current word
        word = word.substring(1);             // ... and remove it from the string.

        boolean wordEnded = word.length() == 0;      // Record whether the word string still has letters left.
        currentNode.content.computeIfAbsent(letter, c -> new Node()); // Add the letter to the trie if it isn't already present.

        if(!wordEnded) {    // If there are still letters left to process, move on to the next one.
            currentNode = currentNode.content.get(letter);
            addHelper(word, currentNode);
        }
        else {
            currentNode.content.get(letter).isEndOfWord = true;
        }

        return;
    }

    @Override
    public boolean contains(String word) {
        word = word.toLowerCase();
        return containsHelper(word, this.root);
    }

    /***
     * Recursive Function for checking the presence of a word in the Trie.
     * @param word
     * @param currentNode
     * @return
     */
    private boolean containsHelper(String word, Node currentNode) {
        boolean found;
        Character letter = word.charAt(0);    // Take first character from the current word
        word = word.substring(1);             // ... and remove it from the string.
        found = currentNode.content.containsKey(letter);
        if(word.length() > 0 && found){
            found = containsHelper(word, currentNode.content.get(letter));  // Praise the Omnissiah.
        }
        return found;
    }
}
