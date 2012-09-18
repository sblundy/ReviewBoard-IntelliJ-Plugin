package org.reviewboard.plugin.configuration;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import org.jdom.Element;

/**
 * Manages and stores project specific configuration values and user preferences.
 */

@State(
        name = "ReviewBoardClientConfiguration",
        storages = {
                @Storage(id = "default", file = "$PROJECT_FILE$"),
                @Storage(id = "dir", file = "$PROJECT_CONFIG_DIR$/reviewboard-client.xml", scheme = StorageScheme.DIRECTORY_BASED)
        }
)
public class ClientConfiguration extends AbstractProjectComponent implements PersistentStateComponent<Element> {
    private String serverUrl;
    private boolean openNewReviewInBrowser = true;
    private String vcsBaseDir;

    public ClientConfiguration(Project project) {
        super(project);
    }

    @Override
    public Element getState() {
        final Element reviewBoard = new Element("ReviewBoard");
        reviewBoard.setAttribute("config-version", "0.1");
        createTextElement(reviewBoard, "ServerUrl", this.serverUrl);
        createTextElement(reviewBoard, "OpenNewReviewInBrowser", Boolean.toString(openNewReviewInBrowser));
        createTextElement(reviewBoard, "VcsBaseDir", this.vcsBaseDir);
        return reviewBoard;
    }

    private static void createTextElement(Element parent, String name, String value) {
        Element serverUrlElement = new Element(name);
        serverUrlElement.setText(value);
        parent.addContent(serverUrlElement);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public void loadState(Element element) {
        this.serverUrl = extractTextElement(element, "ServerUrl");
        final Element openNewReviewInBrowserElement = element.getChild("OpenNewReviewInBrowser");
        if(null != openNewReviewInBrowserElement) {
            this.openNewReviewInBrowser =
                    Boolean.parseBoolean(openNewReviewInBrowserElement.getTextTrim());
        } else {
            this.openNewReviewInBrowser = true;
        }
        this.vcsBaseDir = extractTextElement(element, "VcsBaseDir");
    }

    private static String extractTextElement(Element element, String name) {
        final Element serverUrlElement = element.getChild(name);
        return null == serverUrlElement ? null : serverUrlElement.getTextTrim();
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public boolean isOpenNewReviewInBrowser() {
        return openNewReviewInBrowser;
    }

    public void setOpenNewReviewInBrowser(boolean openNewReviewInBrowser) {
        this.openNewReviewInBrowser = openNewReviewInBrowser;
    }

    public String getVcsBaseDir() {
        return vcsBaseDir;
    }

    public void setVcsBaseDir(String vcsBaseDir) {
        this.vcsBaseDir = vcsBaseDir;
    }
}
