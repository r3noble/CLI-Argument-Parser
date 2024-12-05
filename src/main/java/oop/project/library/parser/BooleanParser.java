package oop.project.library.parser;

/**
 * A parser for converting a {@code String} into a {@code Boolean}.
 *
 * <p>This parser interprets "true" (case-insensitive) as {@code true} and "false" (case-insensitive) as {@code false}.
 * Any other input will result in a {@link ParseException}.
 */
public class BooleanParser implements Parser<Boolean> {

    /**
     * Parses the given string value into a Boolean.
     *
     * @param value the string value to parse as a Boolean
     * @return {@code true} if the input string equals "true" (case-insensitive),
     *         {@code false} if the input string equals "false" (case-insensitive)
     * @throws ParseException if the input string is null or cannot be parsed as a valid boolean value
     */
    @Override
    public Boolean parse(String value) throws ParseException {
        if (value == null) {
            throw new ParseException("Value cannot be null");
        }
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new ParseException("Invalid boolean value: " + value);
        }
    }
}
