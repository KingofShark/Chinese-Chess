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

public class GameController {
    private static int turn, minute, second, level;
    private static final RoundedImageLabel avatarPlayer = new RoundedImageLabel(
            new ImageIcon(System.getProperty("user.dir") + "/resource/image/effect/wait.png"));
    private static final RoundedImageLabel avatarBot = new RoundedImageLabel(
            new ImageIcon(System.getProperty("user.dir") + "/resource/image/effect/bot.png"));
    private static final CountDown clock_1 = new CountDown();
    private static final CountDown clock_2 = new CountDown();
    private static final CloseButton closeButton = new CloseButton();
    private static ChessBoard chessBoard = new ChessBoard();
    private static Check check = new Check();
    private static final SoundEffect soundEffect = new SoundEffect();
    private static Setting setting = new Setting();
    private static Vector<ChessPiece> pieces = new Vector<>();
    private static controller.Event event = new controller.Event();

    public static CloseButton getCloseButton() {
        return GameController.closeButton;
    }

    public static Check getCheck() {
        return GameController.check;
    }
    public static void setCheck() {
        GameController.check = new Check();
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        GameController.level = level;
    }

    public static void setup() {
        GameController.pieces = setupPieces();
        for (ChessPiece piece : GameController.pieces)
            GameController.chessBoard.add(piece);
    }

    public static Vector<ChessPiece> setupPieces() {
        Vector<ChessPiece> pieces = new Vector<>();
        pieces.add(new General(RED));
        pieces.add(new General(BLACK));
        pieces.add(new Advisor(RED, RIGHT));
        pieces.add(new Advisor(RED, LEFT));
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
        return pieces;
    }

    public static Vector<ChessPiece> getPieces() {
        return pieces;
    }

    public static SoundEffect getSoundEffect() {
        return GameController.soundEffect;
    }

    public static controller.Event getEvent() {
        return GameController.event;
    }

    public static void setEvent() {
        GameController.event = new Event();
    }

    public static Setting getSetting() {
        return GameController.setting;
    }

    public static void setNewSetting() {
        GameController.setting = new Setting();
    }

    public static CountDown getClock_1() {
        return GameController.clock_1;
    }

    public static CountDown getClock_2() {
        return GameController.clock_2;
    }

    public static int getTurn() {
        return turn;
    }

    public static void setTurn(int turn) {
        GameController.turn = turn;
    }

    public static ChessBoard getChessBoardPanel() {
        return GameController.chessBoard;
    }

    public static void setChessBoardPanel(ChessBoard chessBoard) {
        GameController.chessBoard = chessBoard;
    }

    public static int getMinute() {
        return GameController.minute;
    }

    public static void setMinute(int minute) {
        GameController.minute = minute;
    }

    public static int getSecond() {
        return second;
    }

    public static void setSecond(int second) {
        GameController.second = second;
    }

    public static RoundedImageLabel getAvatarPlayer() {
        return GameController.avatarPlayer;
    }

    public static RoundedImageLabel getAvatarBot() {
        return GameController.avatarBot;
    }

    public static void setNew(JButton start) {
        GameController.getAvatarBot().setVisible(true);
        GameController.getAvatarPlayer().setVisible(true);
        GameController.setCheck();
        GameController.setTurn(new Random().nextInt(4));
        GameController.getClock_1().setTime(GameController.minute, GameController.second);
        GameController.getClock_2().setTime(GameController.minute, GameController.second);
        for (ChessPiece _piece_ : GameController.getPieces()) {
            GameController.chessBoard.add(_piece_);
        }
        GameController.setNewSetting();
        GameController.getChessBoardPanel().setNew(start);
        GameController.setEvent();
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