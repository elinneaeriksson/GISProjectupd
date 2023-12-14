import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class Project {
    private final MainFrame mainFrame;

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
        String[] s1 = in1.split("\\\\");
        String name1 = s1[s1.length-1].substring(0, s1[s1.length-1].length()-4);
        String[] s2 = in2.split("\\\\");
        String name2 = s2[s2.length-1].substring(0, s2[s2.length-1].length()-4);
        String[] s3 = out.split("\\\\");
        String name3 = s3[s3.length-1].substring(0, s3[s3.length-1].length()-4);
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
                outLayer = inLayer1.localMax(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nLocal maximum.\n\nOutput:\n" + out);
            }
            case "Minimum" -> {
                outLayer = inLayer1.localMin(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nLocal minimum.\n\nOutput:\n" + out);
            }
            case "Sum" -> {
                outLayer = inLayer1.localSum(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nLocal sum\n\nOutput:\n" + out);
            }
            case "Mean" -> {
                outLayer = inLayer1.localMean(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nLocal mean\n\nOutput:\n" + out);
                outLayer.save(out);
            }
            case "Variety" -> {
                outLayer = inLayer1.localVariety(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nLocal variety\n\nOutput:\n" + out);
            }
        }
    }

    public void focalOperation(String in, String out, String sRad, String nbType, String operation){
        String[] s1 = in.split("\\\\");
        String name1 = s1[s1.length-1].substring(0, s1[s1.length-1].length()-4);
        String[] s2 = out.split("\\\\");
        String name2 = s2[s2.length-1].substring(0, s2[s2.length-1].length()-4);
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
                outLayer = inLayer.focalMaximum(rad, isSquare, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText("Null");
                MainFrame.toggleBox3.setText(name2);
                mainFrame.setMaps(inMap, null, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in + "\n\nOperation:\nFocal maximum\n\nOutput:\n" + out);
            }
            case "Minimum" -> {
                outLayer = inLayer.focalMinimum(rad, isSquare, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText("Null");
                MainFrame.toggleBox3.setText(name2);
                mainFrame.setMaps(inMap, null, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in + "\n\nOperation:\nFocal minimum\n\nOutput:\n" + out);
            }
            case "Sum" -> {
                outLayer = inLayer.focalSum(rad, isSquare, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText("Null");
                MainFrame.toggleBox3.setText(name2);
                mainFrame.setMaps(inMap, null, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in + "\n\nOperation:\nFocal sum\n\nOutput:\n" + out);
            }
            case "Mean" -> {
                outLayer = inLayer.focalMean(rad, isSquare, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText("Null");
                MainFrame.toggleBox3.setText(name2);
                mainFrame.setMaps(inMap, null, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in + "\n\nOperation:\nFocal mean\n\nOutput:\n" + out);
            }
            case "Variety" -> {
                outLayer = inLayer.focalVariety(rad, isSquare, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText("Null");
                MainFrame.toggleBox3.setText(name2);
                mainFrame.setMaps(inMap, null, outMap);
                mainFrame.addMap(inLayer.nRows, inLayer.nCols, scale);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in + "\n\nOperation:\nFocal variety\n\nOutput:\n" + out);
            }
        }
    }

    public void zonalOperation(String in1, String in2, String out, String operation){
        String[] s1 = in1.split("\\\\");
        String name1 = s1[s1.length-1].substring(0, s1[s1.length-1].length()-4);
        String[] s2 = in2.split("\\\\");
        String name2 = s2[s2.length-1].substring(0, s2[s2.length-1].length()-4);
        String[] s3 = out.split("\\\\");
        String name3 = s3[s3.length-1].substring(0, s3[s3.length-1].length()-4);
        Layer inLayer1 = new Layer("", in1);
        Layer inLayer2 = new Layer("", in2);
        Layer outLayer = null;
        MapPanel inMap1, inMap2, outMap;
        int frame = Math.min(mainFrame.getMapPanel().getWidth(), mainFrame.getMapPanel().getHeight());
        int map = (int) Math.max(inLayer1.nRows, inLayer1.nCols);
        int scale = frame / map;
        inMap1 = new MapPanel(inLayer1.toImage(), inLayer1, scale);
        inMap2 = new MapPanel(inLayer2.toImage(), inLayer2, scale);

        switch (operation){
            case "Maximum" -> {
                outLayer = inLayer1.zonalMaximum(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale,2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nZonal maximum\n\nOutput:\n" + out);
            }
            case "Minimum" -> {
                outLayer = inLayer1.zonalMinimum(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale, 2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nZonal minimum\n\nOutput:\n" + out);
            }
            case "Sum" -> {
                outLayer = inLayer1.zonalSum(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale, 2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nZonal sum\n\nOutput:\n" + out);
            }
            case "Mean" -> {
                outLayer = inLayer1.zonalMean(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale, 2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nZonal mean\n\nOutput:\n" + out);
            }
            case "Variety" -> {
                outLayer = inLayer1.zonalVariety(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale, 2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nZonal variety\n\nOutput:\n" + out);
            }
            case "Majority" -> {
                outLayer = inLayer1.zonalMajority(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale, 2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nZonal variety\n\nOutput:\n" + out);
            }
            case "Minority" -> {
                outLayer = inLayer1.zonalMinority(inLayer2, "", MainFrame.progressBar);
                outMap = new MapPanel(outLayer.toImage(), outLayer, scale);
                MainFrame.toggleBox1.setText(name1);
                MainFrame.toggleBox2.setText(name2);
                MainFrame.toggleBox3.setText(name3);
                mainFrame.setMaps(inMap1, inMap2, outMap);
                mainFrame.addMap(inLayer1.nRows, inLayer1.nCols, scale, 2);
                outLayer.save(out);
                mainFrame.showMessage("Input:\n"+in1+"\n"+in2 + "\n\nOperation:\nZonal variety\n\nOutput:\n" + out);
            }
        }
    }
}

