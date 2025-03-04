package view;

import controller.Ai;
import model.ChessPiece;
import constant.Piece;
import controller.StaticPieces;
import file.IOFile;
import image.NewImage;
import model.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Vector;

public class ChessBoard extends JPanel implements Piece {
    private Boolean pause;
    private JLabel timer_2, timer_1;
    private JButton back;

    public ChessBoard() {
        this.setup();
    }

    private void setup() {
        StaticPieces.getNotice_1().setSize(200, 106);
        StaticPieces.getNotice_2().setSize(200, 106);
        StaticPieces.getNotice_1().setLocation(11 * CELL_SIZE - CELL_SIZE / 4, 3 * CELL_SIZE - CELL_SIZE / 2);
        StaticPieces.getNotice_2().setLocation(11 * CELL_SIZE - CELL_SIZE / 4, 7 * CELL_SIZE);
        StaticPieces.changeImage("ss", 1);
        StaticPieces.changeImage("ss", 2);
        StaticPieces.getNotice_1().setVisible(true);
        StaticPieces.getNotice_2().setVisible(true);
        this.add(StaticPieces.getNotice_2());
        this.add(StaticPieces.getNotice_1());

        this.back = new JButton();
        this.back.setSize(35, 35);
        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/menu/back.png");
        image = new NewImage().resizeImage(image, 35, 35);
        this.back.setIcon(image);
        this.back.setLocation(CELL_SIZE * 13 + CELL_SIZE/ 2, CELL_SIZE * 5 + CELL_SIZE / 4);
        this.back.setRolloverEnabled(false);
        this.back.setBorderPainted(false);
        this.back.setContentAreaFilled(false);
        this.timer_2 = new JLabel();
        this.timer_1 = new JLabel();
        this.timer_2.setLocation(CELL_SIZE * 11 + CELL_SIZE/2, CELL_SIZE * 6 + CELL_SIZE / 4);
        this.timer_1.setLocation(CELL_SIZE * 11 + CELL_SIZE/2, CELL_SIZE * 4 + CELL_SIZE / 4);

        this.pause = true;
        this.setBackground(new Color(192, 187, 187));
        this.setSize(_width_, _height_);
        this.add(this.back);
        this.add(this.timer_2);
        this.add(this.timer_1);
        StaticPieces.getClock_1().start(this.timer_1);
        StaticPieces.getClock_2().start(this.timer_2);
        this.setLayout(null);
    }

    public void setNew(JButton start) {
        this.setTime(0, StaticPieces.getMinute(), StaticPieces.getSecond());
        this.setTime(1, StaticPieces.getMinute(), StaticPieces.getSecond());
        this.setButton(start);
        this.backLastMove(start);
        this.play(start);
        this.setVisible(true);
    }

    public void removePieces() {
        for (ChessPiece piece : StaticPieces.getPieces())
            piece.resetDefauft();
        this.removeAll();
        this.add(this.timer_1);
        this.add(this.timer_2);
        this.add(this.back);
        this.add(StaticPieces.getNotice_1());
        this.add(StaticPieces.getNotice_2());
        this.pause = true;
    }

    public void play(JButton button) {
        StaticPieces.getCloseButton().setClose(this);
        StaticPieces.getCloseButton().setHide(this);
        button.addActionListener(e -> {
            if (StaticPieces.getTurn() == -1)
                return;

            if (StaticPieces.getFirst() == 2) {
                JOptionPane.showMessageDialog(null, (StaticPieces.getTurn() % 2 == 1) ? "Đen đi trước" : "Đỏ đi trước", "người đi trước", JOptionPane.INFORMATION_MESSAGE);
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/start.png");
                imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
                button.setIcon(imageIcon);
                if (StaticPieces.getTurn() % 2 == Piece.BLACK) {
                    Move move = Ai.findBestMove(StaticPieces.getCheck(), 2, Piece.BLACK);
                    System.out.println(move.toX + " " + move.toY + " \nfrom " + move.fromX + " " + move.fromY);

                    StaticPieces.getClock_1().resume();
                    StaticPieces.changeImage("wait", 2);

                    int ch = StaticPieces.getCheck().getPiece(move.fromX, move.fromY);
                    ChessPiece piece = StaticPieces.getPieces().elementAt(ch);
                    int cell = piece.getSIZE();
                    piece.setLocation(move.toX + Piece.RADIUS - cell / 2, move.toY + Piece.RADIUS - cell / 2);
                } else {
                    StaticPieces.getClock_2().resume();
                    StaticPieces.changeImage("wait", 1);
                }
            }
            StaticPieces.setFirst(StaticPieces.getTurn() % 2);
            if (this.pause) {
                if (StaticPieces.getFirst() == Piece.BLACK) {
                    StaticPieces.getClock_1().resume();
                    StaticPieces.changeImage("wait", 1);
                } else {
                    StaticPieces.getClock_2().resume();
                    StaticPieces.changeImage("wait", 2);
                }
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/stop.png");
                imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
                button.setIcon(imageIcon);
                StaticPieces.getSetting().closeSetting();
            } else {
                StaticPieces.getClock_1().stop();
                StaticPieces.getClock_2().stop();
                StaticPieces.changeImage("", 0);
                StaticPieces.getEvent().hideButton();
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/start.png");
                imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
                button.setIcon(imageIcon);
            }
            this.pause = !this.pause;
        });
    }

