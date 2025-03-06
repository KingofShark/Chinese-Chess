package model;

public class Move {
    public int fromX, fromY; // Vị trí bắt đầu
    public int toX, toY;     // Vị trí kết thúc

    public Move(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public String toString() {
        return "(" + fromX + ", " + fromY + ") -> (" + toX + ", " + toY + ")";
    }
}
