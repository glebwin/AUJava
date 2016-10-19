package ru.spbau.glebwin.fp;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test class for Predicate.
 */
public class PredicateTest {
    @Test
    public void apply() throws Exception {
        Predicate<Integer> isPositive = x -> x > 0;
        assertThat(isPositive.apply(-1), is(false));
        assertThat(isPositive.apply(1), is(true));
    }

    @Test
    public void or() throws Exception {
        Predicate<Integer> multipleThree = x -> x % 3 == 0;
        Predicate<Integer> multipleFive = x -> x % 5 == 0;
        assertThat(multipleThree.or(multipleFive).apply(14), is(false));
        assertThat(multipleThree.or(multipleFive).apply(6), is(true));
        assertThat(multipleFive.or(multipleThree).apply(10), is(true));
        assertThat(multipleThree.or(multipleFive).apply(15), is(true));
    }

    @Test
    public void and() throws Exception {
        Predicate<String> startsWithCapitalA = s -> s.charAt(0) == 'A';
        Predicate<String> containsLowerCaseH = s -> s.indexOf('h') >= 0;
        assertThat(startsWithCapitalA.and(containsLowerCaseH).apply("Abacaba"), is(false));
        assertThat(startsWithCapitalA.and(containsLowerCaseH).apply("abahaba"), is(false));
        assertThat(startsWithCapitalA.and(containsLowerCaseH).apply("Abahaba"), is(true));
    }

    @Test
    public void not() throws Exception {
        Predicate<Integer> isOdd = x -> (x & 1) != 0;
        assertThat(isOdd.not().apply(1), is(false));
        assertThat(isOdd.not().apply(2), is(true));
    }

    @Test
    public void alwaysTrue() throws Exception {
        Predicate<Integer> alwaysTrue = Predicate.alwaysTrue();
        for (int i = -10; i <= 10; i++) {
            assertThat(alwaysTrue.apply(i), is(true));
        }
    }

    @Test
    public void alwaysFalse() throws Exception {
        Predicate<Integer> alwaysFalse = Predicate.alwaysFalse();
        for (int i = -10; i <= 10; i++) {
            assertThat(alwaysFalse.apply(i), is(false));
        }
    }

}