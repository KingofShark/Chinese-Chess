package menu;

import main.ChessBoard;

import javax.swing.*;
import java.awt.*;

public class Start {
    public Start() {
        ImageIcon logo = new ImageIcon(getClass().getResource("/image/logo.jpg").getPath());
        Image temp = logo.getImage();
        temp = temp.getScaledInstance(210, 280, Image.SCALE_SMOOTH);
        logo = new ImageIcon(temp);

        JFrame b = new JFrame();
        b.setResizable(false);
        b.setIconImage(logo.getImage());
        b.setSize(200, 280);
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.setLocationRelativeTo(null);

        JButton c = new JButton();
        c.setIcon(logo);
        c.setSize(210, 280);
        c.setFont(new Font("Arial", Font.BOLD, 14));
        c.setForeground(Color.GRAY);
        c.setText("Bắt đầu");

        b.add(c);

        b.setVisible(true);
        c.addActionListener(e -> {
            new ChessBoard();
            b.dispose();
        });

    }
}
