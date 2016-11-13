package ru.spbau.glebwin.binarytree;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class AVLTreeTest {
    private static int[] testData;

    @BeforeClass
    public static void setUpClass() {
        Random random = new Random(42);
        testData = new int[100000];
        for (int i = 0; i < testData.length; i++) {
            testData[i] = random.nextInt();
        }
    }

    @Test
    public void add() throws Exception {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        assertTrue(tree.add(4));
        assertTrue(tree.add(2));
        assertFalse(tree.add(4));
        assertTrue(tree.add(1));
        assertTrue(tree.add(-3));
        assertTrue(tree.add(3));
        assertFalse(tree.add(2));
        assertFalse(tree.add(1));
        assertFalse(tree.add(-3));
        assertFalse(tree.add(3));
    }

    @Test
    public void contains() throws Exception {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        tree.add(4);
        tree.add(3);
        tree.add(2);
        tree.add(5);
        tree.add(7);
        tree.add(1);
        tree.add(8);
        for (int i = 1; i <= 5; i++) {
            assertTrue(tree.contains(i));
        }
        assertTrue(tree.contains(7));
        assertTrue(tree.contains(8));
        assertFalse(tree.contains(-1));
        assertFalse(tree.contains(6));
    }

    @Test
    public void size() throws Exception {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        int[] elements = {5, 2, 1, 3, 5, 4, 2};
        int[] sizes = {1, 2, 3, 4, 4, 5, 5};
        assertEquals(0, tree.size());
        for (int i = 0; i < elements.length; i++) {
            tree.add(elements[i]);
            assertEquals(sizes[i], tree.size());
        }
    }

    @Test(timeout = 1000)
    public void largeInput() throws Exception {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        for (int testItem : testData) {
            tree.add(testItem);
        }
        for (int testItem : testData) {
            assertTrue(tree.contains(testItem));
        }
    }

    @Test(timeout = 1000)
    public void largeOrderedInput() throws Exception {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        final int inputSize = 200000;
        for (int i = 0; i < inputSize; i++) {
            tree.add(i);
        }
        assertEquals(inputSize, tree.size());
        for (int i = 0; i < inputSize; i++) {
            assertTrue(tree.contains(i));
        }
    }
}