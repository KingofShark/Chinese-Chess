package model;

import constant.Piece;
import controller.StaticPieces;

import javax.swing.*;
import java.util.Vector;

public class Knight extends ChessPiece {
    public Knight(int color, int position) {
        super(color, position);
        _name_ = "Knight";
        TYPE = RED_LEFT_KNIGHT;
        if (_COLOR_ == BLACK)
            TYPE = (POSITION == Piece.RIGHT) ? BLACK_RIGHT_KNIGHT : BLACK_LEFT_KNIGHT;
        else if (POSITION == Piece.RIGHT)
            TYPE = RED_RIGHT_KNIGHT;
        this.resetDefauft();
        this.setImage();
    }

    @Override
    public void resetDefauft() {
        super.resetDefauft();
        locateX = (POSITION == Piece.RIGHT) ? 7 : 1;
        locateY = (_COLOR_ == BLACK) ? 0 : 9;
        int x = PADDING + (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int y = (locateY + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        setBounds(x, y, SIZE_PIECE, SIZE_PIECE);
    }

    @Override
    public Vector<Integer> choosePiecePosition(Vector<JButton> buttonH, Vector<JButton> buttonV) {
        Vector<Integer> choose = new Vector<>();
        Check check = StaticPieces.getCheck();
        Vector<ChessPiece> pieces = StaticPieces.getPieces();
        if (locateY > 0 && locateX - 1 > 0 && check.isEmpty(locateX - 1, locateY) && check.isEmpty(locateX - 2, locateY - 1)) {
            buttonH.firstElement().setLocation(PADDING + (locateX - 1) * CELL_SIZE - RADIUS, (locateY) * CELL_SIZE - RADIUS);
            buttonH.firstElement().setVisible(true);//tl21
        }
        if (locateY > 0 && locateX - 1 > 0 && check.isEmpty(locateX - 1, locateY) && !check.isEmpty(locateX - 2, locateY - 1) && pieces.elementAt(check.getPiece(locateX - 2, locateY - 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 2, locateY - 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 2, locateY - 1)).changeImage();
        }
        if (locateY > 0 && locateX + 1 < 8 && check.isEmpty(locateX + 1, locateY) && check.isEmpty(locateX + 2, locateY - 1)) {
            buttonH.elementAt(1).setLocation(PADDING + (locateX + 3) * CELL_SIZE - RADIUS, (locateY) * CELL_SIZE - RADIUS);
            buttonH.elementAt(1).setVisible(true);//tr21
        }
        if (locateY > 0 && locateX + 1 < 8 && check.isEmpty(locateX + 1, locateY) && !check.isEmpty(locateX + 2, locateY - 1) && pieces.elementAt(check.getPiece(locateX + 2, locateY - 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 2, locateY - 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 2, locateY - 1)).changeImage();
        }
        if (locateY - 1 > 0 && locateX > 0 && check.isEmpty(locateX, locateY - 1) && check.isEmpty(locateX - 1, locateY - 2)) {
            buttonH.elementAt(2).setLocation(PADDING + (locateX) * CELL_SIZE - RADIUS, (locateY - 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(2).setVisible(true);//tl12
        }
        if (locateY - 1 > 0 && locateX > 0 && check.isEmpty(locateX, locateY - 1) && !check.isEmpty(locateX - 1, locateY - 2) && pieces.elementAt(check.getPiece(locateX - 1, locateY - 2))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 1, locateY - 2)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 1, locateY - 2)).changeImage();
        }
        if (locateY - 1 > 0 && locateX < 8 && check.isEmpty(locateX, locateY - 1) && check.isEmpty(locateX + 1, locateY - 2)) {
            buttonH.elementAt(3).setLocation(PADDING + (locateX + 2) * CELL_SIZE - RADIUS, (locateY - 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(3).setVisible(true);//tr12
        }
        if (locateY - 1 > 0 && locateX < 8 && check.isEmpty(locateX, locateY - 1) && !check.isEmpty(locateX + 1, locateY - 2) && pieces.elementAt(check.getPiece(locateX + 1, locateY - 2))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 1, locateY - 2)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 1, locateY - 2)).changeImage();
        }
        if (locateY < 8 && locateX - 1 > 0 && check.isEmpty(locateX - 1, locateY) && check.isEmpty(locateX - 2, locateY + 1)) {
            buttonH.elementAt(4).setLocation(PADDING + (locateX - 1) * CELL_SIZE - RADIUS, (locateY + 2) * CELL_SIZE - RADIUS);
            buttonH.elementAt(4).setVisible(true);//dl21
        }
        if (locateY < 8 && locateX - 1 > 0 && check.isEmpty(locateX - 1, locateY) && !check.isEmpty(locateX - 2, locateY + 1) && pieces.elementAt(check.getPiece(locateX - 2, locateY + 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 2, locateY + 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 2, locateY + 1)).changeImage();
        }
        if (locateY < 8 && locateX + 1 < 8 && check.isEmpty(locateX + 1, locateY) && check.isEmpty(locateX + 2, locateY + 1)) {
            buttonH.elementAt(5).setLocation(PADDING + (locateX + 3) * CELL_SIZE - RADIUS, (locateY + 2) * CELL_SIZE - RADIUS);
            buttonH.elementAt(5).setVisible(true);//dr21
        }
        if (locateY < 8 && locateX + 1 < 8 && check.isEmpty(locateX + 1, locateY) && !check.isEmpty(locateX + 2, locateY + 1) && pieces.elementAt(check.getPiece(locateX + 2, locateY + 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 2, locateY + 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 2, locateY + 1)).changeImage();
        }
        if (locateY < 8 && locateX > 0 && check.isEmpty(locateX, locateY + 1) && check.isEmpty(locateX - 1, locateY + 2)) {
            buttonH.elementAt(6).setLocation(PADDING + (locateX) * CELL_SIZE - RADIUS, (locateY + 3) * CELL_SIZE - RADIUS);
            buttonH.elementAt(6).setVisible(true);//dl12
        }
        if (locateY < 8 && locateX > 0 && check.isEmpty(locateX, locateY + 1) && !check.isEmpty(locateX - 1, locateY + 2) && pieces.elementAt(check.getPiece(locateX - 1, locateY + 2))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 1, locateY + 2)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 1, locateY + 2)).changeImage();
        }
        if (locateY < 8 && locateX < 8 && check.isEmpty(locateX, locateY + 1) && check.isEmpty(locateX + 1, locateY + 2)) {
            buttonH.elementAt(7).setLocation(PADDING + (locateX + 2) * CELL_SIZE - RADIUS, (locateY + 3) * CELL_SIZE - RADIUS);
            buttonH.elementAt(7).setVisible(true);//dr12
        }
        if (locateY < 8 && locateX < 8 && check.isEmpty(locateX, locateY + 1) && !check.isEmpty(locateX + 1, locateY + 2) && pieces.elementAt(check.getPiece(locateX + 1, locateY + 2))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 1, locateY + 2)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 1, locateY + 2)).changeImage();
        }
        return choose;
    }

    @Override
    public Boolean checkMate() {
        Check check = StaticPieces.getCheck();
        Vector<ChessPiece> pieces = StaticPieces.getPieces();
        if (locateY < 8 && locateX < 8 && (check.getPiece(locateX + 1, locateY + 2) == 0 || check.getPiece(locateX + 1, locateY + 2) == 1) && pieces.elementAt(check.getPiece(locateX + 1, locateY + 2))._COLOR_ != this._COLOR_)
            return true;
        if (locateY < 8 && locateX > 0 && (check.getPiece(locateX - 1, locateY + 2) == 0 || check.getPiece(locateX - 1, locateY + 2) == 1) && pieces.elementAt(check.getPiece(locateX - 1, locateY + 2))._COLOR_ != this._COLOR_)
            return true;
        if (locateY < 8 && locateX + 1 < 8 && (check.getPiece(locateX + 2, locateY + 1) == 0 || check.getPiece(locateX + 2, locateY + 1) == 1)  && pieces.elementAt(check.getPiece(locateX + 2, locateY + 1))._COLOR_ != this._COLOR_)
            return true;
        if (locateY < 8 && locateX - 1 > 0 && (check.getPiece(locateX - 2, locateY + 1) == 0 || check.getPiece(locateX - 2, locateY + 1) == 1)  && pieces.elementAt(check.getPiece(locateX - 2, locateY + 1))._COLOR_ != this._COLOR_)
            return true;
        if (locateY - 1 > 0 && locateX < 8 && (check.getPiece(locateX + 1, locateY - 2) == 0 || check.getPiece(locateX + 1, locateY - 2) == 1)  && pieces.elementAt(check.getPiece(locateX + 1, locateY - 2))._COLOR_ != this._COLOR_)
            return true;
        if (locateY - 1 > 0 && locateX > 0 && (check.getPiece(locateX - 1, locateY - 2) == 0 || check.getPiece(locateX - 1, locateY - 2) == 1) && pieces.elementAt(check.getPiece(locateX - 1, locateY - 2))._COLOR_ != this._COLOR_)
            return true;
        if (locateY > 0 && locateX + 1 < 8 && (check.getPiece(locateX + 2, locateY - 1) == 0 || check.getPiece(locateX + 2, locateY - 1) == 1)  && pieces.elementAt(check.getPiece(locateX + 2, locateY - 1))._COLOR_ != this._COLOR_)
            return true;
        return locateY > 0 && locateX - 1 > 0 && (check.getPiece(locateX - 2, locateY - 1) == 0 || check.getPiece(locateX - 2, locateY - 1) == 1) && pieces.elementAt(check.getPiece(locateX - 2, locateY - 1))._COLOR_ != this._COLOR_;
    }

    @Override
    public void updateLocate(int typeClick) {
        switch (typeClick) {
            case 0:
                locateX -= 2;
                locateY--;
                break;
            case 1:
                locateY--;
                locateX += 2;
                break;
            case 2:
                locateX--;
                locateY -= 2;
                break;
            case 3:
                locateX++;
                locateY -= 2;
                break;
            case 4:
                locateY++;
                locateX -= 2;
                break;
            case 5:
                locateY++;
                locateX += 2;
                break;
            case 6:
                locateY += 2;
                locateX--;
                break;
            case 7:
                locateX++;
                locateY += 2;
                break;
        }
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }
}
