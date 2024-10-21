package com.example.sodoku.controller;

import com.example.sodoku.models.SudokuGame;
import com.example.sodoku.utils.CustomAlert;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

/**
 * The GameController class manages the logic and interaction between the game and the user interface (UI)
 * for the Sudoku game. It controls the game state, updates the board, and handles user input.
 * @author Juan Toro
 */
public class GameController {

    private int[][] matrix;
    private SudokuGame sudoku;
    private final CustomAlert customAlert = new CustomAlert();
    private final Map<TextField, ChangeListener<String>> listenersMap = new HashMap<>();

    @FXML
    private GridPane sudokuGrid;

    @FXML
    private Button OnActionButtonPlay, OnActionButtonHelp;


    /**
     * Starts a new game by initializing the Sudoku board, disabling/enabling relevant buttons,
     * and populating the board with helper numbers.
     * It also sets up listeners on each cell for user input.
     */
    @FXML
    public void playGame() {
        if(!customAlert.makeAlertConfirmation()) {
            return;
        }
        OnActionButtonPlay.setText("NUEVO JUEGO");
        game();
        sudokuGrid.getChildren();
        OnActionButtonHelp.setDisable(false);
    }

    /**
     * Initializes the game by resetting the Sudoku grid, creating a new SudokuGame instance,
     * and setting up the initial values in the grid.
     */
    public void game() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField textField = (TextField) getNodeByRowColumnIndex(row, col);
                textField.setText("");
                textField.setDisable(false);
                textField.setEditable(true);
                String original = textField.getStyle();

