package org.reviewboard.plugin.configuration;

import com.intellij.openapi.project.Project;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.reviewboard.plugin.test.EasyMockTestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 */
public class ClientConfigurationTest extends EasyMockTestBase<ClientConfiguration> {
    @BeforeMethod
    public void setUp() {
        final Project project = createMock(Project.class);
        super.sut = new ClientConfiguration(project);
    }

    @Test
    public void testLoadsEmptyState() {
        final Element element = new Element("ReviewBoard");

        replay();
        super.sut.loadState(element);
        verify();

        Assert.assertNull(super.sut.getVcsBaseDir());
        Assert.assertTrue(super.sut.isOpenNewReviewInBrowser());
        Assert.assertNull(super.sut.getServerUrl());
    }

    @Test
    public void testGetStateEmpty() {

        replay();
        final Element actual = super.sut.getState();
        verify();

        Assert.assertNotNull(actual);
        assertElementValue(actual, "ServerUrl", "");
        assertElementValue(actual, "OpenNewReviewInBrowser", "true");
        assertElementValue(actual, "VcsBaseDir", "");
        Assert.assertEquals(actual.getAttributeValue("config-version"), "0.1");
    }

    @Test
    public void testGetStateFull() {
        final boolean openInBrowser = RandomUtils.nextBoolean();
        final String serverUrl = RandomStringUtils.randomAlphabetic(10);
        final String baseDir = RandomStringUtils.randomAlphabetic(11);
        super.sut.setServerUrl(serverUrl);
        super.sut.setVcsBaseDir(baseDir);
        super.sut.setOpenNewReviewInBrowser(openInBrowser);

        replay();
        final Element actual = super.sut.getState();
        verify();

        Assert.assertNotNull(actual);
        assertElementValue(actual, "ServerUrl", serverUrl);
        assertElementValue(actual, "OpenNewReviewInBrowser", Boolean.toString(openInBrowser));
        assertElementValue(actual, "VcsBaseDir", baseDir);
        Assert.assertEquals(actual.getAttributeValue("config-version"), "0.1");
    }

    private void assertElementValue(Element parent, String name, String expectedValue) {
        Assert.assertNotNull(parent.getChild(name));
        Assert.assertEquals(parent.getChild(name).getText(), expectedValue);
    }

    @Test
    public void testLoadsFullState() throws JDOMException, IOException {
        final String serverUrl = RandomStringUtils.randomAlphabetic(10);
        final String baseDir = RandomStringUtils.randomAlphabetic(11);
        final boolean openInBrowser = RandomUtils.nextBoolean();

        final Document document = new SAXBuilder().build(new StringReader(
                "<ReviewBoard config-version='0.1'>" +
                        "<ServerUrl>" + serverUrl+ "</ServerUrl>" +
                        "<OpenNewReviewInBrowser>" + Boolean.toString(openInBrowser) + "</OpenNewReviewInBrowser>" +
                        "<VcsBaseDir>" + baseDir + "</VcsBaseDir>" +
                "</ReviewBoard>"));

        replay();
        super.sut.loadState(document.getRootElement());
        verify();

        Assert.assertEquals(super.sut.getVcsBaseDir(), baseDir);
        Assert.assertEquals(super.sut.isOpenNewReviewInBrowser(), openInBrowser);
        Assert.assertEquals(super.sut.getServerUrl(), serverUrl);
    }
}
