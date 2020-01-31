package sample;

import javafx.application.Application;
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

    public Checkers(int tile, int size, String col1, String col2){
        this.TILE_SIZE = tile;
        this.WIDTH = size;
        this.HEIGHT = size;
        this.board = new Tile[WIDTH+1][HEIGHT+1];
        this.color1 = col1;
        this.color2 = col2;
    }

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    boolean lastColor=true, gameOver, opponentSet;

    public boolean getLastColor() {
        return lastColor;
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
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MoveType.NONE);
        }

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());


        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
            if (newY == 1){
                System.out.println("WHITE QUEEN");
                return new MoveResult(MoveType.QUEEN1);
            }

            else if (newY == 8){
                System.out.println("BLACK QUEEN");
                return new MoveResult(MoveType.QUEEN2);
            }

            else return new MoveResult(MoveType.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
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
                    //board[newX][newY].setPiece(piece);
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
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
    private void endGame(){
        int playerOne = 0;
        int playerTwo = 0;
        for (int y = 1; y < HEIGHT+1; y++) {
            for (int x = 1; x < WIDTH + 1; x++) {
                if (board[x][y].getPiece().getType() == PieceType.WHITE){
                    playerOne +=1;
                }
                if (board[x][y].getPiece().getType() == PieceType.RED){
                    playerTwo +=1;
                }
            }
        }
        if (playerOne== 0) System.out.println("Wygral zawodnik 2");
        else if (playerTwo== 0) System.out.println("Wygral zawodnik 1");
    }
}
