package ru.spbau.glebwin.maybe;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.function.Function;

/**
 * Wrapper containing value or indicating the opposite.
 *
 * @param <T> Type of the supposed value.
 */
public class Maybe<T> {
    @Nullable
    private final T value;

    private Maybe(T value) {
        this.value = value;
    }

    /**
     * @return Newly created instance of Maybe containing given value.
     */
    @NotNull
    public static <T> Maybe<T> just(T t) {
        return new Maybe<>(t);
    }

    /**
     * @return New empty instance of Maybe.
     */
    @NotNull
    public static <T> Maybe<T> nothing() {
        return new Maybe<>(null);
    }

    /**
     * @return Value if it exists.
     * @throws NothingDereferenceException If object is empty.
     */
    @NotNull
    public T get() {
        if (value != null) {
            return value;
        } else {
            throw new NothingDereferenceException("Attempt to get value from Maybe object which is Nothing");
        }
    }

    /**
     * @return True if object stores value, false if it's empty.
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * Applies given function to the stored value and returns instance of Maybe containing the result.
     * If there is no value nothing is done and the result is empty instance of Maybe.
     *
     * @param mapper Function to apply.
     * @return Instance of Maybe containing result. It's empty if current object is empty.
     */
    @NotNull
    public <U> Maybe<U> map(Function<? super T, ? extends U> mapper) {
        if (value != null) {
            return Maybe.just(mapper.apply(value));
        } else {
            return Maybe.nothing();
        }
    }

    /**
     * Caused by attempt to get value of empty instance of Maybe.
     */
    public static class NothingDereferenceException extends RuntimeException {
        public NothingDereferenceException() {
        }

        public NothingDereferenceException(String exceptionMessage) {
            super(exceptionMessage);
        }
    }
}
