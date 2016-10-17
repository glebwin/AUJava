package ru.spbau.glebwin.trie;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

public class TrieTest {
    private Trie trie;

    @Before
    public void setUp() throws Exception {
        trie = new Trie();

        trie.add("string");
        trie.add("word");
        trie.add("sentence");
        trie.add("word");
        trie.add("$text");
        trie.add("$text2");
    }

    @Test
    public void add() throws Exception {
        assertFalse(trie.add("string"));
        assertFalse(trie.add("$text"));

        assertTrue(trie.add("$text3"));
        assertTrue(trie.add("abacaba"));
        assertTrue(trie.add("world"));
        assertTrue(trie.add("s"));

        assertFalse(trie.add("abacaba"));
        assertFalse(trie.add("$text3"));
        assertFalse(trie.add("s"));
    }

    @Test
    public void contains() throws Exception {
        assertTrue(trie.contains("string"));
        assertTrue(trie.contains("word"));
        assertTrue(trie.contains("$text"));

        assertFalse(trie.contains("abacaba"));
        assertFalse(trie.contains("tex"));
        assertFalse(trie.contains("s"));

        assertTrue(trie.contains("sentence"));
        assertTrue(trie.contains("$text2"));
    }

    @Test
    public void remove() throws Exception {
        assertTrue(trie.remove("$text"));
        assertFalse(trie.remove("$text"));
        assertTrue(trie.remove("$text2"));
        assertFalse(trie.remove("$text2"));
        assertTrue(trie.remove("sentence"));
        assertTrue(trie.remove("string"));
        assertFalse(trie.remove("wor"));
        assertTrue(trie.remove("word"));
        assertFalse(trie.remove("word"));
        assertFalse(trie.remove("sentence"));
        assertFalse(trie.remove("string"));
    }

    @Test
    public void size() throws Exception {
        assertEquals(5, trie.size());

        trie.remove("string");
        assertEquals(4, trie.size());
        trie.remove("word");
        assertEquals(3, trie.size());
        trie.remove("word");
        assertEquals(3, trie.size());
        trie.remove("string");
        assertEquals(3, trie.size());
        trie.remove("sentence");
        assertEquals(2, trie.size());
        trie.remove("$tex");
        assertEquals(2, trie.size());
        trie.remove("$text");
        assertEquals(1, trie.size());
        trie.remove("$text2");
        assertEquals(0, trie.size());
    }

    @Test
    public void howManyStartsWithPrefix() throws Exception {
        assertEquals(2, trie.howManyStartsWithPrefix("s"));
        assertEquals(1, trie.howManyStartsWithPrefix("st"));
        assertEquals(1, trie.howManyStartsWithPrefix("w"));
        assertEquals(1, trie.howManyStartsWithPrefix("word"));
        assertEquals(2, trie.howManyStartsWithPrefix("$text"));
        assertEquals(1, trie.howManyStartsWithPrefix("$text2"));
    }

    @Test
    public void serialization() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        trie.serialize(outputStream);
        Trie resultTrie = new Trie();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        resultTrie.deserialize(inputStream);

        assertEquals(5, resultTrie.size());
        assertTrue(resultTrie.contains("string"));
        assertTrue(resultTrie.contains("word"));
        assertTrue(resultTrie.contains("sentence"));
        assertTrue(resultTrie.contains("$text"));
        assertTrue(resultTrie.contains("$text2"));
        assertFalse(resultTrie.contains("something else"));
    }
}