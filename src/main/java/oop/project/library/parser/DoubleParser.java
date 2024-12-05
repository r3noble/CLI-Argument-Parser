package oop.project.library.parser;

/**
 * A parser for converting a {@code String} into a {@code Double}.
 *
 * <p>This parser attempts to interpret the input string as a double-precision floating-point number.
 * If the input string cannot be parsed, a {@link ParseException} is thrown.
 */
public class DoubleParser implements Parser<Double> {

    /**
     * Parses the given string value into a Double.
     *
     * @param value the string value to parse as a Double
     * @return the parsed {@code Double} value
     * @throws ParseException if the input string cannot be parsed as a valid double
     */
    @Override
    public Double parse(String value) throws ParseException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
