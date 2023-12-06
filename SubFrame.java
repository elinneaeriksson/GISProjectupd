import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;


public class SubFrame extends MainFrame{
    static Icon icon = new ImageIcon("./data/file_folder.png"); // file icon

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

    static class LocalSubFrame{
        // Input Boxes
        JLabel inputLabel1 = new JLabel("Input 1");
        JPanel inputPanel1 = new JPanel();
        JButton inputButton1 = new JButton(icon);
        JTextField inputField1 = new JTextField(40);
        JLabel inputLabel2 = new JLabel("Input 2");
        JPanel inputPanel2 = new JPanel();
        JButton inputButton2 = new JButton(icon);
        JTextField inputField2 = new JTextField(40);

        // Output Box
        JLabel outputLabel = new JLabel("Output");
        JPanel outputPanel = new JPanel();
        JButton outputButton = new JButton(icon);
        JTextField outputField = new JTextField(40);

//        // Map scale
//        JLabel scLabel = new JLabel("Map Scale Factor");
//        JPanel scPanel = new JPanel();
//        JTextField scField = new JTextField(5);

        // Operation drop down
        JLabel opLabel = new JLabel("Operation Type");
        JPanel opPanel = new JPanel();
        String[] choices = { "Sum", "Mean", "Maximum", "Minimum", "Variety" };
        JComboBox<String> opBox = new JComboBox<String>(choices);

        // Confirm button
        JButton confirmButton = new JButton("Confirm");

        public LocalSubFrame(Project project){
            EventQueue.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    JFrame frame = new JFrame("Local Operations");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    try
                    {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(5,1));
                    panel.setOpaque(true);

                    // Input box
                    inputPanel1.setLayout(new FlowLayout());
                    inputPanel1.add(inputLabel1);
                    inputPanel1.add(inputField1);
                    inputPanel1.add(inputButton1);
                    inputButton1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String text = openDir();
                            inputField1.setText(text);
                        }
                    });
                    panel.add(inputPanel1);
                    inputPanel2.setLayout(new FlowLayout());
                    inputPanel2.add(inputLabel2);
                    inputPanel2.add(inputField2);
                    inputPanel2.add(inputButton2);
                    inputButton2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String text = openDir();
                            inputField2.setText(text);
                        }
                    });
                    panel.add(inputPanel2);

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

//                    // Scale box
//                    scPanel.setLayout(new FlowLayout());
//                    scPanel.add(scLabel);
//                    scPanel.add(scField);
//                    scPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//                    panel.add(scPanel);

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
                            String inFile1 = inputField1.getText();
                            String inFile2 = inputField2.getText();
                            String outFile = outputField.getText();
                            String op = opBox.getSelectedItem().toString();

                            toggleBox1.setSelected(true);
                            toggleBox2.setSelected(true);
                            toggleBox3.setSelected(true);

                            File file1 = new File(inFile1);
                            File file2 = new File(inFile2);

                            boolean error1 = Objects.equals(inFile1, "") ||
                                    Objects.equals(inFile2, "") ||
                                    Objects.equals(outFile, "");

                            boolean error2 = !file1.exists() || !file2.exists();

                            if (error1)
                                JOptionPane.showMessageDialog(null, "Missing parameter(s)!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (error2)
                                JOptionPane.showMessageDialog(null, "Input file(s) does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && !error2)
                                project.localOperation(inFile1, inFile2, outFile, op);
                        }
                    });

                    frame.getContentPane().add(BorderLayout.CENTER, panel);
                    frame.pack();
                    frame.setLocationByPlatform(true);
                    frame.setVisible(true);
                    frame.setResizable(false);
                    inputField1.requestFocus();
                    inputField2.requestFocus();
                }
            });
        }
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

//        // Map scale
//        JLabel scLabel = new JLabel("Map Scale Factor");
//        JPanel scPanel = new JPanel();
//        JTextField scField = new JTextField(5);

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
                    panel.setLayout(new GridLayout(5,1));
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

