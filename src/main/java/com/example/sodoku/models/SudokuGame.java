package com.example.sodoku.models;

import com.example.sodoku.controller.GameController;
import javafx.scene.control.TextField;

import java.util.Random;
import java.util.Arrays;

public class SudokuGame {
    private final int[][] matrix;
    private final int attempts;
    private final int help;
    private final boolean gameFinished;

    public SudokuGame(){
        this.matrix = new int[6][6];
        this.attempts = 3;
        this.help = 3;
        this.gameFinished = false;
        initializeMatrix();
    }

    public void initializeMatrix() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                matrix[i][j] = 0;
            }
        }
        System.out.println(Arrays.deepToString(matrix));
    }

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

    public void addHelpNumbers(int count, GameController gameController) {
        Random random = new Random();
        int placed = 0;

        while (placed < count) {
            int row = random.nextInt(6);
            int col = random.nextInt(6);
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

    //Gets attributes
    public int[][] getMatriz() {
        return matrix;
    }
    public int getAttempts() {
        return attempts;
    }
    public int getHelp() {
        return help;
    }
    public boolean isGameFinished() {
        return gameFinished;
    }
}
