package oop.project.library.lexer;

import oop.project.library.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * The Lexer class is responsible to parsing the String[] into a data structure that is more manageable and easier to use.
 */
public class Lexer {

    /**
     * Contains a record that is in charge of keeping the positional and named arguments in a manageable un-mutable structure.
     * @param positional Accepts a List of String objects
     * @param named Accepts a Map of String to String
     */
    public record Data(
            List<String> positional,
            Map<String, String> named
    ) {}

    /**
     * Public method in charge of splitting up the String[] into the Data record.
     * @param arguments Accepts a String that represents the list of arguments and values.
     * @return returns a new record Data(positional, named).
     * @throws ParseException throws a custom ParseException if any sort of invalid input is provided into the lexer.
     */
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