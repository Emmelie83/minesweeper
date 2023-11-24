package com.emmeliejohansson.minesweeper;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int Y_TILES = HEIGHT / Tile.SIZE;
    private static final int X_TILES = WIDTH / Tile.SIZE;
    private Tile[][] gameField = new Tile[Y_TILES][X_TILES];
    private int countMinesOnField;
    private int countFlags;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";

    public void createGame() {
        for (int yPos = 0; yPos < Y_TILES; yPos++) {
            for (int xPos = 0; xPos < X_TILES; xPos++) {
                boolean isMine = Math.random() < 0.2;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[yPos][xPos] = new Tile(xPos, yPos, isMine);
            }
            countMineNeighbors();
            countFlags = countMinesOnField;
        }
    }

    private List<Tile> getNeighbors (Tile gameCell){
        List<Tile> result = new ArrayList<>();
        for (int y = gameCell.getYPos() - 1; y <= gameCell.getYPos() + 1; y++) {
            for (int x = gameCell.getXPos() - 1; x <= gameCell.getXPos() + 1; x++) {
                if (y < 0 || y >= Y_TILES) {
                    continue;
                }
                if (x < 0 || x >= X_TILES) {
                    continue;
                }
                if (x == gameCell.getXPos() && y == gameCell.getYPos()) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }

    private void countMineNeighbors() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = gameField[y][x];

                if (!tile.isMine()) {
                    tile.nrOfMineNeighbors = Math.toIntExact(getNeighbors(tile).
                            stream().filter(neighbor -> neighbor.isMine()).count());
                }
            }
        }
    }


}