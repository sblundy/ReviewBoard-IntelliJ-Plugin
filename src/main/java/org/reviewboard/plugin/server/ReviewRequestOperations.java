package org.reviewboard.plugin.server;

import java.io.InputStream;
import java.net.URL;

/**
 * Facade to remote calls to a ReviewBoard server.
 */
public interface ReviewRequestOperations {
    URL createDraftReviewRequestFromPatch(String patchFileName, InputStream patchFile, String svnRoot);
}
