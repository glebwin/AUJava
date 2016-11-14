package ru.spbau.glebwin.fp;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test class for Function1.
 */
public class Function1Test {
    @Test
    public void apply1() throws Exception {
        Function1<Integer, Integer> f = x -> (x - 1) * (x + 1);
        assertThat(f.apply(-3), is(8));
        assertThat(f.apply(0), is(-1));
        assertThat(f.apply(4), is(15));
    }

    @Test
    public void apply2() throws Exception {
        Function1<String, Integer> getLength = String::length;
        assertThat(getLength.apply(""), is(0));
        assertThat(getLength.apply("f"), is(1));
        assertThat(getLength.apply("a word"), is(6));
    }

    @Test
    public void compose1() throws Exception {
        Function1<Integer, Integer> abs = argument -> {
            if (argument >= 0) return argument;
            else return -argument;
        };
        Function1<Integer, Integer> cube = x -> x * x * x;
        assertThat(abs.compose(cube).apply(-3), allOf(is(cube.compose(abs).apply(-3)), is(27)));
        assertThat(abs.compose(cube).apply(-1), allOf(is(cube.compose(abs).apply(-1)), is(1)));
        assertThat(abs.compose(cube).apply(0), allOf(is(cube.compose(abs).apply(0)), is(0)));
        assertThat(abs.compose(cube).apply(2), allOf(is(cube.compose(abs).apply(2)), is(8)));
    }

    @Test
    public void compose2() throws Exception {
        Function1<Integer, String> toString = String::valueOf;
        Function1<String, Integer> getLength = String::length;
        assertThat(toString.compose(getLength).apply(-13), is(3));
        assertThat(toString.compose(getLength).apply(12345), is(5));
    }
}
