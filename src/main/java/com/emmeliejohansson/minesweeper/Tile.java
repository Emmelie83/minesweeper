package com.emmeliejohansson.minesweeper;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Tile extends StackPane {
    public static final int SIZE = 40;
    private final int xPos;
    private final int yPos;
    public boolean isMine;
    private Rectangle rectangle = new Rectangle(SIZE - 2, SIZE - 2);

    private final GameModel gameModel;

    private Text text = new Text();
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";

    public boolean isMine() {
        return isMine;
    }
    public Text getText() {
        return text;
    }

    private boolean isOpen = false;

    private boolean isFlag;

    Tile(int xPos, int yPos, boolean isMine, GameModel gameModel) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isMine = isMine;
        this.gameModel = gameModel;

        rectangle.setFill(Color.DARKGREEN);
        text.setVisible(false);
        text.setText(isMine() ? MINE : "");
        text.setFont(Font.font("System", FontWeight.BOLD, 20));
        getChildren().addAll(rectangle, text);
        setTranslateX(xPos * SIZE);
        setTranslateY(yPos * SIZE);
        setOnMouseClicked(event ->
        {
            if (event.getButton() == MouseButton.PRIMARY)
                open();
            else if (event.getButton() == MouseButton.SECONDARY)
                mark();
        });
    }

    public List<Tile> getNeighbors (){
        List<Tile> result = new ArrayList<>();
        for (int y = yPos - 1; y <= yPos + 1; y++) {
            for (int x = xPos - 1; x <= xPos + 1; x++) {
                if (y < 0 || y >= gameModel.getY_TILES()) {
                    continue;
                }
                if (x < 0 || x >= gameModel.getX_TILES()) {
                    continue;
                }
                if (x == xPos && y == yPos) {
                    continue;
                }
                result.add(gameModel.getGameField()[y][x]);
            }
        }
        return result;
    }

    public void open() {
        if (isOpen || gameModel.isGameStopped()) return;
        isOpen = true;
        text.setVisible(true);
        rectangle.setFill(Color.LIGHTGREY);
        if (isMine) {
            gameModel.gameOver();
        }
        if (text.getText().isEmpty()) {
            getNeighbors().forEach(Tile::open);
        }
    }

    public void mark() {
        if (isOpen) return;
        if (!isFlag) {
            text.setVisible(true);
            text.setText(FLAG);
            isFlag = true;
        }
    }
}
