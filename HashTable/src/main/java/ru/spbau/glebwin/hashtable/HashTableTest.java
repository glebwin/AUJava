package ru.spbau.glebwin.hashtable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for Hash table
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
        assertEquals(3, hashTable.size());

        hashTable.put("input", "output");
        assertEquals(3, hashTable.size());

        hashTable.put("a", "b");
        assertEquals(4, hashTable.size());

        hashTable.remove("key");
        hashTable.remove("a");
        assertEquals(2, hashTable.size());
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
        assertEquals("output", hashTable.get("input"));
        assertEquals("value", hashTable.get("key"));
        assertEquals("data", hashTable.get("tag"));

        assertNull(hashTable.get("new key"));
    }

    @Test
    public void put() throws Exception {
        assertEquals("output", hashTable.put("input", "putout"));
        assertEquals("value", hashTable.put("key", "notvalue"));
        assertEquals("data", hashTable.put("tag", "tag"));

        assertNull(hashTable.put("new key", "new value"));

        assertEquals("tag", hashTable.put("tag", "t"));
    }

    @Test
    public void remove() throws Exception {
        assertEquals("output", hashTable.remove("input"));
        assertEquals("data", hashTable.remove("tag"));

        assertNull(hashTable.remove("new key"));
    }

    @Test
    public void clear() throws Exception {
        hashTable.clear();

        assertEquals(0, hashTable.size());
        assertFalse(hashTable.contains("key"));
        assertNull(hashTable.get("input"));
        assertNull(hashTable.put("tag", "new data"));
    }
}