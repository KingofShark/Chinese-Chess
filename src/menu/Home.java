package menu;

import chesspiece.Piece;
import chesspiece.StaticPieces;
import file.IOFile;
import game.Event;
import image.NewImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Vector;

import static java.awt.Color.WHITE;
import static java.awt.Color.white;

public class Home extends JFrame {
    private final JPanel menu;
    private final JButton newGame;
    private final JButton oldGame;
    private final JButton quit;
    private final JButton setting;
    private final JButton volume;
    private final JButton backHome;
    private final JButton time;
    private final JSlider slider_1;
    private final JSlider slider_2;
    private final JLabel label_1;
    private final JLabel label_2;
    private final JButton fifteen;
    private final JButton twelve;
    private final JButton ten;
    private int level;

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
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/home.png");
                g.drawImage(imageIcon.getImage(), 0, 0, 500, 700, this);
            }
        };
        this.fifteen = new JButton();
        this.twelve = new JButton();
        this.ten = new JButton();
        this.slider_1 = new JSlider();
        this.slider_2 = new JSlider();
        this.label_1 = new JLabel();
        this.label_2 = new JLabel();
        this.slider_1.setOpaque(false);
        this.slider_2.setOpaque(false);
        this.slider_1.setFocusable(false);
        this.slider_2.setFocusable(false);
        this.slider_1.setUI(new StaticPieces.CustomSliderUI(this.slider_1));
        this.slider_2.setUI(new StaticPieces.CustomSliderUI(this.slider_2));
        this.label_1.setForeground(WHITE);
        this.label_2.setForeground(white);

        this.slider_1.setVisible(false);
        this.label_1.setVisible(false);
        this.slider_2.setVisible(false);
        this.label_2.setVisible(false);

        this.menu.add(this.slider_1);
        this.menu.add(this.slider_2);
        this.menu.add(this.label_1);
        this.menu.add(this.label_2);

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
        this.backHome = new JButton();
        this.volume = new JButton();
        this.time = new JButton();
        this.setSettingHome();
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
        this.level = 0;
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

    public void setButton() {
        this.setOption(this.newGame);
        this.setOption(this.oldGame);
        this.setOption(this.setting);
        this.setOption(this.quit);
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

    public void setOldGame() {
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

    public void setQuit() {
        this.quit.addActionListener(e -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
    }

    public void setClickSetting() {
        this.setting.addActionListener(e -> {
            this.level++;
            this.newGame.setVisible(false);
            this.oldGame.setVisible(false);
            this.setting.setVisible(false);
            this.quit.setVisible(false);

            this.volume.setVisible(true);
            this.backHome.setVisible(true);
            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
        });
    }

    public void setSettingHome() {
        this.level++;
        this.volume.setSize(100, 36);
        this.volume.setLocation(200, 350);
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/option/volume.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 100, 36);
        this.volume.setIcon(imageIcon);
        this.volume.setContentAreaFilled(false);
        this.volume.setBorderPainted(false);
        this.volume.setVisible(false);

        this.time.setSize(100, 36);
        this.time.setLocation(200, 400);
        imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/option/time.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 100, 36);
        this.time.setIcon(imageIcon);
        this.time.setContentAreaFilled(false);
        this.time.setBorderPainted(false);
        this.time.setVisible(false);

        this.backHome.setSize(100, 36);
        this.backHome.setLocation(200, 450);
        imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/option/back2.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 100, 36);
        this.backHome.setIcon(imageIcon);
        this.backHome.setContentAreaFilled(false);
        this.backHome.setBorderPainted(false);
        this.backHome.setVisible(false);

        this.fifteen.setSize(100, 36);
        this.fifteen.setLocation(200, 450);
        imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/time/15.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 100, 36);
        this.fifteen.setIcon(imageIcon);
        this.fifteen.setContentAreaFilled(false);
        this.fifteen.setBorderPainted(false);
        this.fifteen.setVisible(false);

        this.twelve.setSize(100, 36);
        this.twelve.setLocation(200, 400);
        imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/time/12.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 100, 36);
        this.twelve.setIcon(imageIcon);
        this.twelve.setContentAreaFilled(false);
        this.twelve.setBorderPainted(false);
        this.twelve.setVisible(false);

        this.ten.setSize(100, 36);
        this.ten.setLocation(200, 350);
        imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/time/10.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 100, 36);
        this.ten.setIcon(imageIcon);
        this.ten.setContentAreaFilled(false);
        this.ten.setBorderPainted(false);
        this.ten.setVisible(false);

        this.menu.add(this.volume);
        this.menu.add(this.time);
        this.menu.add(this.backHome);

        this.menu.add(this.ten);
        this.menu.add(this.twelve);
        this.menu.add(this.fifteen);
    }

    public void setClickBack() {
        this.backHome.addActionListener(e -> {
            switch (this.level) {
                case 1:
                    this.backHome.setVisible(false);
                    this.time.setVisible(false);
                    this.volume.setVisible(false);

                    this.newGame.setVisible(true);
                    if (IOFile.checkFile(System.getProperty("user.dir") + "/src/file/old.txt"))
                        this.oldGame.setVisible(true);
                    this.quit.setVisible(true);
                    this.setting.setVisible(true);
                    this.level--;
                    break;
                case 2:
                    this.slider_1.setVisible(false);
                    this.label_1.setVisible(false);
                    this.slider_2.setVisible(false);
                    this.label_2.setVisible(false);

                    this.backHome.setLocation(200, 450);
                    this.time.setVisible(true);
                    this.volume.setVisible(true);

                    this.ten.setVisible(false);
                    this.twelve.setVisible(false);
                    this.fifteen.setVisible(false);
                    this.level--;
                    break;
            }
        });
    }

    public void setVolume() {
        this.volume.addActionListener(e -> {
            this.level++;
            Vector<Integer> newVolume = IOFile.getVolume();
            this.slider_1.setMaximum(100);
            this.slider_1.setMinimum(0);
            this.slider_1.setValue(newVolume.elementAt(0));
            this.slider_2.setMaximum(100);
            this.slider_2.setMinimum(0);
            this.slider_2.setValue(newVolume.elementAt(1));
            this.label_1.setText(" Nhạc nền: " + newVolume.elementAt(0));
            this.label_2.setText(" Hiệu ứng: " + newVolume.elementAt(1));
            this.quit.setVisible(false);
            this.volume.setVisible(false);
            this.backHome.setLocation(200, 500);
            this.time.setVisible(false);

            this.slider_1.setBounds(150, 350, 200, 50);
            this.label_1.setBounds(350, 350, 100, 50);
            this.slider_1.setVisible(true);
            this.label_1.setVisible(true);

            slider_2.setBounds(150, 400, 200, 50);
            label_2.setBounds(350, 400, 100, 50);
            slider_2.setVisible(true);
            label_2.setVisible(true);
            this.slider_1.addChangeListener(e1 -> {
                this.label_1.setText(" Nhạc nền: " + this.slider_1.getValue());
                StaticPieces.getSoundEffect().setVolumeSoundTrack(this.slider_1.getValue());
            });

            this.slider_2.addChangeListener(e1 -> {
                this.label_2.setText(" Hiệu ứng: " + this.slider_2.getValue());
                StaticPieces.getSoundEffect().setVolumeSoundTrack(this.slider_2.getValue());
                StaticPieces.getSoundEffect().setVolumeSoundEffect(this.slider_2.getValue());
            });
        });
    }

    public void setTime(){
        this.time.addActionListener(e -> {
            this.level++;
            this.quit.setVisible(false);
            this.volume.setVisible(false);
            this.backHome.setLocation(200, 500);
            this.time.setVisible(false);

            this.ten.setVisible(true);
            this.twelve.setVisible(true);
            this.fifteen.setVisible(true);
        });
    }
    public void chooseFif(){
        this.fifteen.addActionListener(e -> {
            StaticPieces.setMinute(15);
            StaticPieces.setSecond(0);

            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);

            this.ten.setVisible(false);
            this.twelve.setVisible(false);
            this.fifteen.setVisible(false);
            this.level--;
        });
    }
    public void chooseTwel(){
        this.twelve.addActionListener(e -> {
            StaticPieces.setMinute(12);
            StaticPieces.setSecond(0);

            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);

            this.ten.setVisible(false);
            this.twelve.setVisible(false);
            this.fifteen.setVisible(false);
            this.level--;
        });
    }
    public void chooseTen(){
        this.ten.addActionListener(e -> {
            StaticPieces.setMinute(10);
            StaticPieces.setSecond(0);

            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);

            this.ten.setVisible(false);
            this.twelve.setVisible(false);
            this.fifteen.setVisible(false);
            this.level--;
        });
    }

    public void setEvent() {
        this.setNewGame();
        this.setQuit();
        this.setOldGame();
        this.setClickSetting();
        this.closeFrame();
        this.hideFrame();
        this.setClickBack();
        this.setVolume();
        this.setTime();
        this.chooseFif();
        this.chooseTwel();
        this.chooseTen();
    }

    public void closeFrame() {
        StaticPieces.getCloseButton().getClose().addActionListener(e -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
    }

    public void hideFrame() {
        StaticPieces.getCloseButton().getHide().addActionListener(e -> this.setState(JFrame.ICONIFIED));
    }

    public JPanel getMenu() {
        return this.menu;
    }
}
