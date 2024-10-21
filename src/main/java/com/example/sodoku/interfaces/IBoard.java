package com.example.sodoku.interfaces;

/**
 * This interface defines the methods required to manage a Sudoku board,
 * including methods for initialization, solving, and validation.
 */
public interface IBoard {

    /**
     * Creates a copy of the given Sudoku matrix.
     *
     * @param original The original Sudoku matrix to copy.
     * @return A new 2D array that is a copy of the original matrix.
     */
    int[][] copySudoku(int[][] original);

    /**
     * Initializes the given Sudoku matrix to an empty state and prepares it for solving.
     *
     * @param sudoku The Sudoku matrix to initialize.
     */
    void initializeSudoku(int[][] sudoku);

    /**
     * Checks if a number can be safely placed at the specified position in the Sudoku matrix.
     *
     * @param sudoku The current Sudoku matrix.
     * @param row The row index where the number will be placed.
     * @param col The column index where the number will be placed.
     * @param num The number to be placed in the matrix.
     * @return true if the number can be placed without violating Sudoku rules; false otherwise.
     */
    boolean isSafe(int[][] sudoku, int row, int col, int num);

    /**
     * Solves the given Sudoku matrix using a backtracking algorithm.
     *
     * @param sudoku The Sudoku matrix to solve.
     * @return true if the Sudoku was solved successfully; false if no solution exists.
     */
    boolean solveSudoku(int[][] sudoku);

    /**
     * Randomly shuffles the elements of an array.
     *
     * @param array The array to be shuffled.
     */
    void shuffleArray(Integer[] array);

    /**
     * Removes a specified number of elements from random positions in each block of the Sudoku matrix.
     *
     * @param sudoku The Sudoku matrix from which numbers will be removed.
     */
    void removeNumbersFromBlocks(int[][] sudoku);

    /**
     * Retrieves the current Sudoku matrix.
     *
     * @return A 2D array representing the current state of the Sudoku matrix.
     */
    int[][] getSudoku();

    /**
     * Retrieves a full, solved version of the Sudoku matrix.
     *
     * @return A 2D array representing the complete Sudoku board.
     */
    int[][] getBoardFull();
}
