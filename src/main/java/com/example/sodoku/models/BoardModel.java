package com.example.sodoku.models;

import com.example.sodoku.interfaces.IBoard;

import java.util.Arrays;
import java.util.Random;

/**
 * This class implements the IBoard interface and provides methods to manage
 * a Sudoku board, including initialization, solving, and number removal.
 */
public class BoardModel implements IBoard {

    private static final int sizePref = 6;
    private static final int rowsB = 2;
    private static final int colsB = 3;
    private final int[][] sudoku;
    private final int[][] boardFull;

    /**
     * Initializes a new BoardModel instance, creating an empty Sudoku matrix,
     * filling it with a solved version, and removing numbers to create a playable board.
     */
    public BoardModel() {
        this.sudoku = new int[sizePref][sizePref];

        initializeSudoku(this.sudoku);
        this.boardFull = copySudoku(this.sudoku);
        removeNumbersFromBlocks(this.sudoku);
    }

    /**
     * Creates a copy of the original Sudoku matrix.
     *
     * @param original The original Sudoku matrix to copy.
     * @return A new 2D array that is a copy of the original matrix.
     */
    public int[][] copySudoku(int[][] original) {
        int[][] copy = new int[sizePref][sizePref];
        for (int i = 0; i < sizePref; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, sizePref);
        }
        return copy;
    }

    /**
     * Initializes the given Sudoku matrix to an empty state and solves it.
     *
     * @param sudoku The Sudoku matrix to initialize.
     */
    public void initializeSudoku(int[][] sudoku) {
        for (int i = 0; i < sizePref; i++) {
            Arrays.fill(sudoku[i], 0);
        }
        solveSudoku(sudoku);
    }

    /**
     * Checks if a number can be safely placed at the specified position in the Sudoku matrix.
     *
     * @param sudoku The current Sudoku matrix.
     * @param row The row index where the number will be placed.
     * @param col The column index where the number will be placed.
     * @param num The number to be placed in the matrix.
     * @return true if the number can be placed without violating Sudoku rules; false otherwise.
     */
    public boolean isSafe(int[][] sudoku, int row, int col, int num) {
        for (int x = 0; x < sizePref; x++) {
            if (sudoku[row][x] == num) {
                return false;
            }
        }

        for (int x = 0; x < sizePref; x++) {
            if (sudoku[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % rowsB;
        int startCol = col - col % colsB;
        for (int i = 0; i < rowsB; i++) {
            for (int j = 0; j < colsB; j++) {
                if (sudoku[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Solves the given Sudoku matrix using a backtracking algorithm.
     *
     * @param sudoku The Sudoku matrix to solve.
     * @return true if the Sudoku was solved successfully; false if no solution exists.
     */
    public boolean solveSudoku(int[][] sudoku) {
        for (int row = 0; row < sizePref; row++) {
            for (int col = 0; col < sizePref; col++) {
                if (sudoku[row][col] == 0) {
                    Integer[] nums = new Integer[sizePref];
                    for (int i = 0; i < sizePref; i++) {
                        nums[i] = i + 1;
                    }
                    shuffleArray(nums);

                    for (int num : nums) {
                        if (isSafe(sudoku, row, col, num)) {
                            sudoku[row][col] = num;

                            if (solveSudoku(sudoku)) {
                                return true;
                            }

                            sudoku[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Randomly shuffles the elements of an array.
     *
     * @param array The array to be shuffled.
     */
    public void shuffleArray(Integer[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    /**
     * Removes a specified number of elements from random positions in each block of the Sudoku matrix.
     *
     * @param sudoku The Sudoku matrix from which numbers will be removed.
     */
    public void removeNumbersFromBlocks(int[][] sudoku) {
        Random rand = new Random();

        for (int blockRow = 0; blockRow < sizePref / rowsB; blockRow++) {
            for (int blockCol = 0; blockCol < sizePref / colsB; blockCol++) {
                int numbersToRemove = 4;

                boolean[][] positions = new boolean[rowsB][colsB];
                for (int i = 0; i < rowsB; i++) {
                    for (int j = 0; j < colsB; j++) {
                        positions[i][j] = true;
                    }
                }

                for (int i = 0; i < numbersToRemove; i++) {
                    int randomPos;
                    do {
                        randomPos = rand.nextInt(rowsB * colsB);
                    } while (!positions[randomPos / colsB][randomPos % colsB]);

                    positions[randomPos / colsB][randomPos % colsB] = false;

                    sudoku[blockRow * rowsB + randomPos / colsB][blockCol * colsB + randomPos % colsB] = 0;
                }
            }
        }
    }

    /**
     * Retrieves the current Sudoku matrix.
     *
     * @return A 2D array representing the current state of the Sudoku matrix.
     */
    public int[][] getSudoku() {
        return this.sudoku;
    }

    /**
     * Retrieves a full, solved version of the Sudoku matrix.
     *
     * @return A 2D array representing the complete Sudoku board.
     */
    public int[][] getBoardFull() {
       return this.boardFull;
    }
}