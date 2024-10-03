package com.example.sodoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GameView extends Stage {

    public GameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/sodoku/fxml/sodoku-view.fxml")
        );
        Parent root = loader.load();
        this.setTitle("Sodoku");
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.show();
    }

    public static void getInstance() throws IOException {
        if (GameViewHolder.INSTANCE == null) {
            GameViewHolder.INSTANCE = new GameView();
        }
    }

    private static class GameViewHolder {
        private static GameView INSTANCE;
    }
}