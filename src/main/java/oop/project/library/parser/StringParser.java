package oop.project.library.parser;

/**
 * A parser for validating and processing a {@code String}.
 *
 * <p>This parser ensures the input string is not {@code null} or empty.
 * The input string is trimmed of leading and trailing whitespace before being returned.
 * If the input is invalid, a {@link ParseException} is thrown.
 */
public class StringParser implements Parser<String> {
    /**
     * Parses the given string value by validating and trimming it.
     *
     * @param value the input string to parse
     * @return the trimmed {@code String} value
     * @throws ParseException if the input string is {@code null} or empty
     */
    @Override
    public String parse(String value) throws ParseException {
        if (value == null || value.isEmpty()) {
            throw new ParseException("String value cannot be null or empty.");
        }
        return value.trim();
    }
}
