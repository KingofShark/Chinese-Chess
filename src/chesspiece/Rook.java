package chesspiece;

import main.Check;

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
        locateX = (POSITION == Piece.RIGHT) ? 8 : 0;
        locateY = (_COLOR_ == BLACK) ? 0 : 9;
        int x = (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int y = (locateY + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        setBounds(x, y, SIZE_PIECE, SIZE_PIECE);
        setImage();
    }

    @Override
    public Vector<Integer> choosePiecePosition(Check check, Vector<JButton> buttonH, Vector<JButton> buttonV, Vector<ChessPiece> pieces) {
        int j = 0;
        Vector<Integer> choose = new Vector<>();
        for (int i = locateX + 1; i < 9; i++, j++) {
            if (!check.checkLocal(i, locateY) && pieces.elementAt(check.getPiece(i, locateY))._COLOR_ != this._COLOR_) {
                choose.add(pieces.elementAt(check.getPiece(i, locateY)).TYPE);
                pieces.elementAt(check.getPiece(i, locateY)).changeImage();
            }
            if (!check.checkLocal(i, locateY))
                break;
            buttonH.elementAt(j).setLocation((i + 1) * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(j).setVisible(true);

        }
        for (int i = locateX - 1; i >= 0; i--, j++) {
            if (!check.checkLocal(i, locateY) && pieces.elementAt(check.getPiece(i, locateY))._COLOR_ != this._COLOR_) {
                choose.add(pieces.elementAt(check.getPiece(i, locateY)).TYPE);
                pieces.elementAt(check.getPiece(i, locateY)).changeImage();
            }
            if (!check.checkLocal(i, locateY))
                break;
            buttonH.elementAt(j).setLocation((i + 1) * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(j).setVisible(true);
        }
        j = 0;
        for (int i = locateY + 1; i < 10; i++, j++) {
            if (!check.checkLocal(locateX, i) && pieces.elementAt(check.getPiece(locateX, i))._COLOR_ != this._COLOR_) {
                choose.add(pieces.elementAt(check.getPiece(locateX, i)).TYPE);
                pieces.elementAt(check.getPiece(locateX, i)).changeImage();
            }
            if (!check.checkLocal(locateX, i))
                break;
            buttonV.elementAt(j).setLocation((locateX + 1) * CELL_SIZE - RADIUS, (i + 1) * CELL_SIZE - RADIUS);
            buttonV.elementAt(j).setVisible(true);
        }
        for (int i = locateY - 1; i >= 0; i--, j++) {
            if (!check.checkLocal(locateX, i) && pieces.elementAt(check.getPiece(locateX, i))._COLOR_ != this._COLOR_) {
                choose.add(pieces.elementAt(check.getPiece(locateX, i)).TYPE);
                pieces.elementAt(check.getPiece(locateX, i)).changeImage();
            }
            if (!check.checkLocal(locateX, i))
                break;
            buttonV.elementAt(j).setLocation((locateX + 1) * CELL_SIZE - RADIUS, (i + 1) * CELL_SIZE - RADIUS);
            buttonV.elementAt(j).setVisible(true);
        }
        return choose;
    }

    @Override
    public void updateLocate(String temp, JButton button) {
        if (temp.equals("h"))
            locateX = Math.abs(get_X() + SIZE_PIECE - (button.getX() + RADIUS)) / CELL_SIZE;
        else locateY = Math.abs(get_Y() + SIZE_PIECE - (button.getY() + RADIUS)) / CELL_SIZE;
        System.out.println("x: " + locateX + ", y: " + locateY);
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }
}
