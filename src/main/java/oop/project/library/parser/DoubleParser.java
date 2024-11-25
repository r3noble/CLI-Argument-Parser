package oop.project.library.parser;

public class DoubleParser implements Parser<Double> {

    @Override
    public Double parse(String value) throws ParseException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
