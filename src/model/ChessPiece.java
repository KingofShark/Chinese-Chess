package model;

import constant.Piece;
import controller.MovingMethod;
import image.NewImage;

import javax.swing.*;
import java.util.Objects;
import java.util.Vector;

public abstract class ChessPiece extends JButton implements Piece, MovingMethod {
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

    public int getCOLOR() {
        return this._COLOR_;
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
        this._image_ = new ImageIcon(System.getProperty("user.dir") + "/resource/image/piece/" + this._COLOR_ + "/" + this._name_ + ".png");
        this._image_ = new NewImage().resizeImage(this._image_, SIZE_PIECE, SIZE_PIECE);
        if (this._image_.getImage() == null) {
            System.out.println("Cannot read image " + this._name_);
            return;
        }
        setIcon(this._image_);
        System.out.println("Read successfully " + this._name_);
    }

    public void changeImage() {
        this._image_ = new ImageIcon(System.getProperty("user.dir") + "/resource/image/makeColor/" + this._COLOR_ + "/" + this._name_ + ".png");
        this._image_ = new NewImage().resizeImage(this._image_, SIZE_PIECE, SIZE_PIECE);
        if (Objects.equals(this._image_, new ImageIcon())) {
            System.out.println("Cannot change image " + this._name_);
            return;
        }
        System.out.println("Change successfully " + this._name_);
        setIcon(this._image_);
    }

    public int getTYPE() {
        return TYPE;
    }

    public String getName() {
        return _name_;
    }

    public void setLocate(int x, int y) {
        this.locateX = x;
        this.locateY = y;
        int X = (locateX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        int Y = (locateY + 1)* Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
        this.setBounds(X, Y, SIZE_PIECE, SIZE_PIECE);
    }

    public void updateLocate(ChessPiece chessPiece) {
        this.locateX = chessPiece.getLocateX();
        this.locateY = chessPiece.getLocateY();
        System.out.println(_name_ + " update x: " + locateX + ", y: " + locateY);
    }

    @Override
    public void updateLocate(JButton button) {

    }

    @Override
    public void updateLocate_(String temp) {

    }

    @Override
    public void updateLocate(int typeClick) {

    }

    @Override
    public Vector<Integer> choosePiecePosition(Vector<JButton> buttonH, Vector<JButton> buttonV) {
        return new Vector<>();
    }

    @Override
    public Vector<Integer> choosePiecePosition(JButton top, JButton right, JButton bottom, JButton left) {
        return new Vector<>();
    }

    @Override
    public void updateLocate(String temp) {

    }
    @Override
    public Boolean checkMate() {
        return false;
    }

    @Override
    public void resetDefauft() {
        this.setVisible(true);
    }
}
