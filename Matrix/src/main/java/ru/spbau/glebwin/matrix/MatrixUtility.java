package ru.spbau.glebwin.matrix;

/**
 * Utility class that provides functionality for working with matrices
 */
public class MatrixUtility {
    /**
     * Outputs matrix in a spiral.
     * It starts from the center and continues clockwise
     * @param matrix Square matrix with odd side length
     */
    static public void printSpirally(int[][] matrix) throws IllegalArgumentException {
        if (matrix == null || !isRectangle(matrix)
                || matrix.length != matrix[0].length || (matrix.length & 1) == 0) {
            throw new IllegalArgumentException("Matrix must be a square with odd side length");
        }

        int layersNum = (matrix.length + 1) / 2;
        // central element
        System.out.print(matrix[matrix.length / 2][matrix.length / 2]);

        for (int layer = 1; layer < layersNum; layer++) {
            for (int i = 0; i < 2*layer; i++) {
                System.out.print(" " + matrix[layersNum - layer + i][layersNum + layer - 1]);
            }
            for (int i = 0; i < 2*layer; i++) {
                System.out.print(" " + matrix[layersNum + layer - 1][layersNum + layer - i - 2]);
            }
            for (int i = 0; i < 2*layer; i++) {
                System.out.print(" " + matrix[layersNum + layer - i - 2][layersNum - layer - 1]);
            }
            for (int i = 0; i < 2*layer; i++) {
                System.out.print(" " + matrix[layersNum - layer - 1][layersNum - layer + i]);
            }
        }
    }

    /**
     * Sort columns of the matrix by their first elements
     * Uses selection sort O(rowLength^2)
     * It takes O(rowLength*rowNum) in any case to rearrange the columns that makes it acceptable in most cases
     */
    static public void sortColumns(int[][] matrix) throws IllegalArgumentException {
        if (matrix == null || !isRectangle(matrix)) {
            throw new IllegalArgumentException("Matrix must be a valid rectangle");
        }

        for (int prefix = 0; prefix < matrix[0].length; prefix++) {
            int minColumn = prefix;
            for (int i = prefix; i < matrix[0].length; i++) {
                if (matrix[0][i] < matrix[0][minColumn]) {
                    minColumn = i;
                }
            }

            for (int i = 0; i < matrix.length; i++) {
                int temp = matrix[i][prefix];
                matrix[i][prefix] = matrix[i][minColumn];
                matrix[i][minColumn] = temp;
            }
        }

        for (int[] row : matrix) {
            for (int x : row) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }

    /**
     * Check whether all rows of the matrix are of the same length
     */
    static private boolean isRectangle(int[][] matrix) {
        if (matrix == null) {
            return false;
        }

        for (int[] row : matrix) {
            if (row == null || row.length != matrix[0].length) {
                return false;
            }
        }

        return true;
    }
}
