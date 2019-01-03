package view;


import alice.tuprolog.InvalidTheoryException;
import utils.PlantUMLutilsImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PikaView extends JFrame implements ActionListener {
    private static final int PANE_SIZE = 600;
    private static final int TEXT_SIZE = 20;
    private static final JTextField inputField = new JTextField();
    private static final JLabel input = new JLabel("Input: ");
    private static JButton exitButton = new JButton("Exit");
    private static JButton processButton = new JButton("Process");
    private Computing computing;

    public PikaView(){
        setLayout(new BorderLayout());

        JPanel infoPane = new JPanel();
        infoPane.add(input);
        inputField.setColumns(TEXT_SIZE);
        infoPane.add(inputField);

        processButton.addActionListener(this);
        infoPane.add(processButton);

        exitButton.addActionListener(e -> System.exit(0));
        infoPane.add(exitButton);

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
            } finally {
                new PlantUMLutilsImpl();
                try {
                    new PlantUMLutilsImpl().generateImage();
                    computing.reset();
                } catch (Exception ex1) {
                    ex1.printStackTrace();
                }
            }
        }
        inputField.setText("");
    }
}
