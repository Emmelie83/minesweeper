package com.emmeliejohansson.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class GameController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private static final GameModel gameModel = new GameModel();


    public void initialize() {
    }


}