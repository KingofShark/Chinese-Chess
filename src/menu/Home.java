package menu;

import chesspiece.Piece;
import chesspiece.StaticPieces;
import file.IOFile;
import image.NewImage;
import game.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Home extends JFrame {
    private final JPanel menu;
    private final JButton newGame;
    private final JButton oldGame;
    private final JButton quit;
    private final JButton exit;
    private final JButton setting;
    public Home() {
        long start = System.currentTimeMillis();
        StaticPieces.getSoundEffect().playBackgroundMusic();
        StaticPieces.setup();
        StaticPieces.setMinute(15);
        StaticPieces.setSecond(0);
        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/logo.jpg")).getPath());
        Image temp = logo.getImage();
        temp = temp.getScaledInstance(210, 280, Image.SCALE_SMOOTH);
        logo = new ImageIcon(temp);

        this.setUndecorated(true);
        this.setResizable(false);
        this.setIconImage(logo.getImage());
        this.setSize(500, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.menu = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/home.jpg");
                g.drawImage(imageIcon.getImage(), 0, 0, 500, 700, this);
            }
        };
        this.menu.setLayout(null);
        this.menu.setSize(500, 700);
        this.setting = new JButton();
        this.setting.setName("setting");
        this.newGame = new JButton();
        this.newGame.setName("newGame");
        this.oldGame = new JButton();
        this.oldGame.setName("oldGame");
        this.quit = new JButton();
        this.quit.setName("quit");
        this.exit = new JButton();
        this.setExit(this.menu);
        this.setButton();
        StaticPieces.getCloseButton().setClose(this.menu);
        StaticPieces.getCloseButton().setHide(this.menu);

        this.menu.add(this.newGame);
        this.menu.add(this.oldGame);
        this.menu.add(this.setting);
        this.menu.add(this.quit);

        JLabel label = new JLabel();
        label.setText("Cờ Tướng");
        label.setFont((new Font(Font.SANS_SERIF, Font.PLAIN, 30)));
        this.add(StaticPieces.getChessBoardPanel());
        StaticPieces.getChessBoardPanel().setVisible(false);
        this.menu.add(label);
        this.add(this.menu);
        this.setVisible(true);
        this.setEvent();
        long end = System.currentTimeMillis();
        System.out.println("Time start: " + (end - start));
    }

    public void setOption(JButton button) {
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/menu/" + button.getName() + ".png");
        imageIcon = new NewImage().resizeImage(imageIcon, 100, 36);
        button.setIcon(imageIcon);
        button.setContentAreaFilled(false);
        if (button.getName().equals("newGame"))
            button.setBounds(200, 350, 100, 36);
        if (IOFile.checkFile(System.getProperty("user.dir") + "/src/file/old.txt")) {
            if (button.getName().equals("oldGame"))
                button.setBounds(200, 400, 100, 36);
            if (button.getName().equals("setting"))
                button.setBounds(200, 450, 100, 36);
            if (button.getName().equals("quit"))
                button.setBounds(200, 500, 100, 36);
        } else {
            if (button.getName().equals("oldGame"))
                return;
            if (button.getName().equals("setting"))
                button.setBounds(200, 400, 100, 36);
            if (button.getName().equals("quit"))
                button.setBounds(200, 450, 100, 36);
        }
        button.setBorderPainted(false);
    }
    public void setButton(){
        this.setOption(this.newGame);
        this.setOption(this.oldGame);
        this.setOption(this.setting);
        this.setOption(this.quit);
    }

    public void setExit(JPanel panel) {
        this.exit.setSize(50, 50);
        this.exit.setLocation(50, 250);
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/option/exit.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 50, 50);
        this.exit.setIcon(imageIcon);
        this.exit.setBorderPainted(false);
        this.exit.setContentAreaFilled(false);
        this.exit.setFocusPainted(false);
        this.exit.setVisible(false);
        panel.add(this.exit);
    }

    public void setNewGame() {
        this.newGame.addActionListener(e -> {
            long startt = System.currentTimeMillis();
            this.menu.setVisible(false);
            this.setSize(Piece._width_, Piece._height_);
            this.setLocationRelativeTo(null);
            new Event();
            JButton start = new JButton();
            StaticPieces.setNew(start);
            StaticPieces.getSetting().setChessBoard(this, start);
            long end = System.currentTimeMillis();
            System.out.println("Time start: " + (end - startt));
        });
    }
    public void setOldGame(){
        this.oldGame.addActionListener(e -> {
            this.menu.setVisible(false);
            this.setSize(Piece._width_, Piece._height_);
            this.setLocationRelativeTo(null);
            IOFile.readGame();
            JButton start = new JButton();
            new Event();
            StaticPieces.setNewSetting();
            StaticPieces.getSetting().setChessBoard(this, start);
            StaticPieces.getChessBoardPanel().setButton(start);
            StaticPieces.getChessBoardPanel().play(start);
            StaticPieces.getChessBoardPanel().setVisible(true);
        });
    }
    public void setQuit(){
        this.quit.addActionListener(e -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
    }

    public void setEvent() {
        this.setNewGame();
        this.setQuit();
        this.setOldGame();
        this.closeFrame();
        this.hideFrame();
    }
    public void closeFrame(){StaticPieces.getCloseButton().getClose().addActionListener(e ->this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));}
    public void hideFrame(){
        StaticPieces.getCloseButton().getHide().addActionListener(e -> this.setState(JFrame.ICONIFIED));
    }
    public JPanel getMenu() {
        return this.menu;
    }
}
