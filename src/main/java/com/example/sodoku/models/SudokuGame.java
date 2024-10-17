package com.example.sodoku.models;

import com.example.sodoku.controller.GameController;
import com.example.sodoku.interfaces.IGame;
import javafx.scene.control.TextField;
import java.util.Random;

/**
 * Represents a 6x6 Sudoku game implementing the IGame interface.
 * This class manages the Sudoku game logic, including verifying values,
 * adding help numbers, and checking if the game is finished.
 * @author Juan Toro
 */
public class SudokuGame implements IGame {
    private final int[][] matrix;
    private int aids;

    /**
     * Initializes the Sudoku game with an empty 6x6 matrix and sets the number of aids (help numbers) to 3.
     */
    public SudokuGame(){
        this.matrix = new int[6][6];
        this.aids = 3;
        initializeMatrix();
    }

    /**
     * Initializes the Sudoku matrix, setting all values to 0, indicating an empty board.
     */
    @Override
    public void initializeMatrix() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    /**
     * Verifies whether the given value can be placed at the specified row and column
     * in the Sudoku board without violating the rules of Sudoku (no duplicates in the same row, column, or block).
     *
     * @param value The value to be placed (between 1 and 6).
     * @param row The row index where the value is to be placed (0-based).
     * @param col The column index where the value is to be placed (0-based).
     * @return true if the value can be placed in the specified location, false otherwise.
     */
    @Override
    public boolean verifyValue(int value, int row, int col) {
        for (int j = 0; j < 6; j++) {
            if (matrix[row][j] == value) {
                return false;
            }
        }
        for (int i = 0; i < 6; i++) {
            if (matrix[i][col] == value) {
                return false;
            }
        }

        int blockRowStart = (row / 2) * 2;
        int blockColStart = (col / 3) * 3;
        for (int i = blockRowStart; i < blockRowStart + 2; i++) {
            for (int j = blockColStart; j < blockColStart + 3; j++) {
                if (matrix[i][j] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds help numbers to the Sudoku board. If the count is 1 and there are aids available,
     * it adds a single random valid number. Otherwise, it adds random valid numbers in each block.
     *
     * @param count The number of help numbers to be added.
     * @param gameController The controller responsible for managing the game logic and UI updates.
     */
    @Override
    public void addHelpNumbers(int count, GameController gameController) {
        if (count == 1 && aids > 0) {
            Random randomForHelp = new Random();
            int placed = 0;

            while (placed < count) {
                int row = randomForHelp.nextInt(6);
                int col = randomForHelp.nextInt(6);
                int valueForHelp = randomForHelp.nextInt(1, 7);

                if (matrix[row][col] == 0 && verifyValue(valueForHelp, row, col)) {
                    TextField textField = (TextField) gameController.getNodeByRowColumnIndex(row, col);
                    if (textField != null) {
                        textField.setText(String.valueOf(valueForHelp));
                    }
                    placed++;
                    aids--;
                }
            }
        } else {
            Random random = new Random();
            for (int blockRow = 0; blockRow < 3; blockRow++) {
                for (int blockCol = 0; blockCol < 2; blockCol++) {
                    int placed = 0;

                    while (placed < 2) {
                        int row = blockRow * 2 + random.nextInt(2);
                        int col = blockCol * 3 + random.nextInt(3);
                        int value = random.nextInt(1, 7);

                        if (matrix[row][col] == 0 && verifyValue(value, row, col)) {
                            matrix[row][col] = value;

                            TextField textField = (TextField) gameController.getNodeByRowColumnIndex(row, col);
                            if (textField != null) {
                                textField.setText(String.valueOf(value));
                                gameController.lockTextField(textField);
                            }
                            placed++;
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if the Sudoku game has been completed, i.e., if all cells are filled.
     *
     * @return true if all cells are filled and the game is finished, false otherwise.
     */
    @Override
    public boolean gameFinished(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (matrix[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the current state of the Sudoku board as a 2D array.
     *
     * @return The 6x6 matrix representing the current state of the game.
     */
    @Override
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Returns the number of remaining aids (help numbers) available to the player.
     *
     * @return The number of aids available.
     */
    @Override
    public int getAids() {
        return aids;
    }
}
