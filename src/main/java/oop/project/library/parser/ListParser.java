package oop.project.library.parser;

import java.util.ArrayList;
import java.util.List;

public class ListParser<T> implements Parser<List<T>> {
    private final Parser<T> elementParser;
    private final String delimiter;

    public ListParser(Parser<T> elementParser, String delimiter) {
        this.elementParser = elementParser;
        this.delimiter = delimiter;
    }

    @Override
    public List<T> parse(String value) throws ParseException {
        String[] parts = value.split(delimiter);
        List<T> parsedValues = new ArrayList<>();
        for (String part : parts) {
            parsedValues.add(elementParser.parse(part.trim()));
        }
        return parsedValues;
    }
}
