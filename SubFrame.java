import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;


public class SubFrame extends MainFrame{
    static Icon fileIcon = new ImageIcon("./icons/file_folder.png"); // file icon

    public static String openDir(){
        JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        fileChooser.showOpenDialog(new JFrame());
        File selectedFile = fileChooser.getSelectedFile();
        if (selectedFile != null)
            return selectedFile.getAbsolutePath();
        else
            return "";
    }

    static class LocalSubFrame{
        // Input Boxes
        JLabel inputLabel1 = new JLabel("Input 1");
        JPanel inputPanel1 = new JPanel();
        JButton inputButton1 = new JButton(fileIcon);
        JTextField inputField1 = new JTextField(40);
        JLabel inputLabel2 = new JLabel("Input 2");
        JPanel inputPanel2 = new JPanel();
        JButton inputButton2 = new JButton(fileIcon);
        JTextField inputField2 = new JTextField(40);

        // Output Box
        JLabel outputLabel = new JLabel("Output");
        JPanel outputPanel = new JPanel();
        JButton outputButton = new JButton(fileIcon);
        JTextField outputField = new JTextField(40);

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
                    frame.setLocationRelativeTo(null);
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
                    panel.add(outputPanel);

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

                            File file1 = new File(inFile1);
                            File file2 = new File(inFile2);

                            boolean error1 = Objects.equals(inFile1, "") ||
                                    Objects.equals(inFile2, "") ||
                                    Objects.equals(outFile, "");
                            boolean error2 = !file1.exists() || !file2.exists();
                            int error3 = 0;
                            if (!error2){
                                // if no error2, check dimension
                                Layer layer1 = new Layer("", inFile1);
                                Layer layer2 = new Layer("", inFile2);
                                boolean err3 = !((layer1.nRows==layer2.nRows) && (layer1.nCols==layer2.nCols) && (layer1.resolution==layer2.resolution));

                                if(!err3)  // no error2 and error3
                                    error3 = 1;
                            }

                            if (error1)
                                JOptionPane.showMessageDialog(null, "Missing parameter(s)!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && error2)
                                JOptionPane.showMessageDialog(null, "Input file(s) does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && !error2 && error3 == 0)
                                JOptionPane.showMessageDialog(null, "Inputs do not have the same dimension!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && !error2 && error3==1){
                                frame.dispose();
                                project.localOperation(inFile1, inFile2, outFile, op);
                            }
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
        JLabel inputLabel = new JLabel("Input    ");
        JPanel inputPanel = new JPanel();
        JButton inputButton = new JButton(fileIcon);
        JTextField inputField = new JTextField(40);

        // Output Box
        JLabel outputLabel = new JLabel("Output ");
        JPanel outputPanel = new JPanel();
        JButton outputButton = new JButton(fileIcon);
        JTextField outputField = new JTextField(40);

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
                    panel.add(outputPanel);

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

                            File file1 = new File(inFile);

                            boolean error1 = Objects.equals(inFile, "") ||
                                    Objects.equals(outFile, "") ||
                                    Objects.equals(sRad, "");
                            boolean error2 = !file1.exists();
                            boolean error3 = !sRad.matches("\\d+");

                            if (error1)
                                JOptionPane.showMessageDialog(null, "Missing parameter(s)!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && error2)
                                JOptionPane.showMessageDialog(null, "Input file(s) does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && error3)
                                JOptionPane.showMessageDialog(null, "Radius must be an integer!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && !error2 && !error3){
                                frame.dispose();
                                project.focalOperation(inFile, outFile, sRad, type, op);
                            }
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
        JLabel inputLabel1 = new JLabel("Value  ");
        JPanel inputPanel1 = new JPanel();
        JButton inputButton1 = new JButton(fileIcon);
        JTextField inputField1 = new JTextField(40);
        JLabel inputLabel2 = new JLabel("Zone   ");
        JPanel inputPanel2 = new JPanel();
        JButton inputButton2 = new JButton(fileIcon);
        JTextField inputField2 = new JTextField(40);

        // Output Box
        JLabel outputLabel = new JLabel("Output");
        JPanel outputPanel = new JPanel();
        JButton outputButton = new JButton(fileIcon);
        JTextField outputField = new JTextField(40);

        // Operation drop down
        JLabel opLabel = new JLabel("Operation Type");
        JPanel opPanel = new JPanel();
        String[] choices = { "Sum", "Mean", "Maximum", "Minimum", "Variety", "Majority", "Minority"};
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
                    panel.add(outputPanel);

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
                            final String inFile1 = inputField1.getText();
                            final String inFile2 = inputField2.getText();
                            final String outFile = outputField.getText();
                            final String op = opBox.getSelectedItem().toString();

                            File file1 = new File(inFile1);
                            File file2 = new File(inFile2);

                            boolean error1 = Objects.equals(inFile1, "") ||
                                    Objects.equals(inFile2, "") ||
                                    Objects.equals(outFile, "");
                            boolean error2 = !file1.exists() || !file2.exists();
                            int error3 = 0;
                            if (!error2){
                                // if no error2, check dimension
                                Layer layer1 = new Layer("", inFile1);
                                Layer layer2 = new Layer("", inFile2);
                                boolean err3 = !((layer1.nRows==layer2.nRows) && (layer1.nCols==layer2.nCols) && (layer1.resolution==layer2.resolution));
                                if(!err3)  // no error2 and error3
                                    error3 = 1;
                            }

                            if (error1)
                                JOptionPane.showMessageDialog(null, "Missing parameter(s)!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && error2)
                                JOptionPane.showMessageDialog(null, "Input file(s) does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1  && !error2 && error3 == 0)
                                JOptionPane.showMessageDialog(null, "Inputs do not have the same dimension!", "Error", JOptionPane.ERROR_MESSAGE);
                            if (!error1 && !error2 && error3==1){
                                frame.dispose();
                                project.zonalOperation(inFile1, inFile2, outFile, op);
                            }
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

