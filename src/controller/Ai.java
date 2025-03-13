package controller;

import constant.Piece;
import model.Check;
import model.ChessPiece;
import model.Move;

import java.util.ArrayList;
import java.util.List;

public class Ai {


    /**
     * Giá trị của từng quân cờ
     */
    private static final int[] pieceValues = {
            10000, // Tướng
            10000, // Tướng
            200, 200, // Sĩ
            200, 200, // Sĩ
            450, 450, // Tượng
            450, 450, // Tượng
            600, 600, // Mã
            600, 600, // Mã
            1000, 1000, // Xe
            1000, 1000, // Xe
            800, 800, // Pháo
            800, 800, // Pháo
            150, 150, 150, 150, 150, // Tốt đỏ
            150, 150, 150, 150, 150  // Tốt đen
    };

    private static final int[][] ROOK = {
            {-10, -5, -5, -5, -5, -5, -5, -5, -10},
            {-5, -5, -5, -5, -5, -5, -5, -5, -5},
            {-5, -5, -5, -5, -5, -5, -5, -5, -5},
            {-5, -5, -5, -5, -5, -5, -5, -5, -5},
            {-5, -5, -5, -5, -5, -5, -5, -5, -5},
            {0, 0, 0, 5, 5, 5, 0, 0, 0},
            {0, 0, 0, 5, 5, 5, 0, 0, 0},
            {0, 0, 0, 5, 5, 5, 0, 0, 0},
            {10, 10, 10, 10, 9, 10, 10, 10, 10},
            {20, 20, 20, 15, 20, 15, 20, 20, 20},
    };


