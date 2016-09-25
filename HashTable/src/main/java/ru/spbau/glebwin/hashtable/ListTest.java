package ru.spbau.glebwin.hashtable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Hash tables list tests
 */
public class ListTest {
    private List list;

    @Before
    public void setUp() throws Exception {
        list = new List();
        list.insertUniqueKey("input", "output");
        list.insertUniqueKey("key", "value");
        list.insertUniqueKey("tag", "data");
    }

    @Test
    public void insertUniqueKey() throws Exception {
        assertNull(list.insertUniqueKey("unknown key", "value"));

        assertEquals("value", list.insertUniqueKey("key", "new value"));
        assertEquals("output", list.insertUniqueKey("input", "output"));
        assertEquals("data", list.insertUniqueKey("tag", "data2"));

        assertEquals("new value", list.insertUniqueKey("key", "val"));
    }

    @Test
    public void findByKey() throws Exception {
        assertEquals("output", list.findByKey("input"));
        assertEquals("value", list.findByKey("key"));

        assertNull(list.findByKey("unknown key"));

        assertEquals("data", list.findByKey("tag"));
    }

    @Test
    public void removeByKey() throws Exception {
        assertEquals("output", list.removeByKey("input"));
        assertEquals("data", list.removeByKey("tag"));

        assertNull(list.removeByKey("input"));
        assertNull(list.removeByKey("tagtag"));

        assertEquals("value", list.removeByKey("key"));

        assertTrue(list.empty());
    }

}