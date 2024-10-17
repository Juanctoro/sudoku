package com.example.sodoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Represents the view for the Sudoku game using JavaFX. This class extends {@link Stage}
 * and is responsible for loading the FXML layout for the game's UI.
 * @author Juan Toro
 */
public class GameView extends Stage {

    /**
     * Constructs the GameView by loading the FXML layout for the Sudoku game.
     * This method sets the window title to "Sudoku" and displays the game scene.
     *
     * @throws IOException if there is an error loading the FXML file.
     */
    public GameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/sodoku/fxml/sodoku-view.fxml")
        );
        Parent root = loader.load();
        this.setTitle("Sudoku");
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.show();
    }

    /**
     * Returns the instance of the GameView. If it doesn't exist, it creates a new instance.
     * This method ensures that only one instance of GameView exists (Singleton pattern).
     *
     * @throws IOException if there is an error creating the GameView instance.
     */
    public static void getInstance() throws IOException {
        if (GameViewHolder.INSTANCE == null) {
            GameViewHolder.INSTANCE = new GameView();
        }
    }

    /**
     * Private static class responsible for holding the single instance of GameView.
     * This is part of the Singleton pattern implementation.
     */
    private static class GameViewHolder {
        private static GameView INSTANCE;
    }
}