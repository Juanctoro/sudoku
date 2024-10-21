package com.example.sodoku.interfaces;

import com.example.sodoku.controller.GameController;

/**
 * This interface establishes the methods to manage verification, add number, etc.
 * @author Juan Toro
 */
public interface IGame {

    /**
     * Verifies if the given value is valid for a specific position in the game matrix.
     *
     * @param value The value entered by the user.
     * @param row The row in the matrix where the value will be placed.
     * @param col The column in the matrix where the value will be placed.
     * @return true if the value can be
     */
    boolean verifyValue(int value, int row, int col);

    /**
     * Adds helper numbers to the Sudoku board, filling random empty cells.
     * @param gameController The game controller that manages the Sudoku game logic.
     */
    void addHelpNumbers(GameController gameController);

    /**
     * Checks if the game has finished. The game is considered finished if all cells
     * are filled and the values are correct according to Sudoku rules.
     *
     * @return true if the game is finished, false if not.
     */
    boolean gameFinished();

    /**
     * Returns the sudoku matrix.
     *
     * @return Returns the sudoku matrix.
     */
    int[][] getMatrix();
}