package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Notification extends JPanel {
    private final JLabel notificationLabel;
    private final JButton negativeButton;
    private final JButton positiveButton;

    public Notification() {
        this.setBackground(new Color(255, 223, 186));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setSize(250, 120);
        this.setVisible(false);

        notificationLabel = new JLabel();
        notificationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        negativeButton = new JButton("Đóng");
        negativeButton.setBackground(new Color(220, 53, 69));
        negativeButton.setForeground(Color.WHITE);

        positiveButton = new JButton("Xác nhận");
        positiveButton.setBackground(new Color(40, 167, 69));
        positiveButton.setForeground(Color.WHITE);

        // Panel chứa 2 nút ngang hàng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(negativeButton);
        buttonPanel.add(positiveButton);

        this.add(Box.createVerticalStrut(10));
        this.add(notificationLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(buttonPanel);
        this.add(Box.createVerticalStrut(10));
    }


    private void clickCloseButton() {
        for (ActionListener al : negativeButton.getActionListeners()) {
            negativeButton.removeActionListener(al);
        }
        negativeButton.addActionListener(e -> {
            this.setVisible(false);
        });
    }

    public void showNotification(String message) {
        negativeButton.setVisible(false);
        positiveButton.setVisible(true);
        notificationLabel.setText(message);
        this.setVisible(true);


        for (ActionListener al : positiveButton.getActionListeners()) {
            positiveButton.removeActionListener(al);
        }

        positiveButton.addActionListener(_ -> this.setVisible(false));
    }

    public void showNotification(String message, Handlers handler) {
        notificationLabel.setText(message);
        negativeButton.setVisible(true);
        positiveButton.setVisible(true);
        this.setVisible(true);

        for (ActionListener al : negativeButton.getActionListeners()) {
            negativeButton.removeActionListener(al);
        }
        for (ActionListener al : positiveButton.getActionListeners()) {
            positiveButton.removeActionListener(al);
        }

        negativeButton.addActionListener(e -> {
            handler.onNegative();
            this.setVisible(false);
        });

        positiveButton.addActionListener(e -> {
            handler.onPositive();
            this.setVisible(false);
        });
    }

    public void showNotification(String message, Handler handler) {
        notificationLabel.setText(message);
        negativeButton.setVisible(false);
        positiveButton.setVisible(true);
        this.setVisible(true);


        for (ActionListener al : positiveButton.getActionListeners()) {
            positiveButton.removeActionListener(al);
        }

        positiveButton.addActionListener(e -> {
            handler.onProcess();
            this.setVisible(false);
        });
    }

    public void Loading(String message) {
        notificationLabel.setText(message);
        negativeButton.setVisible(false);
        positiveButton.setVisible(false);
        this.setVisible(true);
    }

    public interface Handlers {
        void onPositive();
        void onNegative();
    }

    public interface Handler {
        void onProcess();
    }
}
