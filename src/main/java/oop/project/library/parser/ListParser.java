package oop.project.library.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser for converting a delimited {@code String} into a {@code List} of elements of a specified type.
 *
 * <p>This parser uses an underlying {@link Parser} to parse each element of the list. The input string
 * is split into parts based on a specified delimiter, and each part is individually parsed.
 *
 * @param <T> the type of elements in the resulting list
 */
public class ListParser<T> implements Parser<List<T>> {
    private final Parser<T> elementParser;
    private final String delimiter;

    /**
     * Constructs a {@code ListParser} with the specified element parser and delimiter.
     *
     * @param elementParser the parser used to parse individual elements of the list
     * @param delimiter     the delimiter used to split the input string into parts
     */
    public ListParser(Parser<T> elementParser, String delimiter) {
        this.elementParser = elementParser;
        this.delimiter = delimiter;
    }

    /**
     * Parses the given string value into a {@code List} of elements.
     *
     * <p>The input string is split into parts based on the specified delimiter. Each part is trimmed
     * of whitespace and parsed using the provided element parser.
     *
     * @param value the input string to parse
     * @return a list of parsed elements
     * @throws ParseException if any part of the input string cannot be parsed as the specified type
     */
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
