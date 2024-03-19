package chesspiece;

import main.Check;

import javax.swing.*;
import java.util.Vector;

public class Cannon extends ChessPiece {
    public Cannon(int color, int position) {
        super(color, position);
        locateX = (POSITION == Piece.RIGHT) ? 7 : 1;
        locateY = (_COLOR_ == BLACK) ? 2 : 7;
        _name_ = "Canon";
        int x = (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int y = (locateY + 1)* Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        TYPE = RED_LEFT_CANNON;
        if (_COLOR_ == BLACK)
            TYPE = (POSITION == Piece.RIGHT) ? BLACK_RIGHT_CANNON : BLACK_LEFT_CANNON;
        else if (POSITION == Piece.RIGHT)
            TYPE = RED_RIGHT_CANNON;
        setBounds(x, y, SIZE_PIECE, SIZE_PIECE);
        setImage();
    }

    @Override
    public Vector<Integer> choosePiecePosition(Check check, Vector<JButton> buttonH, Vector<JButton> buttonV, Vector<ChessPiece> pieces) {
        int j = 0, i;
        Vector<Integer> choose = new Vector<>();
        for (i = locateX + 1; i < 9 ; i++, j++) {
            if (!check.checkLocal(i, locateY))
                break;
            buttonH.elementAt(j).setLocation((i + 1) * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(j).setVisible(true);
        }
        for (int k = i + 1; k < 9; k++) {
            if(!check.checkLocal(k, locateY)) {
                if ( pieces.elementAt(check.getPiece(k, locateY))._COLOR_ != this._COLOR_) {
                    pieces.elementAt(check.getPiece(k, locateY)).changeImage();
                    choose.add(pieces.elementAt(check.getPiece(k, locateY)).TYPE);
                }
                break;
            }
        }

        for (i = locateX - 1; i >= 0; i--, j++) {
            if(!check.checkLocal(i, locateY))
                break;
            buttonH.elementAt(j).setLocation((i + 1) * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            buttonH.elementAt(j).setVisible(true);
        }
        for (int k = i - 1; k >= 0; k--) {
            if(!check.checkLocal(k, locateY)) {
                if ( pieces.elementAt(check.getPiece(k, locateY))._COLOR_ != this._COLOR_) {
                    pieces.elementAt(check.getPiece(k, locateY)).changeImage();
                    choose.add(pieces.elementAt(check.getPiece(k, locateY)).TYPE);
                }
                break;
            }
        }

        j = 0;
        for (i = locateY + 1; i < 10; i++, j++) {
            if (!check.checkLocal(locateX, i))
                break;
            buttonV.elementAt(j).setLocation((locateX + 1) * CELL_SIZE - RADIUS, (i + 1) * CELL_SIZE - RADIUS);
            buttonV.elementAt(j).setVisible(true);
        }
        for (int k = i + 1; k < 10; k++) {
            if(!check.checkLocal(locateX, k)) {
                if ( pieces.elementAt(check.getPiece(locateX, k))._COLOR_ != this._COLOR_) {
                    pieces.elementAt(check.getPiece(locateX, k)).changeImage();
                    choose.add(pieces.elementAt(check.getPiece(locateX, k)).TYPE);
                }
                break;
            }
        }

        for (i = locateY - 1; i >= 0; i--, j++) {
            if (!check.checkLocal(locateX, i))
                break;
            buttonV.elementAt(j).setLocation((locateX + 1) * CELL_SIZE - RADIUS, (i + 1) * CELL_SIZE - RADIUS);
            buttonV.elementAt(j).setVisible(true);
        }
        for (int k = i - 1; k >= 0; k--) {
            if(!check.checkLocal(locateX, k)) {
                if ( pieces.elementAt(check.getPiece(locateX, k))._COLOR_ != this._COLOR_) {
                    pieces.elementAt(check.getPiece(locateX, k)).changeImage();
                    choose.add(pieces.elementAt(check.getPiece(locateX, k)).TYPE);
                }
                break;
            }
        }
        for (Integer integer : choose)
            System.out.println(pieces.elementAt(integer).getName());
        return choose;
    }

    @Override
    public void updateLocate(String temp, JButton button) {
        if (temp.equals("h"))
            locateX = Math.abs(get_X() + SIZE_PIECE - (button.getX() + RADIUS)) / CELL_SIZE;
        else locateY = Math.abs(get_Y() + SIZE_PIECE - (button.getY() + RADIUS)) / CELL_SIZE;
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }
}
