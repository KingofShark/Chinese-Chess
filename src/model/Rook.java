package model;

import constant.Piece;
import controller.StaticPieces;

import javax.swing.*;
import java.util.Vector;

public class Rook extends ChessPiece {
    public Rook(int color, int position) {
        super(color, position);
        _name_ = "Rook";
        TYPE = RED_LEFT_ROOK;
        if (_COLOR_ == BLACK)
            TYPE = (POSITION == Piece.RIGHT) ? BLACK_RIGHT_ROOK : BLACK_LEFT_ROOK;
        else if (POSITION == Piece.RIGHT)
            TYPE = RED_RIGHT_ROOK;
        this.resetDefauft();
        this.setImage();
    }

    @Override
    public void resetDefauft() {
        super.resetDefauft();
        locateX = (POSITION == Piece.RIGHT) ? 8 : 0;
        locateY = (_COLOR_ == BLACK) ? 0 : 9;
        int x = (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int y = (locateY + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        setBounds(x, y, SIZE_PIECE, SIZE_PIECE);
    }

    @Override
    public Vector<Integer> choosePiecePosition(Vector<JButton> buttonH, Vector<JButton> buttonV) {
        Check check = StaticPieces.getCheck();
        Vector<ChessPiece> pieces = StaticPieces.getPieces();
        int j = 0;
        System.out.println("X: " + locateX + " Y: " + locateY);
        Vector<Integer> choose = new Vector<>();
        for (int i = locateX + 1; i < 9; i++, j++) {
            if (!check.isEmpty(i, locateY) && pieces.elementAt(check.getPiece(i, locateY))._COLOR_ != this._COLOR_) {
                choose.add(pieces.elementAt(check.getPiece(i, locateY)).TYPE);
                pieces.elementAt(check.getPiece(i, locateY)).changeImage();
            }
            if (!check.isEmpty(i, locateY))
                break;
            buttonH.elementAt(j).setLocation((i + 1) * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(j).setVisible(true);

        }
        for (int i = locateX - 1; i >= 0; i--, j++) {
            if (!check.isEmpty(i, locateY) && pieces.elementAt(check.getPiece(i, locateY))._COLOR_ != this._COLOR_) {
                choose.add(pieces.elementAt(check.getPiece(i, locateY)).TYPE);
                pieces.elementAt(check.getPiece(i, locateY)).changeImage();
            }
            if (!check.isEmpty(i, locateY))
                break;
            buttonH.elementAt(j).setLocation((i + 1) * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(j).setVisible(true);
        }
        j = 0;
        for (int i = locateY + 1; i < 10; i++, j++) {
            if (!check.isEmpty(locateX, i) && pieces.elementAt(check.getPiece(locateX, i))._COLOR_ != this._COLOR_) {
                choose.add(pieces.elementAt(check.getPiece(locateX, i)).TYPE);
                pieces.elementAt(check.getPiece(locateX, i)).changeImage();
            }
            if (!check.isEmpty(locateX, i))
                break;
            buttonV.elementAt(j).setLocation((locateX + 1) * CELL_SIZE - RADIUS, (i + 1) * CELL_SIZE - RADIUS);
            buttonV.elementAt(j).setVisible(true);
        }
        for (int i = locateY - 1; i >= 0; i--, j++) {
            if (!check.isEmpty(locateX, i) && pieces.elementAt(check.getPiece(locateX, i))._COLOR_ != this._COLOR_) {
                choose.add(pieces.elementAt(check.getPiece(locateX, i)).TYPE);
                pieces.elementAt(check.getPiece(locateX, i)).changeImage();
            }
            if (!check.isEmpty(locateX, i))
                break;
            buttonV.elementAt(j).setLocation((locateX + 1) * CELL_SIZE - RADIUS, (i + 1) * CELL_SIZE - RADIUS);
            buttonV.elementAt(j).setVisible(true);
        }
        return choose;
    }

    @Override
    public Boolean checkMate() {
        Check check = StaticPieces.getCheck();
        Vector<ChessPiece> pieces = StaticPieces.getPieces();
        for (int i = locateX + 1; i < 9; i++)
            if ((check.getPiece(i, locateY) == 0 || check.getPiece(i, locateY) == 1) && pieces.elementAt(check.getPiece(i, locateY))._COLOR_ != this._COLOR_) {
                return true;
            }else if (!check.isEmpty(i, locateY))
                break;
        for (int i = locateX - 1; i >= 0; i--)
            if ((check.getPiece(i, locateY) == 0 || check.getPiece(i, locateY) == 1) && pieces.elementAt(check.getPiece(i, locateY))._COLOR_ != this._COLOR_)
                return true;
            else if (!check.isEmpty(i, locateY))
                break;
        for (int i = locateY + 1; i < 10; i++)
            if ((check.getPiece(locateX, i) == 0 || check.getPiece(locateX, i) == 1) && pieces.elementAt(check.getPiece(locateX, i))._COLOR_ != this._COLOR_)
                return true;
            else if (!check.isEmpty(locateX, i))
                break;
        for (int i = locateY - 1; i >= 0; i--)
            if ((check.getPiece(locateX, i) == 0 || check.getPiece(locateX, i) == 1) && pieces.elementAt(check.getPiece(locateX, i))._COLOR_ != this._COLOR_)
                return true;
            else if (!check.isEmpty(locateX, i))
                break;
        return false;
    }

    @Override
    public void updateLocate(JButton button) {
        locateX = (button.getX() + RADIUS) / CELL_SIZE - 1;
        locateY = (button.getY() + RADIUS) / CELL_SIZE - 1;
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }
}
