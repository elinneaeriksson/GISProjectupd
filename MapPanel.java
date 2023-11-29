import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapPanel extends JPanel {
    public BufferedImage image;
    public int scale;

    public MapPanel(BufferedImage image, int scale) {
        this.image = image;
        this.scale = scale;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this.image.getWidth() * this.scale, this.image.getHeight() * this.scale, this);
    }
}
