package chesspiece;

import main.Check;
import javax.swing.*;
import java.util.Vector;

public class Elephant extends ChessPiece {

    public Elephant(int color, int position) {
        super(color, position);
        _name_ = "Elephant";
        TYPE = RED_LEFT_ELEPHANT;
        if (_COLOR_ == BLACK)
            TYPE = (POSITION == Piece.RIGHT) ? BLACK_RIGHT_ELEPHANT : BLACK_LEFT_ELEPHANT;
        else if (POSITION == Piece.RIGHT)
            TYPE = RED_RIGHT_ELEPHANT;
        this.resetDefauft();
        this.setImage();
    }

    @Override
    public void resetDefauft() {
        super.resetDefauft();
        locateX = (POSITION == Piece.RIGHT) ? 6 : 2;
        locateY = (_COLOR_ == BLACK) ? 0 : 9;
        int x = (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int y = (locateY + 1)* Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        setBounds(x, y, SIZE_PIECE, SIZE_PIECE);
    }

    @Override
    public Vector<Integer> choosePiecePosition(JButton top, JButton right, JButton bottom, JButton left) {
//        Tượng: Đi chéo 2 ô (ngang 2 và dọc 2) cho mỗi nước đi.
//        Tượng chỉ được phép ở một bên của bàn cờ, không được di chuyển sang nữa bàn cờ của đối phương.
//        Nước đi của tượng sẽ không hợp lệ khi có một quân cờ nằm chặn giữa đường đi.
        Check check = StaticPieces.getCheck();
        Vector<ChessPiece> pieces = StaticPieces.getPieces();
        Vector<Integer> chooses = new Vector<>();
        if((_COLOR_ == BLACK)){
            if (locateY - 1 > 0 && locateX - 1 > 0 && check.checkLocal(locateX - 2, locateY - 2) && check.checkLocal(locateX - 1, locateY - 1)) {
                left.setLocation((locateX - 1) * CELL_SIZE - RADIUS, (locateY - 1) * CELL_SIZE - RADIUS);
                left.setVisible(true);//tl
            }
            if (locateY - 1 > 0 && locateX + 1 < 8 && check.checkLocal(locateX + 2, locateY - 2) && check.checkLocal(locateX + 1, locateY - 1)) {
                top.setLocation((locateX + 3) * CELL_SIZE - RADIUS, (locateY - 1) * CELL_SIZE - RADIUS);
                top.setVisible(true);//tr
            }
            if (locateY + 1 < 4 && locateX - 1 > 0 && check.checkLocal(locateX - 2, locateY + 2) && check.checkLocal(locateX - 1, locateY + 1)) {
                bottom.setLocation((locateX - 1) * CELL_SIZE - RADIUS, (locateY + 3) * CELL_SIZE - RADIUS);
                bottom.setVisible(true);//dl
            }
            if (locateY + 1 < 4 && locateX + 1 < 8 && check.checkLocal(locateX + 2, locateY + 2) && check.checkLocal(locateX + 1, locateY + 1)) {
                right.setLocation((locateX + 3) * CELL_SIZE - RADIUS, (locateY + 3) * CELL_SIZE - RADIUS);
                right.setVisible(true);//dr
            }
            //
            if (locateY - 1 > 0 && locateX - 1 > 0 && !check.checkLocal(locateX - 2, locateY - 2) && check.checkLocal(locateX - 1, locateY - 1) && pieces.elementAt(check.getPiece(locateX - 2, locateY - 2))._COLOR_ != _COLOR_) {
                chooses.add(pieces.elementAt(check.getPiece(locateX - 2, locateY - 2)).TYPE);
                pieces.elementAt(check.getPiece(locateX - 2, locateY - 2)).changeImage();
            }
            if (locateY + 1 < 0 && locateX + 1 < 8 && !check.checkLocal(locateX + 2, locateY - 2) && check.checkLocal(locateX + 1, locateY - 1) && pieces.elementAt(check.getPiece(locateX + 2, locateY - 2))._COLOR_ != _COLOR_) {
                chooses.add(pieces.elementAt(check.getPiece(locateX + 2, locateY - 2)).TYPE);
                pieces.elementAt(check.getPiece(locateX + 2, locateY - 2)).changeImage();
            }
            if (locateY + 1 < 4 && locateX - 1 > 0 && !check.checkLocal(locateX - 2, locateY + 2) && check.checkLocal(locateX - 1, locateY + 1) && pieces.elementAt(check.getPiece(locateX - 2, locateY + 2))._COLOR_ != _COLOR_) {
                chooses.add(pieces.elementAt(check.getPiece(locateX - 2, locateY + 2)).TYPE);
                pieces.elementAt(check.getPiece(locateX - 2, locateY + 2)).changeImage();
            }
            if (locateY + 1 < 4 && locateX + 1 < 8 && !check.checkLocal(locateX + 2, locateY + 2) && check.checkLocal(locateX + 1, locateY + 1) && pieces.elementAt(check.getPiece(locateX + 2, locateY + 2))._COLOR_ != _COLOR_) {
                chooses.add(pieces.elementAt(check.getPiece(locateX + 2, locateY + 2)).TYPE);
                pieces.elementAt(check.getPiece(locateX + 2, locateY + 2)).changeImage();
            }
        } else {
            if (locateY > 5 && locateX - 1 > 0 && check.checkLocal(locateX - 2, locateY - 2) && check.checkLocal(locateX - 1, locateY - 1)) {
                left.setLocation((locateX - 1) * CELL_SIZE - RADIUS, (locateY - 1) * CELL_SIZE - RADIUS);
                left.setVisible(true);//tl
            }
            if (locateY > 5 && locateX + 1 < 8 && check.checkLocal(locateX + 2, locateY - 2) && check.checkLocal(locateX + 1, locateY - 1)) {
                top.setLocation((locateX + 3) * CELL_SIZE - RADIUS, (locateY - 1) * CELL_SIZE - RADIUS);
                top.setVisible(true);//tr
            }
            if (locateY + 1 < 9 && locateX - 1 > 0 && check.checkLocal(locateX - 2, locateY + 2) && check.checkLocal(locateX - 1, locateY + 1)) {
                bottom.setLocation((locateX - 1) * CELL_SIZE - RADIUS, (locateY + 3) * CELL_SIZE - RADIUS);
                bottom.setVisible(true);//dl
            }
            if (locateY + 1 < 9 && locateX + 1 < 8 && check.checkLocal(locateX + 2, locateY + 2) && check.checkLocal(locateX + 1, locateY + 1)) {
                right.setLocation((locateX + 3) * CELL_SIZE - RADIUS, (locateY + 3) * CELL_SIZE - RADIUS);
                right.setVisible(true);//dr
            }
            //
            if (locateY > 5 && locateX - 1 > 0 && !check.checkLocal(locateX - 2, locateY - 2) && check.checkLocal(locateX - 1, locateY - 1) && pieces.elementAt(check.getPiece(locateX - 2, locateY - 2))._COLOR_ != _COLOR_) {
                chooses.add(pieces.elementAt(check.getPiece(locateX - 2, locateY - 2)).TYPE);
                pieces.elementAt(check.getPiece(locateX - 2, locateY - 2)).changeImage();
            }
            if (locateY > 5 && locateX + 1 < 8 && !check.checkLocal(locateX + 2, locateY - 2) && check.checkLocal(locateX + 1, locateY - 1) && pieces.elementAt(check.getPiece(locateX + 2, locateY - 2))._COLOR_ != _COLOR_) {
                chooses.add(pieces.elementAt(check.getPiece(locateX + 2, locateY - 2)).TYPE);
                pieces.elementAt(check.getPiece(locateX + 2, locateY - 2)).changeImage();
            }
            if (locateY + 1 < 9 && locateX - 1 > 0 && !check.checkLocal(locateX - 2, locateY + 2) && check.checkLocal(locateX - 1, locateY + 1) && pieces.elementAt(check.getPiece(locateX - 2, locateY + 2))._COLOR_ != _COLOR_) {
                chooses.add(pieces.elementAt(check.getPiece(locateX - 2, locateY + 2)).TYPE);
                pieces.elementAt(check.getPiece(locateX - 2, locateY + 2)).changeImage();
            }
            if (locateY + 1 < 9 && locateX + 1 < 8 && !check.checkLocal(locateX + 2, locateY + 2) && check.checkLocal(locateX + 1, locateY + 1) && pieces.elementAt(check.getPiece(locateX + 2, locateY + 2))._COLOR_ != _COLOR_) {
                chooses.add(pieces.elementAt(check.getPiece(locateX + 2, locateY + 2)).TYPE);
                pieces.elementAt(check.getPiece(locateX + 2, locateY + 2)).changeImage();
            }
        }
        return chooses;
    }

    @Override
    public void updateLocate_(String temp) {
        if(temp.equals("tl")){
            locateX-=2;
            locateY-=2;
        }
        if(temp.equals("tr")){
            locateY-=2;
            locateX+=2;
        }
        if(temp.equals("dl")){
            locateX-=2;
            locateY+=2;
        }
        if(temp.equals("dr")){
            locateX+=2;
            locateY+=2;
        }
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }
}
