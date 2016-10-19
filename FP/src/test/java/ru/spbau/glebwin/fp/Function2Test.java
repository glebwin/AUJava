package ru.spbau.glebwin.fp;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test Class for Function2.
 */
public class Function2Test {
    @Test
    public void apply() throws Exception {
        Function2<String, Integer, Character> shiftFirstLetter = (s, i) -> (char) (s.charAt(0) + i);
        assertThat(shiftFirstLetter.apply("argument", 3), is('d'));
        assertThat(shiftFirstLetter.apply("String", -1), is('R'));
    }

    @Test
    public void compose() throws Exception {
        Function2<String, Integer, String> appendInteger = (s, i) -> s + String.valueOf(i);
        Function1<String, String> toUpperCase = String::toUpperCase;
        assertThat(appendInteger.compose(toUpperCase).apply("aBacAba ", 42), is("ABACABA 42"));
        assertThat(appendInteger.compose(toUpperCase).apply("", -1), is("-1"));
        assertThat(appendInteger.compose(toUpperCase).apply("AB-", -10), is("AB--10"));
    }


    @Test
    public void bind1() throws Exception {
        Function2<String, Boolean, String> changeCase = (s, b) -> {
            if (b) return s.toUpperCase();
            else return s.toLowerCase();
        };
        String s = "aBAcAba";
        Function1<Boolean, String> changeCaseBinded = changeCase.bind1(s);
        assertThat(changeCaseBinded.apply(false), allOf(is(changeCase.apply(s, false)), is("abacaba")));
        assertThat(changeCaseBinded.apply(true), allOf(is(changeCase.apply(s, true)), is("ABACABA")));
    }

    @Test
    public void bind2() throws Exception {
        Function2<String, String, String> f = (s1, s2) -> s1 + s2;
        String string = "binded argument";
        Function1<String, String> fBinded = f.bind2(string);
        String string1 = "prefix";
        String string2 = "string and";
        assertThat(fBinded.apply(string1), allOf(is(f.apply(string1, string)), is(string1 + string)));
        assertThat(fBinded.apply(string2), allOf(is(f.apply(string2, string)), is(string2 + string)));
    }

    @Test
    public void curry() throws Exception {
        Function2<Function1<Character, Character>, Character, Character> f = Function1::apply;
        Function1<Function1<Character, Character>, Function1<Character, Character>> fCurried = f.curry();
        Function1<Character, Character> increment = x -> (char) (x + 1);
        assertThat(fCurried.apply(increment).apply('d'), is('e'));
        increment = f.curry().apply(increment);
        assertThat(increment.apply('d'), is('e'));
    }
}
