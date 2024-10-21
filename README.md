# Sudoku 6x6 en Java

Este proyecto es un juego de Sudoku 6x6 desarrollado en Java utilizando JavaFX para la interfaz gráfica de usuario (GUI). El objetivo del juego es completar el tablero de Sudoku siguiendo las reglas estándar, donde cada fila, columna y bloque 2x3 debe contener los números del 1 al 6 sin repetir.

## Características

- **Interfaz gráfica en JavaFX**: Interacción visual con el tablero de Sudoku mediante `TextField`.
- **Validación de entradas**: Comprobación de si los números introducidos son válidos según las reglas del Sudoku.
- **Ayuda visual**: Posibilidad de añadir números de ayuda en el tablero.
- **Verificación de finalización**: El juego verifica automáticamente si el tablero ha sido completado correctamente.
- **Resaltado de bloques**: Los bloques del tablero se remarcan con líneas que delimitan únicamente los bloques, no las celdas individuales.

## Estructura del proyecto

- `SudokuGame.java`: Clase principal que implementa la lógica del juego y las reglas del Sudoku.
- `GameController.java`: Controlador que maneja la interacción entre la lógica del juego y la interfaz gráfica de usuario.
- `sudoku.fxml`: Archivo FXML que define el diseño de la interfaz gráfica.

## Instalación

1. Clona este repositorio:

   ```bash
   git clone https://github.com/Juanctoro/sudoku.git
   
## Uso 
- Introduce los números del 1 al 6 en el tablero siguiendo las reglas del Sudoku.
- Usa las ayudas para completar el juego si es necesario.
- El juego te notificará cuando hayas completado el tablero correctamente.
- Si no puedes completar el tablero, puedes crear uno nuevo
