//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//
////Input arguments
////Local Sum: localSum, path to first layer, path to second layer, path for output file, scale
////Focal Variety: focalVariety, path to layer, neighborhood size, [true if square, false if circle], path for output file, scale.
////Zonal Minimum: zonalMinimum, path to value layer, path to zone layer, path for output file, scale
//
//public class Ex03 {
//
//    public static void displayRaster(Layer outLayer, int scale) {
//        JFrame appFrame = new JFrame();
//        BufferedImage image = outLayer.toImagePastel();
//        MapPanel myMapPanel = new MapPanel(image, scale);
//
//        Dimension dimension = new Dimension(scale * outLayer.getnCols(),scale * outLayer.getnRows());
//        myMapPanel.setPreferredSize(dimension);
//        appFrame.add(myMapPanel);
//        appFrame.pack();
//        appFrame.setVisible(true);
//    }
//    public static void main(String[] args) {
//        String operation = args[0];
//
//        if (operation.equals("localSum")) {
//            Layer inLayer1 = new Layer("local1", args[1]);
//            Layer inLayer2 = new Layer("local2", args[2]);
//            Layer outLayer = inLayer1.localSum(inLayer2, "localSum");
//            outLayer.save(args[3]);
//            displayRaster(outLayer, Integer.parseInt(args[4]));
//
//        }
//        else if (operation.equals("focalVariety")) {
//            Layer inLayer1 = new Layer("focal", args[1]);
//            Layer outLayer = inLayer1.focalVariety( Integer.parseInt(args[2]), Boolean.valueOf(args[3]),"localSum");
//            outLayer.save(args[4]);
//            displayRaster(outLayer, Integer.parseInt(args[5]));
//        }
//        else if (operation.equals("zonalMinimum")) {
//            Layer valueLayer = new Layer("valueLayer", args[1]);
//            Layer zoneLayer = new Layer("zone", args[2]);
//            Layer outLayer = valueLayer.zonalMinimum(zoneLayer, "zonalMinimum");
//            outLayer.save(args[3]);
//            displayRaster(outLayer, Integer.parseInt(args[4]));
//        }
//
//        else if (operation.equals("zonalSum")) {
//            Layer valueLayer = new Layer("valueLayer", args[1]);
//            Layer zoneLayer = new Layer("zone", args[2]);
//            Layer outLayer = valueLayer.zonalSum(zoneLayer, "zonalSum");
//            outLayer.save(args[3]);
//            outLayer.print();
//            displayRaster(outLayer, Integer.parseInt(args[4]));
//        }
//
//        else if (operation.equals("zonalVariety")) {
//            Layer valueLayer = new Layer("valueLayer", args[1]);
//            Layer zoneLayer = new Layer("zone", args[2]);
//            Layer outLayer = valueLayer.zonalVariety(zoneLayer, "zonalVar");
//            outLayer.save(args[3]);
//            outLayer.print();
//            displayRaster(outLayer, Integer.parseInt(args[4]));
//        }
//        else {
//            System.out.print(operation + " is not currently available.");
//        }
//    }
//}
