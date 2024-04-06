package game;

import chesspiece.*;
import image.NewImage;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class ChessBoard extends JPanel implements Piece {
    private Boolean pause;
    private final JLabel timer_2;
    private final JLabel timer_1;
    public ChessBoard() {
        this.timer_2 = new JLabel();
        this.timer_1 = new JLabel();
        this.timer_2.setLocation(CELL_SIZE * 11, CELL_SIZE * 6 + CELL_SIZE / 4);
        this.timer_1.setLocation(CELL_SIZE * 11, CELL_SIZE * 4 + CELL_SIZE / 4);

        this.pause = true;
        this.setSize(_width_, _height_);
        this.add(this.timer_2);
        this.add(this.timer_1);
        StaticPieces.getClock_1().start(timer_1);
        StaticPieces.getClock_2().start(timer_2);
        this.setLayout(null);
        this.setBackground(new Color(205, 160, 70));
    }

    public Boolean getPause() {
        return pause;
    }
    public void setNew(JButton start){
        this.setTime(0, StaticPieces.getMinute(), StaticPieces.getSecond());
        this.setTime(1, StaticPieces.getMinute(), StaticPieces.getSecond());
        this.setButton(start);
        this.play(start);
        this.setVisible(true);
    }
    public void removePieces(){
        for(ChessPiece piece : StaticPieces.getPieces())
            piece.resetDefauft();
        this.removeAll();
        this.add(this.timer_1);
        this.add(this.timer_2);
        this.pause = true;
    }

    public void play(JButton button) {
        StaticPieces.getCloseButton().setClose(this);
        StaticPieces.getCloseButton().setHide(this);
        button.addActionListener(e -> {
            if(StaticPieces.getTurn() == -1)
                return;
            if (StaticPieces.getFirst() == 2) {
                JOptionPane.showMessageDialog(null, (StaticPieces.getTurn() % 2 == 1) ? "Đen đi trước" : "Đỏ đi trước");
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/start.png");
                imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
                button.setIcon(imageIcon);
                if (StaticPieces.getTurn() % 2 == Piece.BLACK) {
                    StaticPieces.getClock_1().resume();
                } else {
                    StaticPieces.getClock_2().resume();
                }
            }
            StaticPieces.setFirst(StaticPieces.getTurn() % 2);
            System.out.println(pause);
            if (pause) {
                if (StaticPieces.getFirst() == Piece.BLACK) {
                    StaticPieces.getClock_1().resume();
                } else {
                    StaticPieces.getClock_2().resume();
                }
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/stop.png");
                imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
                button.setIcon(imageIcon);
                StaticPieces.getSetting().closeSetting();
            } else {
                StaticPieces.getClock_1().stop();
                StaticPieces.getClock_2().stop();
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/start.png");
                imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
                button.setIcon(imageIcon);
            }
            this.pause = !this.pause;
        });
    }

    public void setButton(JButton button) {
        button.setSize(90, 32);
        button.setLocation(CELL_SIZE * 11, CELL_SIZE * 5 + CELL_SIZE / 4);
        ImageIcon imageIcon;
        imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/stop.png");
        if (this.pause || StaticPieces.getFirst() == 2)
            imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/start.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
        button.setIcon(imageIcon);
        button.setBorderPainted(false);
        this.add(button);
    }


    public void setTime(int type, int minute, int second) {
        if(type == 0) {
            this.timer_1.setSize(100, 30);
            this.timer_1.setFont(new Font("Arial", Font.BOLD, 30));
            String _minute = minute > 9 ? "" + minute : "0" + minute;
            String _second = second > 9 ? "" + second : "0" + second;
            this.timer_1.setText(_minute + " : " + _second);
        }else{
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

