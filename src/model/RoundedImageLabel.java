package model;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

public class RoundedImageLabel extends JLabel {
    private final BufferedImage image;
    int countdown;
    int timeLeft = countdown;
    private final Timer timer;
    private boolean showCountdown = false;

    public RoundedImageLabel(ImageIcon icon) {
        this.image = toBufferedImage(icon.getImage());
        countdown = GameController.getMinute() * 60 + GameController.getSecond();
        timer = new Timer(1000, e -> {
            timeLeft--;
            repaint();
            if (timeLeft <= 0 || GameController.getClock_1().isFullTime() || GameController.getClock_2().isFullTime()) {
                ((Timer) e.getSource()).stop();
                showCountdown = false;
                timeLeft = countdown;
                repaint();
            }
        });
        setOpaque(false);
    }

    public void startCountdown() {
        countdown = GameController.getMinute() * 60 + GameController.getSecond();
        timeLeft = GameController.getMinute() * 60 + GameController.getSecond();
        System.out.println("count" + timeLeft);
        showCountdown = true;
        timer.start();
    }

    public void startCountdown(int countdown) {
        System.out.println(countdown);
        timeLeft = countdown;
        showCountdown = true;
        timer.start();
    }

    public void stopCountdown() {
        showCountdown = false;
        timer.stop();
        System.out.println("Countdown stopped");
        repaint();
    }


    public BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) return (BufferedImage) img;
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bimage.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        return bimage;
    }

    public void setNewCountdown(int countdown) {
        this.countdown = countdown;
        this.timeLeft = countdown;
        this.showCountdown = false;

        this.repaint();
    }

    public void resetCountdown() {
        timer.stop();
        showCountdown = false;
        countdown = GameController.getMinute() * 60 + GameController.getSecond();
        timeLeft = countdown;
        repaint();
        System.out.println("Countdown reset");
    }


    @Override
    protected void paintComponent(Graphics g) {
        int diameter = Math.min(getWidth(), getHeight());
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
// Tính tỷ lệ ảnh
        double aspectRatio = (double) image.getWidth(null) / image.getHeight(null);
        int newWidth = diameter;
        int newHeight = (int) (newWidth / aspectRatio);

        if (newHeight > diameter) {
            newHeight = diameter;
            newWidth = (int) (newHeight * aspectRatio);
        }

        Image scaled = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

// Tạo hình tròn và set clip
        Shape circle = new Ellipse2D.Float(0, 0, diameter, diameter);
        g2.setClip(circle);

// Vẽ ảnh ở giữa
        g2.drawImage(scaled, (diameter - newWidth) / 2, (diameter - newHeight) / 2, null);

// Reset clip để vẽ viền
        g2.setClip(null);

        g2.setStroke(new BasicStroke(4));
        g2.setColor(new Color(230, 213, 186));
        g2.draw(new Ellipse2D.Float(0, 0, diameter, diameter));

        if (showCountdown) {
            // Countdown arc
            g2.setColor(new Color(38, 163, 3));
            if (timeLeft <= 10) {
                g2.setColor(Color.RED);
            }
            g2.setStroke(new BasicStroke(9));
            double angle = 360.0 * (timeLeft) / countdown;
            int innerDiameter = diameter - 8; // Giảm 20px để tạo khoảng cách giữa viền và countdown

            Arc2D arc = new Arc2D.Double(centerX - innerDiameter / 2, centerY - innerDiameter / 2, innerDiameter, innerDiameter,
                    90, -angle, Arc2D.OPEN);
            g2.draw(arc);
        }

        g2.dispose();

    }
}

