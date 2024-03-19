package chesspiece;

import main.Check;

import javax.swing.*;
import java.util.Vector;

public class Advisor extends ChessPiece {
    public Advisor(int color, int position) {
        super(color, position);
        _name_ = "Advisor";
        locateX = (POSITION == Piece.RIGHT) ? 5 : 3;
        locateY = (_COLOR_ == BLACK) ? 0 : 9;
        int x = (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int y = (locateY + 1)* Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        TYPE = RED_LEFT_ADVISOR;
        if (_COLOR_ == BLACK)
            TYPE = (POSITION == Piece.RIGHT) ? BLACK_RIGHT_ADVISOR : BLACK_LEFT_ADVISOR;
        else if (POSITION == Piece.RIGHT)
            TYPE = RED_RIGHT_ADVISOR;
        setBounds(x,y, SIZE_PIECE, SIZE_PIECE);
        setImage();
    }

    @Override
    public Vector<Integer> choosePiecePosition(Check check, JButton top, JButton right, JButton bottom, JButton left, Vector<ChessPiece> pieces) {
        Vector<Integer> choose = new Vector<>();
        int _top = (_COLOR_ == BLACK) ? 0 : 7;
        int _down = (_COLOR_ == BLACK) ? 2 : 9;
        if (locateY < _down && locateX < 5 && check.checkLocal(locateX + 1, locateY + 1)) {
            right.setLocation((locateX + 2) * CELL_SIZE - RADIUS, (locateY + 2) * CELL_SIZE - RADIUS);
            right.setVisible(true);
        }
        if (locateY > _top && locateX < 5 && check.checkLocal(locateX + 1, locateY - 1)) {
            top.setLocation((locateX + 2) * CELL_SIZE - RADIUS, (locateY) * CELL_SIZE - RADIUS);
            top.setVisible(true);
        }
        if (locateX > 3 && locateY > _top && check.checkLocal(locateX - 1, locateY - 1)) {
            left.setLocation((locateX) * CELL_SIZE - RADIUS, (locateY) * CELL_SIZE - RADIUS);
            left.setVisible(true);
        }
        if (locateX > 3 && locateY < _down && check.checkLocal(locateX - 1, locateY + 1)) {
            bottom.setLocation((locateX) * CELL_SIZE - RADIUS, (locateY + 2) * CELL_SIZE - RADIUS);
            bottom.setVisible(true);
        }
        if (locateX != 5 && locateY != 2 && !check.checkLocal(locateX + 1, locateY + 1) && pieces.elementAt(check.getPiece(locateX + 1, locateY + 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 1, locateY + 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 1, locateY + 1)).changeImage();
        }
        if (locateX != 5 && locateY != 0 && !check.checkLocal(locateX + 1, locateY - 1) && pieces.elementAt(check.getPiece(locateX + 1, locateY - 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 1, locateY - 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 1, locateY - 1)).changeImage();
        }
        if (locateX != 3 && locateY != 0 && !check.checkLocal(locateX - 1, locateY - 1) && pieces.elementAt(check.getPiece(locateX - 1, locateY - 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 1, locateY - 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 1, locateY - 1)).changeImage();
        }
        if (locateX != 3 && locateY != 2 && !check.checkLocal(locateX - 1, locateY + 1) && pieces.elementAt(check.getPiece(locateX - 1, locateY + 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 1, locateY + 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 1, locateY + 1)).changeImage();
        }
        return choose;
    }

    @Override
    public void updateLocate_(String temp) {
        if (temp.equals("dr")) {
            locateX++;
            locateY++;
        }
        if (temp.equals("tr")) {
            locateX++;
            locateY--;
        }
        if (temp.equals("dl")) {
            locateX--;
            locateY++;
        }
        if (temp.equals("tl")) {
            locateX--;
            locateY--;
        }
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }
}
