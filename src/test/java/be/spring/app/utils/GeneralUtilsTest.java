package be.spring.app.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by u0090265 on 24/11/16.
 */
public class GeneralUtilsTest {

    @Test
    public void testAbbreviateName() throws Exception {
        assertEquals("DD", GeneralUtils.abbreviateName("Doe Doe"));
        assertEquals("G", GeneralUtils.abbreviateName("Genius"));
        assertEquals("GEG", GeneralUtils.abbreviateName("Great Evil Genius"));
        assertEquals("", GeneralUtils.abbreviateName(""));
        assertEquals("", GeneralUtils.abbreviateName(null));
    }
}