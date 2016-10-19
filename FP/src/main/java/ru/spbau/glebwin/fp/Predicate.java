package ru.spbau.glebwin.fp;

import org.jetbrains.annotations.NotNull;

/**
 * Predicate: function of 1 argument with boolean result.
 *
 * @param <T> Type of the argument.
 */
public interface Predicate<T> extends Function1<T, Boolean> {
    /**
     * Makes or-predicate of the original predicate and given one.
     */
    @NotNull
    default Predicate<T> or(Predicate<? super T> predicate) {
        return argument -> apply(argument) || predicate.apply(argument);
    }

    /**
     * Makes and-predicate of the original predicate and given one.
     */
    @NotNull
    default Predicate<T> and(Predicate<? super T> predicate) {
        return argument -> apply(argument) && predicate.apply(argument);
    }

    /**
     * Makes negation of the predicate.
     */
    @NotNull
    default Predicate<T> not() {
        return argument -> !apply(argument);
    }

    /**
     * Constant true.
     */
    @NotNull
    static <U> Predicate<U> alwaysTrue() {
        return argument -> true;
    }

    /**
     * Constant false.
     */
    @NotNull
    static <U> Predicate<U> alwaysFalse() {
        return argument -> false;
    }
}
