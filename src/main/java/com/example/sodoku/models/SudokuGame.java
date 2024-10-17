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
        if (count == 1) {
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
