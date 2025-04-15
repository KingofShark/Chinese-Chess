package model;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

public class RoundedImageLabel extends JLabel {
    private final BufferedImage image;
    int countdown = 90; // số giây đếm ngược
    int timeLeft = countdown;
    private final Timer timer;
    private boolean showCountdown = false;

    public RoundedImageLabel(ImageIcon icon, int arcW, int arcH) {
        this.image = toBufferedImage(icon.getImage());

        timer = new Timer(1000, e -> {
            timeLeft--;
            repaint();
            if (timeLeft <= 0) {
                JOptionPane.showMessageDialog(null, "Hết giờ\n");
                ((Timer) e.getSource()).stop();
            }
        });
        setOpaque(false);
    }

    public void startCountdown() {
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
            double angle = 360.0 * timeLeft / countdown;
            int innerDiameter = diameter - 8; // Giảm 20px để tạo khoảng cách giữa viền và countdown

            Arc2D arc = new Arc2D.Double(centerX - innerDiameter / 2, centerY - innerDiameter / 2, innerDiameter, innerDiameter,
                    90, -angle, Arc2D.OPEN);
            g2.draw(arc);

            // Che toàn bộ 1/4 dưới cùng
            g2.setColor(new Color(120, 109, 109));  // Màu nền để che đi phần dưới cùng
            g2.fill(new Arc2D.Float(centerX - innerDiameter / 2, centerY - innerDiameter / 2, innerDiameter, innerDiameter,
                    225, 90, Arc2D.CHORD));  // Chọn 1/4 dưới để che

            // Vẽ số đếm ngược vào đoạn đã che
            g2.setFont(new Font("Arial", Font.BOLD, 24)); // Giảm kích thước font để tránh bị cắt
            g2.setColor(Color.WHITE);
            String text = String.valueOf(timeLeft);
            FontMetrics fm = g2.getFontMetrics();

            // Tính toán vị trí vẽ số sao cho nó nằm ở phần che khuất (dưới cùng)
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();

            // Căn chỉnh vị trí vẽ số ở đoạn 1/4 dưới cùng
            int x = centerX - textWidth / 2;
            int y = centerY + innerDiameter / 2 + textHeight / 4;  // Vẽ vào phần dưới cùng của vòng tròn

            // Nếu số quá lớn, điều chỉnh lại cho vừa với vòng tròn
            if (textWidth > innerDiameter - 20) {  // Điều chỉnh nếu số quá to
                x = centerX - (innerDiameter - 20) / 2;
            }

            g2.drawString(text, x, y - 10); // Vẽ số đếm vào vị trí mới
        }

        g2.dispose();

    }
}

