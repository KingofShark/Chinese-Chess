package menu;

import chesspiece.StaticPieces;
import image.NewImage;

import javax.swing.*;

public class CloseButton {
    private final JButton hide;
    private final JButton close;
    public CloseButton(){
        this.hide = new JButton();
        this.close = new JButton();
    }
    public void setClose(JPanel panel){
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/close.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 25, 25);
        this.close.setIcon(imageIcon);
        this.close.setContentAreaFilled(false);
        this.close.setFocusPainted(false);
        this.close.setBorderPainted(false);
        this.close.setSize(26, 25);
        System.out.println("W: " + panel.getWidth() + ", H: " + panel.getHeight());
        this.close.setLocation(panel.getWidth() - 30, 5);
        panel.add(this.close);
    }
    public void setHide(JPanel panel){
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/hide.png");
        imageIcon = new NewImage().resizeImage(imageIcon, 25, 25);
        this.hide.setIcon(imageIcon);
        this.hide.setContentAreaFilled(false);
        this.hide.setFocusPainted(false);
        this.hide.setBorderPainted(false);
        this.hide.setSize(26, 25);
        this.hide.setLocation(panel.getWidth() - 60, 5);
        panel.add(this.hide);
    }
    public JButton getClose(){return this.close;}
    public JButton getHide(){return this.hide;}
}
