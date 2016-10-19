package ru.spbau.glebwin.fp;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Test class for functional-style methods for work with Collections.
 */
public class CollectionsTest {
    @Test
    public void map() throws Exception {
        Function1<Integer, Integer> squarer = x -> x * x;
        ArrayList<Integer> array = new ArrayList<>();
        array.add(0);
        array.add(-3);
        array.add(1);
        array.add(10);
        assertThat(Collections.map(squarer, array), is(contains(0, 9, 1, 100)));
    }

    @Test
    public void filter() throws Exception {
        Predicate<String> containsZ = s -> s.toLowerCase().indexOf('z') >= 0;
        Predicate<String> longerThanFive = s -> s.length() > 5;
        ArrayList<String> array = new ArrayList<>();
        array.add("abaca");
        array.add("zzzzzz");
        array.add("z");
        array.add("no");
        array.add("long enough");
        assertThat(Collections.filter(containsZ.or(longerThanFive), array), is(contains("zzzzzz", "z", "long enough")));
    }

    @Test
    public void takeWhile() throws Exception {
        Predicate<Integer> isNegative = x -> x < 0;
        ArrayList<Integer> array = new ArrayList<>();
        array.add(-1);
        array.add(-7);
        array.add(-1);
        array.add(3);
        array.add(-1);
        assertThat(Collections.takeWhile(isNegative, array), is(contains(-1, -7, -1)));
    }

    @Test
    public void takeUnless() throws Exception {
        Predicate<String> isEmpty = String::isEmpty;
        ArrayList<String> array = new ArrayList<>();
        array.add("aba");
        array.add("string");
        array.add("");
        array.add("not empty");
        assertThat(Collections.takeUnless(isEmpty, array), is(contains("aba", "string")));
    }

    @Test
    public void foldl() throws Exception {
        Function2<Integer, String, Integer> stringsSum = (i, s) -> i + Integer.parseInt(s);
        ArrayList<String> array = new ArrayList<>();
        array.add("10");
        array.add("0");
        array.add("-4");
        array.add("3");
        assertThat(Collections.foldl(stringsSum, array, 0), is(9));
    }

    @Test
    public void foldr() throws Exception {
        Function2<Integer, Integer, Integer> div = (x, y) -> x / y;
        ArrayList<Integer> array = new ArrayList<>();
        array.add(8);
        array.add(12);
        array.add(24);
        array.add(4);
        assertThat(Collections.foldr(div, array, 2), is(8));
    }
}
