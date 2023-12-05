
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//adding
public class Main {
    public static void main(String[] args) {

        if (args.length == 4) {
            Layer layer = new Layer(args[0], args[1]);

            JFrame appFrame1 = new JFrame();
            JFrame appFrame2 = new JFrame();
            BufferedImage image = layer.toImage();
            MapPanel myMapPanel = new MapPanel(image, Integer.parseInt(args[2]));
            String[] highlightString = args[3].split(",");
            double[] highlightValues = new double[highlightString.length];

            int scale;
            for(scale = 0; scale < highlightString.length; ++scale) {
                highlightValues[scale] = Double.parseDouble(highlightString[scale]);
            }

            scale = Integer.parseInt(args[2]);
            BufferedImage image2 = layer.toImage(highlightValues);
            MapPanel myMapPanel2 = new MapPanel(image2, scale);
            appFrame1.add(myMapPanel);
            appFrame2.add(myMapPanel2);
            Dimension dimension = new Dimension(scale * 180, scale * 180);
            appFrame1.setSize(dimension);
            appFrame2.setSize(dimension);
            appFrame1.setVisible(true);
            appFrame2.setVisible(true);
        } else {
            System.out.println("Too many or few arguments......");
        }

    }
}
