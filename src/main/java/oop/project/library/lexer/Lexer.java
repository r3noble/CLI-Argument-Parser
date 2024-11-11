package oop.project.library.lexer;

import oop.project.library.parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public final class Lexer {

    // Private fields
    private final List<String> positionalArgs;
    private final Map<String, String> namedArgs;

    public Lexer() {
        this.positionalArgs = new ArrayList<>();
        this.namedArgs = new HashMap<>();
    }

    // Public methods
    public Lexer parse(String arguments) {
        this.positionalArgs.clear();
        this.namedArgs.clear();

        String[] tokens = arguments.split(" ");

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].startsWith("---")) {
                throw new AssertionError("Undefined command " + tokens[i]);
            } else if (tokens[i].startsWith("--")) {
                String flag = tokens[i].substring(2);
                if (i + 1 < tokens.length && !tokens[i + 1].startsWith("--")) {
                    String value = tokens[++i];
                    this.namedArgs.put(flag, value);
                } else {
                    return null;
                }
            } else {
                if (tokens[i].startsWith("-") && isDouble(tokens[i]))
                    if (isNegative(tokens[i])) {
                        this.positionalArgs.add(tokens[i]);
                    } else {
                        this.positionalArgs.add(tokens[i].substring(1));
                    }
                else if (!tokens[i].isEmpty()) {
                    this.positionalArgs.add(tokens[i]);
                }
            }
        }
        return this;
    }

    public List<String> getPositionalArgs() {
        return this.positionalArgs;
    }

    public Map<String, String> getNamedArgs() {
        return this.namedArgs;
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