//                    // Scale box
//                    scPanel.setLayout(new FlowLayout());
//                    scPanel.add(scLabel);
//                    scPanel.add(scField);
//                    scPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//                    panel.add(scPanel);

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
//                            String sScale = scField.getText();
                            String sRad = nbField.getText();
                            String type = typeBox.getSelectedItem().toString();
                            String op = opBox.getSelectedItem().toString();

                            toggleBox1.setSelected(true);
                            toggleBox3.setSelected(true);

                            File file1 = new File(inFile);

                            boolean error1 = Objects.equals(inFile, "") ||
                                    Objects.equals(outFile, "");

                            boolean error2 = !file1.exists();

                            if (error1)
                                JOptionPane.showMessageDialog(null, "Missing parameter(s)!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (error2)
                                JOptionPane.showMessageDialog(null, "Input file(s) does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && !error2)
                                project.focalOperation(inFile, outFile, sRad, type, op);
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

    static class ZonalSubFrame{
        // Input Boxes
        JLabel inputLabel1 = new JLabel("Value ");
        JPanel inputPanel1 = new JPanel();
        JButton inputButton1 = new JButton(icon);
        JTextField inputField1 = new JTextField(40);
        JLabel inputLabel2 = new JLabel("Zone  ");
        JPanel inputPanel2 = new JPanel();
        JButton inputButton2 = new JButton(icon);
        JTextField inputField2 = new JTextField(40);

        // Output Box
        JLabel outputLabel = new JLabel("Output");
        JPanel outputPanel = new JPanel();
        JButton outputButton = new JButton(icon);
        JTextField outputField = new JTextField(40);

//        // Map scale
//        JLabel scLabel = new JLabel("Map Scale Factor");
//        JPanel scPanel = new JPanel();
//        JTextField scField = new JTextField(5);

        // Operation drop down
        JLabel opLabel = new JLabel("Operation Type");
        JPanel opPanel = new JPanel();
        String[] choices = { "Sum", "Mean", "Maximum", "Minimum", "Variety" };
        JComboBox<String> opBox = new JComboBox<String>(choices);

        // Confirm button
        JButton confirmButton = new JButton("Confirm");

        public ZonalSubFrame(Project project){
            EventQueue.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    JFrame frame = new JFrame("Zonal Operations");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    try
                    {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(5,1));
                    panel.setOpaque(true);

                    // Input box
                    inputPanel1.setLayout(new FlowLayout());
                    inputPanel1.add(inputLabel1);
                    inputPanel1.add(inputField1);
                    inputPanel1.add(inputButton1);
                    inputButton1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String text = openDir();
                            inputField1.setText(text);
                        }
                    });
                    panel.add(inputPanel1);
                    inputPanel2.setLayout(new FlowLayout());
                    inputPanel2.add(inputLabel2);
                    inputPanel2.add(inputField2);
                    inputPanel2.add(inputButton2);
                    inputButton2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String text = openDir();
                            inputField2.setText(text);
                        }
                    });
                    panel.add(inputPanel2);

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

//                    // Scale box
//                    scPanel.setLayout(new FlowLayout());
//                    scPanel.add(scLabel);
//                    scPanel.add(scField);
//                    scPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//                    panel.add(scPanel);

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
                            String inFile1 = inputField1.getText();
                            String inFile2 = inputField2.getText();
                            String outFile = outputField.getText();
//                            String sScale = scField.getText();
                            String op = opBox.getSelectedItem().toString();

                            toggleBox1.setSelected(true);
                            toggleBox2.setSelected(true);
                            toggleBox3.setSelected(true);

                            File file1 = new File(inFile1);
                            File file2 = new File(inFile2);

                            boolean error1 = Objects.equals(inFile1, "") ||
                                    Objects.equals(inFile2, "") ||
                                    Objects.equals(outFile, "");

                            boolean error2 = !file1.exists() || !file2.exists();

                            if (error1)
                                JOptionPane.showMessageDialog(null, "Missing parameter(s)!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (error2)
                                JOptionPane.showMessageDialog(null, "Input file(s) does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && !error2)
                                project.zonalOperation(inFile1, inFile2, outFile, op);
                        }
                    });

                    frame.getContentPane().add(BorderLayout.CENTER, panel);
                    frame.pack();
                    frame.setLocationByPlatform(true);
                    frame.setVisible(true);
                    frame.setResizable(false);
                    inputField1.requestFocus();
                    inputField2.requestFocus();
                }
            });
        }
    }

}

