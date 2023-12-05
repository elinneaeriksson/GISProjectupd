import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame{
    private Project project;
    public MapPanel inMap, outMap;
    JButton localButton, focalButton, zonalButton, toggleButton;

    public void setProject(Project project){
        this.project = project;
    }

    public void setMaps(MapPanel inMap, MapPanel outMap){
        this.inMap = inMap;
        this.outMap = outMap;
        Dimension dimension = new Dimension(720, 720);
        inMap.setPreferredSize(dimension);
        outMap.setPreferredSize(dimension);
    }

    public MainFrame(){
        setTitle("App");
        localButton = new JButton("Local Operations");
        focalButton = new JButton("Focal Operations");
        zonalButton = new JButton("Zonal Operations");
        toggleButton = new JButton("Toggle Result");

        focalButton.addActionListener(e ->{
            SubFrame.FocalSubFrame focalSubFrame = new SubFrame.FocalSubFrame(project);
        });

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        // Button panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(localButton);
        panel.add(focalButton);
        panel.add(zonalButton);
        panel.add(toggleButton);
        getContentPane().add(panel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        revalidate();
        repaint();
        pack();
    }

    public void addMap(int rows, int cols, int scale){
        JLayeredPane layeredPane = new JLayeredPane();
        JScrollPane scroller = new JScrollPane(outMap);
        layeredPane.add(scroller);
        int width = cols * scale;
        int height = rows * scale;
        layeredPane.setPreferredSize(new Dimension(width, height));
        inMap.setBounds(0, 0, width, height);
        outMap.setBounds(0, 0, width, height);
        layeredPane.add(inMap, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(outMap, JLayeredPane.PALETTE_LAYER);
        add(layeredPane);

        toggleButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle the visibility of the result panel
                outMap.setVisible(!outMap.isVisible());
            }
        });

        revalidate();
        repaint();
        pack();
    }
}
