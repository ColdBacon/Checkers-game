package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;
import java.util.HashMap;

public class Piece extends StackPane {

    private PieceType type;
    private int TILE_SIZE;
    private String color1, color2;

    private double mouseX, mouseY;
    private double oldX, oldY;
    private HashMap<Integer,Integer> neighbours;

    public PieceType getType() {
        return type;
    }

    public boolean getPlayer(){return type.playerType;}

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public Piece(PieceType type, int TILE_SIZE, int x, int y, String col1, String col2) {
        this.type = type;
        this.TILE_SIZE = TILE_SIZE;
        this.color1 = col1;
        this.color2 = col2;

        move(x, y, type);

        Ellipse bg = new Ellipse(TILE_SIZE * 0.31, TILE_SIZE * 0.25);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.31 * 2) / 2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.25 * 2) / 2 + TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.31, TILE_SIZE * 0.25);

        if (color1.equals("WHITE") && color2.equals("RED")){
            if (type == PieceType.WHITE) ellipse.setFill(Color.WHITE);
            else if (type == PieceType.RED) ellipse.setFill(Color.RED);
            else if (type == PieceType.QUEEN_WHITE) ellipse.setFill(Color.WHITE);
            else if (type == PieceType.QUEEN_RED) ellipse.setFill(Color.RED);}

        else if (color1.equals("WHITE") && color2.equals("BLACK")){
            if (type == PieceType.WHITE) ellipse.setFill(Color.WHITE);
            else if (type == PieceType.RED) ellipse.setFill(Color.valueOf("#2a2a2b"));
            else if (type == PieceType.QUEEN_WHITE) ellipse.setFill(Color.WHITE);
            else if (type == PieceType.QUEEN_RED) ellipse.setFill(Color.valueOf("#2a2a2b"));}

        else if (color1.equals("RED") && color2.equals("BLACK")){
            if (type == PieceType.WHITE) ellipse.setFill(Color.RED);
            else if (type == PieceType.RED) ellipse.setFill(Color.valueOf("#2a2a2b"));
            else if (type == PieceType.QUEEN_WHITE) ellipse.setFill(Color.RED);
            else if (type == PieceType.QUEEN_RED) ellipse.setFill(Color.valueOf("#2a2a2b"));}

        if (type == PieceType.QUEEN_RED || type == PieceType.QUEEN_WHITE){
            ellipse.setStroke(Color.GOLD);
            bg.setFill(Color.GOLD);
            bg.setStroke(Color.GOLD);
        }
        else {
            ellipse.setStroke(Color.BLACK);
            bg.setFill(Color.BLACK);
            bg.setStroke(Color.BLACK);
        }

        ellipse.setStrokeWidth(TILE_SIZE * 0.05);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.31 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.25 * 2) / 2);

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    public void move(int x, int y,PieceType type) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
        /*
        if (type == PieceType.RED){
            this.neighbours.put(x+1,y+1);
            this.neighbours.put(x+1,y-1);
        }
        else if (type == PieceType.WHITE){
            this.neighbours.put(x-1,y+1);
            this.neighbours.put(x-1,y-1);
        }
        else {
            this.neighbours.put(x-1,y+1);
            this.neighbours.put(x-1,y-1);
            this.neighbours.put(x+1,y-1);
            this.neighbours.put(x+1,y-1);
        }

         */
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
