package model;

import constant.Piece;

import java.util.Random;

public class Check implements Piece, Cloneable {
    private int[][] check;
    private int moveCount; // Count of moves made
    private long zobristKey; // Zobrist key for board state
    private Move lastMove; // Store the last move
    private int currentPlayer; // Track current player (Piece.BLACK or Piece.RED)
    private static final long[][] zobristTable = new long[32][90]; // 32 pieces, 9x10 positions
    private static final long playerZobristKey; // Zobrist key for player turn

    // Initialize Zobrist table and player key
    static {
        Random rand = new Random();
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 90; j++) {
                zobristTable[i][j] = rand.nextLong();
            }
        }
        playerZobristKey = rand.nextLong(); // Unique key for player turn
    }

    public Check() {
        check = new int[9][10];
        moveCount = 0;
        lastMove = null; // Initialize lastMove as null
        currentPlayer = Piece.BLACK; // Black moves first
        initializeBoard();
        initializeZobristKey();
    }

    private void initializeBoard() {
        // Black pieces
        check[0][0] = BLACK_LEFT_ROOK + 1;
        check[1][0] = BLACK_LEFT_KNIGHT + 1;
        check[2][0] = BLACK_LEFT_ELEPHANT + 1;
        check[3][0] = BLACK_LEFT_ADVISOR + 1;
        check[4][0] = BLACK_GENERAL + 1;
        check[5][0] = BLACK_RIGHT_ADVISOR + 1;
        check[6][0] = BLACK_RIGHT_ELEPHANT + 1;
        check[7][0] = BLACK_RIGHT_KNIGHT + 1;
        check[8][0] = BLACK_RIGHT_ROOK + 1;
        // Red pieces
        check[0][9] = RED_LEFT_ROOK + 1;
        check[1][9] = RED_LEFT_KNIGHT + 1;
        check[2][9] = RED_LEFT_ELEPHANT + 1;
        check[3][9] = RED_LEFT_ADVISOR + 1;
        check[4][9] = RED_GENERAL + 1;
        check[5][9] = RED_RIGHT_ADVISOR + 1;
        check[6][9] = RED_RIGHT_ELEPHANT + 1;
        check[7][9] = RED_RIGHT_KNIGHT + 1;
        check[8][9] = RED_RIGHT_ROOK + 1;
        // Cannons
        check[1][2] = BLACK_LEFT_CANNON + 1;
        check[7][2] = BLACK_RIGHT_CANNON + 1;
        check[1][7] = RED_LEFT_CANNON + 1;
        check[7][7] = RED_RIGHT_CANNON + 1;
        // Pawns
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

    /**
     * Initialize Zobrist key for initial board state
     */
    private void initializeZobristKey() {
        zobristKey = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = getPiece(x, y);
                if (piece >= 0) {
                    zobristKey ^= zobristTable[piece][x * 10 + y];
                }
            }
        }
        // Include player turn in Zobrist key
        if (currentPlayer == Piece.BLACK) {
            zobristKey ^= playerZobristKey;
        }
    }

    /**
     * Check if a square is empty
     */
    public boolean isEmpty(int x, int y) {
        return check[x][y] == 0;
    }

    /**
     * Get piece at position (x, y)
     */
    public int getPiece(int x, int y) {
        return check[x][y] - 1;
    }

    /**
     * Set piece at position (x, y)
     */
    public void setPiece(int x, int y, int type) {
        int oldPiece = getPiece(x, y);
        check[x][y] = type + 1;
        // Update Zobrist key
        if (oldPiece >= 0) {
            zobristKey ^= zobristTable[oldPiece][x * 10 + y];
        }
        if (type >= 0) {
            zobristKey ^= zobristTable[type][x * 10 + y];
        }
    }

    /**
     * Make a move
     */
    public void makeMove(int fromX, int fromY, int toX, int toY) {
        int piece = getPiece(fromX, fromY);
        setPiece(toX, toY, piece);
        setPiece(fromX, fromY, -1);
        lastMove = new Move(fromX, fromY, toX, toY); // Store last move
        moveCount++;
        // Switch player and update Zobrist key
        switchPlayer();
    }

    /**
     * Undo a move
     */
    public void undoMove(int fromX, int fromY, int toX, int toY, int capturedPiece) {
        int movingPiece = getPiece(toX, toY);
        setPiece(fromX, fromY, movingPiece);
        setPiece(toX, toY, capturedPiece);
        lastMove = null; // Reset lastMove
        moveCount--;
        // Switch player back and update Zobrist key
        switchPlayer();
    }

    /**
     * Switch the current player
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == Piece.BLACK) ? Piece.RED : Piece.BLACK;
        // Update Zobrist key for player turn
        zobristKey ^= playerZobristKey;
    }

    /**
     * Get the current player
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Get the last move
     */
    public Move getLastMove() {
        return lastMove;
    }

    /**
     * Get the number of moves made
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Get the Zobrist key
     */
    public long getZobristKey() {
        return zobristKey;
    }

    /**
     * Clone the board
     */
    @Override
    public Check clone() {
        try {
            Check copy = (Check) super.clone();
            copy.check = new int[9][10];
            for (int i = 0; i < 9; i++) {
                System.arraycopy(this.check[i], 0, copy.check[i], 0, 10);
            }
            copy.moveCount = this.moveCount;
            copy.zobristKey = this.zobristKey;
            copy.currentPlayer = this.currentPlayer;
            copy.lastMove = this.lastMove != null ? new Move(this.lastMove.fromX, this.lastMove.fromY, this.lastMove.toX, this.lastMove.toY) : null; // Clone lastMove
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}