package oop.project.library.parser;

/**
 * A parser for converting a {@code String} into an {@code Integer}.
 *
 * <p>This parser attempts to interpret the input string as a 32-bit signed integer.
 * If the input string cannot be parsed, a {@link ParseException} is thrown.
 */
public class IntegerParser implements Parser<Integer> {

    /**
     * Parses the given string value into an Integer.
     *
     * @param value the string value to parse as an Integer
     * @return the parsed {@code Integer} value
     * @throws ParseException if the input string cannot be parsed as a valid integer
     */
    @Override
    public Integer parse(String value) throws ParseException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
