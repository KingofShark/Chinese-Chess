package image;

import javax.swing.*;
import java.awt.*;

public class NewImage {
    public NewImage(){}
    public ImageIcon resizeImage(ImageIcon image, int width, int height) {
        Image temp = image.getImage();
        temp = temp.getScaledInstance(width, height, 4);
        return new ImageIcon(temp);
    }
}
