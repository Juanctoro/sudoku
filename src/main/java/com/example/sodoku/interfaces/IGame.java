package com.example.sodoku.interfaces;

import com.example.sodoku.controller.GameController;


public interface IGame {

    void initializeMatrix();

    boolean verifyValue(int value, int row, int col);

    void addHelpNumbers(int count, GameController gameController);
    boolean gameFinished();

    int[][] getMatriz();

    int getAttempts();
}
