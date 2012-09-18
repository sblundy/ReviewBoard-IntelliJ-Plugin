package org.reviewboard.plugin.reviews;

import com.intellij.history.integration.patches.PatchCreator;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.Change;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

/**
 */
public class ExternalTools extends AbstractProjectComponent {
    public ExternalTools(Project project) {
        super(project);
    }

    public InputStream createPatch(Change[] changes, Project project) throws IOException, VcsException {
        final File patchFile = File.createTempFile("review-request", ".patch");
        PatchCreator.create(project, Arrays.asList(changes),
                patchFile.getAbsolutePath(), false, null);
        return new FileInputStream(patchFile);
    }

    public void launchBrowser(URL target) {
        BrowserUtil.launchBrowser(target.toString());
    }
}
