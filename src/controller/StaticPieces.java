package controller;

import model.*;
import view.ChessBoard;
import view.CountDown;
import image.NewImage;
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
    private static int player;
    private static int turn, minute, second, first;
    private static final JLabel notice_1 = new JLabel();
    private static final JLabel notice_2 = new JLabel();
    private static final CountDown clock_1 = new CountDown("Đen");
    private static final CountDown clock_2 = new CountDown("Đỏ");
    private static final CloseButton closeButton = new CloseButton();
    private static final ChessBoard chessBoard = new ChessBoard();
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

    public static int getPlayer() {
        return player;
    }

    public static void setPlayer(int player) {
        StaticPieces.player = player;
    }

    public static void setCheck() {
        StaticPieces.check = new Check();
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

    public static void setFirst(int first) {
        StaticPieces.first = first;
    }

    public static int getFirst() {
        return first;
    }

    public static ChessBoard getChessBoardPanel() {
        return StaticPieces.chessBoard;
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

    public static JLabel getNotice_1() {
        return StaticPieces.notice_1;
    }

    public static JLabel getNotice_2() {
        return StaticPieces.notice_2;
    }

    public static void setNew(JButton start) {
        StaticPieces.changeImage("ss", 1);
        StaticPieces.changeImage("ss", 2);
        StaticPieces.getNotice_2().setVisible(true);
        StaticPieces.getNotice_1().setVisible(true);
        StaticPieces.setCheck();
        StaticPieces.setFirst(2);
        StaticPieces.setTurn(new Random().nextInt(101));
        StaticPieces.setTurn(0);
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
        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/effect/" + status + ".png");
        image = new NewImage().resizeImage(image, 200, 106);
        if (type == 1) {
            StaticPieces.getNotice_1().setIcon(image);
            StaticPieces.getNotice_1().setVisible(true);
            StaticPieces.getNotice_2().setVisible(false);
        } else if (type == 2) {
            StaticPieces.getNotice_2().setIcon(image);
            StaticPieces.getNotice_2().setVisible(true);
            StaticPieces.getNotice_1().setVisible(false);
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
        public CustomSliderUI(JSlider b) {
            super(b);
        }

        @Override
        protected void installDefaults(JSlider slider) {
            super.installDefaults(slider);
            slider.setBackground(Color.WHITE);
            slider.setForeground(Color.BLUE);
        }
    }
}