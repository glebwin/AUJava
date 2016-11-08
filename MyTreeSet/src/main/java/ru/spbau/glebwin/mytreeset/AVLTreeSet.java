package ru.spbau.glebwin.mytreeset;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Implementation of TreeSet Interface based on AVL tree.
 */
public class AVLTreeSet<E> extends AbstractSet<E> implements MyTreeSet<E> {
    private Node root;
    private int size;

    @Nullable
    private final Comparator<? super E> comparator;

    public AVLTreeSet() {
        comparator = null;
    }

    public AVLTreeSet(@NotNull Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Adds the specified element to the set if it is not already present.
     *
     * @return true if the set did not already contain the specified element
     */
    @Override
    public boolean add(E element) {
        if (root == null) {
            root = new Node(element);
            size = 1;
            return true;
        }

        ArrayList<DownEdge> path = new ArrayList<DownEdge>();
        if (addUnbalanced(element, path)) {
            size++;
            balance(path);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Inserts element in the tree as in regular unbalanced search tree.
     *
     * @param path path from the root of the tree to the insert position of the element
     * @return true if the tree did not already contain the specified element
     */
    private boolean addUnbalanced(E element, ArrayList<DownEdge> path) {
        Node node = root;
        while (node != null) {
            int comparisonResult = compareElements(element, node.getItem());
            if (comparisonResult < 0) {
                path.add(new DownEdge(node, -1));
                node = node.getLeftChild();
            } else if (comparisonResult > 0) {
                path.add(new DownEdge(node, 1));
                node = node.getRightChild();
            } else {
                return false;
            }
        }
        node = path.get(path.size() - 1).getNode();
        int direction = path.get(path.size() - 1).getDirection();
        if (direction < 0) {
            node.setLeftChild(new Node(element));
        } else {
            node.setRightChild(new Node(element));
        }
        return true;
    }

    /**
     * Changes tree structure so that the heights of the two subtrees of any node differ by at most one.
     *
     * @param path path to the last added element
     */
    private void balance(ArrayList<DownEdge> path) {
        while (!path.isEmpty()) {
            Node node = path.get(path.size() - 1).getNode();
            int direction = path.get(path.size() - 1).getDirection();
            path.remove(path.size() - 1);
            Node balancedSubtree;

            if (direction < 0) {
                if (node.getBalance() > -1) {
                    node.decreaseBalance();
                    balancedSubtree = node;
                } else {
                    if (node.getLeftChild().getBalance() <= 0) {
                        balancedSubtree = smallRightRotation(node);
                    } else {
                        balancedSubtree = bigRightRotation(node);
                    }
                }
            } else {
                if (node.getBalance() < 1) {
                    node.increaseBalance();
                    balancedSubtree = node;
                } else {
                    if (node.getRightChild().getBalance() >= 0) {
                        balancedSubtree = smallLeftRotation(node);
                    } else {
                        balancedSubtree = bigLeftRotation(node);
                    }
                }
            }

            if (!path.isEmpty()) {
                node = path.get(path.size() - 1).getNode();
                direction = path.get(path.size() - 1).getDirection();
                if (direction < 0) {
                    node.setLeftChild(balancedSubtree);
                } else {
                    node.setRightChild(balancedSubtree);
                }
            } else {
                root = balancedSubtree;
            }
            if (balancedSubtree.getBalance() == 0) {
                return;
            }
        }
    }

    private Node smallLeftRotation(Node node) {
        Node rightChild = node.getRightChild();
        node.setRightChild(rightChild.getLeftChild());
        rightChild.setLeftChild(node);
        if (rightChild.getBalance() > 0) {
            node.decreaseBalance();
        }
        rightChild.decreaseBalance();
        return rightChild;
    }

    private Node bigLeftRotation(Node node) {
        Node rightChild = node.getRightChild();
        Node rightLeftChild = rightChild.getLeftChild();
        node.setRightChild(rightLeftChild.getLeftChild());
        rightChild.setLeftChild(rightLeftChild.getRightChild());
        rightLeftChild.setLeftChild(node);
        rightLeftChild.setRightChild(rightChild);
        node.decreaseBalance();
        rightChild.increaseBalance();
        if (rightLeftChild.getBalance() < 0) {
            rightChild.increaseBalance();
            rightLeftChild.increaseBalance();
        } else if (rightLeftChild.getBalance() > 0) {
            node.decreaseBalance();
            rightLeftChild.decreaseBalance();
        }
        return rightLeftChild;
    }

    private Node smallRightRotation(Node node) {
        Node leftChild = node.getLeftChild();
        node.setLeftChild(leftChild.getRightChild());
        leftChild.setRightChild(node);
        if (leftChild.getBalance() < 0) {
            node.increaseBalance();
        }
        leftChild.increaseBalance();
        return leftChild;
    }

    private Node bigRightRotation(Node node) {
        Node leftChild = node.getLeftChild();
        Node leftRightChild = leftChild.getRightChild();
        node.setLeftChild(leftRightChild.getRightChild());
        leftChild.setRightChild(leftRightChild.getLeftChild());
        leftRightChild.setRightChild(node);
        leftRightChild.setLeftChild(leftChild);
        node.increaseBalance();
        leftChild.decreaseBalance();
        if (leftRightChild.getBalance() > 0) {
            leftChild.decreaseBalance();
            leftRightChild.decreaseBalance();
        } else if (leftRightChild.getBalance() < 0) {
            node.increaseBalance();
            leftRightChild.increaseBalance();
        }
        return leftRightChild;
    }

    /**
     * Checks whether the set contains the element.
     *
     * @return true if the set contains the specified element
     */
    @Override
    public boolean contains(Object element) {
        Node node = root;
        while (node != null) {
            int comparisonResult = compareElements((E) element, node.getItem());
            if (comparisonResult < 0) {
                node = node.getLeftChild();
            } else if (comparisonResult > 0) {
                node = node.getRightChild();
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the number of elements in the set
     */
    @Override
    public int size() {
        return size;
    }

    private int compareElements(E element1, E element2) {
        if (comparator != null) {
            return comparator.compare(element1, element2);
        } else {
            return ((Comparable<E>) element1).compareTo(element2);
        }
    }

    /**
     * Single node of the tree.
     */
    private class Node {
        private final E item;
        private Node leftChild;
        private Node rightChild;
        private int balance;

        public Node(E item) {
            this.item = item;
        }

        public E getItem() {
            return item;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

        public void increaseBalance() {
            balance++;
        }

        public void decreaseBalance() {
            balance--;
        }

        public int getBalance() {
            return balance;
        }
    }

    /**
     * Tree edge presented by the source node and direction to one of it's two children.
     */
    private class DownEdge {
        private final Node node;
        private final int direction;

        public DownEdge(Node node, int direction) {
            this.node = node;
            this.direction = direction;
        }

        public Node getNode() {
            return node;
        }

        public int getDirection() {
            return direction;
        }
    }

    /**
     * Returns an iterator over the elements in the set in ascending order.
     *
     * @return an iterator over the elements in the set in ascending order
     */
    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new AVLTreeSetIterator();
    }

    /**
     * Returns an iterator over the elements in the set in descending order.
     *
     * @return an iterator over the elements in the set in descending order
     */
    @NotNull
    @Override
    public Iterator<E> descendingIterator() {
        return new AVLTreeSetDescendingIterator();
    }

    /**
     * Returns a reverse order view of the set.
     *
     * @return a reverse order view of the set
     */
    @NotNull
    @Override
    public MyTreeSet<E> descendingSet() {
        return new AVLTreeDescendingSet();
    }

    /**
     * Returns the first (lowest) element currently in the set.
     *
     * @return the first (lowest) element currently in the set
     */
    @NotNull
    @Override
    public E first() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        Node node = root;
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node.getItem();
    }

    /**
     * Returns the last (highest) element currently in the set.
     *
     * @return the last (highest) element currently in the set
     */
    @NotNull
    @Override
    public E last() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        Node node = root;
        while (node.getRightChild() != null) {
            node = node.getRightChild();
        }
        return node.getItem();
    }

    /**
     * Returns the greatest element in the set strictly less than the given element, or null if there is no such element.
     *
     * @return the greatest element in the set strictly less than the given element, or null if there is no such element
     */
    @Nullable
    @Override
    public E lower(E e) {
        E result = null;
        Node node = root;
        while (node != null) {
            int comparisonResult = compareElements(node.getItem(), e);
            if (comparisonResult < 0) {
                result = node.getItem();
                node = node.getRightChild();
            } else {
                node = node.getLeftChild();
            }
        }
        return result;
    }

    /**
     * Returns the greatest element in the set less than or equal to the given element, or null if there is no such element.
     *
     * @return the greatest element in the set less than or equal to the given element, or null if there is no such element
     */
    @Nullable
    @Override
    public E floor(E e) {
        E result = null;
        Node node = root;
        while (node != null) {
            int comparisonResult = compareElements(node.getItem(), e);
            if (comparisonResult <= 0) {
                result = node.getItem();
                node = node.getRightChild();
            } else {
                node = node.getLeftChild();
            }
        }
        return result;
    }

    /**
     * Returns the least element in the set greater than or equal to the given element, or null if there is no such element.
     *
     * @return the least element in the set greater than or equal to the given element, or null if there is no such element
     */
    @Nullable
    @Override
    public E ceiling(E e) {
        E result = null;
        Node node = root;
        while (node != null) {
            int comparisonResult = compareElements(node.getItem(), e);
            if (comparisonResult >= 0) {
                result = node.getItem();
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        return result;
    }

    /**
     * Returns the least element in the set strictly greater than the given element, or null if there is no such element.
     *
     * @return the least element in the set strictly greater than the given element, or null if there is no such element
     */
    @Nullable
    @Override
    public E higher(E e) {
        E result = null;
        Node node = root;
        while (node != null) {
            int comparisonResult = compareElements(node.getItem(), e);
            if (comparisonResult > 0) {
                result = node.getItem();
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        return result;
    }

    /**
     * Iterator over elements in binary tree in ascending order.
     */
    private class AVLTreeSetIterator implements Iterator<E> {
        private Node next;
        private ArrayList<Node> pathToRoot;

        public AVLTreeSetIterator() {
            next = root;
            pathToRoot = new ArrayList<>();
            if (next == null) {
                return;
            }
            while (next.getLeftChild() != null) {
                pathToRoot.add(next);
                next = next.getLeftChild();
            }
        }

        /**
         * Returns true if the iteration has more elements.
         *
         * @return true if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return next != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result = next.getItem();
            if (next.getRightChild() != null) {
                pathToRoot.add(next);
                next = next.getRightChild();
                while (next.getLeftChild() != null) {
                    pathToRoot.add(next);
                    next = next.getLeftChild();
                }
                return result;
            } else while (!pathToRoot.isEmpty()) {
                Node parent = pathToRoot.get(pathToRoot.size() - 1);
                pathToRoot.remove(pathToRoot.size() - 1);
                if (parent.getLeftChild() == next) {
                    next = parent;
                    return result;
                }
                next = parent;
            }
            next = null;
            return result;
        }
    }

    /**
     * Iterator over elements in binary tree in descending order.
     */
    private class AVLTreeSetDescendingIterator implements Iterator<E> {
        private Node next;
        private ArrayList<Node> pathToRoot;

        public AVLTreeSetDescendingIterator() {
            next = root;
            pathToRoot = new ArrayList<>();
            if (next == null) {
                return;
            }
            while (next.getRightChild() != null) {
                pathToRoot.add(next);
                next = next.getRightChild();
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result = next.getItem();
            if (next.getLeftChild() != null) {
                pathToRoot.add(next);
                next = next.getLeftChild();
                while (next.getRightChild() != null) {
                    pathToRoot.add(next);
                    next = next.getRightChild();
                }
                return result;
            } else while (!pathToRoot.isEmpty()) {
                Node parent = pathToRoot.get(pathToRoot.size() - 1);
                pathToRoot.remove(pathToRoot.size() - 1);
                if (parent.getRightChild() == next) {
                    next = parent;
                    return result;
                }
                next = parent;
            }
            next = null;
            return result;
        }
    }

    /**
     * Reverse order view of the elements contained in the set.
     * The descending set is backed by the set, so changes to the set are reflected in the descending set, and vice-versa.
     */
    private class AVLTreeDescendingSet extends AbstractSet<E> implements MyTreeSet<E> {
        @Override
        public boolean add(E element) {
            return AVLTreeSet.this.add(element);
        }

        @NotNull
        @Override
        public Iterator<E> iterator() {
            return AVLTreeSet.this.descendingIterator();
        }

        @Override
        public int size() {
            return AVLTreeSet.this.size();
        }

        @NotNull
        @Override
        public Iterator<E> descendingIterator() {
            return AVLTreeSet.this.iterator();
        }

        @NotNull
        @Override
        public MyTreeSet<E> descendingSet() {
            return AVLTreeSet.this;
        }

        @NotNull
        @Override
        public E first() {
            return AVLTreeSet.this.last();
        }

        @NotNull
        @Override
        public E last() {
            return AVLTreeSet.this.first();
        }

        @Nullable
        @Override
        public E lower(E e) {
            return AVLTreeSet.this.higher(e);
        }

        @Nullable
        @Override
        public E floor(E e) {
            return AVLTreeSet.this.ceiling(e);
        }

        @Nullable
        @Override
        public E ceiling(E e) {
            return AVLTreeSet.this.floor(e);
        }

        @Nullable
        @Override
        public E higher(E e) {
            return AVLTreeSet.this.lower(e);
        }
    }
}
