import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Layer {
    public String name;
    private int nRows;
    private int nCols;
    private double[] origin = new double[2];
    private double[] values;
    private double resolution;
    private static double nullValue;

    public Layer(String layerName, String fileName) {
        try {
            File rFile = new File(fileName);
            FileReader fReader = new FileReader(rFile);
            BufferedReader bReader = new BufferedReader(fReader);
            this.name = layerName;
            String[] text = bReader.readLine().split(" ");
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
                String[] stringValue = textValue.split(" ");
                String[] var10 = stringValue;
                int var11 = stringValue.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    String s = var10[var12];
                    this.values[count] = Double.parseDouble(s);
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
            System.out.println("File created.");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public BufferedImage toImage() {
        BufferedImage image = new BufferedImage(this.nRows, this.nCols, 1);
        WritableRaster raster = image.getRaster();
        int[] color = new int[]{128, 128, 128};

        for(int i = 0; i < this.nRows; ++i) {
            for(int j = 0; j < this.nCols; ++j) {
                if (this.getMax() == this.getMin()) {
                    raster.setPixel(j, i, color);
                } else {
                    color[0] = (int)(255.0 - (this.values[i * this.nCols + j] - this.getMin()) / (this.getMax() - this.getMin()) * 255.0);
                    color[1] = color[0];
                    color[2] = color[0];
                    raster.setPixel(j, i, color);
                }
            }
        }

        return image;
    }

    public BufferedImage toImage(double[] highlight) {
        BufferedImage image = new BufferedImage(this.nRows, this.nCols, 1);
        WritableRaster raster = image.getRaster();
        Random random = new Random();
        int[][] hColors = new int[highlight.length][3];
        int count = 0;
        double[] var7 = highlight;
        int var8 = highlight.length;

        int index;
        for(index = 0; index < var8; ++index) {
            double var10000 = var7[index];
            hColors[count][0] = random.nextInt(256);
            hColors[count][1] = random.nextInt(256);
            hColors[count][2] = random.nextInt(256);
            ++count;
        }

        int[] otherColor = new int[]{128, 128, 128};
        int[] colors = new int[3];

        for(int i = 0; i < this.nRows; ++i) {
            for(int j = 0; j < this.nCols; ++j) {
                index = findIndex(highlight, this.values[i * this.nCols + j]);
                if (index != -1) {
                    colors[0] = hColors[index][0];
                    colors[1] = hColors[index][1];
                    colors[2] = hColors[index][2];
                    raster.setPixel(j, i, colors);
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

    public Layer localSum(Layer inLayer, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, this.nRows, this.nCols, this.origin, this.resolution, nullValue);

        for(int i = 0; i < this.nRows * this.nCols; ++i) {
            if (outLayer.values[i] != nullValue) {
                outLayer.values[i] = this.values[i] + inLayer.values[i];
            }
        }

        return outLayer;
    }

    public Layer focalVariety(int radius, boolean isSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, this.nRows, this.nCols, this.origin, this.resolution, nullValue);
        outLayer.values = new double[this.nRows * this.nCols];

        for(int i = 0; i < this.nRows; ++i) {
            for(int j = 0; j < this.nCols; ++j) {
                int[] neighborIndices = this.getNeighborhood(i * this.nCols + j, radius, isSquare);
                double[] neighborValues = new double[neighborIndices.length];
                int count = 0;
                int[] var10 = neighborIndices;
                int var11 = neighborIndices.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    int id = var10[var12];
                    neighborValues[count] = this.values[id];
                    ++count;
                }

                outLayer.values[i * this.nCols + j] = (double)countUniqueValues(neighborValues);
            }
        }

        return outLayer;
    }

    public Layer zonalMinimum(Layer inLayer, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, this.nRows, this.nCols, this.origin, this.resolution, nullValue);
        outLayer.values = new double[this.nRows * this.nCols];
        HashMap<Double, Double> zone_min = new HashMap();

        int i;
        int j;
        for(i = 0; i < this.nRows; ++i) {
            for(j = 0; j < this.nCols; ++j) {
                double zone = inLayer.values[i * this.nCols + j];
                if (!zone_min.containsKey(zone)) {
                    zone_min.put(zone, this.values[i * this.nCols + j]);
                } else if ((Double)zone_min.get(zone) > this.values[i * this.nCols + j]) {
                    zone_min.put(zone, this.values[i * this.nCols + j]);
                }
            }
        }

        for(i = 0; i < this.nRows; ++i) {
            for(j = 0; j < this.nCols; ++j) {
                outLayer.values[i * this.nCols + j] = (Double)zone_min.get(inLayer.values[i * this.nCols + j]);
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

