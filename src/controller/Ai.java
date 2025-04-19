package controller;

import constant.Piece;
import model.Check;
import model.ChessPiece;
import model.Move;

import java.util.*;

public class Ai {

    /**
     * Piece values for evaluation, adjusted for game phases
     */
    private static final int[][] pieceValues = {
            // Opening/Middlegame, Endgame
            {10000, 10000}, // General
            {10000, 10000}, // General
            {220, 180}, {220, 180}, // Advisor
            {220, 180}, {220, 180}, // Advisor
            {480, 420}, {480, 420}, // Elephant
            {480, 420}, {480, 420}, // Elephant
            {650, 600}, {650, 600}, // Knight
            {650, 600}, {650, 600}, // Knight
            {1050, 1000}, {1050, 1000}, // Rook
            {1050, 1000}, {1050, 1000}, // Rook
            {850, 800}, {850, 800}, // Cannon
            {850, 800}, {850, 800}, // Cannon
            {180, 220}, {180, 220}, {180, 220}, {180, 220}, {180, 220}, // Red Pawns
            {180, 220}, {180, 220}, {180, 220}, {180, 220}, {180, 220}  // Black Pawns
    };

    // Killer moves
    private static final Move[][] killerMoves = new Move[100][4];

    // History heuristics
    private static final int[][][] historyTable = new int[9][10][90];

    // Transposition Table with smaller size to prevent memory issues
    private static final Map<Long, TranspositionEntry> transpositionTable = new HashMap<>(1 << 18);

    private static class TranspositionEntry {
        int depth;
        int value;
        Move bestMove;
        int flag; // 0: exact, 1: lower bound, 2: upper bound

        TranspositionEntry(int depth, int value, Move bestMove, int flag) {
            this.depth = depth;
            this.value = value;
            this.bestMove = bestMove;
            this.flag = flag;
        }
    }

