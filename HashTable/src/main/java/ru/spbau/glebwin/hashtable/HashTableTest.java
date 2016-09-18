package ru.spbau.glebwin.hashtable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by glebwin on 9/17/16.
 */
public class HashTableTest {
    private HashTable hashTable;

    @Before
    public void setUp() throws Exception {
        hashTable = new HashTable();

        hashTable.put("input", "output");
        hashTable.put("key", "value");
        hashTable.put("tag", "data");
    }

    @Test
    public void size() throws Exception {
        assertEquals(hashTable.size(), 3);

        hashTable.put("input", "output");
        assertEquals(hashTable.size(), 3);

        hashTable.put("a", "b");
        assertEquals(hashTable.size(), 4);

        hashTable.remove("key");
        hashTable.remove("a");
        assertEquals(hashTable.size(), 2);
    }

    @Test
    public void contains() throws Exception {
        assertTrue(hashTable.contains("input"));
        assertTrue(hashTable.contains("key"));
        assertTrue(hashTable.contains("tag"));

        assertFalse(hashTable.contains("new key"));
    }

    @Test
    public void get() throws Exception {
        assertEquals(hashTable.get("input"), "output");
        assertEquals(hashTable.get("key"), "value");
        assertEquals(hashTable.get("tag"), "data");

        assertNull(hashTable.get("new key"));
    }

    @Test
    public void put() throws Exception {
        assertEquals(hashTable.put("input", "putout"), "output");
        assertEquals(hashTable.put("key", "notvalue"), "value");
        assertEquals(hashTable.put("tag", "tag"), "data");

        assertNull(hashTable.put("new key", "new value"));

        assertEquals(hashTable.put("tag", "t"), "tag");
    }

    @Test
    public void remove() throws Exception {
        assertEquals(hashTable.remove("input"), "output");
        assertEquals(hashTable.remove("tag"), "data");

        assertNull(hashTable.remove("new key"));
    }

    @Test
    public void clear() throws Exception {
        hashTable.clear();

        assertEquals(hashTable.size(), 0);
        assertFalse(hashTable.contains("key"));
        assertNull(hashTable.get("input"));
        assertNull(hashTable.put("tag", "new data"));
    }
}