package controller;

import constant.Piece;
import controller.StaticPieces;
import file.IOFile;
import image.NewImage;
import view.ChessBoard;
import view.Home;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Setting implements Piece {
    private final JButton setting, volume, quit, backHome, exit;
    private Boolean status;
    private final JSlider slider_1, slider_2;
    private final JLabel label_1, label_2;

    public Setting() {
        Vector<Integer> newVolume = IOFile.getVolume();
        this.slider_1 = new JSlider(0, 100, newVolume.elementAt(0));
        this.slider_2 = new JSlider(0, 100, newVolume.elementAt(1));
        this.label_1 = new JLabel(" Nhạc nền: " + newVolume.elementAt(0));
        this.label_2 = new JLabel(" Hiệu ứng: " + newVolume.elementAt(1));
        this.slider_1.setOpaque(false);
        this.slider_2.setOpaque(false);
        this.slider_1.setFocusable(false);
        this.slider_2.setFocusable(false);
        this.slider_1.setUI(new StaticPieces.CustomSliderUI(this.slider_1));
        this.slider_2.setUI(new StaticPieces.CustomSliderUI(this.slider_2));
        this.label_1.setFont(new Font("Arial", Font.BOLD, 15));
        this.label_2.setFont(new Font("Arial", Font.BOLD, 15));
        this.status = false;
        this.setting = new JButton();
        this.volume = new JButton();
        this.quit = new JButton();
        this.backHome = new JButton();
        this.exit = new JButton();

        this.volume.setName("volume");
        this.quit.setName("quit");
        this.backHome.setName("back");

        this.slider_1.setVisible(false);
        this.label_1.setVisible(false);
        this.slider_2.setVisible(false);
        this.label_2.setVisible(false);
    }

    public void setChessBoard(Home home, JButton start) {
        ChessBoard chessBoard = StaticPieces.getChessBoardPanel();
        chessBoard.add(slider_1);
        chessBoard.add(label_1);
        chessBoard.add(slider_2);
        chessBoard.add(label_2);

        this.setSetting();
        this.setOption(this.volume);
        this.setOption(this.backHome);
        this.setOption(this.quit);
        this.setExit();
        this.setEventListeners(home, start);
    }


    private void setSetting() {
        this.setting.setSize(50, 50);
        this.setting.setLocation(Piece.CELL_SIZE * 14, CELL_SIZE - Piece.CELL_SIZE / 2);
        ImageIcon defaultIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/option/setting2.png");
        ImageIcon hoverIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/moved/setting2.png");
        defaultIcon = new NewImage().resizeImage(defaultIcon, 36, 36);
        hoverIcon = new NewImage().resizeImage(hoverIcon, 36, 36);
        this.setting.setIcon(defaultIcon);
        this.setting.setRolloverIcon(hoverIcon);
        this.setting.setBorderPainted(false);
        this.setting.setContentAreaFilled(false);
        this.setting.setFocusPainted(false);
        StaticPieces.getChessBoardPanel().add(this.setting);
    }

    private void setExit() {
        this.exit.setSize(50, 50);
        this.exit.setLocation(Piece.CELL_SIZE * 10, CELL_SIZE - Piece.CELL_SIZE / 2);
        ImageIcon defaultIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/option/exit.png");
        ImageIcon hoverIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/moved/exit.png");
        defaultIcon = new NewImage().resizeImage(defaultIcon, 36, 36);
        hoverIcon = new NewImage().resizeImage(hoverIcon, 36, 36);
        this.exit.setIcon(defaultIcon);
        this.exit.setRolloverIcon(hoverIcon);
        this.exit.setBorderPainted(false);
        this.exit.setContentAreaFilled(false);
        this.exit.setFocusPainted(false);
        this.exit.setVisible(false);
        StaticPieces.getChessBoardPanel().add(this.exit);
    }

    private void setOption(JButton button) {
        button.setSize(100, 36);
        if (button.getName().equals("volume"))
            button.setLocation(Piece.CELL_SIZE * 11 + Piece.CELL_SIZE / 2, 2 * CELL_SIZE - Piece.CELL_SIZE);
        else if (button.getName().equals("back"))
            button.setLocation(Piece.CELL_SIZE * 11 + Piece.CELL_SIZE / 2, 2 * CELL_SIZE);
        else button.setLocation(Piece.CELL_SIZE * 11 + Piece.CELL_SIZE / 2, 2 * CELL_SIZE + Piece.CELL_SIZE);
        this.addHoverEffect(button);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setVisible(false);
        StaticPieces.getChessBoardPanel().add(button);
    }

    private void clickSetting(JButton button) {
        this.setting.addActionListener(e -> {
            if (StaticPieces.getTurn() % 2 == Piece.BLACK && StaticPieces.getFirst() != 2)
                StaticPieces.changeImage("wait", 1);
            else
                StaticPieces.changeImage("wait", 2);
            if (!this.status || StaticPieces.getChessBoardPanel().getPause()) {
                StaticPieces.changeImage("ss", 1);
                StaticPieces.changeImage("ss", 2);
                StaticPieces.getNotice_1().setVisible(false);
                StaticPieces.getNotice_2().setVisible(false);
            }
            this.slider_1.setVisible(false);
            this.label_1.setVisible(false);
            this.slider_2.setVisible(false);
            this.label_2.setVisible(false);
            this.volume.setVisible(!this.status);
            this.backHome.setVisible(!this.status);
            this.quit.setVisible(!this.status);
            this.exit.setVisible(false);
            this.status = !this.status;
            if (StaticPieces.getChessBoardPanel().getPause())
                return;
            ImageIcon imageIcon;
            if (StaticPieces.getTurn() == -1)
                return;
            if (!status)
                imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/stop.png");
            else {
                imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/start.png");
                StaticPieces.getEvent().hideButton();
            }
            imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
            button.setIcon(imageIcon);
        });
    }

    private void clickVolume() {
        this.volume.addActionListener(e -> {
            this.exit.setVisible(true);
            this.quit.setVisible(false);
            this.volume.setVisible(false);
            this.backHome.setVisible(false);

            this.slider_1.setBounds(10 * CELL_SIZE + CELL_SIZE / 2, 2 * CELL_SIZE, 200, 50);
            this.label_1.setBounds(10 * CELL_SIZE + CELL_SIZE / 2, 2 * CELL_SIZE - CELL_SIZE / 2, 150, 50);
            this.slider_1.setVisible(true);
            this.label_1.setVisible(true);

            this.slider_2.setBounds(10 * CELL_SIZE + CELL_SIZE / 2, 3 * CELL_SIZE, 200, 50);
            this.label_2.setBounds(10 * CELL_SIZE + CELL_SIZE / 2, 3 * CELL_SIZE - CELL_SIZE / 2, 150, 50);
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


    private void clickBack(Home home) {
        this.backHome.addActionListener(e -> {
            new Thread(() -> {
                if (StaticPieces.getFirst() != 2)
                    file.IOFile.saveGame();
                home.setButton();
                StaticPieces.getChessBoardPanel().removePieces();
                StaticPieces.getChessBoardPanel().setVisible(false);
                StaticPieces.getClock_1().stop();
                StaticPieces.getClock_2().stop();
                this.slider_1.setVisible(false);
                this.label_1.setVisible(false);
                this.slider_2.setVisible(false);
                this.label_2.setVisible(false);
                this.volume.setVisible(!this.status);
                this.backHome.setVisible(!this.status);
                this.quit.setVisible(!this.status);
                this.exit.setVisible(false);
                this.status = !this.status;

                StaticPieces.getCloseButton().setClose(home.getMenu());
                StaticPieces.getCloseButton().setHide(home.getMenu());
                home.getMenu().setVisible(true);
                home.setSize(500, 700);
                home.setLocationRelativeTo(null);
            }).start();
        });
    }

    private void clickQuit(Home home) {
        this.quit.addActionListener(e -> {
            if (StaticPieces.getFirst() != 2)
                file.IOFile.saveGame();
            StaticPieces.confirmQuit(home);
        });
    }

    private void clickExit() {
        this.exit.addActionListener(e -> {
            this.slider_1.setVisible(false);
            this.label_1.setVisible(false);
            this.slider_2.setVisible(false);
            this.label_2.setVisible(false);
            this.volume.setVisible(true);
            this.backHome.setVisible(true);
            this.quit.setVisible(true);
            this.exit.setVisible(false);
        });
    }

    private void setEventListeners(Home home, JButton button) {
        this.clickQuit(home);
        this.clickVolume();
        this.clickSetting(button);
        this.clickExit();
        this.clickBack(home);
    }

    public void closeSetting() {
        this.volume.setVisible(false);
        this.backHome.setVisible(false);
        this.quit.setVisible(false);
        this.status = false;
        this.slider_1.setVisible(false);
        this.label_1.setVisible(false);
        this.slider_2.setVisible(false);
        this.label_2.setVisible(false);
        this.exit.setVisible(false);
    }

    private void addHoverEffect(JButton button) {
        ImageIcon defaultIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/option/" + button.getName() + ".png");
        ImageIcon hoverIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/moved/" + button.getName() + ".png");
        defaultIcon = new ImageIcon(defaultIcon.getImage().getScaledInstance(100, 36, Image.SCALE_SMOOTH));
        hoverIcon = new ImageIcon(hoverIcon.getImage().getScaledInstance(100, 36, Image.SCALE_SMOOTH));

        button.setIcon(defaultIcon);
        button.setRolloverIcon(hoverIcon);
    }

    public Boolean getStatus() {
        return this.status;
    }
}
