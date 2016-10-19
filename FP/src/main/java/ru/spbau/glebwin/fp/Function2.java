package ru.spbau.glebwin.fp;

import org.jetbrains.annotations.NotNull;

/**
 * Function of 2 arguments.
 *
 * @param <T1> Type of the 1st argument.
 * @param <T2> Type of the 2nd argument.
 * @param <R>  Type of the result.
 */
public interface Function2<T1, T2, R> {
    /**
     * Applies function to given arguments.
     */
    R apply(T1 argument1, T2 argument2);

    /**
     * Composes the function f with given function g: g(f(...)).
     *
     * @param <RR> Type of result of given function.
     * @return Composition of functions.
     */
    @NotNull
    default <RR> Function2<T1, T2, RR> compose(Function1<? super R, ? extends RR> g) {
        return (argument1, argument2) -> g.apply(apply(argument1, argument2));
    }

    /**
     * Binds the 1st argument of the function with given value.
     *
     * @return Function of 1 argument that is the original function one argument of which is constant.
     */
    @NotNull
    default Function1<T2, R> bind1(T1 argument1) {
        return argument2 -> apply(argument1, argument2);
    }

    /**
     * Binds the 2nd argument of the function with given value.
     *
     * @return Function of 1 argument that is the original function one argument of which is constant.
     */
    @NotNull
    default Function1<T1, R> bind2(T2 argument2) {
        return argument1 -> apply(argument1, argument2);
    }

    /**
     * Makes curried version of function allowing to apply arguments one by one.
     *
     * @return Curried function.
     */
    @NotNull
    default Function1<T1, Function1<T2, R>> curry() {
        return this::bind1;
    }
}
