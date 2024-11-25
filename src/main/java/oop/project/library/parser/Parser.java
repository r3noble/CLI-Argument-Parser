package oop.project.library.parser;

@FunctionalInterface
public interface Parser<T> {

    T parse(String value) throws ParseException;

    public static <T> T useParser(Parser<T> parser, String value) throws ParseException {
        return parser.parse(value);
    }
}