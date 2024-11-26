package oop.project.library.parser;

public class StringParser implements Parser<String> {
    @Override
    public String parse(String value) throws ParseException {
        if (value == null || value.isEmpty()) {
            throw new ParseException("String value cannot be null or empty.");
        }
        return value.trim();
    }
}
