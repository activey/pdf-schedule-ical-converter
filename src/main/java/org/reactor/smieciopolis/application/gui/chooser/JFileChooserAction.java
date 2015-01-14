package org.reactor.smieciopolis.application.gui.chooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class JFileChooserAction extends AbstractAction {

    private final JFileChooser fileChooser;

    public JFileChooserAction(JFileChooser fileChooser, String label, Icon icon) {
        super(label, icon);
        this.fileChooser = fileChooser;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int result = fileChooser.showOpenDialog(null);
        if (JFileChooser.APPROVE_OPTION == result) {
            onFileChosen(fileChooser.getSelectedFile());
        }
    }

    protected void onFileChosen(File selectedFile) {

    }
}
