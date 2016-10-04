package ru.spbau.glebwin.trie;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.HashMap;

/**
 * Serializable trie.
 */
public class Trie implements StreamSerializable {
    private Node root;

    Trie() {
        root = new Node();
    }

    /**
     * Adds element to the trie.
     * @return True if element wasn't in the trie, false otherwise.
     */
    public boolean add(String element) {
        if (element == null) {
            throw new IllegalArgumentException("null argument");
        }
        if (contains(element)) {
            return false;
        }

        Node node = root;
        for (char symbol : element.toCharArray()) {
            node.increaseSubtreeSize();
            node = node.goChild(symbol);
        }
        node.increaseSubtreeSize();
        node.setTerminal(true);

        return true;
    }

    /**
     * @return True if element exists in trie, false otherwise.
     */
    public boolean contains(String element) {
        if (element == null) {
            throw new IllegalArgumentException("null argument");
        }
        Node node = root;
        for (char symbol : element.toCharArray()) {
            if (!node.hasChild(symbol)) {
                return false;
            }
            node = node.getChild(symbol);
        }
        return node.isTerminal();
    }

    /**
     * Removes element from the trie.
     * @return True if element existed in the trie before the operation, false otherwise.
     */
    public boolean remove(String element) {
        if (element == null) {
            throw new IllegalArgumentException("null argument");
        }
        if (!contains(element)) {
            return false;
        }

        Node node = root;
        for (char symbol : element.toCharArray()) {
            node.decreaseSubtreeSize();
            node = node.goChild(symbol);
        }
        node.decreaseSubtreeSize();
        node.setTerminal(false);
        cleanup(element);

        return true;
    }

    /**
     * Cuts off the largest empty branch corresponding to the path.
     */
    private void cleanup(String path) {
        Node node = root;
        for (int i = 0; i < path.length() - 1; i++) {
            if (node.getChild(path.charAt(i)).getSubtreeTerminalsNumber() == 0) {
                node.removeChild(path.charAt(i));
                return;
            }
            node = node.getChild(path.charAt(i));
        }
    }

    /**
     * @return Number of elements in the trie.
     */
    public int size() {
        return root.getSubtreeTerminalsNumber();
    }

    /**
     * @return Number of elements that begin with the prefix.
     */
    public int howManyStartsWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("null argument");
        }
        Node node = root;
        for (char symbol : prefix.toCharArray()) {
            if (!node.hasChild(symbol)) {
                return 0;
            }
            node = node.getChild(symbol);
        }

        return node.getSubtreeTerminalsNumber();
    }

    /**
     * Writes the trie into stream.
     * @param out The stream to write into.
     */
    @Override
    public void serialize(OutputStream out) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(root);
    }

    /**
     * Reads the trie from stream.
     * All old data from the trie is removed.
     * @param in The stream to read from.
     */
    @Override
    public void deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        root = (Node) ois.readObject();
    }

    /**
     * Trie's node.
     */
    private static class Node implements Serializable {
        private HashMap<Character, Node> children;
        private boolean terminal;
        private int subtreeTerminalsNumber;

        Node() {
            children = new HashMap<Character, Node>();
        }

        boolean hasChild(Character character) {
            return children.containsKey(character);
        }

        Node getChild(Character character) {
            return children.get(character);
        }

        void removeChild(Character character) {
            children.remove(character);
        }

        /**
         * Returns node's corresponding child.
         * Creates a new one if it doesn't exist.
         */
        @NotNull
        Node goChild(Character character) {
            if (!hasChild(character)) {
                children.put(character, new Node());
            }
            return getChild(character);
        }

        boolean isTerminal() {
            return terminal;
        }

        void setTerminal(boolean terminal) {
            this.terminal = terminal;
        }

        /**
         * Increases the number of terminal nodes in the subtree of current node.
         */
        void increaseSubtreeSize() {
            subtreeTerminalsNumber++;
        }

        /**
         * Decreases the number of terminal nodes in the subtree of current node.
         */
        void decreaseSubtreeSize() {
            subtreeTerminalsNumber--;
        }

        int getSubtreeTerminalsNumber() {
            return subtreeTerminalsNumber;
        }
    }
}
