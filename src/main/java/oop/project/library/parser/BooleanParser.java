package oop.project.library.parser;

public class BooleanParser implements Parser<Boolean> {

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
