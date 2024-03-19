package main;

import chesspiece.ChessPiece;
import chesspiece.Piece;
import image.NewImage;

import javax.swing.*;
import java.util.Vector;

public class Event implements Piece {
    private int typeClick;
    private Vector<Integer> choose;
    private final JButton top, left, bottom, right;
    private boolean status;
    private final Check check;
    private final Vector<JButton> buttonH;
    private final Vector<JButton> buttonV;

    public Event(Vector<ChessPiece> pieces, JPanel panel) {
        this.typeClick = -1;
        this.check = new Check();
        this.status = false;
        this.top = new JButton();
        this.left = new JButton();
        this.bottom = new JButton();
        this.right = new JButton();
        this.buttonH = new Vector<>();
        this.buttonV = new Vector<>();
        this.choose = new Vector<>();

        this.setButton(this.top, panel);
        this.setButton(this.left, panel);
        this.setButton(this.bottom, panel);
        this.setButton(this.right, panel);
        for (int i = 0; i < 8; i++) {
            JButton button = new JButton();
            this.setButton(button, panel);
            this.buttonH.add(button);
        }
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            setButton(button, panel);
            this.buttonV.add(button);
        }
        this.setEventListensers(pieces);
    }

    public void setChooseEventListeners(Vector<ChessPiece> pieces) {
        for (ChessPiece _piece_ : pieces) {
            _piece_.addActionListener(e -> {
                this.status = true;
                this.hideButton(pieces);
                for (Integer temp : this.choose) {
                    if (temp == _piece_.getTYPE()) {
                        this.setKillEnemies(_piece_, pieces);
                        return;
                    }
                }
                this.choose = _piece_.choosePiecePosition(this.check, this.buttonH, this.buttonV, pieces);
                if (this.choose.isEmpty())
                    this.choose = _piece_.choosePiecePosition(this.check, this.top, this.right, this.bottom, this.left, pieces);
                System.out.println("size: " + this.choose.size());
                if (this.typeClick == _piece_.getTYPE()) {
                    System.out.println("reset");
                    this.hideButton(pieces);
                    this.typeClick = -1;
                    this.status = false;
                    this.choose = new Vector<>();
                } else
                    this.typeClick = _piece_.getTYPE();
            });
        }
    }

    public void resetImageChess(Vector<Integer> choose, Vector<ChessPiece> pieces) {
        for (Integer temp : choose) {
            pieces.elementAt(temp).setImage();
        }
    }

    public void setMoveTop(Vector<ChessPiece> pieces) {
        this.top.addActionListener(e -> {
            if (this.status) {
                int cell = pieces.elementAt(this.typeClick).getSIZE();
                pieces.elementAt(this.typeClick).setLocation(this.top.getX() + Piece.RADIUS - cell / 2, this.top.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), -1);
                pieces.elementAt(this.typeClick).updateLocate("top");
                pieces.elementAt(this.typeClick).updateLocate_("tr");
                this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), this.typeClick);
                this.hideButton(pieces);
                this.typeClick = -1;
            }
        });
    }

    public void setMoveRight(Vector<ChessPiece> pieces) {
        this.right.addActionListener(e -> {
            if (this.status) {
                int cell = pieces.elementAt(this.typeClick).getSIZE();
                pieces.elementAt(this.typeClick).setLocation(this.right.getX() + Piece.RADIUS - cell / 2, this.right.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), -1);
                pieces.elementAt(this.typeClick).updateLocate("right");
                pieces.elementAt(this.typeClick).updateLocate_("dr");
                this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), this.typeClick);
                this.hideButton(pieces);
                this.typeClick = -1;
            }
        });
    }

    public void setMoveBottom(Vector<ChessPiece> pieces) {
        this.bottom.addActionListener(e -> {
            if (this.status) {
                int cell = pieces.elementAt(this.typeClick).getSIZE();
                pieces.elementAt(this.typeClick).setLocation(this.bottom.getX() + Piece.RADIUS - cell / 2, this.bottom.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), -1);
                pieces.elementAt(this.typeClick).updateLocate("bottom");
                pieces.elementAt(this.typeClick).updateLocate_("dl");
                this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), this.typeClick);
                this.hideButton(pieces);
                this.typeClick = -1;
            }
        });
    }

    public void setMoveLeft(Vector<ChessPiece> pieces) {
        this.left.addActionListener(e -> {
            if (this.status) {
                int cell = pieces.elementAt(this.typeClick).getSIZE();
                pieces.elementAt(this.typeClick).setLocation(this.left.getX() + Piece.RADIUS - cell / 2, this.left.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), -1);
                pieces.elementAt(this.typeClick).updateLocate("left");
                pieces.elementAt(this.typeClick).updateLocate_("tl");
                this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), this.typeClick);
                this.hideButton(pieces);
                this.typeClick = -1;
            }
        });
    }

    public void setMoveHAnother(Vector<ChessPiece> pieces) {
        for (JButton button : this.buttonH) {
            button.addActionListener(e -> {
                if (this.status) {
                    int cell = pieces.elementAt(this.typeClick).getSIZE();
                    pieces.elementAt(this.typeClick).setLocation(button.getX() + Piece.RADIUS - cell / 2, button.getY() + Piece.RADIUS - cell / 2);
                    this.status = !this.status;
                    this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), -1);
                    pieces.elementAt(this.typeClick).updateLocate("h", button);
                    pieces.elementAt(this.typeClick).updateLocate(this.buttonH.indexOf(button));
                    this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), this.typeClick);
                    this.hideButton(pieces);
                    this.typeClick = -1;
                }
            });
        }
    }

    public void setMoveVAnother(Vector<ChessPiece> pieces) {
        for (JButton button : this.buttonV) {
            button.addActionListener(e -> {
                if (this.status) {
                    int cell = pieces.elementAt(this.typeClick).getSIZE();
                    pieces.elementAt(this.typeClick).setLocation(button.getX() + Piece.RADIUS - cell / 2, button.getY() + Piece.RADIUS - cell / 2);
                    this.status = !this.status;
                    this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), -1);
                    pieces.elementAt(this.typeClick).updateLocate("v", button);
                    this.check.setPiece(pieces.elementAt(this.typeClick).getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), this.typeClick);
                    this.hideButton(pieces);
                    this.typeClick = -1;
                }
            });
        }
    }

    public void setKillEnemies(ChessPiece chessPiece, Vector<ChessPiece> pieces) {
        ChessPiece temp = pieces.elementAt(this.typeClick);
        System.out.println(temp.getName() + " killed " + chessPiece.getName());
        temp.setLocation((chessPiece.getLocateX() + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2, (chessPiece.getLocateY() + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2);
        this.status = !this.status;
        this.check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
        temp.updateLocate(chessPiece);
        this.check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
        this.hideButton(pieces);
        chessPiece.setVisible(false);
        this.typeClick = -1;
    }

    public void setMoveEventListeners(Vector<ChessPiece> pieces) {
        this.setMoveTop(pieces);
        this.setMoveRight(pieces);
        this.setMoveBottom(pieces);
        this.setMoveLeft(pieces);
        this.setMoveVAnother(pieces);
        this.setMoveHAnother(pieces);
    }

    public void setEventListensers(Vector<ChessPiece> pieces) {
        this.setChooseEventListeners(pieces);
        this.setMoveEventListeners(pieces);
    }

    public void setButton(JButton button, JPanel panel) {
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/R.png");
        imageIcon = new NewImage().resizeImage(imageIcon, Piece.RADIUS * 2, Piece.RADIUS * 2);
        button.setBorderPainted(false);
        button.setIcon(imageIcon);
        button.setContentAreaFilled(false);
        button.setSize(2 * Piece.RADIUS, 2 * Piece.RADIUS);
        button.setVisible(false);
        panel.add(button);
    }

    public void hideButton(Vector<ChessPiece> pieces) {
        this.top.setVisible(false);
        this.right.setVisible(false);
        this.bottom.setVisible(false);
        this.left.setVisible(false);
        for (JButton button : this.buttonV)
            button.setVisible(false);
        for (JButton button : this.buttonH)
            button.setVisible(false);
        this.resetImageChess(this.choose, pieces);
    }
}
