package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.event.InputEvent;

public class Checkers {

    private int TILE_SIZE;
    private int WIDTH;
    private int HEIGHT;
    private Tile[][] board;
    private String color1, color2;
    private boolean lastColor;
    private boolean AIgame;
    private int WHITEcounter;
    private int REDcounter;

    public Checkers(int tile, int size, String col1, String col2,boolean AI){
        this.TILE_SIZE = tile;
        this.WIDTH = size;
        this.HEIGHT = size;
        this.board = new Tile[WIDTH+1][HEIGHT+1];
        this.color1 = col1;
        this.color2 = col2;
        this.lastColor = false;
        this.AIgame = AI;
        this.WHITEcounter = (int) (size * 1.5);
        this.REDcounter = (int) (size * 1.5);
    }

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    public int getHEIGHT() {
        return HEIGHT;
    }

    public Pane createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        for (int y = 1; y < HEIGHT+1; y++) {
            for (int x = 1; x < WIDTH+1; x++) {
                Tile tile = new Tile(TILE_SIZE,(x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 3 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.RED, x, y);
                }

                if (y >= WIDTH-2 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }
        return root;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY) {

        if(this.lastColor == piece.getType().playerType){

            if ((piece.getType() == PieceType.WHITE || piece.getType() == PieceType.RED) && (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0)) {
                return new MoveResult(MoveType.NONE);
            }

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
                if (newY == 1 && piece.getType() == PieceType.WHITE) {

                    this.lastColor = !this.lastColor;

                    return new MoveResult(MoveType.QUEEN1); }

                else if (newY == this.getHEIGHT() && piece.getType() == PieceType.RED) {

                    this.lastColor = !this.lastColor;

                    return new MoveResult(MoveType.QUEEN2); }

                else {

                    this.lastColor = !this.lastColor;

                    return new MoveResult(MoveType.NORMAL); }

            } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {

                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;

                Group movePath = new Group();

                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getPlayer() != piece.getPlayer()) {
                    MoveResult currentMove =  new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
                    movePath.getChildren().add(board[x1][y1].getPiece());
                    return currentMove;
                }
            }
            else if (piece.getType() == PieceType.QUEEN_RED || piece.getType() == PieceType.QUEEN_WHITE){
                if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
                    return new MoveResult(MoveType.NONE);
                }
                else if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir || newY - y0 == piece.getType().moveDir * -1) {

                    this.lastColor = !this.lastColor;

                    return new MoveResult(MoveType.NORMAL);
                }
                else if (Math.abs(newX - x0) == 2 && (newY - y0 == piece.getType().moveDir * 2 || newY - y0 == piece.getType().moveDir * -2)){
                    int x1 = x0 + (newX - x0) / 2;
                    int y1 = y0 + (newY - y0) / 2;

                    Group movePath = new Group();

                    if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getPlayer() != piece.getPlayer()) {

                        this.lastColor = !this.lastColor;

                        MoveResult currentMove =  new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
                        movePath.getChildren().add(board[x1][y1].getPiece());
                        return currentMove;
                    }
                }
            }
        }
        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    private Piece Moving(Piece piece, int newX, int newY){

        MoveResult result;

        if (newX < 1 || newY < 1 || newX >= WIDTH+1 || newY >= HEIGHT+1) {
            result = new MoveResult(MoveType.NONE);
        }
        else if (this.AIgame && this.lastColor){
            //tutaj trzeba stworzyc jakies ruchy dla komputera xd
            result = new MoveResult(MoveType.NONE);
        }
        else {
            result = tryMove(piece, newX, newY);
        }

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        switch (result.getType()) {
            case NONE:
                piece.abortMove();
                break;
            case NORMAL:
                piece.move(newX, newY,piece.getType());
                board[x0][y0].setPiece(null);
                board[newX][newY].setPiece(piece);
                break;
            case KILL:
                piece.move(newX, newY,piece.getType());
                board[x0][y0].setPiece(null);
                board[newX][newY].setPiece(piece);

                Piece otherPiece = result.getPiece();
                board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                pieceGroup.getChildren().remove(otherPiece);
                if(piece.getType() == PieceType.WHITE || piece.getType() == PieceType.QUEEN_WHITE) this.REDcounter -= 1;
                else if(piece.getType() == PieceType.RED || piece.getType() == PieceType.QUEEN_RED ) this.WHITEcounter -= 1;

                if (newY == 1 && piece.getType() == PieceType.WHITE) {
                    Piece newPiece;
                    newPiece = makePiece(PieceType.QUEEN_WHITE, newX, newY);
                    pieceGroup.getChildren().remove(piece);
                    pieceGroup.getChildren().add(newPiece);
                    board[newX][newY].setPiece(newPiece);
                }
                else if (newY == this.getHEIGHT() && piece.getType() == PieceType.RED){
                    Piece newPiece2;
                    newPiece2 = makePiece(PieceType.QUEEN_RED, newX, newY);
                    pieceGroup.getChildren().remove(piece);
                    pieceGroup.getChildren().add(newPiece2);
                    board[newX][newY].setPiece(newPiece2);
                }
                else if (this.REDcounter == 0) {
                    System.out.println("WHITE WIN");
                    endGame("WHITE WIN");
                }
                else if (this.WHITEcounter == 0) {
                    System.out.println("RED WIN");
                    endGame("RED WIN");
                }

                this.lastColor = !this.lastColor;
                break;

            case QUEEN1:
                Piece newPiece;
                newPiece = makePiece(PieceType.QUEEN_WHITE, newX, newY);
                piece.move(newX, newY,piece.getType());
                board[x0][y0].setPiece(null);
                pieceGroup.getChildren().remove(piece);
                pieceGroup.getChildren().add(newPiece);
                board[newX][newY].setPiece(newPiece);
                break;
            case QUEEN2:
                Piece newPiece2;
                newPiece2 = makePiece(PieceType.QUEEN_RED, newX, newY);
                piece.move(newX, newY,piece.getType());
                board[x0][y0].setPiece(null);
                pieceGroup.getChildren().remove(piece);
                pieceGroup.getChildren().add(newPiece2);
                board[newX][newY].setPiece(newPiece2);
                break;
        }
        return piece;
    }

    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, TILE_SIZE, x, y, color1, color2);

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());
            System.out.println(newX + " " + newY);
            Moving(piece, newX, newY);
        });
        return piece;
    }

    private void endGame(String info){
        Boolean answer = ConfirmBox.display("THE GAME IS OVER", info, "NEW GAME", "EXIT");
        System.out.println(answer);
        if (!answer){
            Platform.exit();
            System.exit(0);
        }
    }
}
