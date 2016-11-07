package be.svk.webapp.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by u0090265 on 9/18/15.
 */
public class ValidationHelperTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testIsValidDate() throws Exception {

    }

    @Test
    public void testReturnDate() throws Exception {

    }

    @Test
    public void testIsPasswordMatch() throws Exception {
        //Too short
        assertFalse(ValidationHelper.isPasswordMatch("aaaa"));
        //OK
        assertTrue(ValidationHelper.isPasswordMatch("aaaaa"));
        //OK
        assertTrue(ValidationHelper.isPasswordMatch("12aaaa._-"));
        //Too long
        assertFalse(ValidationHelper.isPasswordMatch("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        //Invalid chars
        assertFalse(ValidationHelper.isPasswordMatch("aaaa()%$"));

    }

    @Test
    public void testIsNameMatch() throws Exception {

    }

    @Test
    public void testIsLength() throws Exception {

    }

    @Test
    public void testIsEmailMatch() throws Exception {

    }
}