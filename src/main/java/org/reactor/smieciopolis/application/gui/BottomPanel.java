package org.reactor.smieciopolis.application.gui;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.UIManager.getIcon;

import java.awt.event.ActionEvent;

import javax.swing.*;

public class BottomPanel extends JPanel {

    public static final int MARGIN = 10;
    private JButton executeButton;

    public BottomPanel() {
        initializePanelLayout();
        initializePanelComponents();
    }

    private void initializePanelLayout() {
        addMarginBorder();
    }

    private void addMarginBorder() {
        setBorder(createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
    }

    private void initializePanelComponents() {
        add(executeButton = new JButton(new AbstractAction("Generuj kalendarze", getIcon("OptionPane.yesIcon")) {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        onExecuteButtonClicked();
                    }
                });
            }
        }));
        executeButton.setEnabled(false);
    }

    protected void onExecuteButtonClicked() {

    }

    public void enableExecuteButton() {
        executeButton.setEnabled(true);
    }
}
