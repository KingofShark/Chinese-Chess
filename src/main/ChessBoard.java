package main;

import javax.swing.*;

public class ChessBoard extends JFrame implements chesspiece.Piece {
    public ChessBoard() {
        setTitle("Cờ Tướng");
        ImageIcon logo = new ImageIcon(System.getProperty("user.dir") + "/src/image/logo.jpg");
        setIconImage(logo.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(_width_, _height_);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        ChessBoardPanel chess = new ChessBoardPanel();
        add(chess);
    }
}
