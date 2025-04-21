package controller;

import model.*;
import view.ChessBoard;
import view.CountDown;
import view.CloseButton;
import sound.SoundEffect;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Vector;

import static constant.Piece.*;

public class StaticPieces {
    private static int turn, minute, second, level;
    private static final RoundedImageLabel notice_1 = new RoundedImageLabel(
            new ImageIcon(System.getProperty("user.dir") + "/resource/image/effect/wait.png"), 30, 30);
    private static final RoundedImageLabel notice_2 = new RoundedImageLabel(
            new ImageIcon(System.getProperty("user.dir") + "/resource/image/effect/bot.png"), 30, 30);
    private static final CountDown clock_1 = new CountDown();
    private static final CountDown clock_2 = new CountDown();
    private static final CloseButton closeButton = new CloseButton();
    private static ChessBoard chessBoard = new ChessBoard();
    private static Check check = new Check();
    private static final SoundEffect soundEffect = new SoundEffect();
    private static Setting setting = new Setting();
    private static final Vector<ChessPiece> pieces = new Vector<>();
    private static controller.Event event = new controller.Event();

    public static CloseButton getCloseButton() {
        return StaticPieces.closeButton;
    }

    public static Check getCheck() {
        return StaticPieces.check;
    }
    public static void setCheck() {
        StaticPieces.check = new Check();
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        StaticPieces.level = level;
    }

    public static void setup() {
        StaticPieces.setupPieces();
        for (ChessPiece piece : StaticPieces.pieces)
            StaticPieces.chessBoard.add(piece);
    }

    private static void setupPieces() {
        StaticPieces.pieces.add(new General(RED));
        StaticPieces.pieces.add(new General(BLACK));
        StaticPieces.pieces.add(new Advisor(RED, LEFT));
        StaticPieces.pieces.add(new Advisor(RED, RIGHT));
        StaticPieces.pieces.add(new Advisor(BLACK, LEFT));
        StaticPieces.pieces.add(new Advisor(BLACK, RIGHT));
        StaticPieces.pieces.add(new Elephant(RED, LEFT));
        StaticPieces.pieces.add(new Elephant(RED, RIGHT));
        StaticPieces.pieces.add(new Elephant(BLACK, LEFT));
        StaticPieces.pieces.add(new Elephant(BLACK, RIGHT));
        StaticPieces.pieces.add(new Knight(RED, LEFT));
        StaticPieces.pieces.add(new Knight(RED, RIGHT));
        StaticPieces.pieces.add(new Knight(BLACK, LEFT));
        StaticPieces.pieces.add(new Knight(BLACK, RIGHT));
        StaticPieces.pieces.add(new Rook(RED, LEFT));
        StaticPieces.pieces.add(new Rook(RED, RIGHT));
        StaticPieces.pieces.add(new Rook(BLACK, LEFT));
        StaticPieces.pieces.add(new Rook(BLACK, RIGHT));
        StaticPieces.pieces.add(new Cannon(RED, LEFT));
        StaticPieces.pieces.add(new Cannon(RED, RIGHT));
        StaticPieces.pieces.add(new Cannon(BLACK, LEFT));
        StaticPieces.pieces.add(new Cannon(BLACK, RIGHT));
        StaticPieces.pieces.add(new Pawn(RED, 0));
        StaticPieces.pieces.add(new Pawn(RED, 1));
        StaticPieces.pieces.add(new Pawn(RED, 2));
        StaticPieces.pieces.add(new Pawn(RED, 3));
        StaticPieces.pieces.add(new Pawn(RED, 4));
        StaticPieces.pieces.add(new Pawn(BLACK, 0));
        StaticPieces.pieces.add(new Pawn(BLACK, 1));
        StaticPieces.pieces.add(new Pawn(BLACK, 2));
        StaticPieces.pieces.add(new Pawn(BLACK, 3));
        StaticPieces.pieces.add(new Pawn(BLACK, 4));
    }

    public static Vector<ChessPiece> getPieces() {
        return pieces;
    }

    public static SoundEffect getSoundEffect() {
        return StaticPieces.soundEffect;
    }

    public static controller.Event getEvent() {
        return StaticPieces.event;
    }

    public static void setEvent() {
        StaticPieces.event = new Event();
    }

    public static Setting getSetting() {
        return StaticPieces.setting;
    }

    public static void setNewSetting() {
        StaticPieces.setting = new Setting();
    }

    public static CountDown getClock_1() {
        return StaticPieces.clock_1;
    }

    public static CountDown getClock_2() {
        return StaticPieces.clock_2;
    }

    public static int getTurn() {
        return turn;
    }

