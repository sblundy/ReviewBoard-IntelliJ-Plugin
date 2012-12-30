package org.reviewboard.plugin.reviews;

import javax.swing.*;

/**
 */
public class CreateRequestFromChanges {
    private JPanel panel;
    private JTextField patchFileName;
    private JCheckBox openInBrowser;
    private JTextField baseDir;

    public JPanel getPanel() {
        return panel;
    }

    public String getPatchFileName() {
        return patchFileName.getText();
    }

    public void setPatchFileName(String name) {
        this.patchFileName.setText(name);
    }

    public boolean getOpenInBrowser() {
        return openInBrowser.isSelected();
    }

    public void setOpenInBrowser(boolean open) {
        openInBrowser.setSelected(open);
    }

    public String getBaseDir() {
        return baseDir.getText();
    }

    public void setBaseDir(String baseDir) {
        this.baseDir.setText(baseDir);
    }
}
