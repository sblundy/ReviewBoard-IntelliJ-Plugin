package org.reviewboard.plugin.configuration;

import javax.swing.*;

/**
 * Bound class for ClientConfigurationPanel.
 */
public class ClientConfigurationPanel {
    private JTextField serverUrl;
    private JPanel component;

    public String getServerUrl() {
        return serverUrl.getText();
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl.setText(serverUrl);
    }

    public JPanel getComponent() {
        return component;
    }
}
