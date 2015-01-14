package org.reactor.smieciopolis.application.gui.chooser;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ChooseDirectoryFilter extends FileFilter {

    private final String description;

    public ChooseDirectoryFilter(String description) {
        this.description = description;
    }
    
    @Override
    public boolean accept(File file) {
        return file.isDirectory();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
