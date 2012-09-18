package org.reviewboard.plugin.test;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.BeforeMethod;

/**
 */
public class EasyMockTestBase<SUT> {
    private IMocksControl mocksControl;
    protected SUT sut;

    protected <T> T createMock(Class<T> toMock) {
        return this.mocksControl.createMock(toMock);
    }

    @BeforeMethod
    public final void setupControl() {
        this.mocksControl = EasyMock.createControl();
    }

    protected void replay() {
        this.mocksControl.replay();
    }

    protected void verify() {
        this.mocksControl.verify();
    }
}
