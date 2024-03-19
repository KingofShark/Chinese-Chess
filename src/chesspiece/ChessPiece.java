package chesspiece;

import image.NewImage;
import main.Check;

import javax.swing.*;
import java.util.Objects;
import java.util.Vector;

public class ChessPiece extends JButton implements Piece {
    protected int TYPE;
    protected int _COLOR_, POSITION, locateX, locateY;
    protected ImageIcon _image_;
    protected String _name_;

    public ChessPiece(int color) {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        this.POSITION = 2;
        this._COLOR_ = color;
    }

    public ChessPiece(int color, int position) {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        this.POSITION = position;
        this._COLOR_ = color;
    }

    public int getLocateX() {
        return this.locateX;
    }

    public int getLocateY() {
        return this.locateY;
    }

    public int getSIZE() {
        return SIZE_PIECE;
    }

    public void setImage() {
        this._image_ = new ImageIcon(System.getProperty("user.dir") + "/src/image/piece/" + this._COLOR_ + "/" + this._name_ + ".png");
        this._image_ = new NewImage().resizeImage(this._image_, SIZE_PIECE, SIZE_PIECE);
        if (this._image_.getImage() == null) {
            System.out.println("Cannot read image " + this._name_);
            return;
        }
        setIcon(this._image_);
        System.out.println("Read successfully " + this._name_);
    }

    public void changeImage() {
        this._image_ = new ImageIcon(System.getProperty("user.dir") + "/src/image/makeColor/" + this._COLOR_ + "/" + this._name_ + ".png");
        this._image_ = new NewImage().resizeImage(this._image_, SIZE_PIECE, SIZE_PIECE);
        if (Objects.equals(this._image_, new ImageIcon())) {
            System.out.println("Cannot change image " + this._name_);
            return;
        }
        System.out.println("Change successfully " + this._name_);
        setIcon(this._image_);
    }

    public int get_X() {
        return (getX() - CELL_SIZE / 2) / CELL_SIZE;
    }

    public int get_Y() {
        return (getY() - CELL_SIZE / 2) / CELL_SIZE;
    }

    public int getTYPE() {
        return TYPE;
    }

    public String getName() {
        return _name_;
    }

    public Vector<Integer> choosePiecePosition(Check check, Vector<JButton> buttonH, Vector<JButton> buttonV, Vector<ChessPiece> pieces) {
        return new Vector<>();
    }

    public Vector<Integer> choosePiecePosition(Check check, JButton top, JButton right, JButton bottom, JButton left, Vector<ChessPiece> pieces) {
        return new Vector<>();
    }

    public void updateLocate(String temp) {
    }

    public void updateLocate(ChessPiece chessPiece) {
        this.locateX = chessPiece.getLocateX();
        this.locateY = chessPiece.getLocateY();
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }

    public void updateLocate(String temp, JButton button) {
    }

    public void updateLocate_(String temp) {
    }

    public void updateLocate(int typeClick) {
    }
}
