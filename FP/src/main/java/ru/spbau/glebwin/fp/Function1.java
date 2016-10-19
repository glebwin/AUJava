package ru.spbau.glebwin.fp;

import org.jetbrains.annotations.NotNull;

/**
 * Function of 1 argument.
 *
 * @param <T> Type of the argument.
 * @param <R> Type of the result.
 */
public interface Function1<T, R> {
    /**
     * Applies function to given argument.
     */
    R apply(T argument);

    /**
     * Composes the function f with given function g: g(f(...)).
     *
     * @param <RR> Type of result of given function.
     * @return Composition of functions.
     */
    @NotNull
    default <RR> Function1<T, RR> compose(Function1<? super R, ? extends RR> g) {
        return argument -> g.apply(apply(argument));
    }
}
