package org.reviewboard.plugin.reviews;

import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import org.reviewboard.plugin.ReviewBoardBundle;
import org.reviewboard.plugin.configuration.ClientConfiguration;

/**
 */
public class CreateReviewRequestView extends AbstractProjectComponent {
    private final ClientConfiguration config;

    public CreateReviewRequestView(Project project, ClientConfiguration config) {
        super(project);
        this.config = config;
    }

    public ReviewRequestData displayDialog() {
        final DialogBuilder builder = new DialogBuilder(super.myProject);
        builder.addOkAction();
        builder.addCancelAction();
        builder.setTitle(ReviewBoardBundle.message("createRequest.view.title"));
        final CreateRequestFromChanges createRequestFromChanges = new CreateRequestFromChanges();
        createRequestFromChanges.setOpenInBrowser(config.isOpenNewReviewInBrowser());
        createRequestFromChanges.setBaseDir(config.getVcsBaseDir());
        builder.setCenterPanel(createRequestFromChanges.getPanel());

        if(builder.show() != DialogWrapper.OK_EXIT_CODE) {
            return null;
        } else {
            config.setOpenNewReviewInBrowser(createRequestFromChanges.getOpenInBrowser());
            config.setVcsBaseDir(createRequestFromChanges.getBaseDir());
            return new ReviewRequestData(config.isOpenNewReviewInBrowser(), config.getVcsBaseDir(), createRequestFromChanges.getPatchFileName());
        }

    }

    public static class ReviewRequestData {
        private final boolean openNewReviewInBrowser;
        private final String vcsBaseDir;
        private final String patchFileName;

        public ReviewRequestData(boolean openNewReviewInBrowser, String vcsBaseDir, String patchFileName) {
            this.openNewReviewInBrowser = openNewReviewInBrowser;
            this.vcsBaseDir = vcsBaseDir;
            this.patchFileName = patchFileName;
        }

        public boolean isOpenNewReviewInBrowser() {
            return openNewReviewInBrowser;
        }

        public String getVcsBaseDir() {
            return vcsBaseDir;
        }

        public String getPatchFileName() {
            return patchFileName;
        }
    }
}
