package ca.qc.johnabbott.cs406;

import java.util.HashMap;

/**
 * Represents a Trie.
 */
public class Trie implements Lexicon {

    // Internal Class
    private class Node {
        public HashMap<Character, Node> content;
        boolean isEndOfWord;

        public Node(Character letter, boolean isEndOfWord){
        }
    }

    // Class Properties
    private Node root;                  // Empty starting Node.
    private final int alphabetLength;   // The possible characters a Node can contain.

    public Trie(int alphabetLength) {
        this.alphabetLength = alphabetLength;
    }

    @Override
    public void add(String word) {
        word = word.toLowerCase();
        addHelper(word, root);
    }

    // Recursive Function for adding words to the Trie.
    public void addHelper(String currentWord, Node currentNode) {
        Character currentLetter = currentWord.charAt(0);    // Take first character from the current word
        currentWord = currentWord.substring(1);             // ... and remove it from the string.

        boolean wordEnded = currentWord.length() == 0;      // Record whether the word string still has letters left.

        if(!currentNode.content.containsKey(currentLetter)) { // Does the letter need to be added to this Node?
            currentNode.content.put(currentLetter, new Node(currentLetter, wordEnded));
        }

        if(!wordEnded) {    // If there are still letters left to process, move on to the next one.
            currentNode = currentNode.content.get(currentLetter);
            addHelper(currentWord, currentNode);
        }

        return;
    }

    @Override
    public boolean contains(String word) {
        word = word.toLowerCase();
        // TODO
        return false;
    }

    public boolean containsHelper(String word) {
        // TODO
        return false;
    }


}
