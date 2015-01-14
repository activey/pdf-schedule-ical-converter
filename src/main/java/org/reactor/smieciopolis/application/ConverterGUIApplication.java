package org.reactor.smieciopolis.application;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.reactor.smieciopolis.application.gui.ApplicationWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConverterGUIApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConverterGUIApplication.class);
    
    public static void main(String[] args) {
        setLookAndFeel();

        ConverterGUIApplication guiApplication = new ConverterGUIApplication();
        guiApplication.start();
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            LOGGER.error("An error occurred while setting system look and feel", e);
        }
    }

    private void start() {
        new ApplicationWindow().start();
    }
}
