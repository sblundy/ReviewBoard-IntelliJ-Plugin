package org.reviewboard.plugin.reviews;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.Change;
import org.reviewboard.plugin.server.ReviewRequestOperations;
import org.reviewboard.plugin.test.EasyMockTestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.easymock.EasyMock.expect;

/**
 */
public class CreateReviewRequestHelperTest extends EasyMockTestBase<CreateReviewRequestHelper> {
    private Project project;
    private CreateReviewRequestView view;
    private ReviewRequestOperations ops;
    private ExternalTools tools;
    private Logger logger;

    @BeforeMethod
    public void setUp() {
        this.project = createMock(Project.class);
        this.view = createMock(CreateReviewRequestView.class);
        this.ops = createMock(ReviewRequestOperations.class);
        this.tools = createMock(ExternalTools.class);
        this.logger = createMock(Logger.class);
        this.sut = new CreateReviewRequestHelper(project, view, ops, tools, logger);
    }

    @Test
    public void testNoChangesResultsInNothing() {
        runTest((Change[]) null);
    }

    @Test
    public void testHappyPath() throws VcsException, IOException {
        final Change change = createMock(Change.class);
        final InputStream in = new ByteArrayInputStream(new byte[0]);
        final CreateReviewRequestView.ReviewRequestData data = new CreateReviewRequestView.ReviewRequestData(true,
                randomAlphabetic(14), randomAlphabetic(15));

        final Change[] changes = { change };
        final URL url = new URL("http://www.google.com");

        expect(view.displayDialog()).andReturn(data);
        expect(tools.createPatch(changes, project)).andReturn(in);
        expect(ops.createDraftReviewRequestFromPatch(data.getPatchFileName(), in, data.getVcsBaseDir())).andReturn(url);
        tools.launchBrowser(url);

        runTest(changes);
    }

    @Test
    public void testDoNothingWhenNullDataReturned() {
        final Change change = createMock(Change.class);

        final Change[] changes = { change };

        expect(view.displayDialog()).andReturn(null);

        runTest(changes);
    }

    @Test
    public void testLaunchBrowserNotCalledWhenNotConfigured() throws VcsException, IOException {
        final Change change = createMock(Change.class);
        final InputStream in = new ByteArrayInputStream(new byte[0]);
        final CreateReviewRequestView.ReviewRequestData data = new CreateReviewRequestView.ReviewRequestData(false,
                randomAlphabetic(14), randomAlphabetic(15));

        final Change[] changes = { change };
        final URL url = new URL("http://www.google.com");

        expect(view.displayDialog()).andReturn(data);
        expect(tools.createPatch(changes, project)).andReturn(in);
        expect(ops.createDraftReviewRequestFromPatch(data.getPatchFileName(), in, data.getVcsBaseDir())).andReturn(url);

        runTest(changes);
    }

    @Test
    public void testVcsExceptionLogged() throws VcsException, IOException {
        final Change change = createMock(Change.class);
        final CreateReviewRequestView.ReviewRequestData data = new CreateReviewRequestView.ReviewRequestData(true,
                randomAlphabetic(14), randomAlphabetic(15));

        final Change[] changes = { change };
        final VcsException e = new VcsException(randomAlphabetic(4));

        expect(view.displayDialog()).andReturn(data);
        expect(tools.createPatch(changes, project)).andThrow(e);
        logger.error(e);

        runTest(changes);
    }

    @Test
    public void testIOExceptionLogged() throws VcsException, IOException {
        final Change change = createMock(Change.class);
        final CreateReviewRequestView.ReviewRequestData data = new CreateReviewRequestView.ReviewRequestData(true,
                randomAlphabetic(14), randomAlphabetic(15));

        final Change[] changes = { change };
        final IOException e = new IOException(randomAlphabetic(4));

        expect(view.displayDialog()).andReturn(data);
        expect(tools.createPatch(changes, project)).andThrow(e);
        logger.error(e);

        runTest(changes);
    }

    private void runTest(Change... changes) {
        replay();
        this.sut.doAction(changes);
        verify();
    }
}
