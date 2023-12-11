import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class MapPanel extends JPanel {
    public BufferedImage image;
    public int scale;
    public Layer layer;

    public MapPanel(BufferedImage image, Layer layer, int scale) {
        this.image = image;
        this.layer = layer;
        this.scale = scale;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this.image.getWidth() * this.scale, this.image.getHeight() * this.scale, this);
    }

    public MapPanel changeMapColor(MapPanel inMap, String color, int scale){
        Layer layer = inMap.layer;
        switch(color){
            case "Black and White" ->{
                return new MapPanel(layer.toImage(), layer, scale);
            }
            case "Cool" ->{
                return new MapPanel(layer.toImageCool(), layer, scale);
            }
            case "Heat" ->{
                return new MapPanel(layer.toImageHeat(), layer, scale);
            }
            case "Rainbow" ->{
                return new MapPanel(layer.toImageRainbow(), layer, scale);
            }
            default -> {
                throw new IllegalArgumentException("Invalid color: " + color);
            }
        }
    }

    public void getPixelValue(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int row = (int) (y / (double)scale);
                int col = (int) (x / (double)scale);

                if (x >= 0 && x < layer.nCols * scale && y >= 0 && y < layer.nRows * scale) {
                    double value = layer.values[row * layer.nCols + col];
                    JOptionPane.showMessageDialog(null, "Pixel value at (" + row + ", " + col + "): " + value,
                            "Pixel Value", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }
}
