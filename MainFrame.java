import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame{
    public MapPanel inMap1, inMap2, outMap;
    private Project project;
    private final JPanel mapPanel = new JPanel();
    public static JCheckBox toggleBox1 = new JCheckBox("Layer 1");
    public static JCheckBox toggleBox2 = new JCheckBox("Layer 2");
    public static JCheckBox toggleBox3 = new JCheckBox("Result");
    public JTextArea messageBox = new JTextArea("Message");
    private final JLayeredPane layeredPane = new JLayeredPane();
    String[] choices = { "Black and White", "Cool", "Heat", "Rainbow", "Pastel"};
    private final JComboBox<String> colorBox = new JComboBox<>(choices);
    public static final JProgressBar progressBar = new JProgressBar(0, 100);

    public MainFrame(){
        setTitle("App");
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
        c.gridx = 0;
        c.gridy = 0;
        gridBag.setConstraints(localButton, c);
        buttonPanel.add(localButton);
        c.gridx = 0;
        c.gridy = 1;
        gridBag.setConstraints(focalButton, c);
        buttonPanel.add(focalButton);
        c.gridx = 0;
        c.gridy = 2;
        gridBag.setConstraints(zonalButton, c);
        buttonPanel.add(zonalButton);

        c.gridx = 0;
        c.gridy = 3;
        gridBag.setConstraints(toggleBox3, c);
        buttonPanel.add(toggleBox3);
        c.gridx = 0;
        c.gridy = 4;
        gridBag.setConstraints(toggleBox2, c);
        buttonPanel.add(toggleBox2);

        c.gridx = 0;
        c.gridy = 5;
        gridBag.setConstraints(toggleBox1, c);
        buttonPanel.add(toggleBox1);

        c.ipady = 10;
        c.gridx = 0;
        c.gridy = 6;
        JLabel cLabel = new JLabel("Change colors");
        gridBag.setConstraints(cLabel, c);
        buttonPanel.add(cLabel);

        c.ipady = 5;
        c.gridx = 0;
        c.gridy = 7;
        gridBag.setConstraints(colorBox, c);
        buttonPanel.add(colorBox);

        c.ipady = 150;
        c.gridx = 0;
        c.gridy = 8;
        JScrollPane scrollPane = new JScrollPane(messageBox);
        gridBag.setConstraints(scrollPane, c);
        messageBox.setLineWrap(true);
        messageBox.setWrapStyleWord(true);
        buttonPanel.add(scrollPane);

        c.ipady = 5;
        c.gridx = 0;
        c.gridy = 9;
        gridBag.setConstraints(progressBar, c);
        progressBar.setStringPainted(true);
        buttonPanel.add(progressBar);

        add(buttonPanel);

        // Map panel
        mapPanel.setBackground(Color.LIGHT_GRAY);
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

    public void setMaps(MapPanel inMap, MapPanel outMap){
        this.inMap1 = inMap;
        this.outMap = outMap;
    }

    public void setMaps(MapPanel inMap1, MapPanel inMap2, MapPanel outMap){
        this.inMap1 = inMap1;
        this.inMap2 = inMap2;
        this.outMap = outMap;
    }

    public void addMap(int rows, int cols, int scale){
        layeredPane.removeAll();
        int width = cols * scale;
        int height = rows * scale;
        inMap1.setBounds(0, 0, width, height);
        outMap.setBounds(0, 0, width, height);
        layeredPane.add(inMap1, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(outMap, JLayeredPane.PALETTE_LAYER);
        layeredPane.setPreferredSize(new Dimension(width, height));

        inMap1.setVisible(true);
        outMap.setVisible(true);

        mapPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mapPanel.add(layeredPane, gbc);

        revalidate();
        repaint();
    }

    public void addMap(int rows, int cols, int scale, int inLayers){
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

        inMap1.setVisible(true);
        inMap2.setVisible(true);
        outMap.setVisible(true);

        mapPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mapPanel.add(layeredPane, gbc);

        revalidate();
        repaint();
    }

    public void changeLayeredPane(MapPanel inMap1, MapPanel inMap2, MapPanel outMap) {
        int width = layeredPane.getWidth();
        int height = layeredPane.getHeight();

        // Set bounds before adding to pane
        inMap1.setBounds(0, 0, width, height);
        if (!(inMap2 == null))
            inMap2.setBounds(0, 0, width, height);
        outMap.setBounds(0, 0, width, height);

        layeredPane.removeAll();
        layeredPane.add(inMap1, JLayeredPane.DEFAULT_LAYER);
        if (!(inMap2 == null)){
            layeredPane.add(inMap2, JLayeredPane.PALETTE_LAYER);
            layeredPane.add(outMap, JLayeredPane.MODAL_LAYER);
        }
        else
            layeredPane.add(outMap, JLayeredPane.PALETTE_LAYER);


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
                    inMap1.setVisible(!inMap1.isVisible());
                }
            });
            toggleBox2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    inMap2.setVisible(!inMap2.isVisible());
                }
            });
            toggleBox3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    outMap.setVisible(!outMap.isVisible());
                }
            });

            colorBox.addActionListener(e -> {
                String color = colorBox.getSelectedItem().toString();
                inMap1 = inMap1.changeMapColor(inMap1, color, inMap1.scale);
                if (!(inMap2 == null))
                    inMap2 = inMap2.changeMapColor(inMap2, color, inMap2.scale);
                outMap = outMap.changeMapColor(outMap, color, outMap.scale);
                changeLayeredPane(inMap1, inMap2, outMap);
            });

        });

        revalidate();
        repaint();
        //pack();
    }

    public static void simulateProcess(JProgressBar progressBar) {
        // Simulate a process with a loop
        for (int i = 0; i <= 100; i++) {
            try {
                // Simulate some work
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            // Update the progress bar
            progressBar.setValue(i);
        }

        // Reset the progress bar after completion
        //progressBar.setValue(0);
    }
}
