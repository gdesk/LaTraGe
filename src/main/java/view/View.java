package view;

import alice.tuprolog.InvalidTheoryException;
import viewModel.diagram.PlantUMLutilsImpl;
import viewModel.Initialization;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class View extends JFrame implements ActionListener {
    private static final int PANE_SIZE = 600;
    private static final int TEXT_SIZE = 20;
    private static final int IMAGE_PANE_SIZE = 700;
    private static final String IMAGE_PATH = "LTSimage.png";
    private static final JTextField inputField = new JTextField();
    private static final JTextField httpField = new JTextField();

    private static final JLabel input = new JLabel("Input: ");
    private static final JButton exitButton = new JButton("Exit");
    private static final JButton processButton = new JButton("Process");
    private static final JButton newRuleButton = new JButton("Insert new rule");
    private JLabel image = new JLabel();
    private JPanel imagePane;
    private Initialization initialization;

    public View(){
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

        JPanel linkPane = new JPanel();
        httpField.setColumns(150);
        //httpField.setEnabled(false);
        linkPane.add(httpField);

        imagePane = new JPanel();
        imagePane.setPreferredSize(new Dimension(IMAGE_PANE_SIZE, IMAGE_PANE_SIZE));

        JScrollPane scrollPane = new JScrollPane(imagePane);

        add(BorderLayout.CENTER, scrollPane);
        add(BorderLayout.SOUTH, linkPane);
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
                initialization = new Initialization();
                initialization.start(text);
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
        String url = new PlantUMLutilsImpl().generateImage();
        initialization.reset();
        setImagePane();
        setLink(url);
    }

    private void setImagePane() {
        imagePane.removeAll();
        try {
            BufferedImage img = ImageIO.read(new File(IMAGE_PATH));
            this.image.setIcon(new ImageIcon(img));
            this.image.revalidate();
            this.image.repaint();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
        imagePane.add(BorderLayout.CENTER, image);
        imagePane.validate();
        imagePane.repaint();
    }

    private void setLink(String url) {
        httpField.setText(url);
        httpField.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        httpField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI(url);
                            desktop.browse(uri);
                        } catch (IOException | URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

    }
}
