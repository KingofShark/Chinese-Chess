package controller;

import constant.Piece;
import file.IOFile;
import image.NewImage;
import model.Check;
import model.ChessPiece;
import model.Move;
import view.CountDown;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class Event implements Piece {
    /**
     * typeClick: Kiểu quân cờ được chọn
     * status: Trạng thái chọn quân cờ
     * top, left, bottom, right: Các nút di chuyển
     * buttonH, buttonV: Các nút di chuyển khác
     * choose: Các vị trí có thể di chuyển
     */
    private int typeClick;
    private Vector<Integer> choose;
    private final JButton top, left, bottom, right;
    private boolean status;
    private final Vector<JButton> buttonH;
    private final Vector<JButton> buttonV;
    private int lastPiece;
    private final int speed = 5;

    public Event() {
        lastPiece = -1;
        this.typeClick = -1;
        this.status = false;
        this.top = new JButton();
        this.left = new JButton();
        this.bottom = new JButton();
        this.right = new JButton();
        this.buttonH = new Vector<>();
        this.buttonV = new Vector<>();
        this.choose = new Vector<>();

        this.setButton(this.top);
        this.setButton(this.left);
        this.setButton(this.bottom);
        this.setButton(this.right);
        for (int i = 0; i < 8; i++) {
            JButton button = new JButton();
            this.setButton(button);
            this.buttonH.add(button);
        }
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            this.setButton(button);
            this.buttonV.add(button);
        }
        this.setEventListensers();
    }

    /**
     * setChooseEventListeners: Thiết lập sự kiện chọn quân cờ
     */
    private void setChooseEventListeners() {
        Vector<ChessPiece> pieces = GameController.getPieces();
        for (ChessPiece _piece_ : pieces) {
            _piece_.addActionListener(_ -> {
                CountDown clock_1 = GameController.getClock_1();
                CountDown clock_2 = GameController.getClock_2();
                if (clock_1.isFullTime() || clock_2.isFullTime() || GameController.getTurn() % 2 == BLACK)
                    return;
                if (GameController.getTurn() % 2 == 1 - _piece_.getCOLOR()) {
                    for (Integer temp : this.choose) {
                        if (temp == _piece_.getTYPE()) {
                            this.setKillEnemies(_piece_);
                            return;
                        }
                    }
                    return;
                }
                this.status = true;
                this.hideButton();
                this.choose = _piece_.choosePiecePosition(this.buttonH, this.buttonV);
                if (this.choose.isEmpty())
                    this.choose = _piece_.choosePiecePosition(this.top, this.right, this.bottom, this.left);
                if (this.typeClick == _piece_.getTYPE()) {
                    this.hideButton();
                    this.typeClick = -1;
                    this.status = false;
                    this.choose = new Vector<>();
                } else
                    this.typeClick = _piece_.getTYPE();
            });
        }
    }


    private void resetImageChess(Vector<Integer> choose) {
        for (Integer temp : choose) {
            GameController.getPieces().elementAt(temp).setImage();
        }
    }

    /**
     * setMoveTop: Thiết lập sự kiện di chuyển lên
     */
    private void setMoveTop() {
        this.top.addActionListener(_ -> {
            Check check = GameController.getCheck();
            Vector<ChessPiece> pieces = GameController.getPieces();

            if (this.status) {
                ChessPiece temp = pieces.elementAt(this.typeClick);
                IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, GameController.getTurn());
                int cell = temp.getSIZE();

                System.out.println("now: " + temp.getLocateX() + " " + temp.getLocateY());
                this.status = !this.status;
                check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
                this.move(temp, this.top.getX() + Piece.RADIUS - cell / 2, this.top.getY() + Piece.RADIUS - cell / 2);
                temp.updateLocate("top");
                temp.updateLocate_("tr");
                check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                this.hideButton();

                lastPiece = temp.getTYPE();
                if (this.checkMate(temp))
                    return;
                if (GameController.getTurn() % 2 == Piece.BLACK) {
                    GameController.getAvatarBot().stopCountdown();
                    GameController.getClock_2().stop();
                    GameController.getAvatarPlayer().startCountdown();
                    GameController.getClock_1().resume();
                } else {
                    GameController.getAvatarPlayer().stopCountdown();
                    GameController.getClock_1().stop();
                    GameController.getAvatarBot().startCountdown();
                    GameController.getClock_2().resume();
                }
                GameController.setTurn((GameController.getTurn() > 0) ? GameController.getTurn() - 1 : 1);
                if (!temp.checkMate()) {
                    GameController.getChessBoardPanel().hideCheckMate();
                    GameController.getSoundEffect().playSoundMove();

                } else {
                    GameController.getSoundEffect().playSoundCheckMate();
                    GameController.getChessBoardPanel().setCheckMate(temp.getCOLOR());
                }
                this.typeClick = -1;
            }
            setMachine();
        });
    }

    /**
     * setMoveRight: Thiết lập sự kiện di chuyển sang phải
     */
    private void setMoveRight() {
        this.right.addActionListener(_ -> {
            Check check = GameController.getCheck();
            Vector<ChessPiece> pieces = GameController.getPieces();

            if (this.status) {
                ChessPiece temp = pieces.elementAt(this.typeClick);
                IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, GameController.getTurn());
                int cell = temp.getSIZE();
                System.out.println("now: " + temp.getLocateX() + " " + temp.getLocateY());
                this.move(temp, this.right.getX() + Piece.RADIUS - cell / 2, this.right.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
                temp.updateLocate("right");
                temp.updateLocate_("dr");
                check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                this.hideButton();


                lastPiece = temp.getTYPE();

                if (this.checkMate(temp))
                    return;
                if (GameController.getTurn() % 2 == Piece.BLACK) {
                    GameController.getAvatarBot().stopCountdown();
                    GameController.getClock_2().stop();
                    GameController.getAvatarPlayer().startCountdown();
                    GameController.getClock_1().resume();
                } else {
                    GameController.getAvatarPlayer().stopCountdown();
                    GameController.getClock_1().stop();
                    GameController.getAvatarBot().startCountdown();
                    GameController.getClock_2().resume();
                }
                GameController.setTurn((GameController.getTurn() > 0) ? GameController.getTurn() - 1 : 1);
                if (!temp.checkMate()) {
                    GameController.getSoundEffect().playSoundMove();
                    GameController.getChessBoardPanel().hideCheckMate();
                } else {
                    GameController.getChessBoardPanel().setCheckMate(temp.getCOLOR());
                    GameController.getSoundEffect().playSoundCheckMate();
                }
                this.typeClick = -1;
            }
            setMachine();
        });
    }

    private void setMoveBottom() {
        this.bottom.addActionListener(_ -> {
            Check check = GameController.getCheck();
            Vector<ChessPiece> pieces = GameController.getPieces();

            if (this.status) {
                ChessPiece temp = pieces.elementAt(this.typeClick);
                IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, GameController.getTurn());
                int cell = temp.getSIZE();
                System.out.println("now: " + temp.getLocateX() + " " + temp.getLocateY());
                this.move(temp, this.bottom.getX() + Piece.RADIUS - cell / 2, this.bottom.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                check.setPiece(temp.getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), -1);
                temp.updateLocate("bottom");
                temp.updateLocate_("dl");
                check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                this.hideButton();


                lastPiece = temp.getTYPE();

                if (this.checkMate(temp))
                    return;
                if (GameController.getTurn() % 2 == Piece.BLACK) {
                    GameController.getAvatarBot().stopCountdown();
                    GameController.getClock_2().stop();
                    GameController.getAvatarPlayer().startCountdown();
                    GameController.getClock_1().resume();
                } else {
                    GameController.getAvatarPlayer().stopCountdown();
                    GameController.getClock_1().stop();
                    GameController.getAvatarBot().startCountdown();
                    GameController.getClock_2().resume();
                }
                GameController.setTurn((GameController.getTurn() > 0) ? GameController.getTurn() - 1 : 1);
                if (!temp.checkMate()) {
                    GameController.getSoundEffect().playSoundMove();
                    GameController.getChessBoardPanel().hideCheckMate();
                } else {
                    GameController.getSoundEffect().playSoundCheckMate();
                    GameController.getChessBoardPanel().setCheckMate(temp.getCOLOR());
                }

                this.typeClick = -1;
            }
            setMachine();
        });
    }

    private void setMoveLeft() {
        this.left.addActionListener(_ -> {
            Check check = GameController.getCheck();
            Vector<ChessPiece> pieces = GameController.getPieces();

            if (this.status) {
                ChessPiece temp = pieces.elementAt(this.typeClick);
                IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, GameController.getTurn());
                int cell = temp.getSIZE();
                System.out.println("now-l: " + temp.getLocateX() + " " + temp.getLocateY());
                this.move(temp, this.left.getX() + Piece.RADIUS - cell / 2, this.left.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
                temp.updateLocate("left");
                temp.updateLocate_("tl");
                check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                this.hideButton();


                lastPiece = temp.getTYPE();

                if (this.checkMate(temp))
                    return;
                if (GameController.getTurn() % 2 == Piece.BLACK) {
                    GameController.getAvatarBot().stopCountdown();
                    GameController.getClock_2().stop();
                    GameController.getAvatarPlayer().startCountdown();
                    GameController.getClock_1().resume();
                } else {
                    GameController.getAvatarPlayer().stopCountdown();
                    GameController.getClock_1().stop();
                    GameController.getAvatarBot().startCountdown();
                    GameController.getClock_2().resume();
                }
                GameController.setTurn((GameController.getTurn() > 0) ? GameController.getTurn() - 1 : 1);
                if (!temp.checkMate()) {
                    GameController.getSoundEffect().playSoundMove();
                    GameController.getChessBoardPanel().hideCheckMate();
                } else {
                    GameController.getSoundEffect().playSoundCheckMate();
                    GameController.getChessBoardPanel().setCheckMate(temp.getCOLOR());
                }
                this.typeClick = -1;
            }
            setMachine();
        });
    }

    private void setMoveHAnother() {
        for (JButton button : this.buttonH) {
            button.addActionListener(_ -> {
                Check check = GameController.getCheck();
                Vector<ChessPiece> pieces = GameController.getPieces();
                if (GameController.getSetting().getStatus())
                    return;
                if (this.status) {
                    ChessPiece temp = pieces.elementAt(this.typeClick);
                    IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, GameController.getTurn());
                    int cell = temp.getSIZE();
                    System.out.println("now-h: " + temp.getLocateX() + " " + temp.getLocateY());
                    this.move(temp, button.getX() + Piece.RADIUS - cell / 2, button.getY() + Piece.RADIUS - cell / 2);
                    this.status = !this.status;
                    check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
                    temp.updateLocate(button);
                    temp.updateLocate(this.buttonH.indexOf(button));
                    check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                    this.hideButton();


                    lastPiece = temp.getTYPE();
                    if (this.checkMate(temp))
                        return;
                    if (GameController.getTurn() % 2 == Piece.BLACK) {
                        GameController.getAvatarBot().stopCountdown();
                        GameController.getClock_2().stop();
                        GameController.getAvatarPlayer().startCountdown();
                        GameController.getClock_1().resume();
                    } else {
                        GameController.getAvatarPlayer().stopCountdown();
                        GameController.getClock_1().stop();
                        GameController.getAvatarBot().startCountdown();
                        GameController.getClock_2().resume();
                    }
                    GameController.setTurn((GameController.getTurn() > 0) ? GameController.getTurn() - 1 : 1);
                    if (!temp.checkMate()) {
                        GameController.getSoundEffect().playSoundMove();
                        GameController.getChessBoardPanel().hideCheckMate();
                    } else {
                        GameController.getSoundEffect().playSoundCheckMate();
                        GameController.getChessBoardPanel().setCheckMate(temp.getCOLOR());
                    }
                    System.out.println(2 - GameController.getTurn() % 2 + " " + this.typeClick);
                    this.typeClick = -1;
                }
                setMachine();
            });
        }
    }

    private void setMoveVAnother() {
        for (JButton button : this.buttonV) {
            button.addActionListener(_ -> {
                Check check = GameController.getCheck();
                Vector<ChessPiece> pieces = GameController.getPieces();
                if (GameController.getSetting().getStatus())
                    return;
                if (this.status) {
                    ChessPiece temp = pieces.elementAt(this.typeClick);
                    IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, GameController.getTurn());
                    int cell = temp.getSIZE();
                    System.out.println("now-v: " + temp.getLocateX() + " " + temp.getLocateY());
                    this.move(temp, button.getX() + Piece.RADIUS - cell / 2, button.getY() + Piece.RADIUS - cell / 2);
                    this.status = !this.status;
                    check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
                    temp.updateLocate(button);
                    check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                    this.hideButton();


                    lastPiece = temp.getTYPE();
                    if (this.checkMate(temp))
                        return;
                    if (GameController.getTurn() % 2 == Piece.BLACK) {
                        GameController.getAvatarBot().stopCountdown();
                        GameController.getClock_2().stop();
                        GameController.getAvatarPlayer().startCountdown();
                        GameController.getClock_1().resume();
                    } else {
                        GameController.getAvatarPlayer().stopCountdown();
                        GameController.getClock_1().stop();
                        GameController.getAvatarBot().startCountdown();
                        GameController.getClock_2().resume();
                    }
                    GameController.setTurn((GameController.getTurn() > 0) ? GameController.getTurn() - 1 : 1);
                    if (!temp.checkMate()) {
                        GameController.getSoundEffect().playSoundMove();
                        GameController.getChessBoardPanel().hideCheckMate();
                    } else {
                        GameController.getSoundEffect().playSoundCheckMate();
                        GameController.getChessBoardPanel().setCheckMate(temp.getCOLOR());
                    }
                    System.out.println(2 - GameController.getTurn() % 2 + " " + this.typeClick);
                    this.typeClick = -1;
                }
                setMachine();
            });
        }
    }

    private void setKillEnemies(ChessPiece chessPiece) {
        Check check = GameController.getCheck();
        Vector<ChessPiece> pieces = GameController.getPieces();
        ChessPiece temp = pieces.elementAt(this.typeClick);
        IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), chessPiece.getTYPE(), GameController.getTurn());
        System.out.println(temp.getName() + " killed " + chessPiece.getName());
        System.out.println("now kill: " + temp.getLocateX() + " " + temp.getLocateY());
        this.move(
                temp,
                PADDING + (chessPiece.getLocateX() + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2,
                (chessPiece.getLocateY() + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2
        );
        this.status = !this.status;
        check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
        temp.updateLocate(chessPiece);
        check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
        this.hideButton();


        lastPiece = temp.getTYPE();
        chessPiece.setVisible(false);
        if (GameController.getTurn() % 2 == Piece.BLACK) {
            GameController.getAvatarBot().stopCountdown();
            GameController.getClock_2().stop();
            GameController.getAvatarPlayer().startCountdown();
            GameController.getClock_1().resume();
        } else {
            GameController.getAvatarPlayer().stopCountdown();
            GameController.getClock_1().stop();
            GameController.getAvatarBot().startCountdown();
            GameController.getClock_2().resume();
        }
        ChessPiece general_red = pieces.firstElement();
        if (general_red.checkMate()) {
            GameController.setTurn(-1);
            GameController.getAvatarBot().stopCountdown();
            GameController.getAvatarPlayer().stopCountdown();
            GameController.getClock_2().stop();
            GameController.getClock_1().stop();
            JOptionPane.showMessageDialog(null, (chessPiece.getTYPE() == Piece.BLACK) ? "Đen thắng" : "Đỏ thắng");
            return;
        }
        if (chessPiece.getTYPE() == 0 || chessPiece.getTYPE() == 1) {
            GameController.setTurn(-1);
            GameController.getAvatarBot().stopCountdown();
            GameController.getClock_2().stop();
            GameController.getAvatarPlayer().stopCountdown();
            GameController.getClock_1().stop();
            JOptionPane.showMessageDialog(null, (chessPiece.getTYPE() == 0) ? "Đen thắng" : "Đỏ thắng");
            return;
        }
        if (temp.checkMate()) {
            GameController.getChessBoardPanel().setCheckMate(temp.getCOLOR());
            GameController.getSoundEffect().playSoundCheckMate();
        } else {
            GameController.getSoundEffect().playSounEffect("kill");
            GameController.getChessBoardPanel().hideCheckMate();
        }
        GameController.setTurn((GameController.getTurn() > 0) ? GameController.getTurn() - 1 : 1);
        setMachine();
        System.out.println(2 - GameController.getTurn() % 2 + " " + this.typeClick);
        this.typeClick = -1;
    }

    /**
     * setMoveEventListeners: Thiết lập sự kiện di chuyển quân cờ
     */
    private void setMoveEventListeners() {
        this.setMoveTop();
        this.setMoveRight();
        this.setMoveBottom();
        this.setMoveLeft();
        this.setMoveVAnother();
        this.setMoveHAnother();
    }

    /**
     * setEventListensers: Thiết lập sự kiện
     */
    private void setEventListensers() {
        if (GameController.getTurn() == -1)
            return;
        this.setChooseEventListeners();
        this.setMoveEventListeners();
    }

    /**
     * setButton: Thiết lập nút di chuyển
     *
     * @param button: Nút di chuyển
     */
    private void setButton(JButton button) {
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/R.png");
        imageIcon = new NewImage().resizeImage(imageIcon, Piece.RADIUS * 2, Piece.RADIUS * 2);
        button.setBorderPainted(false);
        button.setIcon(imageIcon);
        button.setContentAreaFilled(false);
        button.setSize(2 * Piece.RADIUS, 2 * Piece.RADIUS);
        button.setVisible(false);
        GameController.getChessBoardPanel().add(button);
    }


    /**
     * hideButton: Ẩn các nút di chuyển
     */
    public void hideButton() {

        if (lastPiece != -1) {
            ChessPiece lastPiece = GameController.getPieces().elementAt(this.lastPiece);
            lastPiece.setImage();
        }

        this.top.setVisible(false);
        this.right.setVisible(false);
        this.bottom.setVisible(false);
        this.left.setVisible(false);
        for (JButton button : this.buttonV)
            button.setVisible(false);
        for (JButton button : this.buttonH)
            button.setVisible(false);
        this.resetImageChess(this.choose);
    }

    /**
     * checkMate: Kiểm tra chiếu bí
     *
     * @param temp: Quân cờ
     * @return: True nếu chiếu bí, ngược lại False
     */
    private Boolean checkMate(ChessPiece temp) {
        ChessPiece general_red = GameController.getPieces().firstElement();
        if (general_red.checkMate()) {
            GameController.setTurn(-1);
            GameController.getAvatarBot().stopCountdown();
            GameController.getAvatarPlayer().stopCountdown();
            GameController.getClock_2().stop();
            GameController.getClock_1().stop();
            JOptionPane.showMessageDialog(null, (temp.getTYPE() % 2 == Piece.BLACK) ? "Đỏ thắng" : "Đen thắng");
            return true;
        }
        return false;
    }

    // máy đi
    public void setMachine() {
        new Thread(() -> {
            if (GameController.getTurn() % 2 == Piece.RED)
                return;

            Check check = GameController.getCheck();
            Vector<ChessPiece> pieces = GameController.getPieces();


            Move move = Ai.findBestMove(check, GameController.getLevel(), Piece.BLACK, 100000000);
            if (GameController.getClock_2().isFullTime() || GameController.getClock_1().isFullTime())
                return;
            if (move == null) {
                GameController.getChessBoardPanel().setFullTime();
                GameController.getAvatarBot().stopCountdown();
                GameController.getAvatarPlayer().stopCountdown();
                GameController.getClock_2().stop();
                GameController.getClock_1().stop();
                GameController.getChessBoardPanel().getNotificationPanel().showNotification("Hết cờ, Bạn thắng!");
                return;
            }
            typeClick = check.getPiece(move.fromX, move.fromY);

            if (!check.isEmpty(move.toX, move.toY) && pieces.elementAt(check.getPiece(move.toX, move.toY)).getCOLOR() == Piece.RED) {
                ChessPiece temp = pieces.elementAt(check.getPiece(move.toX, move.toY));
                this.setKillEnemies(temp);
                if (!Ai.isHasValidMoves(check, Piece.RED)) {
                    GameController.getChessBoardPanel().setFullTime();
                    GameController.getAvatarBot().stopCountdown();
                    GameController.getAvatarPlayer().stopCountdown();
                    GameController.getClock_2().stop();
                    GameController.getClock_1().stop();
                    GameController.getChessBoardPanel().getNotificationPanel().showNotification("Hết cờ, Bạn thua!");
                }
                return;
            }

            ChessPiece temp = pieces.elementAt(this.typeClick);
            IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, GameController.getTurn());
            check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);

            int X = PADDING + (move.toX + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
            int Y = (move.toY + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
            this.move(temp, X, Y);
            temp.setLocate(move.toX, move.toY);
            check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
            this.hideButton();

            GameController.getChessBoardPanel().highlight(move.fromX, move.fromY);

            lastPiece = temp.getTYPE();

            if (this.checkMate(temp))
                return;
            if (GameController.getTurn() % 2 == Piece.BLACK) {
                GameController.getAvatarBot().stopCountdown();
                GameController.getClock_2().stop();
                GameController.getAvatarPlayer().startCountdown();
                GameController.getClock_1().resume();
            } else {
                GameController.getAvatarPlayer().stopCountdown();
                GameController.getClock_1().stop();
                GameController.getAvatarBot().startCountdown();
                GameController.getClock_2().resume();
            }
            GameController.setTurn((GameController.getTurn() > 0) ? GameController.getTurn() - 1 : 1);
            if (!temp.checkMate()) {
                GameController.getSoundEffect().playSoundMove();
                GameController.getChessBoardPanel().hideCheckMate();
            } else {
                GameController.getSoundEffect().playSoundCheckMate();
                GameController.getChessBoardPanel().setCheckMate(temp.getCOLOR());
            }
            this.typeClick = -1;
            if (!Ai.isHasValidMoves(check, Piece.RED)) {
                GameController.getChessBoardPanel().setFullTime();
                GameController.getAvatarBot().stopCountdown();
                GameController.getAvatarPlayer().stopCountdown();
                GameController.getClock_2().stop();
                GameController.getClock_1().stop();
                GameController.getChessBoardPanel().getNotificationPanel().showNotification("Hết cờ, Bạn thua!");
            }
        }).start();

    }

    public void move(ChessPiece temp, int targetX, int targetY) {
        final int durationMs = 400;
        final int frameDelay = 1000 / 120;

        int lX = temp.getLocateX();
        int lY = temp.getLocateY();
        final int startX = temp.getX();
        final int startY = temp.getY();
        final int deltaX = targetX - startX;
        final int deltaY = targetY - startY;

        final long startTime = System.currentTimeMillis();

        Timer timer = new Timer(frameDelay, null);
        timer.addActionListener(e -> {
            long currentTime = System.currentTimeMillis();
            long elapsed = currentTime - startTime;

            if (elapsed >= durationMs) {
                temp.setLocation(targetX, targetY);
                ((Timer) e.getSource()).stop();
                temp.highlight();
                GameController.getChessBoardPanel().highlight(lX, lY);
                return;
            }

            double progress = (double) elapsed / durationMs;

            int currentX = (int) Math.round(startX + deltaX * progress);
            int currentY = (int) Math.round(startY + deltaY * progress);

            temp.setLocation(currentX, currentY);
        });

        timer.start();
    }


}
