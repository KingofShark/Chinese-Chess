package view;

import controller.Notification;
import model.ChessPiece;
import constant.Piece;
import controller.StaticPieces;
import file.IOFile;
import image.NewImage;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

public class ChessBoard extends JPanel implements Piece {
    private JLabel timer_2, timer_1;
    private JButton back;
    private JLayeredPane layeredMe, layeredBot;
    private JButton returnButton;
    private JButton surrender;
    private JButton play;
    private int highlightX = -1;
    private int highlightY = -1;
    private Notification notificationPanel;

    public ChessBoard() {
        this.setup();
    }

    public Notification getNotificationPanel() {
        return notificationPanel;
    }

    private void setup() {
        StaticPieces.getNotice_1().setSize(200, 200);
        StaticPieces.getNotice_2().setSize(200, 200);
        StaticPieces.getNotice_1().setLocation(CELL_SIZE, 3 * CELL_SIZE);
        StaticPieces.getNotice_2().setLocation(PADDING + 11 * CELL_SIZE - CELL_SIZE / 2, 3 * CELL_SIZE);
        StaticPieces.changeImage("wait", 1);
        StaticPieces.changeImage("wait", 2);

        this.back = new JButton();
        this.back.setSize(35, 35);
        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/menu/back.png");
        image = new NewImage().resizeImage(image, 35, 35);
        this.back.setIcon(image);
        this.back.setLocation(PADDING + CELL_SIZE * 13 + CELL_SIZE / 2, CELL_SIZE * 5 + CELL_SIZE / 4);
        this.back.setRolloverEnabled(false);
        this.back.setBorderPainted(false);
        this.back.setContentAreaFilled(false);

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
        StaticPieces.getClock_1().start(this.timer_1, () -> {
            setFullTime();
        });
        StaticPieces.getClock_2().start(this.timer_2, () -> {
            setFullTime();
        });
        this.setLayout(null);
    }

    public void setFullTime() {
        ImageIcon imageIcon;
        imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/replay.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
        this.play.setIcon(imageIcon);
        this.play.setVisible(true);
    }

    public void setNew(JButton button) {
        this.setTime(0, StaticPieces.getMinute(), StaticPieces.getSecond());
        this.setTime(1, StaticPieces.getMinute(), StaticPieces.getSecond());
        this.setButton(button);
        this.backLastMove(button);
        this.play(button);
        this.setVisible(true);
    }

    public void removePieces() {
        for (ChessPiece piece : StaticPieces.getPieces())
            piece.resetDefauft();
        this.removeAll();
        this.add(this.layeredMe);
        this.add(this.layeredBot);
        this.add(this.back);
        this.add(this.notificationPanel);
        this.add(StaticPieces.getNotice_1());
        this.add(StaticPieces.getNotice_2());
    }

    public void play(JButton button) {
        StaticPieces.getCloseButton().setClose(this);
        StaticPieces.getCloseButton().setHide(this);
        button.addActionListener(e -> {
            clickPlay(button);
        });
    }

    private void clickPlay(JButton button) {
        button.setVisible(false);
        if (StaticPieces.getClock_2().getFullTime() || StaticPieces.getClock_1().getFullTime()) {
            notificationPanel.Loading("Vui lòng đợi...");
            int turn = StaticPieces.getTurn();

            new Thread(() -> {
                StaticPieces.setNew(button);
                StaticPieces.setTurn(turn + 1);
                notificationPanel.setVisible(false);

                System.out.println("turn = " + StaticPieces.getTurn());
                String message = (StaticPieces.getTurn() % 2 == 1) ? "Đen đi trước" : "Đỏ đi trước";
                showMessage(message);
            }).start();
        } else if (StaticPieces.getTurn() != -1) {
            System.out.println("turn = " + StaticPieces.getTurn());
            String message = (StaticPieces.getTurn() % 2 == 1) ? "Đen đi trước" : "Đỏ đi trước";
            showMessage(message);
        }
    }