    public static void setTurn(int turn) {
        StaticPieces.turn = turn;
    }

    public static ChessBoard getChessBoardPanel() {
        return StaticPieces.chessBoard;
    }

    public static void setChessBoardPanel(ChessBoard chessBoard) {
        StaticPieces.chessBoard = chessBoard;
    }

    public static int getMinute() {
        return StaticPieces.minute;
    }

    public static void setMinute(int minute) {
        StaticPieces.minute = minute;
    }

    public static int getSecond() {
        return second;
    }

    public static void setSecond(int second) {
        StaticPieces.second = second;
    }

    public static RoundedImageLabel getNotice_1() {
        return StaticPieces.notice_1;
    }

    public static RoundedImageLabel getNotice_2() {
        return StaticPieces.notice_2;
    }

    public static void setNew(JButton start) {
        StaticPieces.changeImage("wait", 1);
        StaticPieces.changeImage("wait", 2);
        StaticPieces.getNotice_2().setVisible(true);
        StaticPieces.getNotice_1().setVisible(true);
        StaticPieces.setCheck();
        StaticPieces.setTurn(new Random().nextInt(2));
        StaticPieces.getClock_1().setTime(StaticPieces.minute, StaticPieces.second);
        StaticPieces.getClock_2().setTime(StaticPieces.minute, StaticPieces.second);
        for (ChessPiece _piece_ : StaticPieces.getPieces()) {
            StaticPieces.chessBoard.add(_piece_);
        }
        StaticPieces.setNewSetting();
        StaticPieces.getChessBoardPanel().setNew(start);
        StaticPieces.setEvent();
    }

    public static void changeImage(String status, int type) {
        if (type == 1) {
            StaticPieces.getNotice_1().setVisible(true);
            StaticPieces.getNotice_2().setVisible(true);
        } else if (type == 2) {
            StaticPieces.getNotice_2().setVisible(true);
            StaticPieces.getNotice_1().setVisible(true);
        } else {
            StaticPieces.getNotice_2().setVisible(false);
            StaticPieces.getNotice_1().setVisible(false);
        }
    }

    public static void confirmQuit(JFrame jFrame) {
        JDialog notice = new JDialog(jFrame, "Xác nhận thoát game", true);
        notice.setSize(250, 150);
        notice.setLayout(null);

        JLabel question = new JLabel("Thoát game?");
        question.setSize(100, 30);
        question.setLocation(75, 20);

        JButton yes = new JButton("Có");
        yes.setSize(70, 30);
        yes.setLocation(30, 65);
        yes.setBackground(new Color(255, 186, 35));
        yes.setFocusPainted(false);
        yes.addActionListener(e -> {
            notice.dispose();
            jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
        });

        JButton no = new JButton("Không");
        no.setSize(70, 30);
        no.setLocation(140, 65);
        no.setBackground(new Color(255, 186, 35));
        no.setFocusPainted(false);
        no.addActionListener(e -> notice.dispose());

        notice.getContentPane().add(question);
        notice.getContentPane().add(yes);
        notice.getContentPane().add(no);
        notice.setLocationRelativeTo(jFrame);
        notice.setVisible(true);
    }

    public static class CustomSliderUI extends BasicSliderUI {

        private final Color trackColor = new Color(200, 200, 255);
        private final Color thumbColor = new Color(100, 100, 255);
        private final Color thumbBorderColor = new Color(50, 50, 200);

        public CustomSliderUI(JSlider b) {
            super(b);
        }

        @Override
        protected void installDefaults(JSlider slider) {
            super.installDefaults(slider);
            slider.setBackground(Color.WHITE);
            slider.setForeground(Color.BLUE);
            slider.setOpaque(false);
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cy = trackRect.y + (trackRect.height / 2) - 2;
            int trackLeft = trackRect.x;
            int trackRight = trackRect.x + trackRect.width;

            // Vẽ thanh track (nền)
            g2.setColor(trackColor);
            g2.fillRoundRect(trackLeft, cy, trackRight - trackLeft, 6, 4, 4);
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int thumbWidth = 10;
            int thumbHeight = 10;

            int x = thumbRect.x + (thumbRect.width - thumbWidth) / 2;
            int y = thumbRect.y + (thumbRect.height - thumbHeight) / 2;

            // Vẽ bóng
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillOval(x + 1, y + 1, thumbWidth, thumbHeight);

            // Vẽ thumb chính
            g2.setColor(thumbColor);
            g2.fillOval(x, y, thumbWidth, thumbHeight);

            // Viền
            g2.setColor(thumbBorderColor);
            g2.drawOval(x, y, thumbWidth, thumbHeight);

            g2.dispose();
        }
    }
}