package oop.project.library.parser;

public class IntegerParser implements Parser<Integer> {

    @Override
    public Integer parse(String value) throws ParseException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
