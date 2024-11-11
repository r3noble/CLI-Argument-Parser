package oop.project.library.parser;

public class Parser {

    public static Boolean parseBoolean(String input) {
        if ("true".equalsIgnoreCase(input) || "false".equalsIgnoreCase(input)) {
            return Boolean.parseBoolean(input);
        }
        throw new IllegalArgumentException("Invalid boolean value: " + input);
    }

    public static Integer parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer value: " + input);
        }
    }

    public static Double parseDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid double value: " + input);
        }
    }

    public static String parseString(String input) {
        return input;
    }
}
