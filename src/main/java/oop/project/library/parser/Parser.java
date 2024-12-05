package oop.project.library.parser;

/**
 * A functional interface for parsing a {@code String} into a specific type.
 *
 * @param <T> the type of the object resulting from the parsing
 */
@FunctionalInterface
public interface Parser<T> {

    /**
     * Parses the given string into an object of type {@code T}.
     *
     * @param value the string to parse
     * @return the parsed object of type {@code T}
     * @throws ParseException if the input string cannot be parsed into the desired type
     */
    T parse(String value) throws ParseException;
}