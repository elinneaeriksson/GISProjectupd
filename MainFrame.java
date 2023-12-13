import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.text.DecimalFormat;

public class MainFrame extends JFrame{
    public static MapPanel inMap1, inMap2, outMap;

    private Project project;

    private final JPanel mapPanel = new JPanel();

    public static JCheckBox toggleBox1 = new JCheckBox("Layer 1");
    public static JCheckBox toggleBox2 = new JCheckBox("Layer 2");
    public static JCheckBox toggleBox3 = new JCheckBox("Result");

    public static JTextArea minMax1 = new JTextArea();
    public static JTextArea minMax2 = new JTextArea();
    public static JTextArea minMax3 = new JTextArea();

    public JTextArea messageBox = new JTextArea("Message");

    private final JLayeredPane layeredPane = new JLayeredPane();

    HashMap<String, Icon> itemImageMap = new HashMap<>();
    private final JComboBox<String> colorBox = new JComboBox<>(new String[] { "Black and White", "Cool", "Heat", "Rainbow" });

    public static final JProgressBar progressBar = new JProgressBar(0, 100);

    public MainFrame(){
        setTitle("Raster");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        JButton localButton = new JButton("Local Operations");
        JButton focalButton = new JButton("Focal Operations");
        JButton zonalButton = new JButton("Zonal Operations");

        localButton.addActionListener(e ->{
            SubFrame.LocalSubFrame localSubFrame = new SubFrame.LocalSubFrame(project);
        });
        focalButton.addActionListener(e ->{
            SubFrame.FocalSubFrame focalSubFrame = new SubFrame.FocalSubFrame(project);
        });
        zonalButton.addActionListener(e ->{
            SubFrame.ZonalSubFrame zonalSubFrame = new SubFrame.ZonalSubFrame(project);
        });

        // Button panel
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(gridBag);
        c.insets = new Insets(2, 2, 2, 2);  // Border pads
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 60;
        c.gridy = 0;
        gridBag.setConstraints(localButton, c);
        buttonPanel.add(localButton);
        c.gridy = 1;
        gridBag.setConstraints(focalButton, c);
        buttonPanel.add(focalButton);
        c.gridy = 2;
        gridBag.setConstraints(zonalButton, c);
        buttonPanel.add(zonalButton);

        c.ipady = 10;
        c.gridy = 3;
        gridBag.setConstraints(toggleBox3, c);
        buttonPanel.add(toggleBox3);
        c.gridy = 4;
        gridBag.setConstraints(minMax3, c);
        buttonPanel.add(minMax3);
        minMax3.setEditable(false);

        c.gridy = 5;
        gridBag.setConstraints(toggleBox2, c);
        buttonPanel.add(toggleBox2);
        c.gridy = 6;
        gridBag.setConstraints(minMax2, c);
        buttonPanel.add(minMax2);
        minMax2.setEditable(false);

        c.gridy = 7;
        gridBag.setConstraints(toggleBox1, c);
        buttonPanel.add(toggleBox1);
        c.gridy = 8;
        gridBag.setConstraints(minMax1, c);
        buttonPanel.add(minMax1);
        minMax1.setEditable(false);

        c.ipady = 10;
        c.gridy = 9;
        JLabel cLabel = new JLabel("Change colors");
        gridBag.setConstraints(cLabel, c);
        buttonPanel.add(cLabel);

        c.ipady = 5;
        c.gridy = 10;
        gridBag.setConstraints(colorBox, c);
        buttonPanel.add(colorBox);
        itemImageMap.put("Black and White", new ImageIcon("./icons/bw.png"));
        itemImageMap.put("Cool", new ImageIcon("./icons/cool.png"));
        itemImageMap.put("Heat", new ImageIcon("./icons/heat.png"));
        itemImageMap.put("Rainbow", new ImageIcon("./icons/rainbow.png"));
        colorBox.setRenderer(new ImageComboBoxRenderer(itemImageMap));

        c.ipady = 150;
        c.gridy = 11;
        JScrollPane scrollPane = new JScrollPane(messageBox);
        gridBag.setConstraints(scrollPane, c);
        messageBox.setLineWrap(true);
        messageBox.setWrapStyleWord(true);
        messageBox.setEditable(false);
        buttonPanel.add(scrollPane);

        c.ipady = 5;
        c.gridx = 0;
        c.gridy = 12;
        gridBag.setConstraints(progressBar, c);
        progressBar.setStringPainted(true);
        buttonPanel.add(progressBar);

        add(buttonPanel);

        // Map panel
        mapPanel.setBackground(Color.white);
        mapPanel.setPreferredSize(new Dimension(800, mapPanel.getPreferredSize().height));
        add(mapPanel);

        mapEvents();

        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        revalidate();
        repaint();
        pack();
    }

    public JPanel getMapPanel(){return mapPanel;};

    public void showMessage(String message) {
        messageBox.setText(message);
    }

    public void setProject(Project project){
        this.project = project;
    }

