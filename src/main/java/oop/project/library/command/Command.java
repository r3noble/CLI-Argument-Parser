package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.ParseException;

import java.util.*;
import java.util.function.Predicate;

/**
 * The command is a publicly available record that allows the user to create custom commands.<br>
 * <br>
 * name - String,<br>
 * positional - List of Arguments,<br>
 * named - Map of String as keys and Objects as values.<br>
 *
 * @param name
 * @param positional
 * @param named
 */
public record Command (
        String name,
        List<Argument> positional,
        Map<String, Argument> named
) {
    /**
     * Compact constructor
     * Calls private method to further check command validity.
     */
    public Command {
        try {
            validateCommand(name, positional, named);
        } catch (BadArgumentException | BadCommandException e) {
            throw new RuntimeException("Command verification failed:" + e.getMessage(), e);
        }
    }

    private void validateCommand(String name, List<Argument> positional, Map<String, Argument> named) throws BadArgumentException, BadCommandException {
        boolean foundOptional = false;
        for (Argument arg : positional) {
            if (arg.optional()) {
                foundOptional = true;
            } else if (foundOptional) {
                throw new BadArgumentException("All required positional arguments must precede optional ones. Check argument: '" + arg.name() + "'.");
            }
        }

        Set<String> argumentNames = new HashSet<>();

        for (Argument argument : positional) {
            if (!argumentNames.add(argument.name())) {
                throw new BadArgumentException("Duplicate argument name found in positional arguments: " + argument.name());
            }
        }

        for (String argumentName : named.keySet()) {
            if (!argumentNames.add(argumentName)) {
                throw new BadArgumentException("Duplicate argument name found between positional and named arguments: " + argumentName);
            }
        }
    }

    /**
     * @param arguments of type String.
     * @return returns a Map of String to Objects.
     * @throws ParseException thrown if any error occurs when trying to parse arguments.
     */
    public <T> Map<String, T> parse(String arguments) throws ParseException, BadArgumentException, BadCommandException {
        var lexer = Lexer.parse(arguments);
        var map = new HashMap<String, T>();

        int positionalIndex = 0;
        if (!lexer.positional().isEmpty()) {
            for (Argument<T> argument : positional) {
                if (lexer.positional().size() <= positionalIndex) {
                    if (argument.optional() && argument.getDefaultValue() != null) {
                        map.put(argument.name(), argument.getDefaultValue());
                        continue;
                    }
                    throw new BadArgumentException("Too few positional arguments.");
                }
                var raw = lexer.positional().get(positionalIndex++);
                T parsed = argument.parser().parse(raw);

                for (Predicate<T> restriction : argument.restrictions()) {
                    if (!restriction.test(parsed)) {
                        throw new BadArgumentException("Invalid value for argument '" + argument.name() + "': " + parsed);
                    }
                }
                map.put(argument.name(), parsed);
            }
        }

        for (var entry : lexer.named().entrySet()) {
            String argName = entry.getKey();
            String rawValue = entry.getValue();

            var argument = named.get(argName);
            if (argument == null) {
                throw new BadArgumentException("Unexpected named argument: " + argName);
            }

            T parsed = (T) argument.parser().parse(rawValue);
            map.put(argName, parsed);
        }

        for (Argument<?> argument : positional) {
            if (!map.containsKey(argument.name()) && argument.optional() && argument.getDefaultValue() != null) {
                map.put(argument.name(), (T) argument.getDefaultValue());
            }
        }

        for (Map.Entry<String, Argument> entry : named.entrySet()) {
            String argName = entry.getKey();
            Argument<?> argument = entry.getValue();

            if (!map.containsKey(argName) && argument.optional() && argument.getDefaultValue() != null) {
                map.put(argName, (T) argument.getDefaultValue());
            }
        }

        if (lexer.positional().size() > positionalIndex) {
            throw new BadCommandException("Too many positional arguments.");
        }

        return map;
    }
}