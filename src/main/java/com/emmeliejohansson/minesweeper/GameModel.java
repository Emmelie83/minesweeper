package com.emmeliejohansson.minesweeper;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;



public class GameModel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double MINE_PROBABILITY = 0.2;
    private boolean isGameStopped = false;
    private final int Y_TILES = HEIGHT / Tile.SIZE;
    private final int X_TILES = WIDTH / Tile.SIZE;
    private Tile[][] gameField = new Tile[Y_TILES][X_TILES];
    private int nrOfClosedTiles = X_TILES * Y_TILES;
    private int nrOfMinesOnField;
    private Label label = new Label();
    private Pane root = new Pane();
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
        root.setPrefSize(GameModel.WIDTH, GameModel.HEIGHT);
        initializeGameField();
        populateAdjacentMineCounts();
        root.getChildren().add(label);
        configureLabel();
        return root;
    }

    private void configureLabel() {
        label.setText("Game over!");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        label.setTextFill(Color.YELLOW);
        label.setPrefSize(WIDTH, HEIGHT);
        label.setAlignment(Pos.CENTER);
        label.setVisible(false);
    }
    public void initializeGameField() {
        for (int yPos = 0; yPos < Y_TILES; yPos++) {
            for (int xPos = 0; xPos < X_TILES; xPos++) {
                Tile tile = createTile(xPos, yPos);
                root.getChildren().add(tile);
            }
        }
    }

    private Tile createTile(int xPos, int yPos) {
        Tile tile = new Tile(xPos, yPos, Math.random() < MINE_PROBABILITY, this);
        if (tile.isMine()) {
            nrOfMinesOnField++;
        }
        gameField[yPos][xPos] = tile;
        return tile;
    }

    public void populateAdjacentMineCounts() {
        for (int yPos = 0; yPos < Y_TILES; yPos++) {
            for (int xPos = 0; xPos < X_TILES; xPos++) {
                Tile tile = gameField[yPos][xPos];
                if (tile.isMine()) {
                    continue;
                }
                long mines = tile.getNeighbors().stream().filter(Tile::isMine).count();
                if (mines > 0) tile.getText().setText(String.valueOf(mines));
            }
        }
    }

    public void checkNrOfClosedTiles() {
        if (nrOfClosedTiles == nrOfMinesOnField) gameWon();
    }
    public void decreaseNrOfClosedTiles() {
        nrOfClosedTiles--;
    }
    public void gameOver() {
        for (int yPos = 0; yPos < Y_TILES; yPos++)
            for (int xPos = 0; xPos < X_TILES; xPos++) {
                Tile tile = gameField[yPos][xPos];
                if (tile.isMine()) tile.showMine();
            }
        label.setVisible(true);
        isGameStopped = true;
    }
    public void gameWon() {
        label.setText("You won!");
        label.setVisible(true);
        isGameStopped = true;

    }
}