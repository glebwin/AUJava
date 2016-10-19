package ru.spbau.glebwin.fp;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.reverse;

/**
 * Implementation of some common functions for transforming collections.
 */
public final class Collections {
    /**
     * Applies given function to every element in collection.
     *
     * @return Transformed collection.
     */
    @NotNull
    public static <T, R> List<R> map(Function1<? super T, ? extends R> function, Iterable<T> collection) {
        List<R> result = new ArrayList<>();
        for (T element : collection) {
            result.add(function.apply(element));
        }
        return result;
    }


    /**
     * Gives elements satisfying predicate.
     *
     * @return Transformed collection.
     */
    @NotNull
    public static <T> List<T> filter(Predicate<? super T> predicate, Iterable<T> collection) {
        List<T> result = new ArrayList<>();
        for (T element : collection) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

    /**
     * Finds maximum prefix satisfying predicate.
     *
     * @return Transformed collection.
     */
    @NotNull
    public static <T> List<T> takeWhile(Predicate<? super T> predicate, Iterable<T> collection) {
        List<T> result = new ArrayList<>();
        for (T element : collection) {
            if (predicate.apply(element)) {
                result.add(element);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Finds maximum prefix not satisfying predicate.
     *
     * @return Transformed collection.
     */
    @NotNull
    public static <T> List<T> takeUnless(Predicate<? super T> predicate, Iterable<T> collection) {
        return takeWhile(predicate.not(), collection);
    }

    /**
     * Applies function f to collection c and initialValue v in the following way:
     * f(...f(f(f(v, c[0]), c[1]), c[2]),..., c[c.length - 1])
     */
    public static <T, U> U foldl(Function2<? super U, ? super T, ? extends U> function,
                                 Iterable<T> collection, U initialValue) {
        U result = initialValue;
        for (T element : collection) {
            result = function.apply(result, element);
        }
        return result;
    }

    /**
     * Applies function f to collection c and initialValue v in the following way:
     * f(c[0],...,f(c[c.length - 3], f(c[c.length - 2], f(c[c.length - 1], v)))...)
     */
    public static <T, U> U foldr(Function2<? super T, ? super U, ? extends U> function,
                                 Iterable<T> collection, U initialValue) {
        ArrayList<T> reversedCollection = new ArrayList<>();
        for (T element : collection) {
            reversedCollection.add(element);
        }
        reverse(reversedCollection);

        U result = initialValue;
        for (T element : reversedCollection) {
            result = function.apply(element, result);
        }
        return result;
    }
}
