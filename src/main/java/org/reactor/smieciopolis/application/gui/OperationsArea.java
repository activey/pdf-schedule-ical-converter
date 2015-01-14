package org.reactor.smieciopolis.application.gui;

import org.reactor.smieciopolis.Converter;
import org.reactor.smieciopolis.SiteSchedule;

import static java.awt.BorderLayout.CENTER;
import static java.lang.String.format;
import static java.lang.System.getProperty;
import static javax.swing.BorderFactory.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import java.awt.BorderLayout;
import java.io.File;

public class OperationsArea extends JPanel {

    private static final String BORDER_TITLE = "Przetwarzanie pliku";
    private static final String PROPERTY_LINE_SEPARATOR = "line.separator";
    private static final String TEXT_EMPTY = "";
    private JTextArea logEntriesArea;

    public OperationsArea() {
        initializePanelBorder();
        initializePanelLayout();
        initializePanelComponents();
    }

    private void initializePanelBorder() {
        setBorder(createCompoundBorder(createEmptyBorder(10, 10, 10, 10),
            createTitledBorder(createBevelBorder(2), BORDER_TITLE)));
    }

    private void initializePanelLayout() {
        setLayout(new BorderLayout());
    }

    private void initializePanelComponents() {
        JScrollPane scrollPane = new JScrollPane(logEntriesArea = new JTextArea());
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(scrollPane, CENTER);
        logEntriesArea.setEditable(false);
        logEntriesArea.setAutoscrolls(true);
        DefaultCaret caret = (DefaultCaret) logEntriesArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    public void generateCalendars(File inputPDF, File outputFolder) {
        resetText();
        appendText("Rozpoczynam przetwarzanie ...");
        
        new Converter(inputPDF, outputFolder) {
            @Override
            protected void onCalendarFileGenerated(File calendarFile) {
                appendFileInformation(calendarFile);
            }

            @Override
            protected void onCalendarProcessingStarted(SiteSchedule siteSchedule) {
                appendNewCalendarInformation(siteSchedule);
            }
        }.generateCalendarFiles();
    }

    private void appendNewCalendarInformation(SiteSchedule siteSchedule) {
        appendText("Przetwarzam harmonogram: '%s'", siteSchedule.getScheduleTitle());
    }

    private void appendFileInformation(File calendarFile) {
        appendText("Nowy kalendarz utworzony w pliku: %s", calendarFile.getAbsolutePath());
    }

    private void appendText(String template, String... parameters) {
        logEntriesArea.append(format(template, parameters) + getProperty(PROPERTY_LINE_SEPARATOR));
        logEntriesArea.repaint();
    }

    private void resetText() {
        logEntriesArea.setText(TEXT_EMPTY);
    }
}
