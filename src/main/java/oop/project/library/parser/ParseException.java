package oop.project.library.parser;

/**
 * A custom exception for handling parsing errors in the {@code Parser} interface implementations.
 *
 * <p>This exception provides detailed error messages about parsing failures, including
 * the problematic input value and the type of parser that encountered the error.
 */
public class ParseException extends Exception {
    /**
     * Constructs a {@code ParseException} with a specified error message.
     *
     * @param message the message describing the parsing error
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code ParseException} with a detailed message, the input value that caused the error,
     * and the type of the parser that encountered the error.
     *
     * @param message     the message describing the parsing error
     * @param inputValue  the input value that caused the error
     * @param parserType  the class type of the parser that encountered the error
     */
    public ParseException(String message, String inputValue, Class<?> parserType) {
        super("Error in parser [" + parserType.getSimpleName() + "]: " + message + " (Input: " + inputValue + ")");
    }
}