                if (original.length() < 53){
                    textField.setStyle("");
                } else {
                    int startIndex = 53;
                    int endIndex = original.length();

                    String newStyle = original.substring(0, startIndex) + original.substring(endIndex);
                    textField.setStyle(newStyle);
                }
            }
        }
        this.sudoku = null;// Crea un nuevo juego
        this.matrix = null; // Obtén la nueva matriz

        this.sudoku = new SudokuGame(); // Crea un nuevo juego
        this.matrix = sudoku.getMatrix(); // Obtén la nueva matriz
        removeAllListeners();
        firstValues();
    }

    /**
     * Initializes the first values of the Sudoku grid based on the generated matrix.
     * It populates the grid with preset numbers and adds input validation listeners to each cell.
     */
    private void firstValues(){
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField textField = (TextField) getNodeByRowColumnIndex(row, col);
                if (this.matrix[row][col] != 0) {
                    textField.setText(String.valueOf(this.matrix[row][col]));
                    blockTextField(textField);
                } else {
                    textField.setText("");
                    textField.setDisable(false);
                }
                textField.setTextFormatter(new TextFormatter<String>(change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("[1-6]?")) {
                        return change;
                    }
                    return null;
                }));
                addTextFieldListener(textField, this.sudoku, row, col);
            }
        }
    }

    /**
     * Adds a listener to the TextField to monitor user input.
     * It updates the matrix, validates user input, and changes the background color based on validity.
     *
     * @param textField The TextField to which the listener is added.
     * @param sudoku The Sudoku game logic object.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     */
    private void addTextFieldListener(TextField textField, SudokuGame sudoku, int row, int col) {
        ChangeListener<String> listener = (observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                int value = Integer.parseInt(newValue);
                if (sudoku.verifyValue(value, row, col)) {
                    this.matrix[row][col] = value;
                    String newStyle = "-fx-background-color: #c7ffb8;";
                    textField.setStyle(textField.getStyle() + newStyle);

                    if (sudoku.gameFinished()) {
                        customAlert.makeAlertInformation("Felicidades", "Lograste completar el juego!!");
                        for (Node node : sudokuGrid.getChildren()) {
                            if (node instanceof TextField) {
                                node.setDisable(true);
                            }
                        }
                        OnActionButtonPlay.setDisable(false);
                    }
                } else {
                    String newStyle = "-fx-background-color: #ff9898;";
                    textField.setStyle(textField.getStyle() + newStyle);
                    customAlert.makeAlertError("Error", "Número no válido");
                }
            } else {
                this.matrix[row][col] = 0;
                String original = textField.getStyle();
                if (original.length() < 45){
                    textField.setStyle("");
                } else if (original.length() > 45) {
                    int startIndex = 53;
                    int endIndex = original.length();

                    String newStyle = original.substring(0, startIndex) + original.substring(endIndex);
                    textField.setStyle(newStyle);
                }
            }
        };

        listenersMap.put(textField, listener);
        textField.textProperty().addListener(listener);
    }

    /**
     * Removes all input listeners from the TextFields in the Sudoku grid.
     */
    private void removeAllListeners() {
        for (Map.Entry<TextField, ChangeListener<String>> entry : listenersMap.entrySet()) {
            TextField textField = entry.getKey();
            ChangeListener<String> listener = entry.getValue();
            textField.textProperty().removeListener(listener);
        }
        listenersMap.clear();
    }

    /**
     * Retrieves the node (TextField) from the GridPane based on the given row and column indices.
     *
     * @param row The row index.
     * @param column The column index.
     * @return The Node at the specified row and column.
     */
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

    /**
     * Locks the given TextField, making it non-editable and changing its background color to light gray.
     *
     * @param textField The TextField to be locked.
     */
    public void blockTextField(TextField textField) {
        textField.setEditable(false);
        String additionalStyle = "-fx-background-color: lightgray;";
        textField.setStyle(textField.getStyle() + additionalStyle);
    }

    /**
     * Handles the help button logic, allowing the user to request a single help number.
     * Disables the button when no more help is available.
     */
    public void help (){
        OnActionButtonHelp.setOnMousePressed(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), OnActionButtonHelp);
            scaleTransition.setFromX(1.0);
            scaleTransition.setFromY(1.0);
            scaleTransition.setToX(0.9); // Escala el botón al 90%
            scaleTransition.setToY(0.9);
            scaleTransition.play();
        });
        OnActionButtonHelp.setOnMouseReleased(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), OnActionButtonHelp);
            scaleTransition.setFromX(0.9);
            scaleTransition.setFromY(0.9);
            scaleTransition.setToX(1.0); // Vuelve al tamaño original
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });

        sudoku.addHelpNumbers(this);
    }

    /**
     * Displays the game instructions using an alert box.
     */
    public void instructions() {
        customAlert.makeAlertInformation("Instrucciones", """
                Objetivo
                
                El objetivo es rellenar la cuadrícula 6x6 con números del 1 al 6, asegurándote de que no se repitan en:
                
                1. Filas: Cada número del 1 al 6 debe aparecer una vez por fila.
                2. Columnas: Cada número del 1 al 6 debe aparecer una vez por columna.
                3. Bloques: La cuadrícula está dividida en 6 bloques de 2x3 o 3x2 (según el diseño), y cada número del 1 al 6 debe aparecer una vez en cada bloque.
                
                Reglas
                
                1. No puedes repetir números: Los números no pueden repetirse en ninguna fila, columna o bloque de 2x3/3x2.
                2. Usa las pistas dadas: Algunas celdas ya tienen números preestablecidos. Estas pistas te ayudarán a deducir el resto de los números.
                3. Elige cuidadosamente: Usa la lógica y la deducción para colocar los números en las celdas vacías. No debes adivinar.
                
                Consejos
                
                1. Escanea filas y columnas: Revisa si solo queda un número posible para una fila, columna o bloque.
                2. Bloques de 2x3 o 3x2: Asegúrate de observar las limitaciones en los bloques. Esta es una clave importante para resolver el puzzle.
                3. Usa eliminación: Si en una fila, columna o bloque ya hay varios números, elimina esos números de las posibles opciones para las celdas vacías.
                
                Nota:
                
                Tienes 3 ayudas!!
                
                Suerte! La vas a necesitar!
                """);
    }
}
