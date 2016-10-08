package ru.spbau.glebwin.matrix;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Tests for MatrixUtil class
 */
public class MatrixUtilityTest {
    private ByteArrayOutputStream outContent;

    @Before
    public void setUp() throws Exception {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test(expected = IllegalArgumentException.class)
    public void printSpirallyArgumentCheck() throws Exception {
        int[][] matrix = new int[3][];
        matrix[0] = new int[4];
        matrix[1] = new int[4];
        matrix[2] = new int[4];

        MatrixUtility.printSpirally(matrix);
    }

    @Test
    public void printSpirallySized1() throws Exception {
        int[][] matrix = {{1}};
        String expectedResult = "1";

        MatrixUtility.printSpirally(matrix);
        assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void printSpirallySized3() throws Exception {
        int[][] matrix = {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        String expectedResult = "5 6 9 8 7 4 1 2 3";

        MatrixUtility.printSpirally(matrix);
        assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void printSpirallySized5() throws Exception {
        int[][] matrix = {{ 1,  2,  3,  4,  5},
                { 6,  7,  8,  9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}};
        String expectedResult = "13 14 19 18 17 12 7 8 9 10 15 20 25 24 23 22 21 16 11 6 1 2 3 4 5";

        MatrixUtility.printSpirally(matrix);
        assertEquals(expectedResult, outContent.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void sortColumnsArgumentCheck1() throws Exception {
        int[][] matrix = new int[3][];
        matrix[0] = new int[4];
        matrix[2] = new int[4];

        MatrixUtility.printSpirally(matrix);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sortColumnsArgumentCheck2() throws Exception {
        int[][] matrix = new int[3][];
        matrix[0] = new int[4];
        matrix[1] = new int[4];
        matrix[2] = new int[3];

        MatrixUtility.printSpirally(matrix);
    }

    @Test
    public void sortColumns1Column() throws Exception {
        int[][] matrix = {{4},
                {0},
                {4}};
        int[][] expectedResult = {{4},
                {0},
                {4}};

        MatrixUtility.sortColumns(matrix);
        assertArrayEquals(expectedResult, matrix);
    }

    @Test
    public void sortColumnsCommon() throws Exception {
        int[][] matrix = {{4,  5, 10, 1, 3},
                {0, 15, -1, 2, 4}};
        int[][] expectedResult = {{1, 3, 4,  5, 10},
                {2, 4, 0, 15, -1}};

        MatrixUtility.sortColumns(matrix);
        assertArrayEquals(expectedResult, matrix);
    }

    @Test
    public void sortColumnsDecreasing() throws Exception {
        int[][] matrix = {{10, 9, 8, 2, 1, -2},
                { 1, 2, 3, 4, 5,  6},
                {12, 8, 6, 9, 8,  7}};
        int[][] expectedResult = {{-2, 1, 2, 8, 9, 10},
                { 6, 5, 4, 3, 2,  1},
                { 7, 8, 9, 6, 8, 12}};

        MatrixUtility.sortColumns(matrix);
        assertArrayEquals(expectedResult, matrix);
    }
}