    public void setButton(JButton button) {
        button.setSize(90, 32);
        button.setLocation(CELL_SIZE * 11 + CELL_SIZE /2, CELL_SIZE * 5 + CELL_SIZE / 4);
        ImageIcon imageIcon;
        imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/stop.png");
        if (this.pause || StaticPieces.getFirst() == 2)
            imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/start.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
        button.setIcon(imageIcon);
        button.setBorderPainted(false);
        this.add(button);
    }

    public void setTime(int type, int minute, int second) {
        if (type == 0) {
            this.timer_1.setSize(100, 30);
            this.timer_1.setFont(new Font("Arial", Font.BOLD, 30));
            String _minute = minute > 9 ? "" + minute : "0" + minute;
            String _second = second > 9 ? "" + second : "0" + second;
            this.timer_1.setText(_minute + " : " + _second);
        } else {
            this.timer_2.setSize(100, 30);
            this.timer_2.setFont(new Font("Arial", Font.BOLD, 30));
            String _minute = minute > 9 ? "" + minute : "0" + minute;
            String _second = second > 9 ? "" + second : "0" + second;
            this.timer_2.setText(_minute + " : " + _second);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(Piece.CELL_SIZE/2, Piece.CELL_SIZE/2, 9 * Piece.CELL_SIZE, 10 * Piece.CELL_SIZE);
        Graphics2D g2d = (Graphics2D) g;
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

        //
        stroke = new BasicStroke(10.0f);
        g2d.setStroke(stroke);
        line = new Line2D.Double((double) CELL_SIZE / 2, (double) CELL_SIZE / 2, 9 * CELL_SIZE + (double) CELL_SIZE / 2, (double) CELL_SIZE / 2);
        g2d.draw(line);
        line = new Line2D.Double((double) CELL_SIZE / 2, 10 * CELL_SIZE + (double) CELL_SIZE / 2, 9 * CELL_SIZE + (double) CELL_SIZE / 2, 10 * CELL_SIZE + (double) CELL_SIZE / 2);
        g2d.draw(line);
        line = new Line2D.Double((double) CELL_SIZE / 2, (double) CELL_SIZE / 2, (double) CELL_SIZE / 2, 10 * CELL_SIZE + (double) CELL_SIZE / 2);
        g2d.draw(line);
        line = new Line2D.Double(9 * CELL_SIZE + (double) CELL_SIZE / 2, (double) CELL_SIZE / 2, 9 * CELL_SIZE + (double) CELL_SIZE / 2, 10 * CELL_SIZE + (double) CELL_SIZE / 2);
        g2d.draw(line);
    }

    private void backLastMove(JButton button) {
        this.back.addActionListener(e -> {
            if (IOFile.isEmpty(System.getProperty("user.dir") + "/resource/file/lastmove.txt"))
                return;
            if (StaticPieces.getTurn() == -1)
                this.pause = true;
            StaticPieces.getEvent().hideButton();
            StaticPieces.getClock_1().stop();
            StaticPieces.getClock_2().stop();
            StaticPieces.changeImage("", 0);
            ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/start.png");
            imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
            button.setIcon(imageIcon);
            Vector<Integer> last = IOFile.loadLastMove();
            int locateX = last.elementAt(1);
            int locateY = last.elementAt(2);
            int x = StaticPieces.getPieces().elementAt(last.firstElement()).getLocateX();
            int y = StaticPieces.getPieces().elementAt(last.firstElement()).getLocateY();
            StaticPieces.getCheck().setPiece(x, y, last.elementAt(3));
            StaticPieces.getPieces().elementAt(last.firstElement()).setLocate(locateX, locateY);
            StaticPieces.getCheck().setPiece(last.elementAt(1), last.elementAt(2), last.firstElement());
            StaticPieces.setTurn(last.lastElement());
            if (last.elementAt(3) != -1)
                StaticPieces.getPieces().elementAt(last.elementAt(3)).setVisible(true);
        });
    }

    public Boolean getPause() {
        return pause;
    }
}