    private static final int[][] Cannon = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 10, 20, 30, 40, 30, 20, 10, 0},
            {0, 10, 20, 30, 40, 30, 20, 10, 0},
            {0, 10, 20, 30, 40, 30, 20, 10, 0},
            {0, 10, 20, 30, 40, 30, 20, 10, 0},
            {0, 10, 20, 30, 40, 30, 20, 10, 0},
            {0, 10, 20, 30, 40, 30, 20, 10, 0},
            {0, 10, 20, 30, 40, 30, 20, 10, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    private static final int[][] Knight = {
            {2, 4, 6, 8, 8, 8, 6, 4, 2},
            {4, 6, 8, 10, 10, 10, 8, 6, 4},
            {6, 8, 10, 12, 12, 12, 10, 8, 6},
            {8, 10, 12, 14, 14, 14, 12, 10, 8},
            {8, 10, 12, 14, 16, 14, 12, 10, 8},
            {8, 10, 12, 14, 16, 14, 12, 10, 8},
            {8, 10, 12, 14, 14, 14, 12, 10, 8},
            {6, 8, 10, 12, 12, 12, 10, 8, 6},
            {4, 6, 8, 10, 10, 10, 8, 6, 4},
            {2, 4, 6, 8, 8, 8, 6, 4, 2}
    };

    /**
     * Hàm đánh giá bàn cờ dựa trên giá trị của từng quân cờ
     *
     * @param board  Bàn cờ
     * @param player loại quân cờ cần đánh giá, Piece.RED hoặc Piece.BLACK
     * @return giá trị đánh giá
     */
    public static int evaluate(Check board, int player) {
        int score = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0) {
                    score += (StaticPieces.getPieces().elementAt(piece).getCOLOR() == Piece.BLACK ? 1 : -1) * pieceValues[piece];
                    ChessPiece chessPiece = StaticPieces.getPieces().elementAt(piece);
                    if (chessPiece.getTYPE() == Piece.BLACK_LEFT_ROOK || chessPiece.getTYPE() == Piece.BLACK_RIGHT_ROOK ||
                            chessPiece.getTYPE() == Piece.RED_LEFT_ROOK || chessPiece.getTYPE() == Piece.RED_RIGHT_ROOK) {
                        {
                            score += (StaticPieces.getPieces().elementAt(piece).getCOLOR() == Piece.BLACK ? 1 : -1)
                                    * ROOK[chessPiece.getLocateY()][chessPiece.getLocateX()];
                        }
                    }

                    if (chessPiece.getTYPE() == Piece.BLACK_LEFT_CANNON || chessPiece.getTYPE() == Piece.BLACK_RIGHT_CANNON ||
                            chessPiece.getTYPE() == Piece.RED_LEFT_CANNON || chessPiece.getTYPE() == Piece.RED_RIGHT_CANNON) {
                        {
                            score += (StaticPieces.getPieces().elementAt(piece).getCOLOR() == Piece.BLACK ? 1 : -1)
                                    * Cannon[chessPiece.getLocateY()][chessPiece.getLocateX()];
                        }
                    }

                    if (chessPiece.getTYPE() == Piece.BLACK_LEFT_KNIGHT || chessPiece.getTYPE() == Piece.BLACK_RIGHT_KNIGHT ||
                            chessPiece.getTYPE() == Piece.RED_LEFT_KNIGHT || chessPiece.getTYPE() == Piece.RED_RIGHT_KNIGHT) {
                        {
                            score += (StaticPieces.getPieces().elementAt(piece).getCOLOR() == Piece.BLACK ? 1 : -1)
                                    * Knight[chessPiece.getLocateY()][chessPiece.getLocateX()];
                        }
                    }
                }
            }
        }
        score = checkMate(board, player) ? score + 2000 : score;

        score = checkMateForEnemy(board, player) ? score - 3500 : score;


        score += checkAcrossTheRiver(board, player);

        return score;
    }

    private static int checkAcrossTheRiver(Check board, int player) {
        // check for pawn, rook, knight, cannon
        int score = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0) {
                    ChessPiece chessPiece = StaticPieces.getPieces().elementAt(piece);
                    if (chessPiece.getTYPE() == Piece.RED_PAWN_0 || chessPiece.getTYPE() == Piece.RED_PAWN_1 ||
                            chessPiece.getTYPE() == Piece.RED_PAWN_2 || chessPiece.getTYPE() == Piece.RED_PAWN_3
                            || chessPiece.getTYPE() == Piece.RED_PAWN_4) {
                        if (y >= 5) {
                            score -= 50;
                        }
                    } else {
                        if (chessPiece.getTYPE() == Piece.BLACK_PAWN_0 || chessPiece.getTYPE() == Piece.BLACK_PAWN_1
                                || chessPiece.getTYPE() == Piece.BLACK_PAWN_2 || chessPiece.getTYPE() == Piece.BLACK_PAWN_3
                                || chessPiece.getTYPE() == Piece.BLACK_PAWN_4) {
                            if (y <= 4) {
                                score += 50;
                            }
                        }
                    }
                }
            }
        }
        return score;
    }

    private static boolean checkMateForEnemy(Check board, int player) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0) {
                    ChessPiece enemyPiece = StaticPieces.getPieces().elementAt(piece);
                    if (enemyPiece.getCOLOR() == Piece.RED && enemyPiece.checkMate())
                        return true;
                }
            }
        }
        return false;
    }

    private static boolean checkMate(Check board, int player) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0) {
                    ChessPiece enemyPiece = StaticPieces.getPieces().elementAt(piece);
                    if (enemyPiece.getCOLOR() == Piece.BLACK && enemyPiece.checkMate())
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Tìm nước đi tốt nhất cho máy
     *
     * @param board  Bàn cờ
     * @param depth  Độ sâu tìm kiếm
     * @param player Loại quân cờ cần tìm nước đi, Piece.RED hoặc Piece.BLACK
     * @return Nước đi tốt nhất
     */
    public static Move findBestMove(Check board, int depth, int player) {
        int bestVal = Integer.MIN_VALUE;
        Move bestMove = null;

        List<Move> moves = generateMoves(board, player);

        for (Move move : moves) {
            Check newBoard = board.clone();
            newBoard.makeMove(move.fromX, move.fromY, move.toX, move.toY);

            int moveValue = minimax(newBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
            if (moveValue > bestVal) {
                bestVal = moveValue;
                bestMove = move;
            }
        }
        System.out.println("Best move: " + bestMove);
        return bestMove;
    }

    /**
     * Thuật toán Minimax với cắt tỉa Alpha-Beta
     *
     * @param board  Bàn cờ
     * @param depth  Độ sâu tìm kiếm
     * @param alpha  Alpha
     * @param beta   Beta
     * @param player Loại quân cờ cần tìm nước đi, Piece.RED hoặc Piece.BLACK
     * @return Giá trị đánh giá
     */
    public static int minimax(Check board, int depth, int alpha, int beta, int player) {
        if (depth == 0) return evaluate(board, player);

        List<Move> moves = generateMoves(board, player);
        if (moves.isEmpty()) return player == Piece.BLACK ? -100000 : 100000; // Không còn nước đi

        if (player == Piece.BLACK) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves) {
                int capturedPiece = board.getPiece(move.toX, move.toY); // Lưu quân bị ăn

                // Thực hiện nước đi
                board.setPiece(move.toX, move.toY, board.getPiece(move.fromX, move.fromY));
                board.setPiece(move.fromX, move.fromY, -1);

                int eval = minimax(board, depth - 1, alpha, beta, Piece.RED);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                // Hoàn tác nước đi
                board.setPiece(move.fromX, move.fromY, board.getPiece(move.toX, move.toY));
                board.setPiece(move.toX, move.toY, capturedPiece);

                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : moves) {
                int capturedPiece = board.getPiece(move.toX, move.toY);

                board.setPiece(move.toX, move.toY, board.getPiece(move.fromX, move.fromY));
                board.setPiece(move.fromX, move.fromY, -1);

                int eval = minimax(board, depth - 1, alpha, beta, Piece.BLACK);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                board.setPiece(move.fromX, move.fromY, board.getPiece(move.toX, move.toY));
                board.setPiece(move.toX, move.toY, capturedPiece);

                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    /**
     * Tạo danh sách nước đi hợp lệ
     *
     * @param board  Bàn cờ
     * @param player Loại quân cờ cần tạo nước đi, Piece.RED hoặc Piece.BLACK
     * @return Danh sách nước đi hợp lệ
     */
    public static List<Move> generateMoves(Check board, int player) {
        List<Move> moves = new ArrayList<>();

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece == -1) continue;
                ChessPiece chessPiece = StaticPieces.getPieces().elementAt(piece);
                // Bỏ qua ô trống hoặc quân cờ của đối thủ
                if (chessPiece.getCOLOR() != player) {
                    continue;
                }

                // Lấy danh sách nước đi hợp lệ
                moves.addAll(getValidMoves(x, y, piece, board));
            }
        }
        return moves;
    }

    /**
     * Lấy danh sách nước đi hợp lệ của từng quân cờ
     *
     * @param x     Tọa độ x của quân cờ
     * @param y     Tọa độ y của quân cờ
     * @param piece Loại quân cờ
     * @param board Bàn cờ
     * @return Danh sách nước đi hợp lệ
     */
    private static List<Move> getValidMoves(int x, int y, int piece, Check board) {
        List<Move> moves = new ArrayList<>();

        // Phân loại quân cờ
        if (piece == Piece.RED_LEFT_ROOK || piece == Piece.RED_RIGHT_ROOK ||
                piece == Piece.BLACK_LEFT_ROOK || piece == Piece.BLACK_RIGHT_ROOK) {
            moves.addAll(addRookMoves(x, y, board));
        } else if (piece == Piece.RED_LEFT_KNIGHT || piece == Piece.RED_RIGHT_KNIGHT ||
                piece == Piece.BLACK_LEFT_KNIGHT || piece == Piece.BLACK_RIGHT_KNIGHT) {
            moves.addAll(addKnightMoves(x, y, board));
        } else if (piece == Piece.RED_LEFT_CANNON || piece == Piece.RED_RIGHT_CANNON ||
                piece == Piece.BLACK_LEFT_CANNON || piece == Piece.BLACK_RIGHT_CANNON) {
            moves.addAll(addCannonMoves(x, y, board));
        } else if (piece >= Piece.RED_PAWN_0 && piece <= Piece.BLACK_PAWN_4) {
            moves.addAll(addPawnMoves(x, y, board));
        } else if (piece == Piece.RED_GENERAL || piece == Piece.BLACK_GENERAL) {
            moves.addAll(addGeneralMoves(x, y, board));
        } else if (piece == Piece.RED_LEFT_ADVISOR || piece == Piece.RED_RIGHT_ADVISOR ||
                piece == Piece.BLACK_LEFT_ADVISOR || piece == Piece.BLACK_RIGHT_ADVISOR) {
            moves.addAll(addAdvisorMoves(x, y, board));
        } else if (piece == Piece.RED_LEFT_ELEPHANT || piece == Piece.RED_RIGHT_ELEPHANT ||
                piece == Piece.BLACK_LEFT_ELEPHANT || piece == Piece.BLACK_RIGHT_ELEPHANT) {
            moves.addAll(addElephantMoves(x, y, board));
        }

        return moves;
    }

    /**
     * Thêm nước đi cho quân tượng
     *
     * @param x     Tọa độ x
     * @param y     Tọa độ y
     * @param board Bàn cờ
     */
    private static List<Move> addElephantMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {{2, 2}, {2, -2}, {-2, 2}, {-2, -2}}; // Chéo 2 ô
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();

        for (int[] dir : directions) {
            int midX = x + dir[0] / 2, midY = y + dir[1] / 2;
            int newX = x + dir[0], newY = y + dir[1];

            if ((newX >= 0 && newX < 9) && ((newY >= 0 && newY <= 4) || (newY >= 5 && newY <= 9)) && board.isEmpty(midX, midY)) {
                if (board.isEmpty(newX, newY)) {
                    moves.add(new Move(x, y, newX, newY));
                } else if (StaticPieces.getPieces().elementAt(board.getPiece(newX, newY)).getCOLOR() != player) {
                    moves.add(new Move(x, y, newX, newY));
                }
            }
        }
        return moves;
    }

    /**
     * Thêm nước đi cho quân sỹ
     *
     * @param x     Tọa độ x
     * @param y     Tọa độ y
     * @param board Bàn cờ
     */
    private static List<Move> addAdvisorMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}; // Chéo 4 hướng
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();

        for (int[] dir : directions) {
            int newX = x + dir[0], newY = y + dir[1];
            if (newX >= 3 && newX <= 5 && ((newY >= 0 && newY <= 2) || (newY >= 7 && newY <= 9))) {
                if (board.isEmpty(newX, newY)) {
                    moves.add(new Move(x, y, newX, newY));
                } else if (StaticPieces.getPieces().elementAt(board.getPiece(newX, newY)).getCOLOR() != player) {
                    moves.add(new Move(x, y, newX, newY));
                }
            }
        }
        return moves;
    }

    /**
     * Thêm nước đi cho quân tướng
     *
     * @param x     Tọa độ x
     * @param y     Tọa độ y
     * @param board Bàn cờ
     */
    private static List<Move> addGeneralMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();

        for (int[] dir : directions) {
            int newX = x + dir[0], newY = y + dir[1];
            if (newX >= 3 && newX <= 5 && ((newY >= 0 && newY <= 2) || (newY >= 7 && newY <= 9))) {
                if (board.isEmpty(newX, newY)) {
                    moves.add(new Move(x, y, newX, newY));
                } else if (StaticPieces.getPieces().elementAt(board.getPiece(newX, newY)).getCOLOR() != player) {
                    moves.add(new Move(x, y, newX, newY));
                }
            }
        }

        return moves;
    }

    /**
     * Thêm nước đi cho quân xe
     *
     * @param x     Tọa độ x
     * @param y     Tọa độ y
     * @param board Bàn cờ
     */
    private static List<Move> addRookMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0}; // Trái, Phải, Trên, Dưới
        int[] dy = {0, 0, -1, 1};
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i], ny = y + dy[i];

            while (nx >= 0 && nx < 9 && ny >= 0 && ny < 10) {
                if (board.isEmpty(nx, ny)) {
                    moves.add(new Move(x, y, nx, ny));
                } else {
                    ChessPiece chessPiece = StaticPieces.getPieces().elementAt(board.getPiece(nx, ny));
                    if (player != chessPiece.getCOLOR()) {
                        moves.add(new Move(x, y, nx, ny)); // Ăn quân đối thủ
                    }
                    break; // Gặp quân cờ thì dừng
                }
                nx += dx[i];
                ny += dy[i];
            }
        }
        return moves;
    }

    /**
     * Thêm nước đi cho quân mã
     *
     * @param x     Tọa độ x
     * @param y     Tọa độ y
     * @param board Bàn cờ
     */
    private static List<Move> addKnightMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[] dx = {-2, -2, -1, 1, 2, 2, 1, -1};
        int[] dy = {-1, 1, 2, 2, 1, -1, -2, -2};

        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // Kiểm tra nếu vị trí mới nằm trong bàn cờ
            if (nx >= 0 && nx < 9 && ny >= 0 && ny < 10) {
                // Kiểm tra chân mã
                if ((dx[i] == -2 || dx[i] == 2) && !board.isEmpty(x + dx[i] / 2, y)) {
                    continue; // Có quân cản theo chiều ngang
                }
                if ((dy[i] == -2 || dy[i] == 2) && !board.isEmpty(x, y + dy[i] / 2)) {
                    continue; // Có quân cản theo chiều dọc
                }

                int location = board.getPiece(nx, ny);
                if (board.isEmpty(nx, ny)) {
                    moves.add(new Move(x, y, nx, ny));
                } else if (StaticPieces.getPieces().elementAt(location).getCOLOR() != player) {
                    moves.add(new Move(x, y, nx, ny));
                }
            }
        }
        return moves;
    }

    /**
     * Thêm nước đi cho quân pháo
     *
     * @param x     Tọa độ x
     * @param y     Tọa độ y
     * @param board Bàn cờ
     */
    private static List<Move> addCannonMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i], ny = y + dy[i];
            boolean jumped = false;

            while (nx >= 0 && nx < 9 && ny >= 0 && ny < 10) {
                if (board.isEmpty(nx, ny)) {
                    if (!jumped) moves.add(new Move(x, y, nx, ny));
                } else {
                    if (jumped) {
                        ChessPiece chessPiece = StaticPieces.getPieces().elementAt(board.getPiece(nx, ny));
                        if (player != chessPiece.getCOLOR()) {
                            moves.add(new Move(x, y, nx, ny));
                        }
                        break;
                    }
                    jumped = true;
                }
                nx += dx[i];
                ny += dy[i];
            }
        }
        return moves;
    }

    /**
     * Thêm nước đi cho quân tốt
     *
     * @param x     Tọa độ x
     * @param y     Tọa độ y
     * @param board Bàn cờ
     */
    private static List<Move> addPawnMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();
        int direction = player == Piece.BLACK ? 1 : -1; // Đỏ đi xuống, Đen đi lên
        int newY = y + direction;


        // Đi thẳng
        if (newY >= 0 && newY < 10) {
            if (board.isEmpty(x, newY)) {
                moves.add(new Move(x, y, x, newY));
            } else if (StaticPieces.getPieces().elementAt(board.getPiece(x, newY)).getCOLOR() != player) {
                moves.add(new Move(x, y, x, newY));
            }
        }

        // Qua sông thì có thể đi ngang
        if ((player == Piece.RED && y < 5) || (player == Piece.BLACK && y > 4)) {
            if (x > 0) {
                if (board.isEmpty(x - 1, y)) moves.add(new Move(x, y, x - 1, y));
                else if (StaticPieces.getPieces().elementAt(board.getPiece(x - 1, y)).getCOLOR() != player)
                    moves.add(new Move(x, y, x - 1, y));
            }
            if (x < 8) {
                if (board.isEmpty(x + 1, y)) moves.add(new Move(x, y, x + 1, y));
                else if (StaticPieces.getPieces().elementAt(board.getPiece(x + 1, y)).getCOLOR() != player)
                    moves.add(new Move(x, y, x + 1, y));
            }
        }
        return moves;
    }
}

