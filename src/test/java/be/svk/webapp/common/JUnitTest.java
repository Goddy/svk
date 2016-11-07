package be.svk.webapp.common;

import static org.easymock.EasyMock.*;

/**
 * Created by u0090265 on 8/14/15.
 */
public abstract class JUnitTest {
    protected void replayAll() {
        replay(getMocks());
    }

    protected void resetAll() {
        reset(getMocks());
    }

    protected void verifyAll() {
        verify(getMocks());
    }

    protected abstract Object[] getMocks();
}
