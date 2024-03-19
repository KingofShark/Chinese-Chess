package main;

import chesspiece.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Vector;

public class ChessBoardPanel extends JPanel implements Piece {
    private final Vector<ChessPiece> pieces;
    public ChessBoardPanel() {
        this.pieces = new Vector<>();
        setSize(_width_, _height_);
        setupChesspiece();
        setLayout(null);
        setBackground(new Color(205, 160, 70));
    }

    public void setupChesspiece() {
        pieces.add(new General(RED));
        pieces.add(new General(BLACK));
        pieces.add(new Advisor(RED, LEFT));
        pieces.add(new Advisor(RED, RIGHT));
        pieces.add(new Advisor(BLACK, LEFT));
        pieces.add(new Advisor(BLACK, RIGHT));
        pieces.add(new Elephant(RED, LEFT));
        pieces.add(new Elephant(RED, RIGHT));
        pieces.add(new Elephant(BLACK, LEFT));
        pieces.add(new Elephant(BLACK, RIGHT));
        pieces.add(new Knight(RED, LEFT));
        pieces.add(new Knight(RED, RIGHT));
        pieces.add(new Knight(BLACK, LEFT));
        pieces.add(new Knight(BLACK, RIGHT));
        pieces.add(new Rook(RED, LEFT));
        pieces.add(new Rook(RED, RIGHT));
        pieces.add(new Rook(BLACK, LEFT));
        pieces.add(new Rook(BLACK, RIGHT));
        pieces.add(new Cannon(RED, LEFT));
        pieces.add(new Cannon(RED, RIGHT));
        pieces.add(new Cannon(BLACK, LEFT));
        pieces.add(new Cannon(BLACK, RIGHT));
        pieces.add(new Pawn(RED, 0));
        pieces.add(new Pawn(RED, 1));
        pieces.add(new Pawn(RED, 2));
        pieces.add(new Pawn(RED, 3));
        pieces.add(new Pawn(RED, 4));
        pieces.add(new Pawn(BLACK, 0));
        pieces.add(new Pawn(BLACK, 1));
        pieces.add(new Pawn(BLACK, 2));
        pieces.add(new Pawn(BLACK, 3));
        pieces.add(new Pawn(BLACK, 4));
        for (ChessPiece _piece_ : pieces)
            add(_piece_);
        new Event(pieces, this);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }
    public void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Khung bàn cờ

        g.setColor(new Color(152, 93, 58));
        int row, col;
        for (row = 0; row < CELL_SIZE / 2; row++)
            for (col = 0; col < CELL_SIZE; col++)
                g.fillRect(row * CELL_SIZE, col * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        for (row = CELL_SIZE / 2; row < CELL_SIZE; row++)
            for (col = 0; col < CELL_SIZE / 2; col++)
                g.fillRect(row * CELL_SIZE, col * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        for (row = CELL_SIZE / 2 + 1; row < CELL_SIZE + 1; row++)
            for (col = 0; col < CELL_SIZE; col++)
                g.fillRect(row * CELL_SIZE, col * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        for (row = 0; row < CELL_SIZE; row++) {
            for (col = CELL_SIZE / 2 + 1; col < CELL_SIZE; col++) {
                g.fillRect(row * CELL_SIZE, col * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        g.setColor(new Color(216, 83, 39));

        Stroke stroke = new BasicStroke(5.0f);
        g2d.setStroke(stroke);
        Line2D line;
        for (int i = 1; i <= 10; i++) {
            line = new Line2D.Double(CELL_SIZE, i * CELL_SIZE, 9 * CELL_SIZE, i * CELL_SIZE);
            g2d.draw(line);
        }
        line = new Line2D.Double(CELL_SIZE, CELL_SIZE, CELL_SIZE, 10 * CELL_SIZE);
        g2d.draw(line);
        line = new Line2D.Double(9 * CELL_SIZE, CELL_SIZE, 9 * CELL_SIZE, 10 * CELL_SIZE);
        g2d.draw(line);
        for (int i = 2; i <= 8; i++) {
            line = new Line2D.Double(i * CELL_SIZE, CELL_SIZE, i * CELL_SIZE, 5 * CELL_SIZE);
            g2d.draw(line);
            line = new Line2D.Double(i * CELL_SIZE, 6 * CELL_SIZE, i * CELL_SIZE, 10 * CELL_SIZE);
            g2d.draw(line);
        }
        // ô tướng
        line = new Line2D.Double(4 * CELL_SIZE, CELL_SIZE + 1, 6 * CELL_SIZE, 3 * CELL_SIZE);
        g2d.draw(line);
        line = new Line2D.Double(4 * CELL_SIZE, 3 * CELL_SIZE, 6 * CELL_SIZE, CELL_SIZE + 1);
        g2d.draw(line);
        line = new Line2D.Double(4 * CELL_SIZE, 8 * CELL_SIZE, 6 * CELL_SIZE, 10 * CELL_SIZE - 1);
        g2d.draw(line);
        line = new Line2D.Double(4 * CELL_SIZE, 10 * CELL_SIZE - 1, 6 * CELL_SIZE, 8 * CELL_SIZE);
        g2d.draw(line);
        // sông
        line = new Line2D.Double(CELL_SIZE, 5 * CELL_SIZE + 1, 5 * CELL_SIZE, 6 * CELL_SIZE - 1);
        g2d.draw(line);
        line = new Line2D.Double(CELL_SIZE, 6 * CELL_SIZE + 1, 5 * CELL_SIZE, 5 * CELL_SIZE - 1);
        g2d.draw(line);
        line = new Line2D.Double(5 * CELL_SIZE, 5 * CELL_SIZE, 9 * CELL_SIZE, 6 * CELL_SIZE - 1);
        g2d.draw(line);
        line = new Line2D.Double(5 * CELL_SIZE, 6 * CELL_SIZE, 9 * CELL_SIZE, 5 * CELL_SIZE - 1);
        g2d.draw(line);
    }
}

