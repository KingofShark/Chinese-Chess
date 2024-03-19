package chesspiece;

import main.Check;

import javax.swing.*;
import java.util.Vector;

public class General extends ChessPiece {

    public General(int color) {
        super(color);
        _name_ = "General";
        locateX = 4;
        locateY = (_COLOR_ == BLACK) ? 0 : 9;
        int x = (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int y = (locateY + 1)* Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        TYPE = (_COLOR_ == BLACK) ? BLACK_GENERAL : RED_GENERAL;
        setImage();
        setBounds(x, y, SIZE_PIECE, SIZE_PIECE);

    }

    @Override
    public Vector<Integer> choosePiecePosition(Check check, JButton top, JButton right, JButton bottom, JButton left, Vector<ChessPiece> pieces) {
        int _top = (_COLOR_ == BLACK) ? 0 : 7;
        int _down = (_COLOR_ == BLACK) ? 2 : 9;
        Vector<Integer> choose = new Vector<>();
        System.out.println(locateX + " , " + locateY);
        if (locateY < _down && check.checkLocal(locateX, locateY + 1)) {
            bottom.setLocation((locateX + 1) * CELL_SIZE - RADIUS, (locateY + 2) * CELL_SIZE - RADIUS);
            bottom.setVisible(true);
        }
        if (locateY < _down && !check.checkLocal(locateX, locateY + 1) && pieces.elementAt(check.getPiece(locateX, locateY + 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX, locateY + 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX, locateY + 1)).changeImage();
        }
        if (locateY > _top && check.checkLocal(locateX, locateY - 1)) {
            top.setLocation((locateX + 1) * CELL_SIZE - RADIUS, (locateY) * CELL_SIZE - RADIUS);
            top.setVisible(true);
        }
        if (locateY > _top && !check.checkLocal(locateX, locateY - 1) && pieces.elementAt(check.getPiece(locateX, locateY - 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX, locateY - 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX, locateY - 1)).changeImage();
        }
        if (locateX > 3 && check.checkLocal(locateX - 1, locateY)) {
            left.setLocation(locateX * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            left.setVisible(true);
        }
        if (locateX > 3 && !check.checkLocal(locateX - 1, locateY) && pieces.elementAt(check.getPiece(locateX - 1, locateY))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 1, locateY)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 1, locateY)).changeImage();
        }
        if (locateX < 5 && check.checkLocal(locateX + 1, locateY)) {
            right.setLocation((locateX + 2) * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            right.setVisible(true);
        }
        if (locateX < 5 && !check.checkLocal(locateX + 1, locateY) && pieces.elementAt(check.getPiece(locateX + 1, locateY))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 1, locateY)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 1, locateY)).changeImage();
        }
        return choose;
    }

    @Override
    public void updateLocate(String temp) {
        switch (temp) {
            case "top" -> locateY--;
            case "right" -> locateX++;
            case "bottom" -> locateY++;
            default -> locateX--;
        }
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }
}
