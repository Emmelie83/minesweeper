package com.emmeliejohansson.minesweeper;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;


public class GameModel {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    private boolean isGameStopped = false;

    private final int Y_TILES = HEIGHT / Tile.SIZE;

    private final int X_TILES = WIDTH / Tile.SIZE;

    private Tile[][] gameField = new Tile[Y_TILES][X_TILES];

    public int getY_TILES() {
        return Y_TILES;
    }

    public int getX_TILES() {
        return X_TILES;
    }

    public boolean isGameStopped() {
        return isGameStopped;
    }

    public Tile[][] getGameField() {
        return gameField;
    }


    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(GameModel.WIDTH, GameModel.HEIGHT);

        for (int yPos = 0; yPos < Y_TILES; yPos++) {
            for (int xPos = 0; xPos < X_TILES; xPos++) {
                Tile tile = new Tile(xPos, yPos, Math.random() < 0.2, this);
                gameField[yPos][xPos] = tile;
                root.getChildren().add(tile);
            }
        }

        for (int yPos = 0; yPos < Y_TILES; yPos++) {
            for (int xPos = 0; xPos < X_TILES; xPos++) {
                Tile tile = gameField[yPos][xPos];
                if (tile.isMine()) continue;
                long mines = tile.getNeighbors().stream().filter(Tile::isMine).count();
                if (mines > 0)  tile.getText().setText(String.valueOf(mines));
            }
        }
        return root;
    }

    public void gameOver() {
        isGameStopped = true;
    }
}