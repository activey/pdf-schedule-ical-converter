package org.reactor.smieciopolis.application.gui.chooser;

import static javax.swing.Box.createHorizontalStrut;
import static javax.swing.JFileChooser.FILES_AND_DIRECTORIES;

import java.io.File;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class JFileChooserField extends JPanel {

    private final String label;
    private final Icon icon;
    private final boolean filesOnly;
    private final FileFilter fileFilter;
    private JTextField textField;
    private JButton selectFileButton;
    private JFileChooser fileChooser;

    public JFileChooserField(String label, Icon icon, FileFilter fileFilter) {
        this(label, icon, fileFilter, true);
    }

    public JFileChooserField(String label, Icon icon, FileFilter fileFilter, boolean filesOnly) {
        this.label = label;
        this.icon = icon;
        this.filesOnly = filesOnly;
        this.fileFilter = fileFilter;

        initializeLayout();
        initializeFileChooser();
        initializeFieldComponents();
    }

    private void initializeLayout() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    private void initializeFileChooser() {
        fileChooser = new JFileChooser();
        if (filesOnly) {
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        } else {
            fileChooser.setFileSelectionMode(FILES_AND_DIRECTORIES);
        }
        fileChooser.setFileFilter(fileFilter);
    }

    private void initializeFieldComponents() {
        add(textField = new JTextField());
        textField.setEditable(false);
        add(createHorizontalStrut(5));
        add(selectFileButton = new JButton(initializeSelectButtonAction()));
    }

    private Action initializeSelectButtonAction() {
        return new JFileChooserAction(fileChooser, label, icon) {
            @Override
            protected void onFileChosen(File selectedFile) {
                textField.setText(selectedFile.getAbsolutePath());
                onFileSelected(selectedFile);
            }
        };
    }

    protected void onFileSelected(File selectedFile) {
        
    }
   
}
