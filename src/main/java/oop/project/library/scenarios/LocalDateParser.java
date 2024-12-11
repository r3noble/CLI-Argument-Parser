package oop.project.library.scenarios;

import oop.project.library.parser.ParseException;
import oop.project.library.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * A parser for converting a {@code String} into a {@code LocalDate}.
 *
 * <p>This parser attempts to parse a string into a {@code LocalDate} using the default ISO-8601
 * date format (e.g., "yyyy-MM-dd"). If the input string cannot be parsed, a {@link ParseException}
 * is thrown.
 */
public class LocalDateParser implements Parser<LocalDate> {

    /**
     * Parses the given string input into a {@code LocalDate}.
     *
     * @param input the string to parse
     * @return the parsed {@code LocalDate}
     * @throws ParseException if the input string cannot be parsed as a valid date
     */
    @Override
    public LocalDate parse(String input) throws ParseException {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