    public void setMaps(MapPanel inMap1, MapPanel inMap2, MapPanel outMap){
        MainFrame.inMap1 = inMap1;
        MainFrame.inMap2 = inMap2;
        MainFrame.outMap = outMap;
    }

    public void addMap(int rows, int cols, int scale){
        colorBox.setSelectedIndex(0);
        layeredPane.removeAll();

        int width = cols * scale;
        int height = rows * scale;
        inMap1.setBounds(0, 0, width, height);
        outMap.setBounds(0, 0, width, height);
        layeredPane.add(inMap1, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(outMap, JLayeredPane.PALETTE_LAYER);
        layeredPane.setPreferredSize(new Dimension(width, height));

        DecimalFormat df = new DecimalFormat("0.00");
        minMax1.setText("Min: " + df.format(inMap1.layer.getMin()) + " Max: " + df.format(inMap1.layer.getMax()));
        minMax2.setText("");
        minMax3.setText("Min: " + df.format(outMap.layer.getMin()) + " Max: " + df.format(outMap.layer.getMax()));

        toggleBox1.setSelected(true);
        toggleBox2.setSelected(false);
        toggleBox3.setSelected(true);

        inMap1.setVisible(true);
        outMap.setVisible(true);

        // Center layeredPane
        mapPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mapPanel.add(layeredPane, gbc);

        outMap.getPixelValue();

        revalidate();
        repaint();
    }

    public void addMap(int rows, int cols, int scale, int inLayers){
        colorBox.setSelectedIndex(0);
        layeredPane.removeAll();

        int width = cols * scale;
        int height = rows * scale;
        inMap1.setBounds(0, 0, width, height);
        inMap2.setBounds(0, 0, width, height);
        outMap.setBounds(0, 0, width, height);
        layeredPane.add(inMap1, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(inMap2, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(outMap, JLayeredPane.MODAL_LAYER);
        layeredPane.setPreferredSize(new Dimension(width, height));

        DecimalFormat df=new DecimalFormat("0.00");
        minMax1.setText("Min: " + df.format(inMap1.layer.getMin()) + " Max: " + df.format(inMap1.layer.getMax()));
        minMax2.setText("Min: " + df.format(inMap2.layer.getMin()) + " Max: " + df.format(inMap2.layer.getMax()));
        minMax3.setText("Min: " + df.format(outMap.layer.getMin()) + " Max: " + df.format(outMap.layer.getMax()));

        toggleBox1.setSelected(true);
        toggleBox2.setSelected(true);
        toggleBox3.setSelected(true);

        inMap1.setVisible(true);
        inMap2.setVisible(true);
        outMap.setVisible(true);

        // Center layeredPane
        mapPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mapPanel.add(layeredPane, gbc);

        outMap.getPixelValue();

        revalidate();
        repaint();
    }

    public void renewMap(MapPanel inMap1, MapPanel inMap2, MapPanel outMap) {
        int width = layeredPane.getWidth();
        int height = layeredPane.getHeight();

        // Set bounds before adding to pane
        inMap1.setBounds(0, 0, width, height);
        if (inMap2 != null)
            inMap2.setBounds(0, 0, width, height);
        outMap.setBounds(0, 0, width, height);

        layeredPane.removeAll();
        layeredPane.add(inMap1, JLayeredPane.DEFAULT_LAYER);
        if (inMap2 != null){
            layeredPane.add(inMap2, JLayeredPane.PALETTE_LAYER);
            layeredPane.add(outMap, JLayeredPane.MODAL_LAYER);
            toggleBox1.setSelected(true);
            toggleBox2.setSelected(true);
            toggleBox3.setSelected(true);
        }
        else{
            layeredPane.add(outMap, JLayeredPane.PALETTE_LAYER);
            toggleBox1.setSelected(true);
            toggleBox3.setSelected(true);
        }

        outMap.getPixelValue();

        // Update the layout
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public void mapEvents(){
        SwingUtilities.invokeLater(() -> {
            // Swing operations here
            toggleBox1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (inMap1 != null)
                        inMap1.setVisible(!inMap1.isVisible());
                    else
                        JOptionPane.showMessageDialog(null, "This layer is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            toggleBox2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (inMap2 != null)
                        inMap2.setVisible(!inMap2.isVisible());
                    else
                        JOptionPane.showMessageDialog(null, "This layer is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            toggleBox3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (outMap != null)
                        outMap.setVisible(!outMap.isVisible());
                    else
                        JOptionPane.showMessageDialog(null, "This layer is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            colorBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (colorBox.hasFocus()){  // if mouse made the selection
                        String color = colorBox.getSelectedItem().toString();

                        if(inMap1 == null){
                            JOptionPane.showMessageDialog(null, "The map panel is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            inMap1 = inMap1.changeMapColor(inMap1, color, inMap1.scale);
                            outMap = outMap.changeMapColor(outMap, color, outMap.scale);
                            if (inMap2 != null)
                                inMap2 = inMap2.changeMapColor(inMap2, color, inMap2.scale);

                            renewMap(inMap1, inMap2, outMap);
                        }
                    }
                }
            });

            revalidate();
            repaint();
        });
    }
}
