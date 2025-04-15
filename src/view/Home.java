package view;

import constant.Piece;
import controller.StaticPieces;
import file.IOFile;
import image.NewImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Vector;

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
        new Thread(() -> {
            StaticPieces.getSoundEffect().playBackgroundMusic();
            StaticPieces.setup();
            StaticPieces.setMinute(IOFile.getTime().firstElement());
            StaticPieces.setSecond(IOFile.getTime().lastElement());
            StaticPieces.setSecond(0);
        }).start();

        ImageIcon logo = new ImageIcon(System.getProperty("user.dir") + "/resource/image/logo.jpg");
        Image temp = logo.getImage();
        temp = temp.getScaledInstance(210, 280, Image.SCALE_SMOOTH);
        logo = new ImageIcon(temp);

        this.setUndecorated(true);
        this.setResizable(false);
        this.setIconImage(logo.getImage());
        this.setSize(500, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

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
        this.label_1.setFont(new Font("Arial", Font.BOLD, 15));
        this.label_2.setFont(new Font("Arial", Font.BOLD, 15));

        this.slider_1.setVisible(false);
        this.label_1.setVisible(false);
        this.slider_2.setVisible(false);
        this.label_2.setVisible(false);

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

        this.menu = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/home.png");
                g.drawImage(imageIcon.getImage(), 0, 0, 500, 700, this);
            }
        };

        this.menu.add(this.slider_1);
        this.menu.add(this.slider_2);
        this.menu.add(this.label_1);
        this.menu.add(this.label_2);

        this.menu.setLayout(null);
        this.menu.setSize(500, 700);

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
        this.level = 0;
    }
    private void addHoverEffect(JButton button) {
        ImageIcon defaultIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/menu/" + button.getName() + ".png");
        ImageIcon hoverIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/moved/" + button.getName() + ".png");
        defaultIcon = new NewImage().resizeImage(defaultIcon, 100, 36);
        hoverIcon = new NewImage().resizeImage(hoverIcon, 100, 36);

        button.setIcon(defaultIcon);
        button.setRolloverIcon(hoverIcon);
    }



    private void setOption(JButton button) {
        this.addHoverEffect(button);
        button.setContentAreaFilled(false);
        if (button.getName().equals("newGame"))
            button.setBounds(200, 350, 100, 36);
        if (!IOFile.isEmpty(System.getProperty("user.dir") + "/resource/file/old.txt")) {
            if (button.getName().equals("oldGame")) {
                button.setBounds(200, 400, 100, 36);
                button.setVisible(true);
            }
            if (button.getName().equals("setting"))
                button.setBounds(200, 450, 100, 36);
            if (button.getName().equals("quit"))
                button.setBounds(200, 500, 100, 36);
        } else {
            if (button.getName().equals("oldGame"))
                button.setVisible(false);
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

    private void setNewGame() {
        this.newGame.addActionListener(e -> {
            new Thread(() -> {
                this.menu.setVisible(false);
                this.setSize(Piece._width_, Piece._height_);
                this.setLocationRelativeTo(null);
                JButton start = new JButton();

                StaticPieces.setNew(start);
                StaticPieces.getSetting().setChessBoard(this, start);
                StaticPieces.getChessBoardPanel().goHome(this);
            }).start();
        });
    }

    private void setOldGame() {
        this.oldGame.addActionListener(e -> {
            new Thread(() -> {
                this.menu.setVisible(false);
                this.setSize(Piece._width_, Piece._height_);
                this.setLocationRelativeTo(null);
                IOFile.readGame();
                JButton start = new JButton();

                StaticPieces.setNewSetting();
                StaticPieces.getSetting().setChessBoard(this, start);
                StaticPieces.getChessBoardPanel().setButton(start);
                StaticPieces.getChessBoardPanel().play(start);
                StaticPieces.setEvent();
                StaticPieces.getChessBoardPanel().setVisible(true);
            }).start();
        });
    }

    private void setQuit() {
        this.quit.addActionListener(e -> StaticPieces.confirmQuit(this));
    }

    private void setClickSetting() {
        this.setting.addActionListener(e -> {
                level++;
                newGame.setVisible(false);
                oldGame.setVisible(false);
                setting.setVisible(false);
                quit.setVisible(false);

                volume.setVisible(true);
                backHome.setVisible(true);
                backHome.setLocation(200, 450);
                time.setVisible(true);
        });
    }


    private void setSettingHome() {
        this.level++;
        this.volume.setSize(100, 36);
        this.volume.setName("volume");
        this.volume.setLocation(200, 350);
        this.volume.setContentAreaFilled(false);
        this.volume.setBorderPainted(false);
        this.volume.setVisible(false);

        this.time.setSize(100, 36);
        this.time.setLocation(200, 400);
        this.time.setName("time");
        this.time.setContentAreaFilled(false);
        this.time.setBorderPainted(false);
        this.time.setVisible(false);

        this.backHome.setSize(100, 36);
        this.backHome.setLocation(200, 450);
        this.backHome.setName("back2");
        this.backHome.setContentAreaFilled(false);
        this.backHome.setBorderPainted(false);
        this.backHome.setVisible(false);

        this.fifteen.setSize(100, 36);
        this.fifteen.setLocation(200, 450);
        this.fifteen.setName("15");
        this.fifteen.setContentAreaFilled(false);
        this.fifteen.setBorderPainted(false);
        this.fifteen.setVisible(false);

        this.twelve.setSize(100, 36);
        this.twelve.setLocation(200, 400);
        this.twelve.setName("12");
        this.twelve.setContentAreaFilled(false);
        this.twelve.setBorderPainted(false);
        this.twelve.setVisible(false);

        this.ten.setSize(100, 36);
        this.ten.setLocation(200, 350);
        this.ten.setName("10");
        this.ten.setContentAreaFilled(false);
        this.ten.setBorderPainted(false);
        this.ten.setVisible(false);

        this.addHoverEffect(this.volume);
        this.addHoverEffect(this.time);
        this.addHoverEffect(this.backHome);
        this.addHoverEffect(this.ten);
        this.addHoverEffect(this.twelve);
        this.addHoverEffect(this.fifteen);

        this.menu.add(this.volume);
        this.menu.add(this.time);
        this.menu.add(this.backHome);

        this.menu.add(this.ten);
        this.menu.add(this.twelve);
        this.menu.add(this.fifteen);
    }

    private void setClickBack() {
        this.backHome.addActionListener(e -> {
            switch (level) {
                case 1:
                    backHome.setVisible(false);
                    time.setVisible(false);
                    volume.setVisible(false);

                    newGame.setVisible(true);
                    if (!IOFile.isEmpty(System.getProperty("user.dir") + "/resource/file/old.txt"))
                        oldGame.setVisible(true);
                    quit.setVisible(true);
                    setting.setVisible(true);
                    level--;
                    break;
                case 2:
                    slider_1.setVisible(false);
                    label_1.setVisible(false);
                    slider_2.setVisible(false);
                    label_2.setVisible(false);

                    backHome.setLocation(200, 450);
                    time.setVisible(true);
                    volume.setVisible(true);

                    ten.setVisible(false);
                    twelve.setVisible(false);
                    fifteen.setVisible(false);
                    level--;
                    break;
            }
        });
    }

    private void setVolume() {
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
            this.label_1.setBounds(150, 320, 150, 50);
            this.slider_1.setVisible(true);
            this.label_1.setVisible(true);

            this.slider_2.setBounds(150, 440, 200, 50);
            this.label_2.setBounds(150, 410, 150, 50);
            this.slider_2.setVisible(true);
            this.label_2.setVisible(true);
            this.slider_1.addChangeListener(e1 -> {
                this.label_1.setText(" Nhạc nền: " + this.slider_1.getValue());
                StaticPieces.getSoundEffect().setVolumeSoundTrack(this.slider_1.getValue());
            });

            this.slider_2.addChangeListener(e1 -> {
                this.label_2.setText(" Hiệu ứng: " + this.slider_2.getValue());
                StaticPieces.getSoundEffect().setVolumeSoundEffect(this.slider_2.getValue());
            });
        });
    }
    

    private void setTime() {
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
    private void chooseTime(){
        this.ten.addActionListener(e -> {
            StaticPieces.setMinute(10);
            StaticPieces.setSecond(0);

            IOFile.saveTime(10, 0);

            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);

            this.ten.setVisible(false);
            this.twelve.setVisible(false);
            this.fifteen.setVisible(false);
            this.level--;
        });

        this.twelve.addActionListener(e -> {
            StaticPieces.setMinute(12);
            StaticPieces.setSecond(0);

            IOFile.saveTime(12, 0);

            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);

            this.ten.setVisible(false);
            this.twelve.setVisible(false);
            this.fifteen.setVisible(false);
            this.level--;
        });

        this.fifteen.addActionListener(e -> {
            StaticPieces.setMinute(15);
            StaticPieces.setSecond(0);

            IOFile.saveTime(15, 0);

            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);

            this.ten.setVisible(false);
            this.twelve.setVisible(false);
            this.fifteen.setVisible(false);
            this.level--;
        });
    }

    private void setEvent() {
        this.setNewGame();
        this.setQuit();
        this.setOldGame();
        this.setClickSetting();
        this.closeFrame();
        this.hideFrame();
        this.setClickBack();
        this.setVolume();
        this.setTime();
        this.chooseTime();
    }

    private void closeFrame() {
        StaticPieces.getCloseButton().getClose().addActionListener(e -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
    }

    private void hideFrame() {
        StaticPieces.getCloseButton().getHide().addActionListener(e -> this.setState(JFrame.ICONIFIED));
    }

    public JPanel getMenu() {
        return this.menu;
    }
}