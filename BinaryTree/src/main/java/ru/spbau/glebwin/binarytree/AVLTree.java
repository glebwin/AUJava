package ru.spbau.glebwin.binarytree;

import java.util.ArrayList;

/**
 * AVL tree.
 * Self-balancing binary search tree.
 */
public class AVLTree<T extends Comparable<? super T>> implements BinaryTree<T> {
    private Node root;
    private int size;

    /**
     * Adds element to the tree if the tree hasn't already contained it.
     *
     * @return True if element was added, false otherwise.
     */
    public boolean add(T element) {
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
     * @param path Path from the root of the tree to the insert position of the element.
     * @return True if element was added(was unique), false otherwise.
     */
    private boolean addUnbalanced(T element, ArrayList<DownEdge> path) {
        Node node = root;
        while (node != null) {
            int comparisonResult = element.compareTo(node.getItem());
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
     * @param path Path to the previous added element.
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
        Node leftRighChild = leftChild.getRightChild();
        node.setLeftChild(leftRighChild.getRightChild());
        leftChild.setRightChild(leftRighChild.getLeftChild());
        leftRighChild.setRightChild(node);
        leftRighChild.setLeftChild(leftChild);
        node.increaseBalance();
        leftChild.decreaseBalance();
        if (leftRighChild.getBalance() > 0) {
            leftChild.decreaseBalance();
            leftRighChild.decreaseBalance();
        } else if (leftRighChild.getBalance() < 0) {
            node.increaseBalance();
            leftRighChild.increaseBalance();
        }
        return leftRighChild;
    }

    /**
     * Checks whether the tree contains element.
     *
     * @return True if element is presented, false otherwise.
     */
    public boolean contains(T element) {
        Node node = root;
        while (node != null) {
            int comparisonResult = element.compareTo(node.getItem());
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
     * @return Number of elements in the tree.
     */
    public int size() {
        return size;
    }

    /**
     * Single node of the tree.
     */
    private class Node {
        private final T item;
        private Node leftChild;
        private Node rightChild;
        private int balance;

        public Node(T item) {
            this.item = item;
        }

        public T getItem() {
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
}
