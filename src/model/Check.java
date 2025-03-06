package model;

import constant.Piece;

public class Check implements Piece, Cloneable {
    private int[][] check;

    public Check() {
        check = new int[9][10];
        initializeBoard();
    }

    private void initializeBoard() {
        // Đen
        check[0][0] = BLACK_LEFT_ROOK + 1;
        check[1][0] = BLACK_LEFT_KNIGHT + 1;
        check[2][0] = BLACK_LEFT_ELEPHANT + 1;
        check[3][0] = BLACK_LEFT_ADVISOR + 1;
        check[4][0] = BLACK_GENERAL + 1;
        check[5][0] = BLACK_RIGHT_ADVISOR + 1;
        check[6][0] = BLACK_RIGHT_ELEPHANT + 1;
        check[7][0] = BLACK_RIGHT_KNIGHT + 1;
        check[8][0] = BLACK_RIGHT_ROOK + 1;
        // Đỏ
        check[0][9] = RED_LEFT_ROOK + 1;
        check[1][9] = RED_LEFT_KNIGHT + 1;
        check[2][9] = RED_LEFT_ELEPHANT + 1;
        check[3][9] = RED_LEFT_ADVISOR + 1;
        check[4][9] = RED_GENERAL + 1;
        check[5][9] = RED_RIGHT_ADVISOR + 1;
        check[6][9] = RED_RIGHT_ELEPHANT + 1;
        check[7][9] = RED_RIGHT_KNIGHT + 1;
        check[8][9] = RED_RIGHT_ROOK + 1;
        // Pháo
        check[1][2] = BLACK_LEFT_CANNON + 1;
        check[7][2] = BLACK_RIGHT_CANNON + 1;
        check[1][7] = RED_LEFT_CANNON + 1;
        check[7][7] = RED_RIGHT_CANNON + 1;
        // Tốt
        check[0][3] = BLACK_PAWN_0 + 1;
        check[2][3] = BLACK_PAWN_1 + 1;
        check[4][3] = BLACK_PAWN_2 + 1;
        check[6][3] = BLACK_PAWN_3 + 1;
        check[8][3] = BLACK_PAWN_4 + 1;
        check[0][6] = RED_PAWN_0 + 1;
        check[2][6] = RED_PAWN_1 + 1;
        check[4][6] = RED_PAWN_2 + 1;
        check[6][6] = RED_PAWN_3 + 1;
        check[8][6] = RED_PAWN_4 + 1;
    }

    public boolean isEmpty(int x, int y) {
        return check[x][y] == 0;
    }

    public int getPiece(int x, int y) {
        return check[x][y] - 1;
    }

    public void setPiece(int x, int y, int type) {
        check[x][y] = type + 1;
    }

    @Override
    public Check clone() {
        try {
            Check copy = (Check) super.clone();
            copy.check = new int[9][10];
            for (int i = 0; i < 9; i++) {
                System.arraycopy(this.check[i], 0, copy.check[i], 0, 10);
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void makeMove(int fromX, int fromY, int toX, int toY) {
        int piece = getPiece(fromX, fromY);
        setPiece(toX, toY, piece);
        setPiece(fromX, fromY, -1);
    }
}
