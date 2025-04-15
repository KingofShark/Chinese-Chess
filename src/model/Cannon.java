package model;

import constant.Piece;
import controller.StaticPieces;

import javax.swing.*;
import java.util.Vector;

public class Cannon extends ChessPiece {
    public Cannon(int color, int position) {
        super(color, position);
        _name_ = "Canon";
        TYPE = RED_LEFT_CANNON;
        if (_COLOR_ == BLACK)
            TYPE = (POSITION == Piece.RIGHT) ? BLACK_RIGHT_CANNON : BLACK_LEFT_CANNON;
        else if (POSITION == Piece.RIGHT)
            TYPE = RED_RIGHT_CANNON;
        this.resetDefauft();
        this.setImage();
    }

    @Override
    public void resetDefauft() {
        super.resetDefauft();
        this.locateX = (POSITION == Piece.RIGHT) ? 7 : 1;
        this.locateY = (_COLOR_ == BLACK) ? 2 : 7;
        int x = PADDING + (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int y = (locateY + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        this.setBounds(x, y, SIZE_PIECE, SIZE_PIECE);
    }

    @Override
    public Vector<Integer> choosePiecePosition(Vector<JButton> buttonH, Vector<JButton> buttonV) {
        int j = 0, i;
        Check check = StaticPieces.getCheck();
        Vector<ChessPiece> pieces = StaticPieces.getPieces();
        Vector<Integer> choose = new Vector<>();
        for (i = locateX + 1; i < 9; i++, j++) {
            if (!check.isEmpty(i, locateY))
                break;
            buttonH.elementAt(j).setLocation(PADDING + (i + 1) * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(j).setVisible(true);
        }
        for (int k = i + 1; k < 9; k++) {
            if (!check.isEmpty(k, locateY)) {
                if (pieces.elementAt(check.getPiece(k, locateY))._COLOR_ != this._COLOR_) {
                    pieces.elementAt(check.getPiece(k, locateY)).changeImage();
                    choose.add(pieces.elementAt(check.getPiece(k, locateY)).TYPE);
                }
                break;
            }
        }

        for (i = locateX - 1; i >= 0; i--, j++) {
            if (!check.isEmpty(i, locateY))
                break;
            buttonH.elementAt(j).setLocation(PADDING + (i + 1) * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(j).setVisible(true);
        }
        for (int k = i - 1; k >= 0; k--) {
            if (!check.isEmpty(k, locateY)) {
                if (pieces.elementAt(check.getPiece(k, locateY))._COLOR_ != this._COLOR_) {
                    pieces.elementAt(check.getPiece(k, locateY)).changeImage();
                    choose.add(pieces.elementAt(check.getPiece(k, locateY)).TYPE);
                }
                break;
            }
        }

        j = 0;
        for (i = locateY + 1; i < 10; i++, j++) {
            if (!check.isEmpty(locateX, i))
                break;
            buttonV.elementAt(j).setLocation(PADDING + (locateX + 1) * CELL_SIZE - RADIUS, (i + 1) * CELL_SIZE - RADIUS);
            buttonV.elementAt(j).setVisible(true);
        }
        for (int k = i + 1; k < 10; k++) {
            if (!check.isEmpty(locateX, k)) {
                if (pieces.elementAt(check.getPiece(locateX, k))._COLOR_ != this._COLOR_) {
                    pieces.elementAt(check.getPiece(locateX, k)).changeImage();
                    choose.add(pieces.elementAt(check.getPiece(locateX, k)).TYPE);
                }
                break;
            }
        }

        for (i = locateY - 1; i >= 0; i--, j++) {
            if (!check.isEmpty(locateX, i))
                break;
            buttonV.elementAt(j).setLocation(PADDING + (locateX + 1) * CELL_SIZE - RADIUS, (i + 1) * CELL_SIZE - RADIUS);
            buttonV.elementAt(j).setVisible(true);
        }
        for (int k = i - 1; k >= 0; k--) {
            if (!check.isEmpty(locateX, k)) {
                if (pieces.elementAt(check.getPiece(locateX, k))._COLOR_ != this._COLOR_) {
                    pieces.elementAt(check.getPiece(locateX, k)).changeImage();
                    choose.add(pieces.elementAt(check.getPiece(locateX, k)).TYPE);
                }
                break;
            }
        }
        return choose;
    }

    @Override
    public Boolean checkMate() {
        int j = 0, i;
        Check check = StaticPieces.getCheck();
        Vector<ChessPiece> pieces = StaticPieces.getPieces();
        for (i = locateX + 1; i < 9; i++, j++)
            if (!check.isEmpty(i, locateY))
                break;
        for (int k = i + 1; k < 9; k++) {
            if (!check.isEmpty(k, locateY)) {
                if (pieces.elementAt(check.getPiece(k, locateY))._COLOR_ != this._COLOR_ && (check.getPiece(k, locateY) == 0 || check.getPiece(k, locateY) == 1))
                    return true;
                break;
            }
        }

        for (i = locateX - 1; i >= 0; i--, j++)
            if (!check.isEmpty(i, locateY))
                break;
        for (int k = i - 1; k >= 0; k--) {
            if (!check.isEmpty(k, locateY)) {
                if (pieces.elementAt(check.getPiece(k, locateY))._COLOR_ != this._COLOR_ && (check.getPiece(k, locateY) == 0 || check.getPiece(k, locateY) == 1))
                    return true;
                break;
            }
        }

        j = 0;
        for (i = locateY + 1; i < 10; i++, j++)
            if (!check.isEmpty(locateX, i))
                break;
        for (int k = i + 1; k < 10; k++) {
            if (!check.isEmpty(locateX, k)) {
                if (pieces.elementAt(check.getPiece(locateX, k))._COLOR_ != this._COLOR_ && (check.getPiece(locateX, k) == 0 || check.getPiece(locateX, k) == 1))
                    return true;
                break;
            }
        }
        for (i = locateY - 1; i >= 0; i--, j++)
            if (!check.isEmpty(locateX, i))
                break;
        for (int k = i - 1; k >= 0; k--) {
            if (!check.isEmpty(locateX, k)) {
                if (pieces.elementAt(check.getPiece(locateX, k))._COLOR_ != this._COLOR_ && (check.getPiece(locateX, k) == 0 || check.getPiece(locateX, k) == 1))
                    return true;
                break;
            }
        }
        return false;
    }

    @Override
    public void updateLocate(JButton button) {
        locateX = (button.getX() - PADDING + RADIUS) / CELL_SIZE - 1;
        locateY = (button.getY() + RADIUS) / CELL_SIZE - 1;
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }
}
