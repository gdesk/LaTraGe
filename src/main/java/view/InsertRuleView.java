package view;

import alice.tuprolog.Theory;
import prologConfiguration.PrologConfig;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertRuleView extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH_SIZE = 930;
    private static final int FRAME_HEIGHT_SIZE = 600;
    private static final int PANE_WIDTH_SIZE = 400;
    private static final int PANE_HEIGHT_SIZE = 1000;
    private JEditorPane textPane = new JEditorPane();
    private JEditorPane prologPane = new JEditorPane();

    public InsertRuleView() {
        setSize(FRAME_WIDTH_SIZE, FRAME_HEIGHT_SIZE);
        setResizable(false);

        JPanel infoPanel = new JPanel();
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(this);
        infoPanel.add(insertButton);

        JPanel textPanel = new JPanel();
        textPane.setSize(PANE_WIDTH_SIZE, PANE_HEIGHT_SIZE);
        textPanel.add(textPane);

        JPanel prologRulesPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(prologRulesPanel);
        prologPane.setSize(PANE_WIDTH_SIZE, PANE_HEIGHT_SIZE);
        prologPane.setEnabled(false);
        prologPane.setDisabledTextColor(Color.BLACK);
        prologPane.setText(String.valueOf(PrologConfig.engine.getTheory()));
        prologRulesPanel.add(prologPane);

        add(BorderLayout.EAST, scrollPane);
        add(BorderLayout.WEST, textPanel);
        add(BorderLayout.SOUTH, infoPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = textPane.getText();
        if(!text.isEmpty()){
            try {
                Theory theory = new Theory(text);
                PrologConfig.engine.addTheory(theory);
                prologPane.setText(PrologConfig.engine.getTheory().toString());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
