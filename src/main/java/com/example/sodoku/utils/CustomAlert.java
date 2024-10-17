package com.example.sodoku.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * The CustomAlert class provides methods for displaying different types of alerts
 * (error, information, and confirmation) to the user. It simplifies the creation of
 * JavaFX Alert dialogs with predefined settings.
 * @author Juan Toro
 */
public class CustomAlert {

    /**
     * Displays an error alert with the given title and content message.
     *
     * @param title   The title of the error alert.
     * @param content The content message to display in the alert.
     */
    public void makeAlertError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Displays an information alert with the given title and content message.
     *
     * @param title   The title of the information alert.
     * @param content The content message to display in the alert.
     */
    public void makeAlertInformation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation dialog asking the user if they want to proceed.
     * The dialog provides two buttons: "Yes" and "No". If the user selects "Yes",
     * the method returns true; otherwise, it returns false.
     *
     * @return true if the user clicks "Yes", false if the user clicks "No" or cancels the dialog.
     */
    public boolean makeAlertConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas continuar?");

        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == buttonTypeYes;
    }
}
