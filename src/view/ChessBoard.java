package view;

import controller.Notification;
import model.ChessPiece;
import constant.Piece;
import controller.GameController;
import file.IOFile;
import image.NewImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

public class ChessBoard extends JPanel implements Piece {
    private JLabel highlight;
    private JLabel timer_2, timer_1, warningMe, warningBot;
    private JButton back;
    private JLayeredPane layeredMe, layeredBot;
    private JButton returnButton;
    private JButton surrender;
    private JButton play;
    private int highlightX = -1;
    private int highlightY = -1;
    private Notification notificationPanel;
    private boolean issurrend, isReplay;

    public ChessBoard() {
        this.setup();
    }

    public Notification getNotificationPanel() {
        return notificationPanel;
    }

    private void setup() {
        GameController.getAvatarPlayer().setSize(200, 200);
        GameController.getAvatarBot().setSize(200, 200);
        GameController.getAvatarPlayer().setLocation(CELL_SIZE, 3 * CELL_SIZE);
        GameController.getAvatarBot().setLocation(PADDING + 11 * CELL_SIZE - CELL_SIZE / 2, 3 * CELL_SIZE);

        this.back = new JButton();
        this.back.setSize(35, 35);
        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/menu/back.png");
        image = new NewImage().resizeImage(image, 35, 35);
        this.back.setIcon(image);
        this.back.setLocation(PADDING + CELL_SIZE * 13 + CELL_SIZE / 2, CELL_SIZE * 5 + CELL_SIZE / 4);
        this.back.setRolloverEnabled(false);
        this.back.setBorderPainted(false);
        this.back.setContentAreaFilled(false);

        this.highlight = new JLabel();
        this.highlight.setSize(35, 35);
        image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/highlight.png");
        image = new NewImage().resizeImage(image, 35, 35);
        this.highlight.setIcon(image);
        this.highlight.setVisible(false);
        add(this.highlight);

        this.warningBot = new JLabel();
        this.warningBot.setSize(3 * CELL_SIZE, 3 * CELL_SIZE / 2);
        this.warningBot.setLocation(14 * CELL_SIZE + 35, 8 * CELL_SIZE);
        image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/effect/checkmate.png");
        image = new NewImage().resizeImage(image, 3 * CELL_SIZE, 3 * CELL_SIZE / 2);
        this.warningBot.setIcon(image);
        this.warningBot.setVisible(false);
        add(this.warningBot);

        this.warningMe = new JLabel();
        this.warningMe.setSize(3 * CELL_SIZE, 3 * CELL_SIZE / 2);
        this.warningMe.setLocation(CELL_SIZE - 25, 8 * CELL_SIZE);
        image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/effect/checkmate.png");
        image = new NewImage().resizeImage(image, 3 * CELL_SIZE, 3 * CELL_SIZE / 2);
        this.warningMe.setIcon(image);
        this.warningMe.setVisible(false);
        add(this.warningMe);

        this.timer_2 = new JLabel();
        this.timer_1 = new JLabel();
        this.timer_1.setLocation(30, 5);
        this.timer_2.setLocation(30, 5);
        this.timer_1.setHorizontalAlignment(SwingConstants.CENTER);
        this.timer_1.setVerticalAlignment(SwingConstants.CENTER);
        this.timer_1.setOpaque(true);
        this.timer_1.setForeground(new Color(244, 192, 21));

        this.timer_2.setHorizontalAlignment(SwingConstants.CENTER);
        this.timer_2.setVerticalAlignment(SwingConstants.CENTER);
        this.timer_2.setOpaque(true);
        this.timer_2.setForeground(new Color(244, 192, 21));

        this.setBackground(new Color(192, 187, 187));
        this.setSize(_width_, _height_);


        layeredMe = new JLayeredPane();
        layeredMe.setBounds(CELL_SIZE + 20, CELL_SIZE * 3 + 217, 130, 40);
        this.add(layeredMe);

        JButton me = new JButton();
        me.setSize(40, 40);
        image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/piece/0/General.png");
        image = new NewImage().resizeImage(image, 40, 40);
        me.setIcon(image);
        me.setLocation(0, 0);
        me.setBorderPainted(false);
        me.setContentAreaFilled(false);

        layeredMe.add(this.timer_1, JLayeredPane.DEFAULT_LAYER);
        layeredMe.add(me, JLayeredPane.POPUP_LAYER);

        layeredBot = new JLayeredPane();
        layeredBot.setBounds(PADDING + 11 * CELL_SIZE - CELL_SIZE / 2 + 20, CELL_SIZE * 3 + 217, 130, 40);
        this.add(layeredBot);

        JButton bot = new JButton();
        bot.setSize(40, 40);
        image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/piece/1/General.png");
        image = new NewImage().resizeImage(image, 40, 40);
        bot.setIcon(image);
        bot.setLocation(0, 0);
        bot.setBorderPainted(false);
        bot.setContentAreaFilled(false);

        layeredBot.add(this.timer_2, JLayeredPane.DEFAULT_LAYER);
        layeredBot.add(bot, JLayeredPane.POPUP_LAYER);


        notificationPanel = new Notification();
        notificationPanel.setLocation((this.getWidth() - notificationPanel.getWidth()) / 2,
                (this.getHeight() - notificationPanel.getHeight()) / 2);
        this.add(notificationPanel);

        this.add(this.back);
        GameController.getClock_1().start(this.timer_1, () -> {
            setFullTime();
        });
        GameController.getClock_2().start(this.timer_2, () -> {
            setFullTime();
        });
        this.setLayout(null);
    }

