package org.reviewboard.plugin.server;

import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 */
class ReviewRequestOperationsImpl extends AbstractProjectComponent implements ReviewRequestOperations {
    public ReviewRequestOperationsImpl(Project project) {
        super(project);
    }

    @Override
    public URL createDraftReviewRequestFromPatch(final String patchFileName, final InputStream patchFile,
                                                 final String svnRoot) {
        try {
            return new URL("http://www.google.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
