package oop.project.library.scenarios;

import oop.project.library.parser.ParseException;
import oop.project.library.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateParser implements Parser<LocalDate> {

    @Override
    public LocalDate parse(String input) throws ParseException {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
