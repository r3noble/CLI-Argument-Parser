package oop.project.library.scenarios;

/**
 * A sealed interface representing the result of an operation that can either succeed or fail.
 *
 * <p>The {@code Result} interface has two possible implementations:
 * <ul>
 *     <li>{@link Success} - Represents a successful operation and contains the resulting value.</li>
 *     <li>{@link Failure} - Represents a failed operation and contains an error message.</li>
 * </ul>
 *
 * @param <T> the type of the value in case of success
 */
public sealed interface Result<T> {

    /**
     * Represents a successful result of an operation.
     *
     * @param <T> the type of the resulting value
     */
    record Success<T>(T value) implements Result<T> {}
    /**
     * Represents a failed result of an operation.
     *
     * @param <T> the type of the expected value (if the operation were successful)
     */
    record Failure<T>(String error) implements Result<T> {}

}
