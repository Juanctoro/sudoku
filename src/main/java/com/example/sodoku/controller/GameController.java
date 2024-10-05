package com.example.sodoku.controller;

import com.example.sodoku.models.SudokuGame;
import com.example.sodoku.utils.CustomAlert;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

public class GameController {

    private int[][] matrix;
    private SudokuGame sudoku;
    private final CustomAlert customAlert = new CustomAlert();

    @FXML
    private GridPane sudokuGrid;

    @FXML
    private Button OnActionButtonPlay;

    @FXML
    public void playGame() {
        OnActionButtonPlay.setDisable(true);
        this.sudoku = new SudokuGame();
        sudoku.addHelpNumbers(12, this);
        this.matrix = sudoku.getMatriz();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField textField = (TextField) getNodeByRowColumnIndex(row, col);
                textField.setText(matrix[row][col] != 0 ? String.valueOf(matrix[row][col]) : "");
                textField.setTextFormatter(new TextFormatter<String>(change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("[1-6]?")) {
                        return change;
                    }
                    return null;
                }));
                addTextFieldListener(textField, sudoku, row, col);
            }
        }
    }

    private void addTextFieldListener(TextField textField, SudokuGame sudoku, int row, int col) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(newValue);
                    if (sudoku.verifyValue(value, row, col)) {
                        matrix[row][col] = value;
                        textField.setStyle("-fx-background-color: #d3ffc8 ;");
                        if (sudoku.gameFinished()){
                            customAlert.makeAlertInformation("Felicidades", "Lograste completar el juego!!");
                            for (Node node : sudokuGrid.getChildren()) {
                                if (node instanceof TextField) {
                                    node.setDisable(true);
                                }
                            }
                            OnActionButtonPlay.setDisable(false);
                        }
                    } else if(!sudoku.verifyValue(value, row, col)) {
                        textField.setStyle("-fx-background-color: #ff9898 ;");
                        customAlert.makeAlertError("Error", "Numero no valido");
                    }
                } catch (NumberFormatException e) {
                    matrix[row][col] = 0;
                    textField.setText("");
                }
            } else {
                matrix[row][col] = 0;
            }
        });
    }

    public Node getNodeByRowColumnIndex(int row, int column) {
        for (Node node : sudokuGrid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);

            if (rowIndex == null) {
                rowIndex = 0;
            }
            if (columnIndex == null) {
                columnIndex = 0;
            }
            if (rowIndex == row && columnIndex == column) {
                return node;
            }
        }
        return null;
    }

    public void lockTextField(TextField textField) {
        textField.setEditable(false);
        textField.setStyle("-fx-background-color: lightgray;");
    }

    public void help (){
        sudoku.addHelpNumbers(1, this);
    }
}
