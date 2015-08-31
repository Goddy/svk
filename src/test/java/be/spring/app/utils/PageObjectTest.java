package be.spring.app.utils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PageObjectTest {
    Model model;
    String test = "testPage";

    @Before
    public void setUp() {
        model = new ExtendedModelMap();
    }

    @Test
    public void testPages() throws Exception {
        //Only one page
        PageObject p = new PageObject(model, 1, 0, test);
        p.addAttributes();

        assertNull(p.getFirstPage());
        assertNull(p.getPreviousPage());
        assertNull(p.getNextPage());
        assertNull(p.getLastPage());

        //Two pages, first page
        p = new PageObject(model, 2, 0, test);
        p.addAttributes();

        assertNull(p.getFirstPage());
        assertNull(p.getPreviousPage());
        assertEquals(new Integer(1), p.getNextPage());
        assertNull(p.getLastPage());

        //Two pages, second page
        p = new PageObject(model, 2, 1, test);
        p.addAttributes();

        assertNull(p.getFirstPage());
        assertEquals(new Integer(0), p.getPreviousPage());
        assertNull(p.getNextPage());
        assertNull(p.getLastPage());

        //Three pages, first page
        p = new PageObject(model, 3, 0, test);
        p.addAttributes();

        assertNull(p.getFirstPage());
        assertNull(p.getPreviousPage());
        assertEquals(new Integer(1), p.getNextPage());
        assertEquals(new Integer(2), p.getLastPage());

        //Five pages, third page
        p = new PageObject(model, 5, 2, test);
        p.addAttributes();

        assertEquals(new Integer(0), p.getFirstPage());
        assertEquals(new Integer(1), p.getPreviousPage());
        assertEquals(new Integer(3), p.getNextPage());
        assertEquals(new Integer(4), p.getLastPage());
    }

}