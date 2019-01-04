package view;

import alice.tuprolog.InvalidTheoryException;
import utils.PlantUMLutilsImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PikaView extends JFrame implements ActionListener {
    private static final int PANE_SIZE = 600;
    private static final int TEXT_SIZE = 20;
    private static final String IMAGE_PATH = "LTSimage.png";
    private static final JTextField inputField = new JTextField();
    private static final JLabel input = new JLabel("Input: ");
    private static final JButton exitButton = new JButton("Exit");
    private static final JButton processButton = new JButton("Process");
    private static final JButton newRuleButton = new JButton("Insert new rule");
    private JLabel image = new JLabel();
    private JPanel imagePane;
    private Computing computing;

    public PikaView(){
        setLayout(new BorderLayout());

        JPanel infoPane = new JPanel();
        infoPane.add(input);
        inputField.setColumns(TEXT_SIZE);
        infoPane.add(inputField);

        processButton.addActionListener(this);
        infoPane.add(processButton);

        infoPane.add(newRuleButton);

        exitButton.addActionListener(e -> System.exit(0));
        infoPane.add(exitButton);

        imagePane = new ImageUtils();
        JScrollPane scrollPane = new JScrollPane(imagePane);
        add(BorderLayout.CENTER, scrollPane);

        add(BorderLayout.NORTH, infoPane);
        setSize(PANE_SIZE,PANE_SIZE);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = inputField.getText();
        if(!text.isEmpty()){
            try {
                computing = new Computing();
                computing.initialization(text);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        try {
            createImage();
        } catch (IOException | InvalidTheoryException e1) {
            e1.printStackTrace();
        }
        inputField.setText("");
    }

    private void createImage() throws IOException, InvalidTheoryException {
        new PlantUMLutilsImpl().generateImage();
        computing.reset();
        setImagePane();
    }

    private void setImagePane() {
        imagePane.removeAll();
        try {
            BufferedImage img = ImageIO.read(new File(IMAGE_PATH));
            this.image.setIcon(new ImageIcon(img));
            this.image.revalidate();
            this.image.repaint();
        } catch (IOException ex) {}

        imagePane.add(BorderLayout.CENTER, image);
        imagePane.validate();
        imagePane.repaint();
    }
}
