package com.example.sodoku;

import com.example.sodoku.views.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Sudoku extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GameView.getInstance();
    }
}