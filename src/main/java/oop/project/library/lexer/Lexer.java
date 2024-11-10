package oop.project.library.lexer;

import oop.project.library.parser.Parser;

import java.util.HashMap;
import java.util.Map;

public final class Lexer {

    // Public methods
    public static Map<String, Object> parse(String command, String arguments) {
        return switch (command) {
            case "lex" -> parseLex(arguments);
            case "add" -> parseAdd(arguments);
            case "sub" -> parseSub(arguments);
            case "fizzbuzz" -> parseFizzbuzz(arguments);
            case "difficulty" -> parseDifficulty(arguments);
            case "echo" -> parseEcho(arguments);
            case "search" -> parseSearch(arguments);
            case "weekday" -> parseWeekday(arguments);
            default -> throw new AssertionError("Undefined command " + command + ".");
        };
    }

    // Private methods
    private static Map<String, Object> parseLex(String arguments) {
        Map<String, Object> lexMap = new HashMap<>();

        String[] tokens = arguments.split(" ");

        var positionalIndex = 0;

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].startsWith("---")) {
                throw new AssertionError("Undefined command " + tokens[i]);
            } else if (tokens[i].startsWith("--")) {
                String flag = tokens[i].substring(2);
                if (i + 1 < tokens.length && !tokens[i + 1].startsWith("--")) {
                    String value = tokens[++i];
                    lexMap.put(flag, value);
                } else {
                    return null;
                }
            } else {
                if (tokens[i].startsWith("-") && isDouble(tokens[i]))
                    if (isNegative(tokens[i])) {
                        lexMap.put(String.valueOf(positionalIndex++), tokens[i]);
                    } else {
                        lexMap.put(String.valueOf(positionalIndex++), tokens[i].substring(1));
                    }
                else {
                    lexMap.put(String.valueOf(positionalIndex++), tokens[i]);
                }
            }
        }
        return lexMap;
    }

    private static Map<String, Object> parseAdd(String arguments) {
        Map<String, Object> lexMap = new HashMap<>();

        String[] tokens = arguments.split(" ");
        if (tokens.length != 2) {
            return null;
        }

        Object right = Parser.parse(tokens[1]);
        Object left = Parser.parse(tokens[0]);

        if (!(right instanceof Integer) || !(left instanceof Integer)) {
            return null;
        }

        lexMap.put("right", right);
        lexMap.put("left", left);

        return lexMap;
    }

    private static Map<String, Object> parseSub(String arguments) {
        Map<String, Object> lexMap = new HashMap<>();

        String[] tokens = arguments.split(" ");
        if (tokens.length != 4) {
            return null;
        }

        for (int i = 0; i < tokens.length; i++) {
            String flag = "";

            switch (tokens[i]) {
                case "--left" -> flag = "left";
                case "--right" -> flag = "right";
                default -> {
                    return null;
                }
            }

            if (i + 1 < tokens.length) {
                Object obj = Parser.parse(tokens[++i]);
                if (obj instanceof Integer value) {
                    lexMap.put(flag, value.doubleValue());
                } else if (obj instanceof Double value) {
                    lexMap.put(flag, value);
                }
            } else {
                return null;
            }
        }

        if (!lexMap.containsKey("left") || !lexMap.containsKey("right")) {
            return null;
        }

        return lexMap;
    }

    private static Map<String, Object> parseFizzbuzz(String arguments) {
        Map<String, Object> lexMap = new HashMap<>();

        String[] tokens = arguments.split(" ");
        if (tokens.length != 1) {
            return null;
        }

        Object obj = Parser.parse(tokens[0]);

        if (!(obj instanceof Integer number)) {
            return null;
        }

        if (number >= 1 && number <= 100) {
            lexMap.put("number", number);
            return lexMap;
        } else
            return null;
    }

    private static Map<String, Object> parseDifficulty(String arguments) {
        Map<String, Object> lexMap = new HashMap<>();

        String[] tokens = arguments.split(" ");

        if (tokens.length != 1) {
            return null;
        }

        var obj = Parser.parse(tokens[0]);

        if (obj instanceof String difficulty) {
            if (difficulty.equals("easy") || difficulty.equals("normal")
                    || difficulty.equals("medium") || difficulty.equals("hard")) {
                lexMap.put("difficulty", difficulty);
            } else
                return null;
        }
        return lexMap;
    }

    //TODO
    private static Map<String, Object> parseEcho(String arguments) {
        Map<String, Object> lexMap = new HashMap<>();

        return lexMap;
    }

    //TODO
    private static Map<String, Object> parseSearch(String arguments) {
        Map<String, Object> lexMap = new HashMap<>();

        return lexMap;
    }

    // TODO
    private static Map<String, Object> parseWeekday(String arguments) {
        Map<String, Object> lexMap = new HashMap<>();

        return lexMap;
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