package chesspiece;

import javax.swing.*;
import java.util.Vector;

public interface MovingMethod {
    void updateLocate(JButton button);

    void updateLocate_(String temp);

    void updateLocate(int typeClick);

    Vector<Integer> choosePiecePosition(Vector<JButton> buttonH, Vector<JButton> buttonV);

    Vector<Integer> choosePiecePosition(JButton top, JButton right, JButton bottom, JButton left);

    void updateLocate(String temp);

    Boolean checkMate();
    void resetDefauft();
}
