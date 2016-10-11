package ru.spbau.glebwin.binarytree;

public interface BinaryTree<T extends Comparable<? super T>> {
    boolean add(T element);

    boolean contains(T element);

    int size();
}