    public void setFullTime() {
        if (!GameController.getClock_1().isFullTime() && !GameController.getClock_2().isFullTime())
            return;
        ImageIcon imageIcon;
        imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/replay.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
        this.play.setIcon(imageIcon);
        this.play.setVisible(true);
    }

    public void setNew(JButton button) {
        this.issurrend = false;
        this.setTime(0, GameController.getMinute(), GameController.getSecond());
        this.setTime(1, GameController.getMinute(), GameController.getSecond());
        this.setButton(button);
        this.backLastMove(button);
        this.play();
        this.setVisible(true);
    }

    public void removePieces() {
        for (ChessPiece piece : GameController.getPieces())
            piece.resetDefauft();
        this.removeAll();
        this.add(this.layeredMe);
        this.add(this.layeredBot);
        this.add(this.back);
        this.add(this.notificationPanel);
        this.add(highlight);
        this.add(this.warningMe);
        this.add(this.warningBot);
        this.add(GameController.getAvatarPlayer());
        this.add(GameController.getAvatarBot());
    }

    public void play() {
        GameController.getCloseButton().setClose(this);
        GameController.getCloseButton().setHide(this);
        this.play.addActionListener(e -> {
            this.play.setVisible(false);
            clickPlay();
        });
    }

    private void clickPlay() {
        if (this.issurrend) {
            notificationPanel.Loading("Vui lòng đợi...");
            int turn = GameController.getTurn();

            new Thread(() -> {
                this.highlight.setVisible(false);
                Vector<ChessPiece> pieces = GameController.setupPieces();
                for (int i = 0; i < pieces.size(); i++) {
                    ChessPiece piece = pieces.elementAt(i);
                    piece.setLocate(piece.getLocateX(), piece.getLocateY());
                    GameController.getPieces().elementAt(i).setVisible(true);
                    GameController.getPieces().elementAt(i).updateLocate(piece);
                    int X = PADDING + (piece.getLocateX() + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
                    int Y = (piece.getLocateY() + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2;
                    GameController.getEvent().move(GameController.getPieces().elementAt(i), X, Y);
                }
                GameController.getClock_1().setTime(GameController.getMinute(), GameController.getSecond());
                GameController.getClock_2().setTime(GameController.getMinute(), GameController.getSecond());
                GameController.setTurn(turn + 1);
                GameController.setEvent();


                for (ChessPiece piece : GameController.getPieces()) {
                    piece.setImage();
                }
                notificationPanel.setVisible(false);

                this.issurrend = false;
                String message = (GameController.getTurn() % 2 == BLACK) ? "Đen đi trước" : "Đỏ đi trước";
                showMessage(message);
            }).start();
        } else if (GameController.getTurn() != -1) {
            String message = (GameController.getTurn() % 2 == 1) ? "Đen đi trước" : "Đỏ đi trước";
            showMessage(message);
        }
    }

    private void showMessage(String message) {
        notificationPanel.showNotification(message, () -> {
            GameController.getClock_1().setFullTime(false);
            GameController.getClock_2().setFullTime(false);
            if (GameController.getTurn() % 2 == Piece.BLACK) {
                GameController.getClock_2().resume();
                GameController.getAvatarBot().startCountdown();
                GameController.getEvent().setMachine();
            } else {
                GameController.getClock_1().resume();
                GameController.getAvatarPlayer().startCountdown();
            }
            System.out.println("Clock 1: " + GameController.getClock_1().isFullTime());
            System.out.println("Clock 2: " + GameController.getClock_2().isFullTime());
            this.play.setVisible(false);

            new Thread(() -> {
                GameController.getSoundEffect().playSounEffect("start_end");
            }).start();
        });
    }

    public void setButton(JButton button) {
        this.play = button;
        this.play.setSize(90, 32);
        this.play.setLocation(PADDING + CELL_SIZE * 4 + CELL_SIZE / 2 - 10, CELL_SIZE * 5 + CELL_SIZE / 4);
        ImageIcon imageIcon;
        imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/start.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
        this.play.setIcon(imageIcon);
        this.play.setBorderPainted(false);

        this.returnButton = new JButton();
        this.returnButton.setSize(35, 35);
        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/menu/back_.png");
        image = new NewImage().resizeImage(image, 35, 35);
        this.returnButton.setIcon(image);
        this.returnButton.setLocation(CELL_SIZE / 2, CELL_SIZE / 2);
        this.returnButton.setBorderPainted(false);
        this.returnButton.setContentAreaFilled(false);

        this.surrender = new JButton();
        this.surrender.setSize(76, 30);

        image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/menu/surrender.png");
        image = new NewImage().resizeImage(image, 76, 30);
        this.surrender.setIcon(image);
        this.surrender.setLocation(CELL_SIZE + 62, 3 * CELL_SIZE + 270);
        this.surrender.setBorderPainted(false);
        this.surrender.setContentAreaFilled(false);


        GameController.getAvatarPlayer().setVisible(true);
        GameController.getAvatarBot().setVisible(true);
        this.add(GameController.getAvatarBot());
        this.add(GameController.getAvatarPlayer());

        this.add(this.play);
        this.add(this.returnButton);
        this.add(this.surrender);

        clickSurrender();
    }

    private void clickSurrender() {
        this.surrender.addActionListener(e -> {
            if (this.play.isVisible() || GameController.getClock_2().isFullTime() || GameController.getClock_1().isFullTime())
                return;

            notificationPanel.showNotification("Bạn chắc chắn muốn đầu hàng?", new Notification.Handlers() {
                @Override
                public void onPositive() {
                    issurrend = true;
                    GameController.getEvent().hideButton();
                    GameController.getClock_1().stop();
                    GameController.getClock_2().stop();
                    GameController.getClock_1().setFullTime(true);
                    GameController.getClock_2().setFullTime(true);
                    setFullTime();
                }

                @Override
                public void onNegative() {
                }
            });
        });
    }

    public void setTime(int type, int minute, int second) {
        if (type == 0) {
            this.timer_1.setSize(100, 30);
            this.timer_1.setBorder(BorderFactory.createLineBorder(new Color(244, 192, 21), 2));
            this.timer_1.setBackground(new Color(140, 41, 24));
            this.timer_1.setFont(new Font("Arial", Font.BOLD, 20));
            String _minute = minute > 9 ? "" + minute : "0" + minute;
            String _second = second > 9 ? "" + second : "0" + second;
            this.timer_1.setText(_minute + " : " + _second);
        } else {
            this.timer_2.setSize(100, 30);
            this.timer_2.setBorder(BorderFactory.createLineBorder(new Color(244, 192, 21), 2));
            this.timer_2.setBackground(new Color(140, 41, 24));
            this.timer_2.setFont(new Font("Arial", Font.BOLD, 20));
            String _minute = minute > 9 ? "" + minute : "0" + minute;
            String _second = second > 9 ? "" + second : "0" + second;
            this.timer_2.setText(_minute + " : " + _second);
        }
    }

    public void goHome(Home home) {
        returnButton.addActionListener(_ -> {
            final boolean[] isBack = {false, false};
            notificationPanel.showNotification("Bạn có chắc chắn muốn về trang chủ?", new Notification.Handlers() {
                @Override
                public void onPositive() {
                    isBack[0] = true;
                    isBack[1] = true;
                }

                @Override
                public void onNegative() {
                    isBack[1] = true;
                }
            });
            new Thread(() -> {
                while (!isBack[0] && !isBack[1]) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (isBack[0]) {
                    backHome(home);
                }
            }).start();

        });
    }

    private void backHome(Home home) {
        notificationPanel.Loading("Vui lòng đợi...");
        GameController.getClock_1().setFullTime(true);
        GameController.getClock_2().setFullTime(true);
        if (!this.play.isVisible())
            file.IOFile.saveGame();
        home.setButton();
        for (ChessPiece piece : GameController.getPieces())
            piece.setImage();
        removePieces();
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        warningMe.setVisible(false);
        warningBot.setVisible(false);
        highlight.setVisible(false);
        setVisible(false);

        GameController.getAvatarBot().stopCountdown();
        GameController.getAvatarPlayer().stopCountdown();

        GameController.getCloseButton().setClose(home.getMenu());
        GameController.getCloseButton().setHide(home.getMenu());
        home.getMenu().setVisible(true);
        home.setSize(500, 700);
        home.setLocationRelativeTo(null);
        notificationPanel.setVisible(false);
    }

    public void setCheckMate(int player){
        if (player == Piece.RED) {
            this.warningMe.setVisible(true);
            this.warningBot.setVisible(false);
        } else {
            this.warningMe.setVisible(false);
            this.warningBot.setVisible(true);
        }
    }

    public void hideCheckMate(){
        this.warningMe.setVisible(false);
        this.warningBot.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFullBoard(g);
    }

    private void drawFullBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/background.png");
        Image bg = image.getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

        Stroke stroke = new BasicStroke(2.0f);
        g2d.setStroke(stroke);

        drawBoard(g2d, g);
        drawCanonPoint(g2d);
        drawGeneralPalace(g2d);
    }

    private void drawBoard(Graphics2D g2d, Graphics g) {
        g.setColor(new Color(225, 187, 138));
        g.fillRect(PADDING + Piece.CELL_SIZE / 2 - 13, Piece.CELL_SIZE / 4,
                9 * Piece.CELL_SIZE + CELL_SIZE / 2 - 8, 11 * Piece.CELL_SIZE - 15);
        g.setColor(Color.BLACK);

        Line2D line;
        // vẽ bàn cờ
        for (int i = 1; i <= 10; i++) {
            line = new Line2D.Double(PADDING + CELL_SIZE, i * CELL_SIZE, PADDING + 9 * CELL_SIZE, i * CELL_SIZE);
            g2d.draw(line);
        }
        line = new Line2D.Double(PADDING + CELL_SIZE, CELL_SIZE, PADDING + CELL_SIZE, 10 * CELL_SIZE);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + 9 * CELL_SIZE, CELL_SIZE, PADDING + 9 * CELL_SIZE, 10 * CELL_SIZE);
        g2d.draw(line);
        for (int i = 2; i <= 8; i++) {
            line = new Line2D.Double(PADDING + i * CELL_SIZE, CELL_SIZE, PADDING + i * CELL_SIZE, 5 * CELL_SIZE);
            g2d.draw(line);
            line = new Line2D.Double(PADDING + i * CELL_SIZE, 6 * CELL_SIZE, PADDING + i * CELL_SIZE, 10 * CELL_SIZE);
            g2d.draw(line);
        }

        Stroke stroke = new BasicStroke(5);
        g2d.setStroke(stroke);
        g2d.setColor(Color.BLACK);

        double x = PADDING + (double) Piece.CELL_SIZE / 2 - 13;
        double y = (double) CELL_SIZE / 4;
        double width = 9 * Piece.CELL_SIZE + (double) CELL_SIZE / 2 - 8;
        double height = 11 * Piece.CELL_SIZE - 15;

        RoundRectangle2D roundedBorder = new RoundRectangle2D.Double(x, y, width, height, 0, 0);
        g2d.draw(roundedBorder);
    }

