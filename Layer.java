import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Layer {
    public String name;
    public int nRows;
    public int nCols;
    public double[] origin = new double[2];
    public double[] values;
    public double resolution;
    public static double nullValue;
    private double[] var7;

    public Layer(String layerName, String fileName) {
        try {
            File rFile = new File(fileName);
            FileReader fReader = new FileReader(rFile);
            BufferedReader bReader = new BufferedReader(fReader);
            this.name = layerName;
            String[] text = bReader.readLine().toLowerCase().split(" ");
            this.nCols = Integer.parseInt(text[text.length - 1]);
            text = bReader.readLine().split(" ");
            this.nRows = Integer.parseInt(text[text.length - 1]);
            text = bReader.readLine().split(" ");
            this.origin[0] = Double.parseDouble(text[text.length - 1]);
            text = bReader.readLine().split(" ");
            this.origin[1] = Double.parseDouble(text[text.length - 1]);
            text = bReader.readLine().split(" ");
            this.resolution = Double.parseDouble(text[text.length - 1]);
            text = bReader.readLine().split(" ");
            nullValue = Double.parseDouble(text[text.length - 1]);
            this.values = new double[this.nRows * this.nCols];
            int count = 0;

            for(String textValue = bReader.readLine(); textValue != null; textValue = bReader.readLine()) {
                if (textValue.trim().isEmpty()) { //handling empty lines
                    continue;
                }
                String[] stringValue = textValue.split(" ");
                String[] var10 = stringValue;
                int var11 = stringValue.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    String s = var10[var12];
                    if (s.contains(",")) {  //this section swaps commas if necessarry
                        char[] valueArr =  s.toCharArray();
                        for (int i = 0; i < valueArr.length; ++i) {
                            if (valueArr[i] == ',') {
                                valueArr[i] = '.';
                                break;
                            }
                        }
                        s = new String(valueArr);
                    }
                    this.values[count] = Double.parseDouble(s); //could change to var12
                    ++count;
                }
            }

            fReader.close();
        } catch (Exception var14) {
            var14.printStackTrace();
        }

    }



    public Layer(String layerName, int rows, int cols, double[] oxy, double res, double nullv) {
        this.name = layerName;
        this.nRows = rows;
        this.nCols = cols;
        this.origin = oxy;
        this.resolution = res;
        nullValue = nullv;
        this.values = new double[this.nRows * this.nCols];
    }

    public int getnCols() {
        return nCols;
    }

    public int getnRows() {
        return nRows;
    }

    public void print() {
        System.out.println("ncols " + this.nCols);
        System.out.println("nrows " + this.nRows);
        System.out.println("xllcorner " + this.origin[0]);
        System.out.println("yllcorner " + this.origin[1]);
        System.out.println("cellsize " + this.resolution);
        System.out.println("NODATA_VALUE " + nullValue);

        for(int i = 0; i < this.values.length; ++i) {
            double var10001 = this.values[i];
            System.out.print("" + var10001 + " ");
            if ((i + 1) % this.nCols == 0) {
                System.out.print("\n");
            }
        }

    }

    public void save(String outputFileName) {
        try {
            File file = new File(outputFileName);
            FileWriter fWriter = new FileWriter(file);
            fWriter.write("ncols " + this.nCols + "\n");
            fWriter.write("nrows " + this.nRows + "\n");
            fWriter.write("xllcorner " + this.origin[0] + "\n");
            fWriter.write("yllcorner " + this.origin[1] + "\n");
            fWriter.write("cellsize " + this.resolution + "\n");
            fWriter.write("NODATA_VALUE " + nullValue + "\n");

            for(int i = 0; i < this.values.length; ++i) {
                double var10001 = this.values[i];
                fWriter.write("" + var10001 + " ");
                if ((i + 1) % this.nCols == 0) {
                    fWriter.write("\n");
                }
            }
            fWriter.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    public BufferedImage toImage() {
        BufferedImage image = new BufferedImage(this.nCols, this.nRows ,1);
        WritableRaster raster = image.getRaster();
        int[] color = new int[]{128, 128, 128};

        for(int i = 0; i < this.nRows; ++i) {
            for(int j = 0; j < this.nCols; ++j) {
                if (this.getMax() == this.getMin()) {
                    raster.setPixel(j,i,color);
                } else {
                    color[0] = (int)(255.0 - (this.values[i * this.nCols + j]- this.getMin()) / (this.getMax() - this.getMin()) * 255.0);
                    color[1] = color[0];
                    color[2] = color[0];
                    raster.setPixel(j,i,color);
                }
            }
        }
        return image;
    }

    //this can be used to
    public BufferedImage toImageHeat() {
        BufferedImage image = new BufferedImage(this.nCols,this.nRows, 1);
        WritableRaster raster = image.getRaster();
        int[] color = new int[3];

        for(int i = 0; i < this.nRows; ++i) {
            for(int j = 0; j < this.nCols; ++j) {
                if (this.getMax() == this.getMin()) {
                    raster.setPixel(j, i, color);
                } else {
                    color[0] = 255;
                    color[1] = (int)(255.0 - (this.values[i * this.nCols + j] - this.getMin()) / (this.getMax() - this.getMin()) * 255.0);//255 - color[0];
                    color[2] = 0;
                    raster.setPixel(j, i, color);
                }
            }
        }
        return image;
    }

    public BufferedImage toImageCool() {
        BufferedImage image = new BufferedImage(this.nCols,this.nRows, 1);
        WritableRaster raster = image.getRaster();
        int[] color = new int[3];

        for(int i = 0; i < this.nRows; ++i) {
            for(int j = 0; j < this.nCols; ++j) {
                if (this.getMax() == this.getMin()) {
                    raster.setPixel(j, i, color);
                } else {
                    color[0] = 0;
                    color[1] = (int)(255.0 - (this.values[i * this.nCols + j] - this.getMin()) / (this.getMax() - this.getMin()) * 255.0);//255 - color[0];
                    color[2] = 255;
                    raster.setPixel(j, i, color);
                }
            }
        }
        return image;
    }

    public BufferedImage toImageRainbow() {
        BufferedImage image = new BufferedImage(this.nCols,this.nRows, 1);
        WritableRaster raster = image.getRaster();
        int[] color = new int[3];
        for(int i = 0; i < this.nRows; ++i) {
            for(int j = 0; j < this.nCols; ++j) {
                if (this.getMax() == this.getMin()) {
                    raster.setPixel(j, i, color);
                } else {
                    float hue = (float)((this.values[i * this.nCols + j] - this.getMin()) / (float)(this.getMax() - this.getMin())*0.9); //0.9 avoids making both low and high values red
                    int sat = 1;
                    int bri = 1;
                    int rgb = Color.HSBtoRGB(hue,sat,bri);
                    color[0] = (rgb >> 16) &0xFF;
                    color[1] = (rgb >> 8) &0xFF;
                    color[2] = rgb &0xFF;
                    raster.setPixel(j, i, color);
                }
            }
        }
        return image;
    }

    public BufferedImage toImagePastel() {
        BufferedImage image = new BufferedImage(this.nCols,this.nRows, 1);
        WritableRaster raster = image.getRaster();
        int[] color = new int[3];
        for(int i = 0; i < this.nRows; ++i) {
            for(int j = 0; j < this.nCols; ++j) {
                if (this.getMax() == this.getMin()) {
                    raster.setPixel(j, i, color);
                } else {
                    float hue = (float)(((this.values[i * this.nCols + j] - this.getMin()) / (float)(this.getMax() - this.getMin()))*0.9); //0.9 avoids making both low and high val
                    float sat = (float) 0.7;
                    float bri = (float) 0.9;
                    int rgb = Color.HSBtoRGB(hue,sat,bri);
                    color[0] = (rgb >> 16) &0xFF;
                    color[1] = (rgb >> 8) &0xFF;
                    color[2] = rgb &0xFF;
                    raster.setPixel(j, i, color);
                }
            }
        }
        return image;
    }

    public BufferedImage toImage(double[] highlight) {
        BufferedImage image = new BufferedImage(this.nCols, this.nRows, 1);
        WritableRaster raster = image.getRaster();
        Random random = new Random();
        int[][] hColors = new int[highlight.length][3];
        int count = 0;
        double[] var7 = highlight;
        int var8 = highlight.length;

        int index;
        for(index = 0; index < var8; ++index) {
            hColors[count][0] = random.nextInt(256);
            hColors[count][1] = random.nextInt(256);
            hColors[count][2] = random.nextInt(256);
            ++count;
        }

        int[] otherColor = new int[]{128, 128, 128};
        int[] colors = new int[3];

        for(int i = 0; i < this.nRows; ++i) {
            for (int j = 0; j < this.nCols; ++j) {
                index = findIndex(highlight, this.values[i * this.nCols + j]);
                if (index != -1) {
                    colors[0] = hColors[index][0];
                    colors[1] = hColors[index][1];
                    colors[2] = hColors[index][2];
                    raster.setPixel(j,i, colors);
                } else {
                    colors[0] = otherColor[0];
                    colors[1] = otherColor[1];
                    colors[2] = otherColor[2];
                    raster.setPixel(j, i, colors);
                }
            }
        }
        return image;
    }
    public Layer localSum(Layer inLayer, String outLayerName, JProgressBar progressBar) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);

        int totalSteps = nRows * nCols;
        for (int i = 0; i < (nRows * nCols); i++){
            final int progress = (int) ((i / (double) totalSteps * 100 + 1));

            if (outLayer.values[i] == nullValue){
                continue;
            }
            else{
                outLayer.values[i] = values[i] + inLayer.values[i];
            }

            progressBar.setValue(progress);
            progressBar.update(progressBar.getGraphics());
            progressBar.setString("Processing: " + progress + "%");
        }
        return outLayer;
    }

	public Layer localMax(Layer inLayer, String outLayerName, JProgressBar progressBar) {
	    Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);

        int totalSteps = nRows * nCols;
        for (int i = 0; i < (nRows * nCols); i++) {
            final int progress = (int) ((i / (double) totalSteps * 100 + 1));

            if (this.values[i] == nullValue || inLayer.values[i] == nullValue) {
	            outLayer.values[i] = nullValue;
	        }
	        else {
	            outLayer.values[i] = Math.max(this.values[i], inLayer.values[i]);
	        }

            progressBar.setValue(progress);
            progressBar.update(progressBar.getGraphics());
            progressBar.setString("Processing: " + progress + "%");
	    }
	    return outLayer;
	}

	public Layer localMin(Layer inLayer, String outLayerName, JProgressBar progressBar) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);

        int totalSteps = nRows * nCols;
        for (int i = 0; i < (nRows * nCols); i++) {
            final int progress = (int) ((i / (double) totalSteps * 100 + 1));

            if (this.values[i] == nullValue || inLayer.values[i] == nullValue) {
	            outLayer.values[i] = nullValue;
	        }
	        else {
	            outLayer.values[i] = Math.min(this.values[i], inLayer.values[i]);
	        }

            progressBar.setValue(progress);
            progressBar.update(progressBar.getGraphics());
            progressBar.setString("Processing: " + progress + "%");
	    }
	    return outLayer;
	}

	public Layer localMean(Layer inLayer, String outLayerName, JProgressBar progressBar) {
	    Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);

        int totalSteps = nRows * nCols;
        for (int i = 0; i < (nRows * nCols); i++) {
            final int progress = (int) ((i / (double) totalSteps * 100 + 1));

            if (this.values[i] == nullValue || inLayer.values[i] == nullValue) {
	            outLayer.values[i] = nullValue;
	        }
	        else {
	            outLayer.values[i] = (this.values[i] + inLayer.values[i]) / 2.0;
	        }

            progressBar.setValue(progress);
            progressBar.update(progressBar.getGraphics());
            progressBar.setString("Processing: " + progress + "%");
	    }
	    return outLayer;
	}

	public Layer localVariety(Layer inLayer, String outLayerName, JProgressBar progressBar) {
	    Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);

        int totalSteps = nRows * nCols;
        for (int i = 0; i < (nRows * nCols); i++) {
            final int progress = (int) ((i / (double) totalSteps * 100 + 1));

            if (this.values[i] == nullValue || inLayer.values[i] == nullValue) {
	            outLayer.values[i] = nullValue;
	        }
	        else {
	            outLayer.values[i] = Math.abs(this.values[i] - inLayer.values[i]);
	        }

            progressBar.setValue(progress);
            progressBar.update(progressBar.getGraphics());
            progressBar.setString("Processing: " + progress + "%");
	    }
	    return outLayer;
	}

    public Layer focalVariety(int radius, boolean isSquare, String outLayerName, JProgressBar progressBar) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[nRows*nCols];

        int totalSteps = nRows * nCols;
        int s = 0;
        // Iterate every cell
        for (int i = 0; i < nRows; i++){
            for (int j = 0; j < nCols; j++){
                final int progress = (int) ((s / (double) totalSteps * 100 + 1));
                s++;

                // Get neighborhood indices
                int[] neighborIndices = getNeighborhood(i*nCols+j, radius, isSquare);

                // Get neighborhood values
                double[] neighborValues = new double[neighborIndices.length];
                int count = 0;
                for (int id : neighborIndices){
                    neighborValues[count] = this.values[id];
                    count++;
                }
                // Focal variety value
                outLayer.values[i*nCols+j] = countUniqueValues(neighborValues); // Null value handled here

                progressBar.setValue(progress);
                progressBar.update(progressBar.getGraphics());
                progressBar.setString("Processing: " + progress + "%");
            }
        }
        return outLayer;
    }

    public Layer focalMaximum(int radius, boolean isSquare, String outLayerName, JProgressBar progressBar){
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[nRows*nCols];

        int totalSteps = nRows * nCols;
        int s = 0;
        for (int i = 0; i < nRows; i++){
            for (int j = 0; j < nCols; j++){
                final int progress = (int) ((s / (double) totalSteps * 100 + 1));
                s++;

                // Get neighborhood indices
                int[] neighborIndices = getNeighborhood(i*nCols+j, radius, isSquare);

                // Get neighborhood values
                double[] neighborValues = new double[neighborIndices.length];
                int count = 0;
                for (int id : neighborIndices){
                    neighborValues[count] = this.values[id];
                    count++;
                }
                // Focal maximum value
                outLayer.values[i*nCols+j] = getMax(neighborValues);

                progressBar.setValue(progress);
                progressBar.update(progressBar.getGraphics());
                progressBar.setString("Processing: " + progress + "%");
            }
        }
        return outLayer;
    }

    public Layer focalMinimum(int radius, boolean isSquare, String outLayerName, JProgressBar progressBar){
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[nRows*nCols];

        for (int i = 0; i < nRows; i++){
            for (int j = 0; j < nCols; j++){
                // Get neighborhood indices
                int[] neighborIndices = getNeighborhood(i*nCols+j, radius, isSquare);

                // Get neighborhood values
                double[] neighborValues = new double[neighborIndices.length];
                int count = 0;
                for (int id : neighborIndices){
                    neighborValues[count] = this.values[id];
                    count++;
                }
                // Focal minimum value
                outLayer.values[i*nCols+j] = getMin(neighborValues); // Null value handled here
            }
        }
        return outLayer;
    }

    public Layer focalSum(int radius, boolean isSquare, String outLayerName, JProgressBar progressBar){
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[nRows*nCols];

        int totalSteps = nRows * nCols;
        int s = 0;
        for (int i = 0; i < nRows; i++){
            for (int j = 0; j < nCols; j++){
                final int progress = (int) ((s / (double) totalSteps * 100 + 1));
                s++;

                // Get neighborhood indices
                int[] neighborIndices = getNeighborhood(i*nCols+j, radius, isSquare);

                // Get neighborhood values
                double[] neighborValues = new double[neighborIndices.length];
                int count = 0;
                for (int id : neighborIndices){
                    neighborValues[count] = this.values[id];
                    count++;
                }
                // Focal sum value
                double sum = 0;
                for (double value : neighborValues) {
                    if(value != nullValue)
                        sum += value;
                    else
                        continue;
                }
                outLayer.values[i*nCols+j] = sum;

                progressBar.setValue(progress);
                progressBar.update(progressBar.getGraphics());
                progressBar.setString("Processing: " + progress + "%");
            }
        }
        return outLayer;
    }

    public Layer focalMean(int radius, boolean isSquare, String outLayerName, JProgressBar progressBar){
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[nRows*nCols];

        int totalSteps = nRows * nCols;
        int s = 0;
        for (int i = 0; i < nRows; i++){
            for (int j = 0; j < nCols; j++){
                final int progress = (int) ((s / (double) totalSteps * 100 + 1));
                s++;

                // Get neighborhood indices
                int[] neighborIndices = getNeighborhood(i*nCols+j, radius, isSquare);

                // Get neighborhood values
                double[] neighborValues = new double[neighborIndices.length];
                int count = 0;
                for (int id : neighborIndices){
                    neighborValues[count] = this.values[id];
                    count++;
                }
                // Focal mean value
                double sum = 0;
                int l = neighborValues.length;
                for (double value : neighborValues) {
                    if(value != nullValue)
                        sum += value;
                    else
                        l -= 1;
                }
                outLayer.values[i*nCols+j] = sum / l;

                progressBar.setValue(progress);
                progressBar.update(progressBar.getGraphics());
                progressBar.setString("Processing: " + progress + "%");
            }
        }
        return outLayer;
    }

    public Layer zonalMinimum(Layer inLayer, String outLayerName, JProgressBar progressBar) { // Changed to one dimensional array
        Layer outLayer = new Layer(outLayerName, this.nRows, this.nCols, this.origin, this.resolution, nullValue);
        outLayer.values = new double[this.nRows * this.nCols];
        HashMap<Double, Double> zone_min = new HashMap<>();

        int totalSteps = nRows * nCols;
        for (int i = 0; i < this.nRows*this.nCols; i++) {
            final int progress = (int) ((i / (double) totalSteps * 100 + 1));

            double zone = inLayer.values[i];
            if (zone==nullValue) {
                zone_min.put(zone, nullValue);
            }
            else if(!zone_min.containsKey(zone)) {
                    zone_min.put(zone, this.values[i]);
                }
                else {
                    if (zone_min.get(zone) > this.values[i]) {
                        zone_min.put(zone, this.values[i]);
                    }
                    else {
                        continue;
                    }
            }
            progressBar.setValue(progress);
            progressBar.update(progressBar.getGraphics());
            progressBar.setString("Processing: " + progress + "%");
        }

        for (int i = 0; i < this.nRows*this.nCols; i++) {
            if (this.values[i] == nullValue) {
                outLayer.values[i] = nullValue;
            }
            else {
                outLayer.values[i] = zone_min.get(inLayer.values[i]);
            }
        }

        return outLayer;
    }

    public Layer zonalMaximum(Layer inLayer, String outLayerName, JProgressBar progressBar) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[this.nRows*this.nCols];
        HashMap<Double, Double> zone_max = new HashMap<>();

        int totalSteps = nRows * nCols;
        for (int i = 0; i < this.nRows*this.nCols; i++) {
            final int progress = (int) ((i / (double) totalSteps * 100 + 1));

            double zone = inLayer.values[i];
            if (zone==nullValue) {
                zone_max.put(zone, nullValue);
            }
            else if(!zone_max.containsKey(zone)) {
                zone_max.put(zone, this.values[i]);
            }
            else {
                if (zone_max.get(zone) < this.values[i]) {
                    zone_max.put(zone, this.values[i]);
                }
                else {
                    continue;
                }
            }

            progressBar.setValue(progress);
            progressBar.update(progressBar.getGraphics());
            progressBar.setString("Processing: " + progress + "%");
        }

        for (int i = 0; i < this.nRows*this.nCols; i++) {
            if (this.values[i] == nullValue) {
                outLayer.values[i] = nullValue;
            }
            else {
                outLayer.values[i] = zone_max.get(inLayer.values[i]);
            }
        }
        return outLayer;
    }

    public Layer zonalMean(Layer inLayer, String outLayerName, JProgressBar progressBar) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[this.nRows*this.nCols];

        HashMap<Double, Double> zone_mean = new HashMap<>();
        HashMap<Double, Integer> zone_mean_length = new HashMap<>(); // Keeps track of the number of values in each zone

        // Assigning cells to zones
        int totalSteps = nRows * nCols;
        for (int i = 0; i < this.nRows*this.nCols; i++) {
            final int progress = (int) ((i / (double) totalSteps * 100 + 1));

            double zone = inLayer.values[i];
            if (zone==nullValue) {
                zone_mean.put(zone, nullValue);
            }
            else if(!zone_mean.containsKey(zone)) {
                    zone_mean.put(zone, this.values[i]);
                    zone_mean_length.put(zone, 1);
                }
                else {
                    double sum = zone_mean.get(zone) + this.values[i];
                    int zoneLength = zone_mean_length.get(zone) + 1;
                    zone_mean.put(zone, sum);
                    zone_mean_length.put(zone, zoneLength);
            }

            progressBar.setValue(progress);
            progressBar.update(progressBar.getGraphics());
            progressBar.setString("Processing: " + progress + "%");
        }

        // Calculate zonal mean
        for (Double key : zone_mean.keySet()) {
            double meanVal = zone_mean.get(key)/zone_mean_length.get(key);
            zone_mean.put(key, meanVal);
        }

        // Print in outLayer
        for (int i = 0; i < this.nRows*this.nCols; i++) {
            if (this.values[i] == nullValue) {
                outLayer.values[i] = nullValue;
            }
            else {
                outLayer.values[i] = zone_mean.get(inLayer.values[i]);
            }
        }

        return outLayer;
    }

    public Layer zonalSum(Layer zoneLayer, String outLayerName, JProgressBar progressBar) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[this.nRows*this.nCols];

        HashMap<Double, Double> zone_sum = new HashMap<>();

        int totalSteps = nRows * nCols;
        for (int i = 0; i < this.nRows*this.nCols; i++) {
            final int progress = (int) ((i / (double) totalSteps * 100 + 1));

            double zone = zoneLayer.values[i];
            if (zone==nullValue) { //if NoData
                zone_sum.put(zone, nullValue); //is this necessary
            }
            else if(!zone_sum.containsKey(zone)) { //first time visiting zone
                zone_sum.put(zone, this.values[i]);
            }
            else { //already visited zone
                zone_sum.put(zone, (zone_sum.get(zone) + this.values[i]));
            }

            progressBar.setValue(progress);
            progressBar.update(progressBar.getGraphics());
            progressBar.setString("Processing: " + progress + "%");
        }

        for (int i = 0; i < this.nRows*this.nCols; i++) {
            if (this.values[i] == nullValue) {
                outLayer.values[i] = nullValue;
            }
            else {
                outLayer.values[i] = zone_sum.get(zoneLayer.values[i]);
            }
        }

        return outLayer;
    }

    public Layer zonalVariety(Layer zoneLayer, String outLayerName, JProgressBar progressBar) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[this.nRows*this.nCols];

        HashMap<Double, Set<Double>> zone_var = new HashMap<>();

        int totalSteps = nRows * nCols;
        for (int i = 0; i < this.nRows*this.nCols; i++) {
            final int progress = (int) ((i / (double) totalSteps * 100 + 1));

            double zone = zoneLayer.values[i];
            if (zone==nullValue) { //if NoData
                Set nullSet = new HashSet<Double>();
                zone_var.put(zone,nullSet); //maybe unnecessary
            }
            else if(!zone_var.containsKey(zone)) { //first time visiting zone
                Set zoneSet = new HashSet<Double>();
                zoneSet.add(this.values[i]);
                zone_var.put(zone, zoneSet);
            }
            else { //already visited zone
                Set zoneSet = zone_var.get(zone);
                zoneSet.add(this.values[i]);
                zone_var.put(zone, zoneSet);
            }

            progressBar.setValue(progress);
            progressBar.update(progressBar.getGraphics());
            progressBar.setString("Processing: " + progress + "%");
        }

        for (int i = 0; i < this.nRows*this.nCols; i++) {
            if (this.values[i] == nullValue) {
                outLayer.values[i] = nullValue;
            }
            else {
                outLayer.values[i] = zone_var.get(zoneLayer.values[i]).size();
            }
        }
        return outLayer;
    }

    public Layer zonalMajority(Layer inLayer, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[this.nRows*this.nCols];

        HashMap<Double, Double> zone_maj = new HashMap<>();
        HashMap<Double, ArrayList<Double>> freq = new HashMap<>();

        // Assign list of cell values for each zone
        for (int i = 0; i < this.nRows*this.nCols; i++) {
            double zone = inLayer.values[i];
            if (zone==nullValue) {
                ArrayList<Double> val = new ArrayList<>();
                val.add(nullValue);
                freq.put(zone, val);
            }
            else if(!freq.containsKey(zone)) {
				ArrayList<Double> val = new ArrayList<>();
                val.add(this.values[i]);
                freq.put(zone, val);
            }
            else {
            	ArrayList<Double> val = freq.get(zone);
                val.add(this.values[i]);
                freq.put(zone, val);
            }
        }
        
        // Count value frequency and most common value
        for (Double key : freq.keySet()) {
        	ArrayList <Double> val = freq.get(key);
        	HashMap <Double,Integer> valFreq = new HashMap<>();
        	for (Double i : val) {
        		if (!valFreq.containsKey(i)) {
        			valFreq.put(i, 1);
        		}
        		else {
        			Integer value = valFreq.get(i) + 1;
        			valFreq.put(i,value);
        		}
        	}
        	System.out.println(valFreq);
        	int max = 0;
    		Double maxKey = 0.0;
        	for (Double i : valFreq.keySet()) {
        		if (valFreq.get(i)>max) {
        			max = valFreq.get(i);
        			maxKey = i;
        		}
        	zone_maj.put(key, maxKey);
        	}
        }

        // Print in outLayer
        for (int i = 0; i < this.nRows*this.nCols; i++) {
            if (this.values[i] == nullValue) {
                outLayer.values[i] = nullValue;
            }
            else {
                outLayer.values[i] = zone_maj.get(inLayer.values[i]);
            }
            
        }
        return outLayer;
    }
    
    public Layer zonalMinority(Layer inLayer, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = new double[this.nRows*this.nCols];

        HashMap<Double, Double> zone_min = new HashMap<>();
        HashMap<Double, ArrayList<Double>> freq = new HashMap<>();

        // Assign list of cell values for each zone
        for (int i = 0; i < this.nRows*this.nCols; i++) {
            double zone = inLayer.values[i];
            if (zone==nullValue) {
                ArrayList<Double> val = new ArrayList<>();
                val.add(nullValue);
                freq.put(zone, val);
            }
            else if(!freq.containsKey(zone)) {
				ArrayList<Double> val = new ArrayList<>();
                val.add(this.values[i]);
                freq.put(zone, val);
            }
            else {
            	ArrayList<Double> val = freq.get(zone);
                val.add(this.values[i]);
                freq.put(zone, val);
            }
        }
        
        // Count value frequency and least common value
        for (Double key : freq.keySet()) {
        	ArrayList <Double> val = freq.get(key);
        	HashMap <Double,Double> valFreq = new HashMap<>();
        	for (Double i : val) {
        		if (!valFreq.containsKey(i)) {
        			Double value = 1.0;
        			valFreq.put(i, value);
        		}
        		else {
        			Double value = valFreq.get(i) + 1;
        			valFreq.put(i,value);
        		}
        	}
        	System.out.println(valFreq);
        	double min = Double.POSITIVE_INFINITY;
    		Double maxKey = 0.0;
        	for (Double i : valFreq.keySet()) {
        		if (valFreq.get(i)<min) {
        			min = valFreq.get(i);
        			maxKey = i;
        		}
        	zone_min.put(key, maxKey);
        	}
        }

        // Print in outLayer
        for (int i = 0; i < this.nRows*this.nCols; i++) {
            if (this.values[i] == nullValue) {
                outLayer.values[i] = nullValue;
            }
            else {
                outLayer.values[i] = zone_min.get(inLayer.values[i]);
            }
            
        }
        return outLayer;
    }


    private int[] getNeighborhood(int index, int radius, boolean isSquare) {
        ArrayList<Integer> list = new ArrayList();
        int row = index / this.nCols;
        int col = index % this.nCols;
        int up = Math.max(row - radius, 0);
        int bottom = Math.min(row + radius, this.nRows - 1);
        int left = Math.max(col - radius, 0);
        int right = Math.min(col + radius, this.nCols - 1);
        int neighbor;
        int r;
        int c;
        if (isSquare) {
            for(r = up; r <= bottom; ++r) {
                for(c = left; c <= right; ++c) {
                    neighbor = r * this.nCols + c;
                    list.add(neighbor);
                }
            }
        } else {
            for(r = up; r <= bottom; ++r) {
                for(c = left; c <= right; ++c) {
                    if (getDistance((double)row, (double)col, (double)r, (double)c) <= (double)radius) {
                        neighbor = r * this.nCols + c;
                        list.add(neighbor);
                    }
                }
            }
        }

        int[] neighborArray = new int[list.size()];
        c = 0;

        for(Iterator var14 = list.iterator(); var14.hasNext(); ++c) {
            Integer intObj = (Integer)var14.next();
            neighborArray[c] = intObj;
        }

        return neighborArray;
    }

    private double getMax() {
        double max = Double.NEGATIVE_INFINITY;

        for(int i = 0; i < this.nRows; ++i) {
            for(int j = 0; j < this.nCols; ++j) {
                if (this.values[i * this.nCols + j] > max) {
                    max = this.values[i * this.nCols + j];
                }
            }
        }

        return max;
    }

    private double getMax(double[] arr) {
        double max = Double.NEGATIVE_INFINITY;
        for (double v : arr) {
            if (v > max)
                max = v;
        }
        return max;
    }

    private double getMin() {
        double min = Double.POSITIVE_INFINITY;

        for(int i = 0; i < this.nRows; ++i) {
            for(int j = 0; j < this.nCols; ++j) {
                if (this.values[i * this.nCols + j] < min) {
                    min = this.values[i * this.nCols + j];
                }
            }
        }

        return min;
    }

    private double getMin(double[] arr) {
        double min = Double.POSITIVE_INFINITY;
        for (double v : arr) {
            if (v < min && v != nullValue)
                min = v;
        }
        return min;
    }

    private static int findIndex(double[] array, double targetValue) {
        for(int i = 0; i < array.length; ++i) {
            if (array[i] == targetValue) {
                return i;
            }
        }

        return -1;
    }

    private static int countUniqueValues(double[] array) {
        Set<Double> uniqueSet = new HashSet();
        double[] var2 = array;
        int var3 = array.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            double value = var2[var4];
            if (value != nullValue) {
                uniqueSet.add(value);
            }
        }

        return uniqueSet.size();
    }

    private static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2.0) + Math.pow(y1 - y2, 2.0));
    }
}

