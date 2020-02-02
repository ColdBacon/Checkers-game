package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Checkers {

    private int TILE_SIZE;
    private int WIDTH;
    private int HEIGHT;
    private Tile[][] board;
    private String color1, color2;
    private boolean lastColor;
    private int WHITEcounter;
    private int REDcounter;

    public Checkers(int tile, int size, String col1, String col2){
        this.TILE_SIZE = tile;
        this.WIDTH = size;
        this.HEIGHT = size;
        this.board = new Tile[WIDTH+1][HEIGHT+1];
        this.color1 = col1;
        this.color2 = col2;
        this.lastColor = true;
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
        //root.setPrefSize(600,600);
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

        System.out.println(piece.getType());
        System.out.println("NEW X:"+newX);
        System.out.println("NEW Y:"+newY);

        System.out.println("COUNTER WHITE: " + this.WHITEcounter);
        System.out.println("COUNTER RED: " + this.REDcounter);

        if(attemptMove(this.lastColor) == piece.getType()) {

            if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
                return new MoveResult(MoveType.NONE);
            }

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
                if (newY == 1 && piece.getType() == PieceType.WHITE) {
                    System.out.println("KROLOWA");
                    this.lastColor = !this.lastColor;
                    return new MoveResult(MoveType.QUEEN1); }

                else if (newY == this.getHEIGHT() && piece.getType() == PieceType.RED) {
                    System.out.println("KROLOWA 2");
                    this.lastColor = !this.lastColor;
                    return new MoveResult(MoveType.QUEEN2); }

                else {
                    this.lastColor = !this.lastColor;
                    return new MoveResult(MoveType.NORMAL); }

            } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {

                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;


                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                    this.lastColor = !this.lastColor;
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
                }
            }
        }
        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, TILE_SIZE,x, y,color1, color2);

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            MoveResult result;

            if (newX < 1 || newY < 1 || newX >= WIDTH+1 || newY >= HEIGHT+1) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()) {
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    if(piece.getType() == PieceType.WHITE || piece.getType() == PieceType.QUEEN_WHITE) this.REDcounter -= 1;
                    else if(piece.getType() == PieceType.RED || piece.getType() == PieceType.QUEEN_RED ) this.WHITEcounter -= 1;

                    if (this.REDcounter == 0) {
                        System.out.println("WHITE WIN");
                        endGame("WHITE WIN");
                    }
                    else if (this.WHITEcounter == 0) {
                        System.out.println("RED WIN");
                        endGame("RED WIN");
                    }

                    break;
                case QUEEN1:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(null);
                    pieceGroup.getChildren().remove(piece);
                    Piece queen = new Piece(PieceType.QUEEN_WHITE,TILE_SIZE,newX,newY,piece.getColor1(),piece.getColor2());
                    pieceGroup.getChildren().add(queen);
                    board[newX][newY].setPiece(queen);
                    System.out.println("ZMIANA PIONKA 1");
                    break;
                case QUEEN2:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(null);
                    pieceGroup.getChildren().remove(piece);
                    Piece queen2 = new Piece(PieceType.QUEEN_RED,TILE_SIZE,newX,newY, piece.getColor1(),piece.getColor2());
                    pieceGroup.getChildren().add(queen2);
                    board[newX][newY].setPiece(queen2);
                    System.out.println("ZMIANA PIONKA 2");
                    break;
            }
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

    private PieceType attemptMove(boolean lastColor){
        if(lastColor) return PieceType.WHITE;
        else return PieceType.RED;
    }
}
