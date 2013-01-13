package org.reviewboard.plugin.reviews;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.changes.Change;
import org.reviewboard.plugin.ReviewBoardBundle;

/**
 */
public class CreateReviewRequestAction extends AnAction {
    public CreateReviewRequestAction() {
        super(ReviewBoardBundle.message("createReview.action.text"));
    }

    @Override
    public void actionPerformed(final AnActionEvent event) {

        final Project project = event.getProject();

        if(project != null) {
            final Change[] changes = event.getData(VcsDataKeys.CHANGES);
            final CreateReviewRequestHelper helper = project.getComponent(CreateReviewRequestHelper.class);
            helper.doAction(changes);
        }
    }
}

