package ru.spbau.glebwin.maybe;

import org.junit.Test;

import static org.junit.Assert.*;

public class MaybeTest {
    @Test
    public void justInteger() throws Exception {
        int value = 42;
        Maybe maybe = Maybe.just(value);
        assertEquals(value, maybe.get());
        assertTrue(maybe.isPresent());
    }

    @Test
    public void justString() throws Exception {
        String value = "forty two";
        Maybe maybe = Maybe.just(value);
        assertEquals(value, maybe.get());
        assertTrue(maybe.isPresent());
    }

    @Test
    public void nothing() throws Exception {
        Maybe maybe = Maybe.nothing();
        assertFalse(maybe.isPresent());
    }

    @Test(expected = Maybe.NothingDereferenceException.class)
    public void nothingDereference() throws Exception {
        Maybe maybe = Maybe.nothing();
        maybe.get();
    }

    @Test
    public void mapInteger() throws Exception {
        assertEquals(Maybe.just(11).get(), Maybe.just(14).map(x -> x - 3).get());
        assertEquals(Maybe.just(6).get(), Maybe.just(2).map(x -> x * (x + 1)).get());
        assertNotEquals(Maybe.just(3).get(), Maybe.just(1).map(x -> x + 1).get());
    }

    @Test
    public void mapString() throws Exception {
        assertEquals(Maybe.just("Hello World!").get(), Maybe.just("World").map(x -> "Hello " + x + "!").get());
        assertNotEquals(Maybe.just("ab"), Maybe.just("a").map(x -> x + "b").get());
    }

    @Test
    public void mapNothing() throws Exception {
        assertFalse(Maybe.nothing().map(x -> x).isPresent());
        assertFalse(Maybe.nothing().map(x -> x.toString() + "a").isPresent());
    }
}
