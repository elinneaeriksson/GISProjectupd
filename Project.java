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

    public void focalOperation(String in, String out, String sScale, String sRad, String nbType, String operation){
        Layer inLayer = new Layer("", in);
        Layer outLayer;
        MapPanel inMap, outMap;

        int scale = Integer.parseInt(sScale);
        int rad = Integer.parseInt(sRad);
        boolean isSquare;
        if (Objects.equals(nbType, "Square"))
            isSquare = true;
        else
            isSquare = false;

        inMap = new MapPanel(inLayer.toImage(), scale);

        switch (operation){
            case "Maximum" -> {
                outLayer = inLayer.focalMaximum(rad, isSquare, "");
                outMap = new MapPanel(outLayer.toImage(), scale);
                mainFrame.setMaps(inMap, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
            }
            case "Minimum" -> {
                outLayer = inLayer.focalMinimum(rad, isSquare, "");
                outMap = new MapPanel(outLayer.toImage(), scale);
                mainFrame.setMaps(inMap, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
            }
            case "Sum" -> {
                outLayer = inLayer.focalSum(rad, isSquare, "");
                outMap = new MapPanel(outLayer.toImage(), scale);
                mainFrame.setMaps(inMap, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
            }
            case "Mean" -> {
                outLayer = inLayer.focalMean(rad, isSquare, "");
                outMap = new MapPanel(outLayer.toImage(), scale);
                mainFrame.setMaps(inMap, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
            }
            case "Variety" -> {
                outLayer = inLayer.focalVariety(rad, isSquare, "");
                outMap = new MapPanel(outLayer.toImage(), scale);
                mainFrame.setMaps(inMap, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
            }
        }
    }
}

