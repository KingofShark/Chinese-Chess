//package controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import model.Check;
//import model.ChessPiece;
//import view.ChessBoard;
//
///**
// * Lớp AI xử lý nước đi của máy trong game cờ tướng
// * sử dụng thuật toán minimax với cắt tỉa alpha-beta
// */
//public class ChessAI {
//
//    // Độ sâu tìm kiếm tối đa
//    private int maxDepth;
//
//    // Giá trị của từng quân cờ
//    private static final int KING_VALUE = 10000;     // Tướng
//    private static final int ADVISOR_VALUE = 200;    // Sĩ
//    private static final int ELEPHANT_VALUE = 180;   // Tượng
//    private static final int HORSE_VALUE = 400;      // Mã
//    private static final int CHARIOT_VALUE = 900;    // Xe
//    private static final int CANNON_VALUE = 450;     // Pháo
//    private static final int PAWN_VALUE = 100;       // Tốt
//
//    /**
//     * Khởi tạo AI với độ sâu tìm kiếm được chỉ định
//     * @param depth Độ sâu tìm kiếm tối đa
//     */
//    public ChessAI(int depth) {
//        this.maxDepth = depth;
//    }
//
//    /**
//     * Tìm nước đi tốt nhất cho máy
//     * @param board Bàn cờ hiện tại
//     * @param isRedTurn true nếu là lượt quân đỏ, false nếu là lượt quân đen
//     * @return Nước đi tốt nhất
//     */
//    public Move findBestMove(Check board, int isRedTurn) {
//        List<Move> possibleMoves = generateAllPossibleMoves(board, isRedTurn);
//        Move bestMove = null;
//        int bestValue = Integer.MIN_VALUE;
//        int alpha = Integer.MIN_VALUE;
//        int beta = Integer.MAX_VALUE;
//
//        for (Move move : possibleMoves) {
//            // Thực hiện nước đi
//            Check check = makeMove(board, move);
//
//            // Đánh giá nước đi bằng minimax
//            int moveValue = minimax(check, maxDepth - 1, alpha, beta, (1 - isRedTurn));
//
//            // Khôi phục lại nước đi
//
//            if (moveValue > bestValue) {
//                bestValue = moveValue;
//                bestMove = move;
//            }
//
//            alpha = Math.max(alpha, bestValue);
//        }
//
//        return bestMove;
//    }
//
//    /**
//     * Thuật toán minimax với cắt tỉa alpha-beta
//     * @param board Bàn cờ hiện tại
//     * @param depth Độ sâu tìm kiếm còn lại
//     * @param alpha Giá trị alpha
//     * @param beta Giá trị beta
//     * @param isRedTurn true nếu là lượt quân đỏ, false nếu là lượt quân đen
//     * @return Điểm đánh giá tốt nhất
//     */
//    private int minimax(Check board, int depth, int alpha, int beta, int isRedTurn) {
//        // Điều kiện dừng
//        if (depth == 0 || isGameOver(board)) {
//            return evaluateBoard(board, isRedTurn);
//        }
//
//        List<Move> possibleMoves = generateAllPossibleMoves(board, isRedTurn);
//
//        if (isRedTurn == 0) {  // MAX player (máy)
//            int maxEval = Integer.MIN_VALUE;
//
//            for (Move move : possibleMoves) {
//                // Thực hiện nước đi
//                Check newBoard = makeMove(board, move);
//
//                // Gọi đệ quy minimax
//                int eval = minimax(newBoard, depth - 1, alpha, beta, 1);
//
//                // Cập nhật giá trị tốt nhất
//                maxEval = Math.max(maxEval, eval);
//
//                // Cập nhật alpha
//                alpha = Math.max(alpha, eval);
//
//                // Cắt tỉa alpha-beta
//                if (beta <= alpha) {
//                    break;
//                }
//            }
//
//            return maxEval;
//        } else {  // MIN player (người chơi)
//            int minEval = Integer.MAX_VALUE;
//
//            for (Move move : possibleMoves) {
//                // Thực hiện nước đi
//                Check newBoard = makeMove(board, move);
//
//                // Gọi đệ quy minimax
//                int eval = minimax(newBoard, depth - 1, alpha, beta, 0);
//
//                // Cập nhật giá trị tốt nhất
//                minEval = Math.min(minEval, eval);
//
//                // Cập nhật beta
//                beta = Math.min(beta, eval);
//
//                // Cắt tỉa alpha-beta
//                if (beta <= alpha) {
//                    break;
//                }
//            }
//
//            return minEval;
//        }
//    }
//
//    /**
//     * Đánh giá trạng thái bàn cờ
//     * @param board Bàn cờ cần đánh giá
//     * @param isRedTurn Lượt hiện tại
//     * @return Điểm đánh giá
//     */
//    private int evaluateBoard(Check board, int isRedTurn) {
//        int score = 0;
//
//        // Tính điểm dựa trên giá trị quân cờ và vị trí
//        for (int row = 0; row < 10; row++) {
//            for (int col = 0; col < 9; col++) {
//                ChessPiece piece = board.getPieceAt(row, col);
//
//                if (piece != null) {
//                    int pieceValue = getPieceValue(piece);
//                    int positionValue = getPositionValue(piece, row, col);
//
//                    // Cộng điểm cho quân đỏ, trừ điểm cho quân đen
//                    if (piece.isRed()) {
//                        score += pieceValue + positionValue;
//                    } else {
//                        score -= pieceValue + positionValue;
//                    }
//                }
//            }
//        }
//
//        // Nếu đang xét cho quân đen, đảo ngược điểm
//        if (!isRedTurn) {
//            score = -score;
//        }
//
//        return score;
//    }
//
//    /**
//     * Lấy giá trị cơ bản của quân cờ
//     * @param piece Quân cờ cần đánh giá
//     * @return Giá trị của quân cờ
//     */
//    private int getPieceValue(ChessPiece piece) {
//        switch (piece.getType()) {
//            case KING:
//                return KING_VALUE;
//            case ADVISOR:
//                return ADVISOR_VALUE;
//            case ELEPHANT:
//                return ELEPHANT_VALUE;
//            case HORSE:
//                return HORSE_VALUE;
//            case CHARIOT:
//                return CHARIOT_VALUE;
//            case CANNON:
//                return CANNON_VALUE;
//            case PAWN:
//                return PAWN_VALUE + getPawnAdvancementBonus(piece);
//            default:
//                return 0;
//        }
//    }
//
//    /**
//     * Tính toán điểm thưởng cho tốt tiến lên
//     * @param pawn Quân tốt cần đánh giá
//     * @return Điểm thưởng
//     */
//    private int getPawnAdvancementBonus(ChessPiece pawn) {
//        int row = pawn.getRow();
//        int bonus = 0;
//
//        // Tốt đỏ được thưởng khi tiến lên trên bàn cờ (row giảm)
//        if (pawn.isRed()) {
//            bonus = Math.max(0, 9 - row) * 10;
//
//            // Thưởng thêm khi qua sông
//            if (row < 5) {
//                bonus += 30;
//            }
//        }
//        // Tốt đen được thưởng khi tiến xuống dưới (row tăng)
//        else {
//            bonus = Math.max(0, row) * 10;
//
//            // Thưởng thêm khi qua sông
//            if (row >= 5) {
//                bonus += 30;
//            }
//        }
//
//        return bonus;
//    }
//
//    /**
//     * Đánh giá giá trị vị trí của quân cờ
//     * @param piece Quân cờ
//     * @param row Hàng
//     * @param col Cột
//     * @return Điểm thưởng vị trí
//     */
//    private int getPositionValue(ChessPiece piece, int row, int col) {
//        int value = 0;
//
//        switch (piece.getType()) {
//            case HORSE:
//                // Ngựa được đánh giá cao hơn ở vị trí trung tâm
//                value = 15 - Math.abs(col - 4) * 3 - Math.abs(row - 5) * 3;
//                break;
//            case CANNON:
//                // Pháo được đánh giá cao hơn ở các vị trí kiểm soát
//                value = 10 - Math.abs(col - 4) * 2;
//                break;
//            case CHARIOT:
//                // Xe được đánh giá cao ở các đường mở
//                value = isOpenColumn(col) ? 10 : 0;
//                break;
//        }
//
//        return value;
//    }
//
//    /**
//     * Kiểm tra nếu một cột đang mở (ít quân cờ)
//     * @param col Cột cần kiểm tra
//     * @return true nếu là cột mở
//     */
//    private boolean isOpenColumn(int col) {
//        // Thực hiện kiểm tra cột mở
//        // (Giả định cài đặt thực tế sẽ kiểm tra số quân trên cột)
//        return true; // Giả sử tất cả các cột đều mở để đơn giản
//    }
//
//    /**
//     * Tạo bản sao của bàn cờ và thực hiện nước đi
//     * @param board Bàn cờ gốc
//     * @param move Nước đi cần thực hiện
//     * @return Bàn cờ mới sau khi đã thực hiện nước đi
//     */
//    private Check makeMove(Check board, Move move) {
//        // Tạo bản sao bàn cờ
//        Check newBoard = board.clone();
//
//        // Thực hiện nước đi
//        int temp = newBoard.getPiece(move.getToRow(), move.getToCol());
//        newBoard.setPiece(move.getToRow(), move.getToCol(), temp);
//        newBoard.setPiece(move.getFromRow(), move.getFromCol(), -1);
//
//        return newBoard;
//    }
//
//    /**
//     * Tạo danh sách tất cả các nước đi hợp lệ
//     * @param board Bàn cờ hiện tại
//     * @param isRedTurn Lượt chơi hiện tại
//     * @return Danh sách các nước đi hợp lệ
//     */
//    private List<Move> generateAllPossibleMoves(Check board, int isRedTurn) {
//        List<Move> moves = new ArrayList<>();
//
//        // Duyệt qua tất cả các ô trên bàn cờ
//        for (int row = 0; row < 10; row++) {
//            for (int col = 0; col < 9; col++) {
//                int piece = board.getPiece(row, col);
//
//                // Nếu có quân cờ và đúng màu
//                if (piece != -1 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == isRedTurn) {
//                    // Lấy tất cả các nước đi hợp lệ của quân cờ
//                    List<Move> pieceMoves = board.getValidMoves(row, col);
//                    moves.addAll(pieceMoves);
//                }
//            }
//        }
//
//        return moves;
//    }
//
//    /**
//     * Kiểm tra xem trò chơi đã kết thúc chưa
//     * @param board Bàn cờ hiện tại
//     * @return true nếu trò chơi đã kết thúc
//     */
//    private boolean isGameOver(Check board) {
//        // Kiểm tra chiếu tướng hoặc hết nước đi
//        return isCheckmate(board, true) || isCheckmate(board, false);
//    }
//
//    /**
//     * Kiểm tra xem có bị chiếu tướng không
//     * @param board Bàn cờ hiện tại
//     * @param isRed Kiểm tra cho phe đỏ (true) hoặc phe đen (false)
//     * @return true nếu bị chiếu tướng
//     */
//    private boolean isCheckmate(Check board, boolean isRed) {
//        // Giả định đã có cài đặt để kiểm tra chiếu tướng
//        return false; // Mặc định trả về false để đơn giản
//    }
//}
//
///**
// * Lớp biểu diễn một nước đi trong cờ tướng
// */
//class Move {
//    private int fromRow;
//    private int fromCol;
//    private int toRow;
//    private int toCol;
//
//    public Move(int fromRow, int fromCol, int toRow, int toCol) {
//        this.fromRow = fromRow;
//        this.fromCol = fromCol;
//        this.toRow = toRow;
//        this.toCol = toCol;
//    }
//
//    public int getFromRow() {
//        return fromRow;
//    }
//
//    public int getFromCol() {
//        return fromCol;
//    }
//
//    public int getToRow() {
//        return toRow;
//    }
//
//    public int getToCol() {
//        return toCol;
//    }
//
//    @Override
//    public String toString() {
//        return "Move from (" + fromRow + "," + fromCol + ") to (" + toRow + "," + toCol + ")";
//    }
//}
