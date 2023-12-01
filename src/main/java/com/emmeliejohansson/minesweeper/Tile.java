package com.emmeliejohansson.minesweeper;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    private boolean isMine;
    private Rectangle rectangle = new Rectangle(SIZE - 2, SIZE - 2);
    private final GameModel gameModel;
    private Text text = new Text();
    private Text text2 = new Text();
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
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
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        getChildren().addAll(rectangle, text);
        setTranslateX(xPos * SIZE);
        setTranslateY(yPos * SIZE);
        setOnMouseClicked(this::mouseClickedHandle);
    }

    public boolean isMine() {
        return isMine;
    }
    public Text getText() {
        return text;
    }

    public List<Tile> getNeighbors (){
        List<Tile> result = new ArrayList<>();
        for (int y = yPos - 1; y <= yPos + 1; y++) {
            for (int x = xPos - 1; x <= xPos + 1; x++) {
                if (y < 0 || y >= gameModel.getY_TILES()) continue;
                if (x < 0 || x >= gameModel.getX_TILES()) continue;
                if (x == xPos && y == yPos) continue;
                result.add(gameModel.getGameField()[y][x]);
            }
        }
        return result;
    }

    public void open() {

        gameModel.checkNrOfClosedTiles();
        if (isOpen || gameModel.isGameStopped() || isFlag) return;
        if (isMine) gameModel.gameOver();
        else {
            isOpen = true;
            text.setVisible(true);
            rectangle.setFill(Color.GREY);
            gameModel.decreaseNrOfClosedTiles();
        }
        if (text.getText().isEmpty()) {
            getNeighbors().forEach(Tile::open);
            rectangle.setFill(Color.LIGHTGREY);
        }
    }

    public void mark() {
        if (isOpen || gameModel.isGameStopped()) return;
        if (!isFlag) {
            text.setVisible(true);
            text2.setText(text.getText());
            text.setText(FLAG);
            text.setFill(Color.RED);
            isFlag = true;
        } else {
            text.setVisible(false);
            text.setText(text2.getText());
            text.setFill(Color.BLACK);
            isFlag = false;
        }
        gameModel.checkNrOfClosedTiles();
    }

    public void showMine() {
        text.setText(MINE);
        text.setVisible(true);
        text.setFill(Color.BLACK);
        rectangle.setFill(Color.RED);
    }

    private void mouseClickedHandle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) open();
        else if (event.getButton() == MouseButton.SECONDARY) mark();
    }
}
