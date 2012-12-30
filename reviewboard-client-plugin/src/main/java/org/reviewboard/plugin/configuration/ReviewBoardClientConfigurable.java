package org.reviewboard.plugin.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.IconLoader;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;

import javax.swing.*;

/**
 */
public class ReviewBoardClientConfigurable implements Configurable {
    private final ClientConfiguration config;
    private ClientConfigurationPanel panel;

    public ReviewBoardClientConfigurable(final ClientConfiguration config) {
        this.config = config;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "ReviewBoard Client";
    }

    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/reviewboard.png");
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public JComponent createComponent() {
        panel = new ClientConfigurationPanel();
        panel.setServerUrl(config.getServerUrl());
        return panel.getComponent();
    }

    @Override
    public boolean isModified() {
        return !StringUtils.equals(panel.getServerUrl(), config.getServerUrl());
    }

    @Override
    public void apply() throws ConfigurationException {
        config.setServerUrl(panel.getServerUrl());
    }

    @Override
    public void reset() {
        panel.setServerUrl(config.getServerUrl());
    }

    @Override
    public void disposeUIResources() {

    }
}