    private void showMessage(String message) {
        notificationPanel.showNotification(message, () -> {
            if (StaticPieces.getTurn() % 2 == Piece.BLACK) {
                StaticPieces.getEvent().setMachine();
                StaticPieces.getClock_2().resume();
                StaticPieces.getNotice_2().startCountdown();
            } else {
                StaticPieces.getNotice_1().startCountdown();
                StaticPieces.getClock_1().resume();
            }

            new Thread(() -> {
                StaticPieces.getSoundEffect().playSounEffect("start_end");
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


        StaticPieces.getNotice_1().setVisible(true);
        StaticPieces.getNotice_2().setVisible(true);
        this.add(StaticPieces.getNotice_2());
        this.add(StaticPieces.getNotice_1());

        this.add(this.play);
        this.add(this.returnButton);
        this.add(this.surrender);

        clickSurrender();
    }

    private void clickSurrender() {
        this.surrender.addActionListener(e -> {
            if (this.play.isVisible())
                return;
            notificationPanel.showNotification("Bạn chắc chắn muốn đầu hàng?", new Notification.Handlers() {
                @Override
                public void onPositive() {
                    notificationPanel.showNotification("Bạn thua!", () -> {
                        setFullTime();
                        StaticPieces.getClock_1().setFullTime(true);
                        StaticPieces.getClock_2().stop();
                    });

                }

                @Override
                public void onNegative() {
                    System.out.println("Hủy đầu hàng");
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        highlight(g);
    }

    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + "/resource/image/background.png");
        Image bg = image.getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);


        g.setColor(new Color(225, 187, 138));
        g.fillRect(PADDING + Piece.CELL_SIZE / 2 - 13, Piece.CELL_SIZE / 4,
                9 * Piece.CELL_SIZE + CELL_SIZE / 2 - 8, 11 * Piece.CELL_SIZE - 15);
        g.setColor(Color.BLACK);

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


        stroke = new BasicStroke(2.0f);
        g2d.setStroke(stroke);

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

        //
        stroke = new BasicStroke(5);
        g2d.setStroke(stroke);
        g2d.setColor(Color.BLACK);

        double x = PADDING + (double) Piece.CELL_SIZE / 2 - 13;
        double y = (double) CELL_SIZE / 4;
        double width = 9 * Piece.CELL_SIZE + (double) CELL_SIZE / 2 - 8;
        double height = 11 * Piece.CELL_SIZE - 15;

        RoundRectangle2D roundedBorder = new RoundRectangle2D.Double(x, y, width, height, 0, 0);
        g2d.draw(roundedBorder);

    }

    private void highlight(Graphics g) {
        if (highlightX != -1 && highlightY != -1 && highlightX < 10 && highlightY < 11) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.GREEN);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(4));

            int cornerLength = 8;
            int quarterCell = CELL_SIZE / 4;

            int centerX = PADDING + highlightX * CELL_SIZE;
            int centerY = highlightY * CELL_SIZE;

            int[][] corners = {
                    {-quarterCell, -quarterCell}, // top-left
                    {+quarterCell, -quarterCell}, // top-right
                    {+quarterCell, +quarterCell}, // bottom-right
                    {-quarterCell, +quarterCell}  // bottom-left
            };

            for (int[] offset : corners) {
                int cx = centerX + offset[0];
                int cy = centerY + offset[1];

                int xDir = (offset[0] > 0) ? -1 : 1;
                int yDir = (offset[1] > 0) ? -1 : 1;

                g2.drawLine(cx, cy, cx + xDir * cornerLength, cy); // ngang
                g2.drawLine(cx, cy, cx, cy + yDir * cornerLength); // dọc
            }
            highlightX = -1;
            highlightY = -1;
        }
    }

    private void backLastMove(JButton button) {
        this.back.addActionListener(e -> {
            if (IOFile.isEmpty(System.getProperty("user.dir") + "/resource/file/lastmove.txt"))
                return;
            StaticPieces.getEvent().hideButton();
            StaticPieces.getClock_1().stop();
            StaticPieces.getClock_2().stop();
            StaticPieces.changeImage("", 0);
            ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/start.png");
            imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
            button.setIcon(imageIcon);
            Vector<Integer> last = IOFile.loadLastMove();
            int locateX = last.elementAt(1);
            int locateY = last.elementAt(2);
            int x = StaticPieces.getPieces().elementAt(last.firstElement()).getLocateX();
            int y = StaticPieces.getPieces().elementAt(last.firstElement()).getLocateY();
            StaticPieces.getCheck().setPiece(x, y, last.elementAt(3));
            StaticPieces.getPieces().elementAt(last.firstElement()).setLocate(locateX, locateY);
            StaticPieces.getCheck().setPiece(last.elementAt(1), last.elementAt(2), last.firstElement());
            StaticPieces.setTurn(last.lastElement());
            if (last.elementAt(3) != -1)
                StaticPieces.getPieces().elementAt(last.elementAt(3)).setVisible(true);
        });
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
                System.out.println("isBack[0] = " + isBack[0]);
                if (isBack[0]) {
                    backHome(home);
                }
            }).start();

        });
    }

    private void backHome(Home home) {
        notificationPanel.Loading("Vui lòng đợi...");
        StaticPieces.getClock_1().setFullTime(true);
        StaticPieces.getClock_2().setFullTime(true);
        if (!this.play.isVisible())
            file.IOFile.saveGame();
        home.setButton();
        for (ChessPiece piece : StaticPieces.getPieces())
            piece.setImage();
        StaticPieces.getChessBoardPanel().removePieces();
        StaticPieces.getChessBoardPanel().setVisible(false);

        StaticPieces.getNotice_2().stopCountdown();
        StaticPieces.getNotice_1().stopCountdown();

        StaticPieces.getCloseButton().setClose(home.getMenu());
        StaticPieces.getCloseButton().setHide(home.getMenu());
        home.getMenu().setVisible(true);
        home.setSize(500, 700);
        home.setLocationRelativeTo(null);
        notificationPanel.setVisible(false);
    }

    public void highlight(int x, int y) {
        highlightX = x + 1;
        highlightY = y + 1;

        this.repaint();
    }
}

