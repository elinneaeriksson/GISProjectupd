import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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

    public Layer getLayer(){
        return layer;
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
                // Handle unexpected color values, you might want to throw an exception or return a default value
                throw new IllegalArgumentException("Invalid color: " + color);
            }
        }
    }
}
