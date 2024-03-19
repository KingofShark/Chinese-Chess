package menu;

import javax.swing.*;
import java.awt.*;

public class Home {
    private JFrame home;
    private JPanel menu;
    private JLabel label;
    private JButton setting, one, dual;
    public Home(){
        ImageIcon logo = new ImageIcon(getClass().getResource("/image/logo.jpg").getPath());
        Image temp = logo.getImage();
        temp = temp.getScaledInstance(210, 280, Image.SCALE_SMOOTH);
        logo = new ImageIcon(temp);

        home = new JFrame();
        home.setResizable(false);
        home.setIconImage(logo.getImage());
        home.setSize(500, 700);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setLocationRelativeTo(null);

        menu = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/src/image/home.jpg");
                g.drawImage(imageIcon.getImage(), 0, 0, 500, 700, menu);
            }
        };

        label = new JLabel();
        label.setText("Cờ Tướng");
        label.setFont((new Font(Font.SANS_SERIF, Font.PLAIN,  30)));
        menu.add(label);
        home.add(menu);
        home.setVisible(true);
    }
}
