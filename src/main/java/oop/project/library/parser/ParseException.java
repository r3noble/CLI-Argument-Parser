package oop.project.library.parser;

public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, String inputValue, Class<?> parserType) {
        super("Error in parser [" + parserType.getSimpleName() + "]: " + message + " (Input: " + inputValue + ")");
    }
}
