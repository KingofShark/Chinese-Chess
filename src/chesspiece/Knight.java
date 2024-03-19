package chesspiece;

import main.Check;

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
        locateX = (POSITION == Piece.RIGHT) ? 7 : 1;
        locateY = (_COLOR_ == BLACK) ? 0 : 9;
        int x = (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int y = (locateY + 1)* Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        setBounds(x, y, SIZE_PIECE, SIZE_PIECE);
        setImage();
    }

    @Override
    public Vector<Integer> choosePiecePosition(Check check, Vector<JButton> buttonH, Vector<JButton> buttonV, Vector<ChessPiece> pieces) {
        Vector<Integer> choose = new Vector<>();
        if (locateY > 0 && locateX - 1 > 0 && check.checkLocal(locateX - 1, locateY) && check.checkLocal(locateX - 2, locateY - 1)) {
            buttonH.firstElement().setLocation((locateX - 1) * CELL_SIZE - RADIUS, (locateY) * CELL_SIZE - RADIUS);
            buttonH.firstElement().setVisible(true);//tl21
        }
        if (locateY > 0 && locateX - 1 > 0 && check.checkLocal(locateX - 1, locateY) && !check.checkLocal(locateX - 2, locateY - 1) && pieces.elementAt(check.getPiece(locateX - 2, locateY - 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 2, locateY - 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 2, locateY - 1)).changeImage();
        }
        if (locateY > 0 && locateX + 1 < 8 && check.checkLocal(locateX + 1, locateY) && check.checkLocal(locateX + 2, locateY - 1)) {
            buttonH.elementAt(1).setLocation((locateX + 3) * CELL_SIZE - RADIUS, (locateY) * CELL_SIZE - RADIUS);
            buttonH.elementAt(1).setVisible(true);//tr21
        }
        if (locateY > 0 && locateX + 1 < 8 && check.checkLocal(locateX + 1, locateY) && !check.checkLocal(locateX + 2, locateY - 1) && pieces.elementAt(check.getPiece(locateX + 2, locateY - 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 2, locateY - 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 2, locateY - 1)).changeImage();
        }
        if (locateY - 1 > 0 && locateX > 0 && check.checkLocal(locateX, locateY - 1) && check.checkLocal(locateX - 1, locateY - 2)) {
            buttonH.elementAt(2).setLocation((locateX) * CELL_SIZE - RADIUS, (locateY - 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(2).setVisible(true);//tl12
        }
        if (locateY - 1 > 0 && locateX > 0 && check.checkLocal(locateX, locateY - 1) && !check.checkLocal(locateX - 1, locateY - 2) && pieces.elementAt(check.getPiece(locateX - 1, locateY - 2))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 1, locateY - 2)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 1, locateY - 2)).changeImage();
        }
        if (locateY - 1 > 0 && locateX < 8 && check.checkLocal(locateX, locateY - 1) && check.checkLocal(locateX + 1, locateY - 2)) {
            buttonH.elementAt(3).setLocation((locateX + 2) * CELL_SIZE - RADIUS, (locateY - 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(3).setVisible(true);//tr12
        }
        if (locateY - 1 > 0 && locateX < 8 && check.checkLocal(locateX, locateY - 1) && !check.checkLocal(locateX + 1, locateY - 2) && pieces.elementAt(check.getPiece(locateX + 1, locateY - 2))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 1, locateY - 2)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 1, locateY - 2)).changeImage();
        }
        if (locateY < 8 && locateX - 1 > 0 && check.checkLocal(locateX - 1, locateY) && check.checkLocal(locateX - 2, locateY + 1)) {
            buttonH.elementAt(4).setLocation((locateX - 1) * CELL_SIZE - RADIUS, (locateY + 2) * CELL_SIZE - RADIUS);
            buttonH.elementAt(4).setVisible(true);//dl21
        }
        if (locateY < 8 && locateX - 1 > 0 && check.checkLocal(locateX - 1, locateY) && !check.checkLocal(locateX - 2, locateY + 1) && pieces.elementAt(check.getPiece(locateX - 2, locateY + 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 2, locateY + 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 2, locateY + 1)).changeImage();
        }
        if (locateY < 8 && locateX + 1 < 8 && check.checkLocal(locateX + 1, locateY) && check.checkLocal(locateX + 2, locateY + 1)) {
            buttonH.elementAt(5).setLocation((locateX + 3) * CELL_SIZE - RADIUS, (locateY + 2) * CELL_SIZE - RADIUS);
            buttonH.elementAt(5).setVisible(true);//dr21
        }
        if (locateY < 8 && locateX + 1 < 8 && check.checkLocal(locateX + 1, locateY) && !check.checkLocal(locateX + 2, locateY + 1) && pieces.elementAt(check.getPiece(locateX + 2, locateY + 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 2, locateY + 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 2, locateY + 1)).changeImage();
        }
        if (locateY < 8 && locateX > 0 && check.checkLocal(locateX, locateY + 1) && check.checkLocal(locateX - 1, locateY + 2)) {
            buttonH.elementAt(6).setLocation((locateX) * CELL_SIZE - RADIUS, (locateY + 3) * CELL_SIZE - RADIUS);
            buttonH.elementAt(6).setVisible(true);//dl12
        }
        if (locateY < 8 && locateX > 0 && check.checkLocal(locateX, locateY + 1) && !check.checkLocal(locateX - 1, locateY + 2) && pieces.elementAt(check.getPiece(locateX - 1, locateY + 2))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 1, locateY + 2)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 1, locateY + 2)).changeImage();
        }
        if (locateY < 8 && locateX < 8 && check.checkLocal(locateX, locateY + 1) && check.checkLocal(locateX + 1, locateY + 2)) {
            buttonH.elementAt(7).setLocation((locateX + 2) * CELL_SIZE - RADIUS, (locateY + 3) * CELL_SIZE - RADIUS);
            buttonH.elementAt(7).setVisible(true);//dr12
        }
        if (locateY < 8 && locateX < 8 && check.checkLocal(locateX, locateY + 1) && !check.checkLocal(locateX + 1, locateY + 2) && pieces.elementAt(check.getPiece(locateX + 1, locateY + 2))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 1, locateY + 2)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 1, locateY + 2)).changeImage();
        }
        return choose;
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
