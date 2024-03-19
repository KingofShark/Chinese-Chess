package chesspiece;

import main.Check;

import javax.swing.*;
import java.util.Vector;

public class Pawn extends ChessPiece {
    public Pawn(int color, int position) {
        super(color, position);
        _name_ = "Pawn";
        if (_COLOR_ == BLACK) {
            locateY = 3;
            switch (POSITION) {
                case 0:
                    locateX = 0;
                    TYPE = BLACK_PAWN_0;
                    break;
                case 1:
                    TYPE = BLACK_PAWN_1;
                    locateX = 2;
                    break;
                case 2:
                    TYPE = BLACK_PAWN_2;
                    locateX = 4;
                    break;
                case 3:
                    TYPE = BLACK_PAWN_3;
                    locateX = 6;
                    break;
                default:
                    TYPE = BLACK_PAWN_4;
                    locateX = 8;
            }
        } else {
            locateY = 6;
            switch (POSITION) {
                case 0:
                    TYPE = RED_PAWN_0;
                    locateX = 0;
                    break;
                case 1:
                    TYPE = RED_PAWN_1;
                    locateX = 2;
                    break;
                case 2:
                    TYPE = RED_PAWN_2;
                    locateX = 4;
                    break;
                case 3:
                    TYPE = RED_PAWN_3;
                    locateX = 6;
                    break;
                default:
                    TYPE = RED_PAWN_4;
                    locateX = 8;
            }
        }
        int x = (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int y = (locateY + 1)* Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        setBounds(x, y, SIZE_PIECE, SIZE_PIECE);
        setImage();
    }

    @Override
    public Vector<Integer> choosePiecePosition(Check check, JButton top, JButton right, JButton bottom, JButton left, Vector<ChessPiece> pieces) {
//        Di chuyển cơ bản của Tốt:
//        Khi chưa qua Sông, Tốt chỉ được di chuyển thẳng và ăn quân thẳng theo chiều dọc.
//        Sau khi đã qua Sông, Tốt có thể di chuyển cả 2 chiều ngang và dọc.
//        Tốt chỉ di chuyển mỗi lần 1 ô và chỉ tiến lên không được lùi lại.
//        Quy tắc đặc biệt: Tốt không có quyền đi ngược lại .
        Vector<Integer> choose = new Vector<>();
        if (_COLOR_ == RED && locateY > 0 && check.checkLocal(locateX, locateY - 1)) {
            top.setLocation((locateX + 1) * CELL_SIZE - RADIUS, (locateY) * CELL_SIZE - RADIUS);
            top.setVisible(true);
        }
        if (_COLOR_ == RED && locateY > 0 && !check.checkLocal(locateX, locateY - 1) && pieces.elementAt(check.getPiece(locateX, locateY - 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX, locateY - 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX, locateY - 1)).changeImage();
        }
        if (_COLOR_ == BLACK && locateY < 9 && check.checkLocal(locateX, locateY + 1)) {
            bottom.setLocation((locateX + 1) * CELL_SIZE - RADIUS, (locateY + 2) * CELL_SIZE - RADIUS);
            bottom.setVisible(true);
        }
        if (_COLOR_ == BLACK && locateY < 9 && !check.checkLocal(locateX, locateY + 1) && pieces.elementAt(check.getPiece(locateX, locateY + 1))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX, locateY + 1)).TYPE);
            pieces.elementAt(check.getPiece(locateX, locateY + 1)).changeImage();
        }
        if (((_COLOR_ == BLACK && locateY > 4) || ((_COLOR_ == RED) && locateY < 5)) && locateX > 0 && check.checkLocal(locateX - 1, locateY)){
            left.setLocation(locateX * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            left.setVisible(true);
        }
        if (((_COLOR_ == BLACK && locateY > 4) || ((_COLOR_ == RED) && locateY < 5)) && locateX > 0 && !check.checkLocal(locateX - 1, locateY) && pieces.elementAt(check.getPiece(locateX - 1, locateY))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX - 1, locateY)).TYPE);
            pieces.elementAt(check.getPiece(locateX - 1, locateY)).changeImage();
        }
        if (((_COLOR_ == BLACK && locateY > 4) || ((_COLOR_ == RED) && locateY < 5)) && locateX < 8 && check.checkLocal(locateX + 1, locateY)){
            right.setLocation((locateX + 2) * CELL_SIZE - RADIUS, (locateY + 1) * CELL_SIZE - RADIUS);
            right.setVisible(true);
        }
        if (((_COLOR_ == BLACK && locateY > 4) || ((_COLOR_ == RED) && locateY < 5)) && locateX < 8 && !check.checkLocal(locateX + 1, locateY) && pieces.elementAt(check.getPiece(locateX + 1, locateY))._COLOR_ != this._COLOR_) {
            choose.add(pieces.elementAt(check.getPiece(locateX + 1, locateY)).TYPE);
            pieces.elementAt(check.getPiece(locateX + 1, locateY)).changeImage();
        }
        return choose;
    }

    @Override
    public void updateLocate(String temp) {
        if(temp.equals("top"))
            locateY--;
        if(temp.equals("bottom"))
            locateY++;
        if(temp.equals("left"))
            locateX--;
        if(temp.equals("right"))
            locateX++;
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }
}
