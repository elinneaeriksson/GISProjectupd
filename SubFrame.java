import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;


public class SubFrame extends JFrame{
    static Icon icon = new ImageIcon("./data/file_folder.png"); // file icon

    static class LocalSubFrame{
        // Input Boxes
        JLabel inputLabel1 = new JLabel("Input 1 ");
        JPanel inputPanel1 = new JPanel();
        JButton inputButton1 = new JButton(icon);
        JTextField inputField1 = new JTextField(40);
        JLabel inputLabel2 = new JLabel("Input 2 ");
        JPanel inputPanel2 = new JPanel();
        JButton inputButton2 = new JButton(icon);
        JTextField inputField2 = new JTextField(40);

        // Output Box
        JLabel outputLabel = new JLabel("Output");
        JPanel outputPanel = new JPanel();
        JButton outputButton = new JButton(icon);
        JTextField outputField = new JTextField(40);

        // Map scale
        JLabel scLabel = new JLabel("Map Scale Factor");
        JPanel scPanel = new JPanel();
        JTextField scField = new JTextField(5);

        // Operation drop down
        JLabel opLabel = new JLabel("Operation Type");
        JPanel opPanel = new JPanel();
        String[] choices = { "Sum", "Mean", "Maximum", "Minimum", "Variety" };
        JComboBox<String> opBox = new JComboBox<String>(choices);

        // Confirm button
        JButton confirmButton = new JButton("Confirm");
    }

    static class FocalSubFrame{
        // Input Box
        JLabel inputLabel = new JLabel("Input   ");
        JPanel inputPanel = new JPanel();
        JButton inputButton = new JButton(icon);
        JTextField inputField = new JTextField(40);

        // Output Box
        JLabel outputLabel = new JLabel("Output");
        JPanel outputPanel = new JPanel();
        JButton outputButton = new JButton(icon);
        JTextField outputField = new JTextField(40);

        // Map scale
        JLabel scLabel = new JLabel("Map Scale Factor");
        JPanel scPanel = new JPanel();
        JTextField scField = new JTextField(5);

        // Neighbor radius
        JLabel nbLabel1 = new JLabel("Neighborhood Radius");
        JLabel nbLabel2 = new JLabel("Neighborhood Type");
        JPanel nbPanel = new JPanel();
        JTextField nbField = new JTextField(5);
        String[] types = { "Square", "Circle" };
        JComboBox<String> typeBox = new JComboBox<String>(types);

        // Operation drop down
        JLabel opLabel = new JLabel("Operation Type");
        JPanel opPanel = new JPanel();
        String[] choices = { "Sum", "Mean", "Maximum", "Minimum", "Variety" };
        JComboBox<String> opBox = new JComboBox<String>(choices);

        // Confirm button
        JButton confirmButton = new JButton("Confirm");

        public FocalSubFrame(Project project){
            EventQueue.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    JFrame frame = new JFrame("Focal Operations");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    try
                    {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(6,1));
                    panel.setOpaque(true);

                    // Input box
                    inputPanel.setLayout(new FlowLayout());
                    inputPanel.add(inputLabel);
                    inputPanel.add(inputField);
                    inputPanel.add(inputButton);
                    inputButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String text = openDir();
                            inputField.setText(text);
                        }
                    });
                    panel.add(inputPanel);
//                panel.setAlignmentX(Component.CENTER_ALIGNMENT);

                    // Output box
                    outputPanel.setLayout(new FlowLayout());
                    outputPanel.add(outputLabel);
                    outputPanel.add(outputField);
                    outputPanel.add(outputButton);
                    outputButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String text = openDir();
                            outputField.setText(text);
                        }
                    });
//                outputPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
                    panel.add(outputPanel);

                    // Scale box
                    scPanel.setLayout(new FlowLayout());
                    scPanel.add(scLabel);
                    scPanel.add(scField);
                    scPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    panel.add(scPanel);

                    // Neighborhood box
                    nbPanel.setLayout(new FlowLayout());
                    nbPanel.add(nbLabel1);
                    nbPanel.add(nbField);
                    nbPanel.add(nbLabel2);
                    nbPanel.add(typeBox);
                    nbPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    panel.add(nbPanel);

                    // Operation drop down list
                    opPanel.setLayout(new FlowLayout());
                    opPanel.add(opLabel);
                    opPanel.add(opBox);
                    opPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
                    panel.add(opPanel);

                    // Confirm button
                    panel.add(confirmButton);
                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String inFile = inputField.getText();
                            String outFile = outputField.getText();
                            String sScale = scField.getText();
                            String sRad = nbField.getText();
                            String type = typeBox.getSelectedItem().toString();
                            String op = opBox.getSelectedItem().toString();

                            if (Objects.equals(inFile, "") ||
                                    Objects.equals(outFile, "") ||
                                    Objects.equals(sScale, "") ||
                                    Objects.equals(sRad, "")){
                                JOptionPane.showMessageDialog(null, "Missing parameter(s)!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                                project.focalOperation(inFile, outFile, sScale, sRad, type, op);
                        }
                    });

                    frame.getContentPane().add(BorderLayout.CENTER, panel);
                    frame.pack();
                    frame.setLocationByPlatform(true);
                    frame.setVisible(true);
                    frame.setResizable(false);
                    inputField.requestFocus();
                }
            });
        }
    }

    public static String openDir(){
        JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        fileChooser.showOpenDialog(new JFrame());
        File selectedFile = fileChooser.getSelectedFile();
        if (selectedFile != null)
            return selectedFile.getAbsolutePath();
        else
            return "Please choose a file!";
    }
}

