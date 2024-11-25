package oop.project.library.lexer;

import oop.project.library.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Lexer {

    public record Data(
        List<String> positional,
        Map<String, String> named
    ) {}

    // Public methods
    public static Data parse(String arguments) throws ParseException {
        List<String> tempPositional = new ArrayList<>();
        Map<String, String> tempNamed = new HashMap<>();

        String[] tokens = arguments.split(" ");

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].startsWith("---")) {
                throw new ParseException("Undefined command " + tokens[i]);
            } else if (tokens[i].startsWith("--")) {
                String flag = tokens[i].substring(2);
                if (i + 1 < tokens.length && !tokens[i + 1].startsWith("--")) {
                    String value = tokens[++i];
                    tempNamed.put(flag, value);
                } else {
                    throw new ParseException("Flag " + flag + " is missing a value.");
                }
            } else {
                if (tokens[i].startsWith("-") && isDouble(tokens[i]))
                    if (isNegative(tokens[i])) {
                        tempPositional.add(tokens[i]);
                    } else {
                        tempPositional.add(tokens[i].substring(1));
                    }
                else if (!tokens[i].isEmpty()) {
                    tempPositional.add(tokens[i]);
                }
            }
        }
        return new Data(tempPositional, tempNamed);
    }

    // Private helpers
    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isNegative(String str) {
        try {
            return Double.parseDouble(str) < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}