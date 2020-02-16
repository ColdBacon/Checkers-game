package sample;

public enum PieceType {
    RED(1,true), WHITE(-1,false), QUEEN_RED(-1,true),QUEEN_WHITE(1,false);

    final int moveDir;
    final boolean playerType;

    PieceType(int moveDir, boolean playerType) {
        this.moveDir = moveDir;
        this.playerType = playerType;
    }
}