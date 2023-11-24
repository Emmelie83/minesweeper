package com.emmeliejohansson.minesweeper;

public class Tile {
    public static final int SIZE = 40;
    private int xPos;
    private int yPos;
    private boolean isMine;
    public int nrOfMineNeighbors;
    private boolean isOpen;
    private boolean isFlag;

    Tile(int xPos, int yPos, boolean isMine) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isMine = isMine;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
    public boolean isMine() {
        return isMine;
    }
}
