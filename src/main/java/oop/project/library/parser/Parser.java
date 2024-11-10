package oop.project.library.parser;

public final class Parser {
    // Public methods
    public static Object parse(String input) {
        // Test for Boolean
        if (input.equals("true") || input.equals("false"))
            return Boolean.parseBoolean(input);

        // Test for Integer
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ignored) { }

        // Test for Double
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException ignored) { }

        // Not a Boolean, Integer, or a Double so treat as String
        return input;
    }
}