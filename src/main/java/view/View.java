package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import viewModel.Initialization;
import viewModel.diagram.PlantUMLUtilsImpl;
import viewModel.InitializationImpl;

/**
 * This class implements system GUI
 */
public class View extends JFrame implements ActionListener {
    private static final int PANE_SIZE = 600;
    private static final int TEXT_SIZE = 20;
    private static final int IMAGE_PANE_SIZE = 700;
    private static final String IMAGE_PATH = "LTSImage.png";
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
        setSize(PANE_SIZE,PANE_SIZE);

        JPanel infoPane = new JPanel();
        infoPane.add(input);
        inputField.setColumns(TEXT_SIZE);
        infoPane.add(inputField);
        processButton.addActionListener(this);
        infoPane.add(processButton);
        newRuleButton.addActionListener(e -> ruleDisplay());
        infoPane.add(newRuleButton);
        exitButton.addActionListener(e -> System.exit(0));
        infoPane.add(exitButton);

        JPanel linkPane = new JPanel();
        httpField.setColumns(150);
        linkPane.add(httpField);

        imagePane = new JPanel();
        imagePane.setPreferredSize(new Dimension(IMAGE_PANE_SIZE, IMAGE_PANE_SIZE));
        JScrollPane scrollPane = new JScrollPane(imagePane);

        add(BorderLayout.CENTER, scrollPane);
        add(BorderLayout.SOUTH, linkPane);
        add(BorderLayout.NORTH, infoPane);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void ruleDisplay() {
        JFrame d = new JFrame();
        d.setSize(950,PANE_SIZE);
        d.setResizable(false);

        JPanel infoPanel = new JPanel();
        JButton insertButton = new JButton("Insert");
        infoPanel.add(insertButton);

        JPanel textPanel = new JPanel();
        JEditorPane textPane = new JEditorPane();
        textPane.setSize(400, 1000);
        textPanel.add(textPane);

        JPanel prologRulesPanel = new JPanel();
        JTextArea prologPane = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(prologRulesPanel);
        prologPane.setSize(400, 100);
        try {
            String textLine = "";
            FileReader fr = new FileReader("src/main/prolog/LTSOperators.pl");
            BufferedReader reader = new BufferedReader(fr);
            while((textLine = reader.readLine()) != null){
                prologPane.read(reader, "jTextArea1");
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        prologRulesPanel.add(prologPane);

        d.add(BorderLayout.EAST, scrollPane);
        d.add(BorderLayout.WEST, textPanel);
        d.add(BorderLayout.SOUTH, infoPanel);
        d.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = inputField.getText();
        if(!text.isEmpty()){
            try {
                initialization = new InitializationImpl();
                initialization.start(text);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        try {
            createImage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createImage() throws IOException {
        String url = new PlantUMLUtilsImpl().generateImage(IMAGE_PATH);
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
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