    /**
     * Calculate game phase
     */
    private static double getGamePhase(Check board) {
        int totalMaterial = 0;
        int initialMaterial = 2 * 1050 + 2 * 850 + 2 * 650 + 2 * 480 + 2 * 220 + 5 * 180;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && piece != Piece.BLACK_GENERAL && piece != Piece.RED_GENERAL) {
                    totalMaterial += pieceValues[piece][0];
                }
            }
        }
        return (double) totalMaterial / (2 * initialMaterial);
    }

    /**
     * Improved evaluate function with more strategic factors
     */
    public static int evaluate(Check board, int player) {
        double phase = getGamePhase(board);
        int phaseIdx = (phase <= 0.4) ? 1 : 0; // 0 for opening/middlegame, 1 for endgame
        int score = 0;

        // Material and positional evaluation
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0) {
                    ChessPiece p = StaticPieces.getPieces().elementAt(piece);
                    int sign = (p.getCOLOR() == Piece.BLACK ? 1 : -1);
                    score += sign * pieceValues[piece][phaseIdx];

                    // Positional bonuses
                    if (p.getCOLOR() == Piece.BLACK) {
                        if (piece >= Piece.BLACK_PAWN_0 && piece <= Piece.BLACK_PAWN_4) {
                            if (y >= 5) score += 60; // Pawns across river
                            if (y >= 8) score += 40; // Pawns near promotion
                            if (x == 4 && y == 3) score += 30; // Central pawn
                        } else if (piece == Piece.BLACK_LEFT_KNIGHT || piece == Piece.BLACK_RIGHT_KNIGHT) {
                            if (y >= 2 && (x == 2 || x == 6)) score += 25;
                            score += getMobilityBonus(board, x, y, piece) * 5;
                        } else if (piece == Piece.BLACK_LEFT_ROOK || piece == Piece.BLACK_RIGHT_ROOK) {
                            if (isOpenFile(board, x)) score += 50;
                        }
                    } else {
                        if (piece >= Piece.RED_PAWN_0 && piece <= Piece.RED_PAWN_4) {
                            if (y <= 4) score -= 60;
                            if (y <= 1) score -= 40;
                            if (x == 4 && y == 6) score -= 30;
                        } else if (piece == Piece.RED_LEFT_KNIGHT || piece == Piece.RED_RIGHT_KNIGHT) {
                            if (y <= 7 && (x == 2 || x == 6)) score -= 25;
                            score -= getMobilityBonus(board, x, y, piece) * 5;
                        } else if (piece == Piece.RED_LEFT_ROOK || piece == Piece.RED_RIGHT_ROOK) {
                            if (isOpenFile(board, x)) score -= 50;
                        }
                    }
                }
            }
        }

        // Strategic factors
        score += evaluateThreats(board, player);
        score += evaluateStructure(board, player);
        score += checkAcrossTheRiver(board, player);
        score += evaluateKingSafety(board, player);
        score += evaluateCenterControl(board, player);

        // Phase-specific adjustments
        if (phase > 0.8) { // Opening
            score += evaluateCenterControl(board, player) * 3;
            score += evaluateDevelopment(board, player);
        } else if (phase <= 0.4) { // Endgame
            score += evaluateKingActivity(board, player) * 3;
        }

        // Checkmate and stalemate
        if (checkMate(board, player)) {
            score += (player == Piece.BLACK ? 1000000 : -1000000); // Increased checkmate value
        }
        if (checkMateForEnemy(board, player)) {
            score += (player == Piece.BLACK ? -1200000 : 1200000); // Increased checkmate value
        }

        return score;
    }

    /**
     * Check if a file is open for a rook
     */
    private static boolean isOpenFile(Check board, int x) {
        for (int y = 0; y < 10; y++) {
            int piece = board.getPiece(x, y);
            if (piece >= Piece.BLACK_PAWN_0 && piece <= Piece.BLACK_PAWN_4 ||
                    piece >= Piece.RED_PAWN_0 && piece <= Piece.RED_PAWN_4) {
                return false;
            }
        }
        return true;
    }

    /**
     * Evaluate piece mobility
     */
    private static int getMobilityBonus(Check board, int x, int y, int piece) {
        List<Move> moves = getValidMoves(x, y, piece, board);
        return moves.size();
    }

    /**
     * Evaluate development (encourage moving knights and rooks early)
     */
    private static int evaluateDevelopment(Check board, int player) {
        int score = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == player) {
                    if (piece == Piece.BLACK_LEFT_KNIGHT || piece == Piece.BLACK_RIGHT_KNIGHT) {
                        if (y > 1) score += 20;
                    } else if (piece == Piece.RED_LEFT_KNIGHT || piece == Piece.RED_RIGHT_KNIGHT) {
                        if (y < 8) score -= 20;
                    }
                }
            }
        }
        return score;
    }

    /**
     * Updated evaluateThreats to penalize high-value pieces under threat
     */
    private static int evaluateThreats(Check board, int player) {
        int score = 0;
        int opponent = (player == Piece.BLACK) ? Piece.RED : Piece.BLACK;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == player) {
                    for (int ox = 0; ox < 9; ox++) {
                        for (int oy = 0; oy < 10; oy++) {
                            int opiece = board.getPiece(ox, oy);
                            if (opiece >= 0 && StaticPieces.getPieces().elementAt(opiece).getCOLOR() == opponent) {
                                List<Move> oppMoves = getValidMoves(ox, oy, opiece, board);
                                for (Move m : oppMoves) {
                                    if (m.toX == x && m.toY == y) {
                                        int pieceValue = pieceValues[piece][0];
                                        int attackerValue = pieceValues[opiece][0];
                                        int penalty = 0;
                                        if (pieceValue > attackerValue) {
                                            penalty = (pieceValue - attackerValue) * 3;
                                        } else {
                                            penalty = pieceValue;
                                        }
                                        score -= penalty;
                                        System.out.println("Threat detected: Piece " + piece + " at (" + x + "," + y + ") threatened by " + opiece + " at (" + ox + "," + oy + "), penalty: " + penalty);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return score;
    }

    /**
     * Check for checkmate
     */
    private static boolean checkMate(Check board, int player) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0) {
                    ChessPiece p = StaticPieces.getPieces().elementAt(piece);
                    if (p.getCOLOR() == player && p.checkMate()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkMateForEnemy(Check board, int player) {
        int opponent = (player == Piece.BLACK) ? Piece.RED : Piece.BLACK;
        return checkMate(board, opponent);
    }

    /**
     * Check if king is in check
     */
    private static boolean isInCheck(Check board, int player) {
        int kingX = -1, kingY = -1;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == player &&
                        (piece == Piece.BLACK_GENERAL || piece == Piece.RED_GENERAL)) {
                    kingX = x;
                    kingY = y;
                    break;
                }
            }
        }
        if (kingX == -1) return false;

        int opponent = (player == Piece.BLACK) ? Piece.RED : Piece.BLACK;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == opponent) {
                    List<Move> moves = getValidMoves(x, y, piece, board);
                    for (Move move : moves) {
                        if (move.toX == kingX && move.toY == kingY) return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Evaluate pawns across the river
     */
    private static int checkAcrossTheRiver(Check board, int player) {
        int score = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0) {
                    ChessPiece p = StaticPieces.getPieces().elementAt(piece);
                    if (p.getCOLOR() == player) {
                        if (p.getTYPE() >= Piece.RED_PAWN_0 && p.getTYPE() <= Piece.RED_PAWN_4) {
                            if (y <= 4) score -= 100;
                            if (y <= 1) score -= 50;
                        } else if (p.getTYPE() >= Piece.BLACK_PAWN_0 && p.getTYPE() <= Piece.BLACK_PAWN_4) {
                            if (y >= 5) score += 100;
                            if (y >= 8) score += 50;
                        }
                    }
                }
            }
        }
        return score;
    }

    /**
     * Evaluate piece structure
     */
    private static int evaluateStructure(Check board, int player) {
        int score = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == player) {
                    ChessPiece p = StaticPieces.getPieces().elementAt(piece);
                    // Reward Rook and Cannon on same file
                    if (p.getTYPE() == Piece.BLACK_LEFT_ROOK || p.getTYPE() == Piece.BLACK_RIGHT_ROOK) {
                        for (int ny = 0; ny < 10; ny++) {
                            int otherPiece = board.getPiece(x, ny);
                            if (otherPiece == Piece.BLACK_LEFT_CANNON || otherPiece == Piece.BLACK_RIGHT_CANNON) {
                                score += 50;
                            }
                        }
                    }
                    // Penalize weak General position
                    if (p.getTYPE() == Piece.BLACK_GENERAL && player == Piece.BLACK) {
                        int advisorCount = 0, elephantCount = 0;
                        for (int cx = 3; cx <= 5; cx++) {
                            for (int cy = 0; cy <= 2; cy++) {
                                int cp = board.getPiece(cx, cy);
                                if (cp == Piece.BLACK_LEFT_ADVISOR || cp == Piece.BLACK_RIGHT_ADVISOR) advisorCount++;
                                if (cp == Piece.BLACK_LEFT_ELEPHANT || cp == Piece.BLACK_RIGHT_ELEPHANT) elephantCount++;
                            }
                        }
                        if (advisorCount == 0) score -= 100;
                        if (elephantCount == 0) score -= 100;
                    } else if (p.getTYPE() == Piece.RED_GENERAL && player == Piece.RED) {
                        int advisorCount = 0, elephantCount = 0;
                        for (int cx = 3; cx <= 5; cx++) {
                            for (int cy = 7; cy <= 9; cy++) {
                                int cp = board.getPiece(cx, cy);
                                if (cp == Piece.RED_LEFT_ADVISOR || cp == Piece.RED_RIGHT_ADVISOR) advisorCount++;
                                if (cp == Piece.RED_LEFT_ELEPHANT || cp == Piece.RED_RIGHT_ELEPHANT) elephantCount++;
                            }
                        }
                        if (advisorCount == 0) score += 100;
                        if (elephantCount == 0) score += 100;
                    }
                }
            }
        }
        return score;
    }

    /**
     * Evaluate king safety
     */
    private static int evaluateKingSafety(Check board, int player) {
        int score = 0;
        int kingX = -1, kingY = -1;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == player &&
                        (piece == Piece.BLACK_GENERAL || piece == Piece.RED_GENERAL)) {
                    kingX = x;
                    kingY = y;
                    break;
                }
            }
        }
        if (kingX == -1) return 0;

        // Penalize open lines to king
        int opponent = (player == Piece.BLACK) ? Piece.RED : Piece.BLACK;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == opponent &&
                        (piece == Piece.RED_LEFT_ROOK || piece == Piece.RED_RIGHT_ROOK ||
                                piece == Piece.BLACK_LEFT_ROOK || piece == Piece.BLACK_RIGHT_ROOK ||
                                piece == Piece.RED_LEFT_CANNON || piece == Piece.RED_RIGHT_CANNON ||
                                piece == Piece.BLACK_LEFT_CANNON || piece == Piece.BLACK_RIGHT_CANNON)) {
                    List<Move> moves = getValidMoves(x, y, piece, board);
                    for (Move m : moves) {
                        if (m.toX == kingX && m.toY == kingY) {
                            score -= 200;
                        }
                    }
                }
            }
        }
        return score;
    }

    /**
     * Evaluate center control
     */
    private static int evaluateCenterControl(Check board, int player) {
        int score = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0) {
                    ChessPiece p = StaticPieces.getPieces().elementAt(piece);
                    if (p.getCOLOR() == player) {
                        if (p.getTYPE() == Piece.BLACK_LEFT_KNIGHT || p.getTYPE() == Piece.BLACK_RIGHT_KNIGHT) {
                            if ((x == 2 && y == 2) || (x == 6 && y == 2)) {
                                score += 30;
                            }
                        } else if (p.getTYPE() == Piece.RED_LEFT_KNIGHT || p.getTYPE() == Piece.RED_RIGHT_KNIGHT) {
                            if ((x == 2 && y == 7) || (x == 6 && y == 7)) {
                                score -= 30;
                            }
                        }
                        if ((p.getTYPE() == Piece.BLACK_LEFT_ROOK || p.getTYPE() == Piece.BLACK_RIGHT_ROOK ||
                                p.getTYPE() == Piece.BLACK_LEFT_CANNON || p.getTYPE() == Piece.BLACK_RIGHT_CANNON) &&
                                y > 0) {
                            score += 20;
                        } else if ((p.getTYPE() == Piece.RED_LEFT_ROOK || p.getTYPE() == Piece.RED_RIGHT_ROOK ||
                                p.getTYPE() == Piece.RED_LEFT_CANNON || p.getTYPE() == Piece.RED_RIGHT_CANNON) &&
                                y < 9) {
                            score -= 20;
                        }
                    }
                }
            }
        }
        if (player == Piece.BLACK && board.getPiece(4, 3) >= Piece.BLACK_PAWN_0 && board.getPiece(4, 3) <= Piece.BLACK_PAWN_4) {
            if (isPieceThreatened(board, 4, 3, player)) {
                score -= 50;
            }
        } else if (player == Piece.RED && board.getPiece(4, 6) >= Piece.RED_PAWN_0 && board.getPiece(4, 6) <= Piece.RED_PAWN_4) {
            if (isPieceThreatened(board, 4, 6, player)) {
                score += 50;
            }
        }
        return score;
    }

    private static boolean isEndgame(Check board) {
        return getGamePhase(board) <= 0.4;
    }

    private static int evaluateKingActivity(Check board, int player) {
        int score = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0) {
                    ChessPiece p = StaticPieces.getPieces().elementAt(piece);
                    if (p.getCOLOR() == player && (p.getTYPE() == Piece.BLACK_GENERAL || p.getTYPE() == Piece.RED_GENERAL)) {
                        if (player == Piece.BLACK && y > 2) score += 50;
                        else if (player == Piece.RED && y < 7) score -= 50;
                    }
                }
            }
        }
        return score;
    }

    private static boolean isRepetition(Check board, List<Check> history) {
        int repetitions = 0;
        for (Check pastBoard : history) {
            if (boardsEqual(board, pastBoard)) {
                repetitions++;
                if (repetitions >= 3) return true;
            }
        }
        return false;
    }

    private static boolean boardsEqual(Check b1, Check b2) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                if (b1.getPiece(x, y) != b2.getPiece(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Improved findBestMove with aspiration windows
     */
    public static Move findBestMove(Check board, int maxDepth, int player, long timeLimitMs) {
        long startTime = System.currentTimeMillis();
        Move bestMove = null;
        int prevValue = evaluate(board, player);
        int window = 50; // Aspiration window size

        for (int depth = 1; depth <= maxDepth; depth++) {
            long depthStartTime = System.currentTimeMillis();
            int alpha = prevValue - window;
            int beta = prevValue + window;
            Move currentMove = null;

            while (true) {
                currentMove = findBestMoveAtDepth(board, depth, player, alpha, beta);
                if (currentMove == null) break;

                Check newBoard = board.clone();
                newBoard.makeMove(currentMove.fromX, currentMove.fromY, currentMove.toX, currentMove.toY);
                int value = minimax(newBoard, depth - 1, alpha, beta, player, false, new ArrayList<>());

                if (value <= alpha) {
                    alpha = Integer.MIN_VALUE;
                    continue;
                } else if (value >= beta) {
                    beta = Integer.MAX_VALUE;
                    continue;
                }
                bestMove = currentMove;
                prevValue = value;
                break;
            }

            long timeUsed = System.currentTimeMillis() - depthStartTime;
            if (System.currentTimeMillis() - startTime > timeLimitMs * 0.8) break;

            // Dynamic time management
            int moveCount = generateMoves(board, player).size();
            if (moveCount > 30 || Math.abs(prevValue - evaluate(board, player)) > 500) {
                timeLimitMs = (long) (timeLimitMs * 0.9);
            }
            window = Math.min(window * 2, 500); // Widen window for next depth
        }

        if (board.getMoveCount() % 20 == 0) {
            for (int[][] row : historyTable) {
                for (int[] col : row) {
                    Arrays.fill(col, 0);
                }
            }
        }

        System.out.println("Best move: " + bestMove);
        return bestMove;
    }

    private static Move findBestMoveAtDepth(Check board, int depth, int player, int alpha, int beta) {
        int bestVal = Integer.MIN_VALUE;
        Move bestMove = null;
        List<Move> moves = generateMoves(board, player);
        moves = orderMoves(board, moves, depth);
        for (Move move : moves) {
            Check newBoard = board.clone();
            newBoard.makeMove(move.fromX, move.fromY, move.toX, move.toY);
            int moveValue = minimax(newBoard, depth - 1, alpha, beta, player, false, new ArrayList<>());
            if (moveValue > bestVal) {
                bestVal = moveValue;
                bestMove = move;
            }
            alpha = Math.max(alpha, moveValue);
            if (alpha >= beta) break;
        }
        return bestMove;
    }

    /**
     * Enhanced minimax with PVS
     */
    public static int minimax(Check board, int depth, int alpha, int beta, int player, boolean isMaximizing, List<Check> history) {
        long zobristKey = board.getZobristKey();
        TranspositionEntry entry = transpositionTable.get(zobristKey);
        if (entry != null && entry.depth >= depth) {
            if (entry.flag == 0) return entry.value;
            if (entry.flag == 1 && entry.value >= beta) return entry.value;
            if (entry.flag == 2 && entry.value <= alpha) return entry.value;
        }

        if (depth <= 0) {
            return quiescenceSearch(board, alpha, beta, player, 0, 6); // Increased maxQDepth to 6
        }
        if (isRepetition(board, history)) {
            return 0;
        }
        history.add(board.clone());

        List<Move> moves = generateMoves(board, isMaximizing ? player : (player == Piece.BLACK ? Piece.RED : Piece.BLACK));
        if (moves.isEmpty()) {
            if (isInCheck(board, isMaximizing ? player : (player == Piece.BLACK ? Piece.RED : Piece.BLACK))) {
                return isMaximizing ? -1000000 : 1000000; // Adjusted checkmate value
            }
            return 0;
        }

        // Null move pruning
        if (!isMaximizing && depth >= 3 && !isInCheck(board, player == Piece.BLACK ? Piece.RED : Piece.BLACK)) {
            Check nullBoard = board.clone();
            nullBoard.switchPlayer();
            int nullValue = -minimax(nullBoard, depth - 3, -beta, -beta + 1, player, true, history);
            if (nullValue >= beta) {
                history.remove(history.size() - 1);
                return beta;
            }
        }

        moves = orderMoves(board, moves, depth);
        int bestValue = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move bestMove = null;
        int originalAlpha = alpha;
        boolean firstMove = true;

        for (Move move : moves) {
            int capturedPiece = board.getPiece(move.toX, move.toY);
            board.makeMove(move.fromX, move.fromY, move.toX, move.toY);

            int eval;
            if (firstMove) {
                eval = minimax(board, depth - 1, alpha, beta, player, !isMaximizing, history);
            } else {
                // PVS: Search with a null window
                eval = minimax(board, depth - 1, alpha, alpha + 1, player, !isMaximizing, history);
                if (eval > alpha && eval < beta) {
                    // Full re-search if the null window fails
                    eval = minimax(board, depth - 1, alpha, beta, player, !isMaximizing, history);
                }
            }

            board.undoMove(move.fromX, move.fromY, move.toX, move.toY, capturedPiece);

            if (isMaximizing) {
                if (eval > bestValue) {
                    bestValue = eval;
                    bestMove = move;
                }
                alpha = Math.max(alpha, eval);
                if (eval >= beta) {
                    historyTable[move.fromX][move.fromY][move.toX * 10 + move.toY] += depth * depth;
                    if (capturedPiece == -1) storeKillerMove(move, depth);
                    break;
                }
            } else {
                if (eval < bestValue) {
                    bestValue = eval;
                    bestMove = move;
                }
                beta = Math.min(beta, eval);
                if (eval <= alpha) {
                    historyTable[move.fromX][move.fromY][move.toX * 10 + move.toY] += depth * depth;
                    if (capturedPiece == -1) storeKillerMove(move, depth);
                    break;
                }
            }
            firstMove = false;
        }

        history.remove(history.size() - 1);

        // Store in transposition table
        int flag = (bestValue <= originalAlpha) ? 2 : (bestValue >= beta) ? 1 : 0;
        transpositionTable.put(zobristKey, new TranspositionEntry(depth, bestValue, bestMove, flag));

        return bestValue;
    }

    /**
     * Updated quiescenceSearch to prioritize checkmate and evaluate checks
     */
    private static int quiescenceSearch(Check board, int alpha, int beta, int player, int qDepth, int maxQDepth) {
        if (qDepth >= maxQDepth) {
            return evaluate(board, player);
        }

        int standPat = evaluate(board, player);
        if (standPat >= beta) {
            return beta;
        }
        if (standPat > alpha) {
            alpha = standPat;
        }

        List<Move> moves = generateCaptureMoves(board, player);
        // Include checks to evaluate responses after a check
        moves.addAll(generateCheckMoves(board, player));
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if ((piece >= Piece.BLACK_PAWN_0 && piece <= Piece.BLACK_PAWN_4 && y >= 7) ||
                        (piece >= Piece.RED_PAWN_0 && piece <= Piece.RED_PAWN_4 && y <= 2)) {
                    List<Move> pawnMoves = getValidMoves(x, y, piece, board);
                    for (Move move : pawnMoves) {
                        if (board.getPiece(move.toX, move.toY) >= 0) {
                            moves.add(move);
                        }
                    }
                }
            }
        }

        moves = orderMoves(board, moves, 0);

        for (Move move : moves) {
            int capturedPiece = board.getPiece(move.toX, move.toY);
            if (capturedPiece >= 0) {
                int attackerValue = pieceValues[board.getPiece(move.fromX, move.fromY)][0];
                int victimValue = pieceValues[capturedPiece][0];
                // Avoid captures where high-value piece (e.g., Cannon) captures lower-value piece (e.g., Knight)
                if (attackerValue > victimValue + 100 && capturedPiece != Piece.RED_GENERAL && capturedPiece != Piece.BLACK_GENERAL) {
                    System.out.println("Skipping unfavorable capture: " + move + " (attacker: " + attackerValue + ", victim: " + victimValue + ")");
                    continue;
                }
            }

            // Skip safety check for checkmate moves
            if (capturedPiece != Piece.RED_GENERAL && capturedPiece != Piece.BLACK_GENERAL && !isMoveSafe(board, move, player)) {
                System.out.println("Skipping unsafe move: " + move + " (piece at (" + move.toX + "," + move.toY + ") is threatened)");
                continue;
            }

            board.makeMove(move.fromX, move.fromY, move.toX, move.toY);
            int score = -quiescenceSearch(board, -beta, -alpha,
                    player == Piece.BLACK ? Piece.RED : Piece.BLACK, qDepth + 1, maxQDepth);
            board.undoMove(move.fromX, move.fromY, move.toX, move.toY, capturedPiece);

            if (score >= beta) {
                return beta;
            }
            if (score > alpha) {
                alpha = score;
            }
        }

        return alpha;
    }

    private static void storeKillerMove(Move move, int depth) {
        for (int i = 3; i > 0; i--) {
            killerMoves[depth][i] = killerMoves[depth][i - 1];
        }
        killerMoves[depth][0] = move;
    }

    /**
     * Updated orderMoves to prioritize checkmate moves absolutely
     */
    private static List<Move> orderMoves(Check board, List<Move> moves, int depth) {
        List<Move> ordered = new ArrayList<>();
        List<Move> checkmateMoves = new ArrayList<>();
        List<Move> safeChecks = new ArrayList<>();
        List<Move> unsafeChecks = new ArrayList<>();
        List<Move> captures = new ArrayList<>();
        List<Move> escapeMoves = new ArrayList<>();
        List<Move> threatenedMoves = new ArrayList<>();
        List<Move> developmentMoves = new ArrayList<>();
        List<Move> others = new ArrayList<>();
        int player = moves.isEmpty() ? Piece.BLACK :
                StaticPieces.getPieces().elementAt(board.getPiece(moves.get(0).fromX, moves.get(0).fromY)).getCOLOR();

        // Add killer moves
        for (int i = 0; i < 4; i++) {
            if (killerMoves[depth][i] != null && moves.contains(killerMoves[depth][i]) && !ordered.contains(killerMoves[depth][i])) {
                ordered.add(killerMoves[depth][i]);
            }
        }

        for (Move move : moves) {
            if (ordered.contains(move)) continue;
            int toPiece = board.getPiece(move.toX, move.toY);
            Check tempBoard = board.clone();
            tempBoard.makeMove(move.fromX, move.fromY, move.toX, move.toY);
            boolean isCheck = isInCheck(tempBoard, player == Piece.BLACK ? Piece.RED : Piece.BLACK);
            int opponent = (player == Piece.BLACK) ? Piece.RED : Piece.BLACK;
            boolean isCheckmate = isCheck && generateMoves(tempBoard, opponent).isEmpty();
            int piece = board.getPiece(move.fromX, move.fromY);
            boolean isDevelopment = (piece == Piece.BLACK_LEFT_KNIGHT || piece == Piece.BLACK_RIGHT_KNIGHT ||
                    piece == Piece.BLACK_LEFT_ROOK || piece == Piece.BLACK_RIGHT_ROOK ||
                    piece == Piece.RED_LEFT_KNIGHT || piece == Piece.RED_RIGHT_KNIGHT ||
                    piece == Piece.RED_LEFT_ROOK || piece == Piece.RED_RIGHT_ROOK) &&
                    toPiece == -1 && isOpening(board);

            boolean isOpenFile = false;
            if (piece == Piece.BLACK_LEFT_ROOK || piece == Piece.BLACK_RIGHT_ROOK ||
                    piece == Piece.RED_LEFT_ROOK || piece == Piece.RED_RIGHT_ROOK) {
                isOpenFile = true;
                for (int ny = 0; ny < 10; ny++) {
                    if (board.getPiece(move.toX, ny) >= 0 && ny != move.toY) {
                        isOpenFile = false;
                        break;
                    }
                }
            }

            boolean isEscape = isPieceThreatened(board, move.fromX, move.fromY, player) &&
                    !isPieceThreatened(tempBoard, move.toX, move.toY, player);

            if (toPiece == Piece.RED_GENERAL || toPiece == Piece.BLACK_GENERAL || isCheckmate) {
                checkmateMoves.add(move);
                System.out.println("Checkmate move added: " + move);
            } else if (isCheck) {
                if (isMoveSafe(board, move, player)) {
                    safeChecks.add(move);
                    System.out.println("Safe check added: " + move);
                } else {
                    unsafeChecks.add(move);
                    System.out.println("Unsafe check added: " + move + " (piece at (" + move.toX + "," + move.toY + ") is threatened)");
                }
            } else if (isEscape) {
                escapeMoves.add(move);
                System.out.println("Escape move added: " + move + " for piece " + piece);
            } else if (toPiece >= 0) {
                // Penalize captures where high-value piece captures lower-value piece
                int attackerValue = pieceValues[piece][0];
                int victimValue = pieceValues[toPiece][0];
                if (attackerValue > victimValue + 100) {
                    others.add(move);
                    System.out.println("De-prioritizing capture: " + move + " (attacker: " + attackerValue + ", victim: " + victimValue + ")");
                } else {
                    captures.add(move);
                }
            } else if (toPiece == -1 && isPieceThreatened(board, move.fromX, move.fromY, player)) {
                threatenedMoves.add(move);
            } else if (isDevelopment || isOpenFile) {
                developmentMoves.add(move);
            } else {
                others.add(move);
            }
        }

        // Sort captures using MVV-LVA
        captures.sort((m1, m2) -> {
            int v1Victim = board.getPiece(m1.toX, m1.toY) >= 0 ? pieceValues[board.getPiece(m1.toX, m1.toY)][0] : 0;
            int v2Victim = board.getPiece(m2.toX, m2.toY) >= 0 ? pieceValues[board.getPiece(m2.toX, m2.toY)][0] : 0;
            int v1Attacker = pieceValues[board.getPiece(m1.fromX, m1.fromY)][0];
            int v2Attacker = pieceValues[board.getPiece(m2.fromX, m2.fromY)][0];
            return (v2Victim - v1Victim) * 1000 + (v1Attacker - v2Attacker);
        });

        // Sort other move categories by history heuristics
        safeChecks.sort((m1, m2) -> {
            int score1 = historyTable[m1.fromX][m1.fromY][m1.toX * 10 + m1.toY];
            int score2 = historyTable[m2.fromX][m2.fromY][m2.toX * 10 + m2.toY];
            return score2 - score1;
        });

        unsafeChecks.sort((m1, m2) -> {
            int score1 = historyTable[m1.fromX][m1.fromY][m1.toX * 10 + m1.toY];
            int score2 = historyTable[m2.fromX][m2.fromY][m2.toX * 10 + m2.toY];
            return score2 - score1;
        });

        escapeMoves.sort((m1, m2) -> {
            int score1 = historyTable[m1.fromX][m1.fromY][m1.toX * 10 + m1.toY];
            int score2 = historyTable[m2.fromX][m2.fromY][m2.toX * 10 + m2.toY];
            return score2 - score1;
        });

        threatenedMoves.sort((m1, m2) -> {
            int score1 = historyTable[m1.fromX][m1.fromY][m1.toX * 10 + m1.toY];
            int score2 = historyTable[m2.fromX][m2.fromY][m2.toX * 10 + m2.toY];
            return score2 - score1;
        });

        developmentMoves.sort((m1, m2) -> {
            int score1 = historyTable[m1.fromX][m1.fromY][m1.toX * 10 + m1.toY];
            int score2 = historyTable[m2.fromX][m2.fromY][m2.toX * 10 + m2.toY];
            return score2 - score1;
        });

        others.sort((m1, m2) -> {
            int score1 = historyTable[m1.fromX][m1.fromY][m1.toX * 10 + m1.toY];
            int score2 = historyTable[m2.fromX][m2.fromY][m2.toX * 10 + m2.toY];
            return score2 - score1;
        });

        ordered.addAll(checkmateMoves); // Checkmate moves have highest priority
        ordered.addAll(captures);
        ordered.addAll(safeChecks);
        ordered.addAll(escapeMoves);
        ordered.addAll(threatenedMoves);
        ordered.addAll(developmentMoves);
        ordered.addAll(unsafeChecks);
        ordered.addAll(others);

        return ordered;
    }

    /**
     * New method to check if a move is safe (not moving into a threatened square)
     */
    private static boolean isMoveSafe(Check board, Move move, int player) {
        Check tempBoard = board.clone();
        tempBoard.makeMove(move.fromX, move.fromY, move.toX, move.toY);
        return !isPieceThreatened(tempBoard, move.toX, move.toY, player);
    }

    private static boolean isPieceThreatened(Check board, int x, int y, int player) {
        int opponent = (player == Piece.BLACK) ? Piece.RED : Piece.BLACK;
        for (int ox = 0; ox < 9; ox++) {
            for (int oy = 0; oy < 10; oy++) {
                int piece = board.getPiece(ox, oy);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == opponent) {
                    List<Move> moves = getValidMoves(ox, oy, piece, board);
                    for (Move m : moves) {
                        if (m.toX == x && m.toY == y) {
                            System.out.println("Piece at (" + x + "," + y + ") threatened by " + piece + " at (" + ox + "," + oy + ")");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static List<Move> generateMoves(Check board, int player) {
        List<Move> moves = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == player) {
                    List<Move> pieceMoves = getValidMoves(x, y, piece, board);
                    for (Move move : pieceMoves) {
                        Check tempBoard = board.clone();
                        tempBoard.makeMove(move.fromX, move.fromY, move.toX, move.toY);
                        if (!isInCheck(tempBoard, player)) {
                            moves.add(move);
                        }
                    }
                }
            }
        }
        return moves;
    }

    private static List<Move> generateCaptureMoves(Check board, int player) {
        List<Move> captures = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == player) {
                    List<Move> moves = getValidMoves(x, y, piece, board);
                    for (Move move : moves) {
                        if (board.getPiece(move.toX, move.toY) >= 0) {
                            Check tempBoard = board.clone();
                            tempBoard.makeMove(move.fromX, move.fromY, move.toX, move.toY);
                            if (!isInCheck(tempBoard, player)) {
                                captures.add(move);
                            }
                        }
                    }
                }
            }
        }
        return captures;
    }

    private static List<Move> generateCheckMoves(Check board, int player) {
        List<Move> checks = new ArrayList<>();
        int opponent = (player == Piece.BLACK) ? Piece.RED : Piece.BLACK;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int piece = board.getPiece(x, y);
                if (piece >= 0 && StaticPieces.getPieces().elementAt(piece).getCOLOR() == player) {
                    List<Move> moves = getValidMoves(x, y, piece, board);
                    for (Move move : moves) {
                        Check tempBoard = board.clone();
                        tempBoard.makeMove(move.fromX, move.fromY, move.toX, move.toY);
                        if (isInCheck(tempBoard, opponent) && !isInCheck(tempBoard, player)) {
                            checks.add(move);
                        }
                    }
                }
            }
        }
        return checks;
    }

    private static List<Move> getValidMoves(int x, int y, int piece, Check board) {
        List<Move> moves = new ArrayList<>();
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

    private static List<Move> addElephantMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {{2, 2}, {2, -2}, {-2, 2}, {-2, -2}};
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();

        for (int[] dir : directions) {
            int midX = x + dir[0] / 2, midY = y + dir[1] / 2;
            int newX = x + dir[0], newY = y + dir[1];

            if ((newX >= 0 && newX < 9) && ((
                    ((newY >= 0 && newY <= 4) && player == Piece.BLACK)
                            || ((newY >= 5 && newY <= 9) && player == Piece.RED)))
                    && board.isEmpty(midX, midY)) {
                if (board.isEmpty(newX, newY)) {
                    moves.add(new Move(x, y, newX, newY));
                } else if (StaticPieces.getPieces().elementAt(board.getPiece(newX, newY)).getCOLOR() != player) {
                    moves.add(new Move(x, y, newX, newY));
                }
            }
        }
        return moves;
    }

    private static List<Move> addAdvisorMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();

        for (int[] dir : directions) {
            int newX = x + dir[0], newY = y + dir[1];
            if (newX >= 3 && newX <= 5 && ((player == Piece.BLACK && newY >= 0 && newY <= 2) || (player == Piece.RED && newY >= 7 && newY <= 9))) {
                if (board.isEmpty(newX, newY) || StaticPieces.getPieces().elementAt(board.getPiece(newX, newY)).getCOLOR() != player) {
                    moves.add(new Move(x, y, newX, newY));
                }
            }
        }
        return moves;
    }

    private static List<Move> addGeneralMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();

        for (int[] dir : directions) {
            int newX = x + dir[0], newY = y + dir[1];
            if (newX >= 3 && newX <= 5 && ((player == Piece.BLACK && newY >= 0 && newY <= 2) || (player == Piece.RED && newY >= 7 && newY <= 9))) {
                if (board.isEmpty(newX, newY) || StaticPieces.getPieces().elementAt(board.getPiece(newX, newY)).getCOLOR() != player) {
                    moves.add(new Move(x, y, newX, newY));
                }
            }
        }

        if (player == Piece.BLACK) {
            for (int ny = y + 1; ny < 10; ny++) {
                int piece = board.getPiece(x, ny);
                if (piece >= 0) {
                    if (piece == Piece.RED_GENERAL) {
                        Move flyingMove = new Move(x, y, x, ny);
                        Check tempBoard = board.clone();
                        tempBoard.makeMove(x, y, x, ny);
                        if (!isInCheck(tempBoard, player)) {
                            moves.add(flyingMove);
                        }
                    }
                    break;
                }
            }
        } else {
            for (int ny = y - 1; ny >= 0; ny--) {
                int piece = board.getPiece(x, ny);
                if (piece >= 0) {
                    if (piece == Piece.BLACK_GENERAL) {
                        Move flyingMove = new Move(x, y, x, ny);
                        Check tempBoard = board.clone();
                        tempBoard.makeMove(x, y, x, ny);
                        if (!isInCheck(tempBoard, player)) {
                            moves.add(flyingMove);
                        }
                    }
                    break;
                }
            }
        }
        return moves;
    }

    private static List<Move> addRookMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i], ny = y + dy[i];
            while (nx >= 0 && nx < 9 && ny >= 0 && ny < 10) {
                if (board.isEmpty(nx, ny)) {
                    moves.add(new Move(x, y, nx, ny));
                } else {
                    if (StaticPieces.getPieces().elementAt(board.getPiece(nx, ny)).getCOLOR() != player) {
                        moves.add(new Move(x, y, nx, ny));
                    }
                    break;
                }
                nx += dx[i];
                ny += dy[i];
            }
        }
        return moves;
    }

    private static List<Move> addKnightMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int[][] directions = {{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}};
        int[][] blocks = {{-1, 0}, {-1, 0}, {1, 0}, {1, 0}, {0, -1}, {0, 1}, {0, -1}, {0, 1}};
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();

        for (int i = 0; i < 8; i++) {
            int nx = x + directions[i][0], ny = y + directions[i][1];
            int bx = x + blocks[i][0], by = y + blocks[i][1];
            if (nx >= 0 && nx < 9 && ny >= 0 && ny < 10 && board.isEmpty(bx, by)) {
                if (board.isEmpty(nx, ny) || StaticPieces.getPieces().elementAt(board.getPiece(nx, ny)).getCOLOR() != player) {
                    moves.add(new Move(x, y, nx, ny));
                }
            }
        }
        return moves;
    }

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
                    if (!jumped) {
                        moves.add(new Move(x, y, nx, ny));
                    }
                } else {
                    if (!jumped) {
                        jumped = true;
                    } else {
                        if (StaticPieces.getPieces().elementAt(board.getPiece(nx, ny)).getCOLOR() != player) {
                            moves.add(new Move(x, y, nx, ny));
                        }
                        break;
                    }
                }
                nx += dx[i];
                ny += dy[i];
            }
        }
        return moves;
    }

    private static List<Move> addPawnMoves(int x, int y, Check board) {
        List<Move> moves = new ArrayList<>();
        int player = StaticPieces.getPieces().elementAt(board.getPiece(x, y)).getCOLOR();
        int direction = (player == Piece.BLACK) ? 1 : -1;
        int newY = y + direction;

        if (newY >= 0 && newY < 10) {
            if (board.isEmpty(x, newY) || StaticPieces.getPieces().elementAt(board.getPiece(x, newY)).getCOLOR() != player) {
                moves.add(new Move(x, y, x, newY));
            }
        }

        if ((player == Piece.BLACK && y >= 5) || (player == Piece.RED && y <= 4)) {
            if (x > 0 && (board.isEmpty(x - 1, y) || StaticPieces.getPieces().elementAt(board.getPiece(x - 1, y)).getCOLOR() != player)) {
                moves.add(new Move(x, y, x - 1, y));
            }
            if (x < 8 && (board.isEmpty(x + 1, y) || StaticPieces.getPieces().elementAt(board.getPiece(x + 1, y)).getCOLOR() != player)) {
                moves.add(new Move(x, y, x + 1, y));
            }
        }
        return moves;
    }

    private static String generatePositionKey(Check board, int player) {
        if (board.getMoveCount() == 0 && player == Piece.BLACK) {
            return "initial_black";
        } else if (board.getPiece(4, 6) >= Piece.RED_PAWN_0 && board.getPiece(4, 6) <= Piece.RED_PAWN_4) {
            return "pawn_opening_red";
        } else if (board.getPiece(4, 0) == Piece.RED_LEFT_CANNON || board.getPiece(4, 0) == Piece.RED_RIGHT_CANNON) {
            return "C50_red";
        }
        return "";
    }

    private static boolean isOpening(Check board) {
        return board.getMoveCount() <= 6;
    }

    private static boolean isValidMove(Check board, Move move, int player) {
        int piece = board.getPiece(move.fromX, move.fromY);
        if (piece < 0 || StaticPieces.getPieces().elementAt(piece).getCOLOR() != player) return false;
        List<Move> validMoves = getValidMoves(move.fromX, move.fromY, piece, board);
        for (Move m : validMoves) {
            if (m.toX == move.toX && m.toY == move.toY) {
                Check tempBoard = board.clone();
                tempBoard.makeMove(move.fromX, move.fromY, move.toX, move.toY);
                return !isInCheck(tempBoard, player);
            }
        }
        return false;
    }
}