package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RulePane extends JFrame implements ActionListener {
    private static final int PANE_SIZE = 600;
    private JEditorPane textPane = new JEditorPane();

    public RulePane() {
        setSize(950,PANE_SIZE);
        setResizable(false);

        JPanel infoPanel = new JPanel();
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(this);
        infoPanel.add(insertButton);

        JPanel textPanel = new JPanel();
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
                prologPane.read(reader, "prologRules");
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        prologRulesPanel.add(prologPane);

        add(BorderLayout.EAST, scrollPane);
        add(BorderLayout.WEST, textPanel);
        add(BorderLayout.SOUTH, infoPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = textPane.getText();
        if(!text.isEmpty()){
            System.out.println("HOLA");
        }
    }
}
