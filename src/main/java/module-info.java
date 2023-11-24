module com.example.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.emmeliejohansson.minesweeper to javafx.fxml;
    exports com.emmeliejohansson.minesweeper;
}