    private void drawGeneralPalace(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2.0f));
        Line2D line;

        // ô tướng
        line = new Line2D.Double(PADDING + 4 * CELL_SIZE, CELL_SIZE + 1, PADDING + 6 * CELL_SIZE, 3 * CELL_SIZE);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + 4 * CELL_SIZE, 3 * CELL_SIZE, PADDING + 6 * CELL_SIZE, CELL_SIZE + 1);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + 4 * CELL_SIZE, 8 * CELL_SIZE, PADDING + 6 * CELL_SIZE, 10 * CELL_SIZE - 1);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + 4 * CELL_SIZE, 10 * CELL_SIZE - 1, PADDING + 6 * CELL_SIZE, 8 * CELL_SIZE);
        g2d.draw(line);
        // sông
        line = new Line2D.Double(PADDING + CELL_SIZE, 5 * CELL_SIZE + 1, PADDING + 5 * CELL_SIZE, 6 * CELL_SIZE - 1);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + CELL_SIZE, 6 * CELL_SIZE + 1, PADDING + 5 * CELL_SIZE, 5 * CELL_SIZE - 1);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + 5 * CELL_SIZE, 5 * CELL_SIZE, PADDING + 9 * CELL_SIZE, 6 * CELL_SIZE - 1);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + 5 * CELL_SIZE, 6 * CELL_SIZE, PADDING + 9 * CELL_SIZE, 5 * CELL_SIZE - 1);
        g2d.draw(line);
    }

    private void drawCanonPoint(Graphics2D g2d) {
        Stroke stroke = new BasicStroke(4.0f);
        g2d.setStroke(stroke);
        Line2D line;
        // vị trí quân pháo
        line = new Line2D.Double(PADDING + 2 * CELL_SIZE - 5, 3 * CELL_SIZE - 5, PADDING + 2 * CELL_SIZE + 5, 3 * CELL_SIZE + 5);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + 2 * CELL_SIZE - 5, 3 * CELL_SIZE + 5, PADDING + 2 * CELL_SIZE + 5, 3 * CELL_SIZE - 5);
        g2d.draw(line);

        line = new Line2D.Double(PADDING + 8 * CELL_SIZE - 5, 3 * CELL_SIZE - 5, PADDING + 8 * CELL_SIZE + 5, 3 * CELL_SIZE + 5);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + 8 * CELL_SIZE - 5, 3 * CELL_SIZE + 5, PADDING + 8 * CELL_SIZE + 5, 3 * CELL_SIZE - 5);
        g2d.draw(line);

        line = new Line2D.Double(PADDING + 2 * CELL_SIZE - 5, 8 * CELL_SIZE - 5, PADDING + 2 * CELL_SIZE + 5, 8 * CELL_SIZE + 5);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + 2 * CELL_SIZE - 5, 8 * CELL_SIZE + 5, PADDING + 2 * CELL_SIZE + 5, 8 * CELL_SIZE - 5);
        g2d.draw(line);

        line = new Line2D.Double(PADDING + 8 * CELL_SIZE - 5, 8 * CELL_SIZE - 5, PADDING + 8 * CELL_SIZE + 5, 8 * CELL_SIZE + 5);
        g2d.draw(line);
        line = new Line2D.Double(PADDING + 8 * CELL_SIZE - 5, 8 * CELL_SIZE + 5, PADDING + 8 * CELL_SIZE + 5, 8 * CELL_SIZE - 5);
        g2d.draw(line);
    }

    private void backLastMove(JButton button) {
        this.back.addActionListener(e -> {
            if (IOFile.isEmpty(System.getProperty("user.dir") + "/resource/file/lastmove.txt"))
                return;
            GameController.getEvent().hideButton();
            GameController.getClock_1().stop();
            GameController.getClock_2().stop();
            ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/start.png");
            imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
            button.setIcon(imageIcon);
            Vector<Integer> last = IOFile.loadLastMove();
            int locateX = last.elementAt(1);
            int locateY = last.elementAt(2);
            int x = GameController.getPieces().elementAt(last.firstElement()).getLocateX();
            int y = GameController.getPieces().elementAt(last.firstElement()).getLocateY();
            GameController.getCheck().setPiece(x, y, last.elementAt(3));
            GameController.getPieces().elementAt(last.firstElement()).setLocate(locateX, locateY);
            GameController.getCheck().setPiece(last.elementAt(1), last.elementAt(2), last.firstElement());
            GameController.setTurn(last.lastElement());
            if (last.elementAt(3) != -1)
                GameController.getPieces().elementAt(last.elementAt(3)).setVisible(true);
        });
    }

    public void highlight(int x, int y) {
        highlightX = x + 1;
        highlightY = y + 1;

        int X = PADDING + (highlightX) * CELL_SIZE - 35 / 2;
        int Y = (highlightY) * CELL_SIZE - 35 / 2;
        highlight.setLocation(X, Y);
        highlight.setVisible(true);
    }
}

