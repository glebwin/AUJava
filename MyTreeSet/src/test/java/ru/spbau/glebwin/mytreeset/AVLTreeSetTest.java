package ru.spbau.glebwin.mytreeset;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for AVLTreeSet.
 */
public class AVLTreeSetTest {
    private ArrayList<Integer> setElements;
    private AVLTreeSet<Integer> set;

    @Before
    public void setUp() throws Exception {
        setElements = new ArrayList<>(Arrays.asList(14, -1, 4, 7, 9));
        set = new AVLTreeSet<>();
        set.addAll(setElements);
    }

    @Test
    public void add() throws Exception {
        assertTrue(set.containsAll(setElements));
        assertThat(set.size(), is(5));
        assertTrue(set.add(2));
        assertTrue(set.contains(2));
        assertThat(set.size(), is(6));
        assertFalse(set.add(4));
        assertThat(set.size(), is(6));
    }

    @Test
    public void iterator() throws Exception {
        Collections.sort(setElements, Integer::compareTo);
        Iterator<Integer> iterator = set.iterator();
        for (Integer element : setElements) {
            assertTrue(iterator.hasNext());
            assertThat(iterator.next(), is(element));
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    public void descendingIterator() throws Exception {
        Collections.sort(setElements, (a, b) -> b.compareTo(a));
        Iterator<Integer> iterator = set.descendingIterator();
        for (Integer element : setElements) {
            assertTrue(iterator.hasNext());
            assertThat(iterator.next(), is(element));
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    public void descendingSet() throws Exception {
        MyTreeSet<Integer> descendingSet = set.descendingSet();
        assertTrue(descendingSet.containsAll(setElements));
        assertThat(descendingSet.first(), is(14));
        assertThat(descendingSet.last(), is(-1));
        assertThat(descendingSet.lower(5), is(7));
        assertThat(descendingSet.higher(0), is(-1));
        Collections.sort(setElements, (a, b) -> b.compareTo(a));
        Iterator<Integer> iterator = descendingSet.iterator();
        for (Integer element : setElements) {
            assertThat(iterator.next(), is(element));
        }
        assertTrue(descendingSet.add(2));
        assertTrue(set.contains(2));
        assertThat(set.size(), is(6));
    }

    @Test
    public void first() throws Exception {
        assertThat(set.first(), is(-1));
    }

    @Test
    public void last() throws Exception {
        assertThat(set.last(), is(14));
    }

    @Test
    public void lower() throws Exception {
        assertThat(set.lower(6), is(4));
        assertThat(set.lower(7), is(4));
        assertThat(set.lower(8), is(7));
        assertThat(set.lower(-1), is(nullValue()));
        assertThat(set.lower(15), is(14));
    }

    @Test
    public void floor() throws Exception {
        assertThat(set.floor(6), is(4));
        assertThat(set.floor(7), is(7));
        assertThat(set.floor(8), is(7));
        assertThat(set.floor(-2), is(nullValue()));
        assertThat(set.floor(14), is(14));
    }

    @Test
    public void ceiling() throws Exception {
        assertThat(set.ceiling(6), is(7));
        assertThat(set.ceiling(7), is(7));
        assertThat(set.ceiling(8), is(9));
        assertThat(set.ceiling(15), is(nullValue()));
        assertThat(set.ceiling(-1), is(-1));
    }

    @Test
    public void higher() throws Exception {
        assertThat(set.higher(6), is(7));
        assertThat(set.higher(7), is(9));
        assertThat(set.higher(8), is(9));
        assertThat(set.higher(14), is(nullValue()));
        assertThat(set.higher(-2), is(-1));
    }

}