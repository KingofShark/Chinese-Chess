package menu;

import chesspiece.Piece;
import chesspiece.StaticPieces;
import file.IOFile;
import image.NewImage;
import game.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
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
        this.label_1 = new JLabel(" " + newVolume.elementAt(0) + " Nhạc nền");
        this.label_2 = new JLabel(" " + newVolume.elementAt(1) + " Hiệu ứng");
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


    public void setSetting() {
        this.setting.setSize(50, 50);
        this.setting.setLocation(Piece.CELL_SIZE * 13 + Piece.CELL_SIZE / 2, CELL_SIZE - Piece.CELL_SIZE / 2);
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/option/setting.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 50, 50);
        this.setting.setIcon(imageIcon);
        this.setting.setBorderPainted(false);
        this.setting.setContentAreaFilled(false);
        this.setting.setFocusPainted(false);
        StaticPieces.getChessBoardPanel().add(this.setting);
    }

    public void setExit() {
        this.exit.setSize(50, 50);
        this.exit.setLocation(Piece.CELL_SIZE * 10, CELL_SIZE - Piece.CELL_SIZE / 2);
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/option/exit.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 50, 50);
        this.exit.setIcon(imageIcon);
        this.exit.setBorderPainted(false);
        this.exit.setContentAreaFilled(false);
        this.exit.setFocusPainted(false);
        this.exit.setVisible(false);
        StaticPieces.getChessBoardPanel().add(this.exit);
    }

    public void setOption(JButton button) {
        button.setSize(100, 36);
        if (button.getName().equals("volume"))
            button.setLocation(Piece.CELL_SIZE * 11, 2 * CELL_SIZE - Piece.CELL_SIZE);
        else if (button.getName().equals("back"))
            button.setLocation(Piece.CELL_SIZE * 11, 2 * CELL_SIZE);
        else button.setLocation(Piece.CELL_SIZE * 11, 2 * CELL_SIZE + Piece.CELL_SIZE);
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/option/" + button.getName() + ".png");
        imageIcon = new NewImage().resizeImage(imageIcon, 100, 36);
        button.setIcon(imageIcon);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setVisible(false);
        StaticPieces.getChessBoardPanel().add(button);
    }

    public void clickSetting(JButton button) {
        this.setting.addActionListener(e -> {
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
            if(StaticPieces.getTurn() == -1)
                return;
            if (!status)
                imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/stop.png");
            else
                imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/start.png");
            imageIcon = new NewImage().resizeImage(imageIcon, 90, 32);
            button.setIcon(imageIcon);
        });
    }

    public void clickVolume() {
        this.volume.addActionListener(e -> {
            this.exit.setVisible(true);
            this.quit.setVisible(false);
            this.volume.setVisible(false);
            this.backHome.setVisible(false);

            this.slider_1.setBounds(10 * CELL_SIZE, 2 * CELL_SIZE, 200, 50);
            this.label_1.setBounds(10 * CELL_SIZE + 200, 2 * CELL_SIZE, 100, 50);
            this.slider_1.setBackground(new Color(152, 93, 58));
            this.slider_1.setForeground(new Color(152, 93, 58));
            this.slider_1.setVisible(true);
            this.label_1.setVisible(true);

            this.slider_2.setBounds(10 * CELL_SIZE, 3 * CELL_SIZE, 200, 50);
            this.label_2.setBounds(10 * CELL_SIZE + 200, 3 * CELL_SIZE, 100, 50);
            this.slider_2.setBackground(new Color(152, 93, 58));
            this.slider_2.setForeground(new Color(152, 93, 58));
            this.slider_2.setVisible(true);
            this.label_2.setVisible(true);
            this.slider_1.addChangeListener(e1 -> {
                int temp = this.slider_1.getValue();
                String text_1;
                if (temp < 10)
                    text_1 = "  " + temp + " Nhạc nền";
                else if (10 < temp && temp < 100)
                    text_1 = " " + temp + " Nhạc nền";
                else text_1 = temp + " Nhạc nền";
                this.label_1.setText(text_1);
                StaticPieces.getSoundEffect().setVolumeSoundTrack(temp);
            });

            this.slider_2.addChangeListener(e1 -> {
                int temp = this.slider_2.getValue();
                String text_1;
                if (temp < 10)
                    text_1 = "  " + temp + " Khác";
                else if (10 < temp && temp < 100)
                    text_1 = " " + temp + " Khác";
                else text_1 = temp + " Khác";
                this.label_2.setText(text_1);
                StaticPieces.getSoundEffect().setVolumeSoundEffect(temp);
            });
        });
    }


    public void clickBack(Home home) {
        this.backHome.addActionListener(e -> {
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
        });
    }

    public void clickQuit(Home home) {
        this.quit.addActionListener(e -> {
            if (StaticPieces.getFirst() != 2)
                file.IOFile.saveGame();
            home.dispatchEvent(new WindowEvent(home, WindowEvent.WINDOW_CLOSING));
        });
    }

    public void clickExit() {
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

    public void setEventListeners(Home home, JButton button) {
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

    public Boolean getStatus() {
        return this.status;
    }
}
