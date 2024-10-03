module com.example.sodoku {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.example.sodoku.controller to javafx.fxml;
    exports com.example.sodoku;
}
