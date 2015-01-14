package org.reactor.smieciopolis.application.gui;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.UIManager.getIcon;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import org.apache.pdfbox.util.ExtensionFileFilter;
import org.reactor.smieciopolis.Converter;
import org.reactor.smieciopolis.application.gui.chooser.ChooseDirectoryFilter;
import org.reactor.smieciopolis.application.gui.chooser.JFileChooserField;

public class TopPanel extends JPanel {

    public static final int MARGIN = 10;
    private File inputPDF;
    private File outputFolder;

    public TopPanel() {
        initializePanelLayout();
        initializePanelComponents();
    }

    private void initializePanelLayout() {
        GridLayout layout = new GridLayout(0, 1);
        layout.setHgap(10);
        layout.setVgap(5);
        setLayout(layout);

        addMarginBorder();
    }

    private void addMarginBorder() {
        setBorder(createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
    }

    private void initializePanelComponents() {
        add(new JLabel("Plik PDF harmonogramu wywozu odpadów:"));
        add(new JFileChooserField("Wybierz plik", getIcon("FileView.fileIcon"), new ExtensionFileFilter(
            new String[] {
                "pdf"
            }, "Pliki PDF")) {
            @Override
            protected void onFileSelected(File selectedFile) {
                setInputPDF(selectedFile);
            }
        });

        add(new JLabel("Katalog docelowy generowanych kalendarzy:"));
        add(new JFileChooserField("Wybierz folder docelowy", getIcon("FileView.directoryIcon"),
            new ChooseDirectoryFilter("Foldery"), false) {
            @Override
            protected void onFileSelected(File selectedFolder) {
                setOutputFolder(selectedFolder);
            }
        });
    }

    private void setInputPDF(File selectedFile) {
        inputPDF = selectedFile;
        verifyRequirements();
    }

    public File getInputPDF() {
        return inputPDF;
    }

    private void setOutputFolder(File selectedFolder) {
        outputFolder = selectedFolder;
        verifyRequirements();
    }

    public File getOutputFolder() {
        return outputFolder;
    }

    private void verifyRequirements() {
        if (inputPDF != null && outputFolder != null) {
            onRequirementsMet();
        }
    }
    
    protected void onRequirementsMet() {
    }
}
