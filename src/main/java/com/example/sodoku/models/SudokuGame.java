package com.example.sodoku.models;

import com.example.sodoku.controller.GameController;
import com.example.sodoku.interfaces.IGame;
import com.example.sodoku.utils.CustomAlert;
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
    private final int[][] boardFull;
    CustomAlert alert = new CustomAlert();

    /**
     * Initializes the Sudoku game with an empty 6x6 matrix and sets the number of aids (help numbers) to 3.
     */
    public SudokuGame(){
        BoardModel boardModel = new BoardModel();
        this.matrix = boardModel.getSudoku();
        this.boardFull = boardModel.getBoardFull();
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
    public boolean verifyValue(int value, int row, int col) {
        for (int j = 0; j < 6; j++) {
            if (matrix[row][j] == value || matrix[j][col] == value) {
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
     * Adds help numbers to the Sudoku board.
     * Otherwise, it adds random valid numbers in each block.
     *
     * @param gameController The controller responsible for managing the game logic and UI updates.
     */
    public void addHelpNumbers( GameController gameController) {
        Random randomForHelp = new Random();
        int counter = 0;
        for (int i = 0; i<6; i++){
            for (int j = 0; j<6; j++){
                if (matrix[i][j] == 0){
                    counter++;
                }
            }
        }
        boolean num = false;
        if(counter > 1) {
            while (!num) {
                int row = randomForHelp.nextInt(6);
                int col = randomForHelp.nextInt(6);
                int valueForHelp = this.boardFull[row][col];

                if (matrix[row][col] == 0) {
                    TextField textField = (TextField) gameController.getNodeByRowColumnIndex(row, col);
                    if (textField != null) {
                        textField.setText(String.valueOf(valueForHelp));
                        textField.setEditable(true);
                    }
                    num = true;
                }
            }
        } else {
            alert.makeAlertError("Error", "No puedes usar la ayuda para ganar.");
        }
    }

    /**
     * Checks if the Sudoku game has been completed, i.e., if all cells are filled.
     *
     * @return true if all cells are filled and the game is finished, false otherwise.
     */
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
     * Returns the sudoku matrix.
     *
     * @return Returns the sudoku matrix.
     */
    public int[][] getMatrix() {
        return matrix;
    }
}
