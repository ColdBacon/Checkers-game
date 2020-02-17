package sample;

import javafx.scene.Group;

public class AIgame {

    private int TILE_SIZE;
    private int WIDTH;
    private int HEIGHT;
    private Tile[][] board;
    private String color1, color2;
    private boolean lastColor;
    private int WHITEcounter;
    private int REDcounter;

    public AIgame(int tile, int size, String col1, String col2,boolean AI){
        this.TILE_SIZE = tile;
        this.WIDTH = size;
        this.HEIGHT = size;
        this.board = new Tile[WIDTH+1][HEIGHT+1];
        this.color1 = col1;
        this.color2 = col2;
        this.lastColor = false;
        this.WHITEcounter = (int) (size * 1.5);
        this.REDcounter = (int) (size * 1.5);
    }

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    public int getHEIGHT() {
        return HEIGHT;
    }
}
