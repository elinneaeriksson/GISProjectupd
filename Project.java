import java.awt.*;
import java.util.Objects;


public class Project {
    private MainFrame mainFrame;

    public Project(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        mainFrame.setLayout(new BorderLayout());
    }

    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();
        Project project = new Project(mainFrame);
        mainFrame.setProject(project);
    }

    public void localOperation(String in1, String in2, String out, String operation){
        Layer inLayer1 = new Layer("", in1);
        Layer inLayer2 = new Layer("", in2);
        Layer outLayer;
        MapPanel inMap1, inMap2, outMap;
        int frame = Math.min(mainFrame.getMapPanel().getWidth(), mainFrame.getMapPanel().getHeight());
        int map = (int) Math.max(inLayer1.nRows, inLayer1.nCols);
        int scale = frame / map;
//        System.out.println(frame+" "+map+" "+scale);
        inMap1 = new MapPanel(inLayer1.toImage(), inLayer1, scale);
        inMap2 = new MapPanel(inLayer2.toImage(), inLayer2, scale);

        switch (operation){
            case "Maximum" -> {
                outLayer = inLayer1.localMax(inLayer2, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                outLayer.save(out);
                mainFrame.showMessage(in1+"\n"+in2 + "\n\nLocal maximum.\n\nOutput file created at path: " + out + ".");
            }
            case "Minimum" -> {
                outLayer = inLayer1.localMin(inLayer2, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                outLayer.save(out);
                mainFrame.showMessage(in1+"\n"+in2 + "\n\nLocal minimum.\n\nOutput file created at path: " + out + ".");
            }
            case "Sum" -> {
                outLayer = inLayer1.localSum(inLayer2, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                outLayer.save(out);
                mainFrame.showMessage(in1+"\n"+in2 + "\n\nLocal sum.\n\nOutput file created at path: " + out + ".");
            }
            case "Mean" -> {
                outLayer = inLayer1.localMean(inLayer2, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                mainFrame.showMessage(in1+"\n"+in2 + "\n\nLocal mean.\n\nOutput file created at path: " + out + ".");
                outLayer.save(out);
            }
            case "Variety" -> {
                outLayer = inLayer1.localVariety(inLayer2, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                outLayer.save(out);
                mainFrame.showMessage(in1+"\n"+in2 + "\n\nLocal variety.\n\nOutput file created at path: " + out + ".");
            }
        }
    }

    public void focalOperation(String in, String out, String sRad, String nbType, String operation){
        Layer inLayer = new Layer("", in);
        Layer outLayer;
        MapPanel inMap, outMap;

        int frame = Math.min(mainFrame.getMapPanel().getWidth(), mainFrame.getMapPanel().getHeight());
        int map = (int) Math.max(inLayer.nRows, inLayer.nCols);
        int scale = frame / map;

        int rad = Integer.parseInt(sRad);
        boolean isSquare;
        if (Objects.equals(nbType, "Square"))
            isSquare = true;
        else
            isSquare = false;

        inMap = new MapPanel(inLayer.toImage(), inLayer, scale);

        switch (operation){
            case "Maximum" -> {
                outLayer = inLayer.focalMaximum(rad, isSquare, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage(in + "\n\nFocal maximum.\n\nOutput file created at path: " + out + ".");
            }
            case "Minimum" -> {
                outLayer = inLayer.focalMinimum(rad, isSquare, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage(in + "\n\nFocal minimum.\n\nOutput file created at path: " + out + ".");
            }
            case "Sum" -> {
                outLayer = inLayer.focalSum(rad, isSquare, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage(in + "\n\nFocal sum.\n\nOutput file created at path: " + out + ".");
            }
            case "Mean" -> {
                outLayer = inLayer.focalMean(rad, isSquare, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage(in + "\n\nFocal mean.\n\nOutput file created at path: " + out + ".");
            }
            case "Variety" -> {
                outLayer = inLayer.focalVariety(rad, isSquare, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage(in + "\n\nFocal variety.\n\nOutput file created at path: " + out + ".");
            }
        }
    }

    public void zonalOperation(String in1, String in2, String out, String operation){
        Layer inLayer1 = new Layer("", in1);
        Layer inLayer2 = new Layer("", in2);
        Layer outLayer;
        MapPanel inMap1, inMap2, outMap;
        int frame = Math.min(mainFrame.getMapPanel().getWidth(), mainFrame.getMapPanel().getHeight());
        int map = (int) Math.max(inLayer1.nRows, inLayer1.nCols);
        int scale = frame / map;
        inMap1 = new MapPanel(inLayer1.toImage(), inLayer1, scale);
        inMap2 = new MapPanel(inLayer2.toImage(), inLayer2, scale);

        switch (operation){
            case "Maximum" -> {
                outLayer = inLayer1.zonalMaximum(inLayer2, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale, 2);
                outLayer.save(out);
                mainFrame.showMessage(in1+"\n"+in2 + "\n\nZonal maximum.\n\nOutput file created at path: " + out + ".");
            }
            case "Minimum" -> {
                outLayer = inLayer1.zonalMinimum(inLayer2, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale, 2);
                outLayer.save(out);
                mainFrame.showMessage(in1+"\n"+in2 + "\n\nZonal minimum.\n\nOutput file created at path: " + out + ".");
            }
            case "Sum" -> {
                outLayer = inLayer1.zonalSum(inLayer2, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage(in1+"\n"+in2 + "\n\nZonal sum.\n\nOutput file created at path: " + out + ".");
            }
            case "Mean" -> {
                outLayer = inLayer1.zonalMean(inLayer2, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale, 2);
                outLayer.save(out);
                mainFrame.showMessage(in1+"\n"+in2 + "\n\nZonal mean.\n\nOutput file created at path: " + out + ".");
            }
            case "Variety" -> {
                outLayer = inLayer1.zonalVariety(inLayer2, "");
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale, 2);
                outLayer.save(out);
                mainFrame.showMessage(in1+"\n"+in2 + "\n\nZonal variety.\n\nOutput file created at path: " + out + ".");
            }
        }
    }
}

