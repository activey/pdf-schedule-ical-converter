package org.reactor.smieciopolis.application.gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_END;
import static java.awt.BorderLayout.PAGE_START;

public class ApplicationWindow extends JFrame {

    private static final String APPLICATION_TITLE = "Śmieciopolis - konwerter kalendarzy";
    private static final Dimension WINDOW_SIZE = new Dimension(800, 600);
    
    private TopPanel topPanel;
    private BottomPanel bottomPanel;
    private OperationsArea operationsArea;

    public ApplicationWindow() {
        super(APPLICATION_TITLE);
        
        initializeDimension();
        initializeLayout();
        initializeEventListeners();
        initializeComponents();
    }

    private void initializeDimension() {
        setPreferredSize(WINDOW_SIZE);
        setResizable(false);
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
    }

    private void initializeEventListeners() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeComponents() {
        add(topPanel = new TopPanel() {
            @Override
            protected void onRequirementsMet() {
                bottomPanel.enableExecuteButton();
            }
        }, PAGE_START);
        add(operationsArea = new OperationsArea(), CENTER);
        add(bottomPanel = new BottomPanel() {
            @Override
            protected void onExecuteButtonClicked() {
                operationsArea.generateCalendars(topPanel.getInputPDF(), topPanel.getOutputFolder());
            }
        }, PAGE_END);
    }

    public void start() {
        pack();
        setVisible(true);
    }
}
