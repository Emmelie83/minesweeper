package com.emmeliejohansson.minesweeper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MinesweeperApplication extends Application {

    GameModel gameModel = new GameModel();
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(gameModel.createContent());
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}