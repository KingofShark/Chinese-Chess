package chesspiece;

import game.Check;
import game.ChessBoard;
import game.CountDown;
import image.NewImage;
import menu.CloseButton;
import menu.Setting;
import sound.SoundEffect;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

import static chesspiece.Piece.*;

public class StaticPieces {
    private static int turn, minute, second;
    private static int first;
    private static final JLabel notice_1 = new JLabel();
    private static final JLabel notice_2 = new JLabel();
    private static final CountDown clock_1 = new CountDown("Đen");
    private static final CountDown clock_2 = new CountDown("Đỏ");
    private static final CloseButton closeButton = new CloseButton();
    private static final ChessBoard chessBoard = new ChessBoard();
    private static Check check = new Check();
    private static final SoundEffect soundEffect = new SoundEffect();
    private static Setting setting = new Setting();
    private static Vector<ChessPiece> pieces = new Vector<>();
    public static CloseButton getCloseButton() {return StaticPieces.closeButton;}
    public static Check getCheck() {
        return StaticPieces.check;
    }
    public static void setCheck() {StaticPieces.check = new Check();}
    public static void setup() {
        Vector<ChessPiece> pieces = new Vector<>();
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
        StaticPieces.pieces = pieces;
        for(ChessPiece piece : StaticPieces.pieces)
            StaticPieces.chessBoard.add(piece);
    }
    public static Vector<ChessPiece> getPieces() {
        return pieces;
    }
    public static SoundEffect getSoundEffect() {
        return soundEffect;
    }
    public static Setting getSetting() {
        return setting;
    }
    public static void setNewSetting() {StaticPieces.setting = new Setting();}
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
    public static int getMinute() {return StaticPieces.minute;}
    public static void setMinute(int minute){StaticPieces.minute = minute;}
    public static int getSecond() {
        return second;
    }
    public static void setSecond(int second) {
        StaticPieces.second = second;
    }
    public static JLabel getNotice_1(){return StaticPieces.notice_1;}
    public static JLabel getNotice_2(){return StaticPieces.notice_2;}
    public static void setNew(JButton start){
        StaticPieces.setCheck();
        StaticPieces.setFirst(2);
        StaticPieces.setTurn(new Random().nextInt(101));
        StaticPieces.getClock_1().setTime(StaticPieces.minute, StaticPieces.second);
        StaticPieces.getClock_2().setTime(StaticPieces.minute, StaticPieces.second);
        for (ChessPiece _piece_ : StaticPieces.getPieces()) {
            StaticPieces.chessBoard.add(_piece_);
        }
        StaticPieces.setNewSetting();
        StaticPieces.getChessBoardPanel().setNew(start);
    }
    public static void changeImage(String status, int type){
        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + "/src/image/effect/" + status +  ".png");
        image = new NewImage().resizeImage(image, 200, 106);
        if(type == 1) {
            StaticPieces.getNotice_1().setIcon(image);
            StaticPieces.getNotice_1().setVisible(true);
            StaticPieces.getNotice_2().setVisible(false);
        }else if(type == 2){
            StaticPieces.getNotice_2().setIcon(image);
            StaticPieces.getNotice_2().setVisible(true);
            StaticPieces.getNotice_1().setVisible(false);
        }else{
            StaticPieces.getNotice_2().setVisible(false);
            StaticPieces.getNotice_1().setVisible(false);
        }
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