package controller;

import constant.Piece;
import file.IOFile;
import image.NewImage;
import view.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Setting implements Piece {
    private final JButton setting;
    private Boolean status;
    private final JSlider slider_1, slider_2;
    private final JLabel label_1, label_2;

    public Setting() {
        Vector<Integer> newVolume = IOFile.getVolume();
        this.slider_1 = new JSlider(0, 100, newVolume.elementAt(0));
        this.slider_2 = new JSlider(0, 100, newVolume.elementAt(1));
        this.label_1 = new JLabel(" Nhạc nền: " + newVolume.elementAt(0));
        this.label_1.setForeground(Color.white);
        this.label_2 = new JLabel(" Hiệu ứng: " + newVolume.elementAt(1));
        this.label_2.setForeground(Color.white);
        this.slider_1.setOpaque(false);
        this.slider_2.setOpaque(false);
        this.slider_1.setFocusable(false);
        this.slider_2.setFocusable(false);
        this.slider_1.setUI(new GameController.CustomSliderUI(this.slider_1));
        this.slider_2.setUI(new GameController.CustomSliderUI(this.slider_2));
        this.label_1.setFont(new Font("Arial", Font.BOLD, 15));
        this.label_2.setFont(new Font("Arial", Font.BOLD, 15));
        this.status = false;
        this.setting = new JButton();

        this.slider_1.setVisible(false);
        this.label_1.setVisible(false);
        this.slider_2.setVisible(false);
        this.label_2.setVisible(false);
    }

    public void setChessBoard() {
        ChessBoard chessBoard = GameController.getChessBoardPanel();
        chessBoard.add(slider_1);
        chessBoard.add(label_1);
        chessBoard.add(slider_2);
        chessBoard.add(label_2);

        this.setSetting();
        this.clickSetting();
    }


    private void setSetting() {
        this.setting.setSize(50, 50);
        this.setting.setLocation(Piece.CELL_SIZE * 14, Piece.CELL_SIZE / 2 - 10);
        ImageIcon defaultIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/menu/vol.png");
        defaultIcon = new NewImage().resizeImage(defaultIcon, 36, 36);
        this.setting.setIcon(defaultIcon);
        this.setting.setBorderPainted(false);
        this.setting.setContentAreaFilled(false);
        this.setting.setFocusPainted(false);
        GameController.getChessBoardPanel().add(this.setting);
    }

    private void clickSetting() {
        this.setting.addActionListener(_ -> {

            this.slider_1.setBounds(Piece.CELL_SIZE * 14 + CELL_SIZE / 2, 2 * CELL_SIZE - Piece.CELL_SIZE / 2 - 10, 200, 50);
            this.label_1.setBounds(Piece.CELL_SIZE * 14 + CELL_SIZE / 2, CELL_SIZE - 10, 150, 50);
            this.slider_1.setVisible(!status);
            this.label_1.setVisible(!status);

            this.slider_2.setBounds(Piece.CELL_SIZE * 14 + CELL_SIZE / 2, 3 * CELL_SIZE - Piece.CELL_SIZE / 2 - 10, 200, 50);
            this.label_2.setBounds(14 * CELL_SIZE + CELL_SIZE / 2, 2 * CELL_SIZE - 10, 150, 50);
            this.slider_2.setVisible(!status);
            this.label_2.setVisible(!status);

            this.slider_1.addChangeListener(_ -> {
                this.label_1.setText(" Nhạc nền: " + this.slider_1.getValue());
                GameController.getSoundEffect().setVolumeSoundTrack(this.slider_1.getValue());
            });

            this.slider_2.addChangeListener(_ -> {
                this.label_2.setText(" Hiệu ứng: " + this.slider_2.getValue());
                GameController.getSoundEffect().setVolumeSoundEffect(this.slider_2.getValue());
            });
            this.status = !this.status;
        });
    }

    public Boolean getStatus() {
        return this.status;
    }
}
