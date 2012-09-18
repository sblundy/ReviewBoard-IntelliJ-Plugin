package org.reviewboard.plugin.reviews;

import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.Change;
import org.reviewboard.plugin.server.ReviewRequestOperations;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 */
class CreateReviewRequestHelper extends AbstractProjectComponent {
    private final Logger logger;

    private final CreateReviewRequestView view;
    private final ReviewRequestOperations ops;
    private final ExternalTools tools;

    @SuppressWarnings("UnusedDeclaration")
    public CreateReviewRequestHelper(Project project, CreateReviewRequestView view, ReviewRequestOperations ops, ExternalTools tools) {
        this(project, view, ops, tools, Logger.getInstance(CreateReviewRequestHelper.class));
    }

    CreateReviewRequestHelper(Project project, CreateReviewRequestView view, ReviewRequestOperations ops, ExternalTools tools, Logger logger) {
        super(project);
        this.view = view;
        this.ops = ops;
        this.tools = tools;
        this.logger = logger;
    }

    public void doAction(final Change... changes) {
        if(null == changes) {
            return;
        }

        final CreateReviewRequestView.ReviewRequestData data = view.displayDialog();

        if(data != null) {
            try {
                final InputStream patch = tools.createPatch(changes, super.myProject);
                final URL draftRequest = ops.createDraftReviewRequestFromPatch(
                        data.getPatchFileName(), patch, data.getVcsBaseDir());

                if (data.isOpenNewReviewInBrowser()) {
                    tools.launchBrowser(draftRequest);
                }
            } catch (IOException e) {
                logger.error(e);
            } catch (VcsException e) {
                logger.error(e);
            }
        }
    }
}
