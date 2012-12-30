package org.reviewboard.plugin.reviews;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.changes.Change;

/**
 */
public class CreateReviewRequestAction extends AnAction {
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

