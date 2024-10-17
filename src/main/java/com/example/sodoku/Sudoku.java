package com.example.sodoku;

import com.example.sodoku.views.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Sudoku class is the main class of the game.
 * It extends the Application class from JavaFX to launch the graphical application.
 * @author Juan Toro
 */
public class Sudoku extends Application {

    /**
     * Method that creates the game start view.
     *
     * @param stage Used to display the graphical interface.
     * @throws Exception If an error occurs while loading the start view.
     */
    @Override
    public void start(Stage stage) throws Exception {
        GameView.getInstance();
    }
}