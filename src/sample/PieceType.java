package sample;

public enum PieceType {
    RED(1), WHITE(-1), QUEEN_RED(8),QUEEN_WHITE(-8);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}