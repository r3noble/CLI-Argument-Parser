package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.ParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public record Command (
    String name,
    List<Argument> positional,
    Map<String, Argument> named
) {

    public Map<String, Object> parse(String arguments) throws ParseException {
        var lexer = Lexer.parse(arguments);
        var map = new HashMap<String, Object>();

        int positionalIndex = 0;
        if (!lexer.positional().isEmpty()) {
            for (Argument argument : positional) {
                if (lexer.positional().size() <= positionalIndex) {
                    if (argument.optional() && argument.getDefaultValue() != null) {
                        map.put(argument.name(), argument.getDefaultValue());
                        continue;
                    }
                    throw new ParseException("Too few positional arguments.");
                }
                var raw = lexer.positional().get(positionalIndex++);
                var parsed = argument.parser().parse(raw);

                for (Predicate<Object> restriction : argument.restrictions()) {
                    if (!restriction.test(parsed)) {
                        throw new ParseException("Invalid value for argument '" + argument.name() + "': " + parsed);
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
                throw new ParseException("Unexpected named argument: " + argName);
            }

            var parsed = argument.parser().parse(rawValue);
            map.put(argName, parsed);
        }

        for (Argument argument : positional) {
            if (!map.containsKey(argument.name()) && argument.optional() && argument.getDefaultValue() != null) {
                map.put(argument.name(), argument.getDefaultValue());
            }
        }

        for (Map.Entry<String, Argument> entry : named.entrySet()) {
            String argName = entry.getKey();
            Argument argument = entry.getValue();

            if (!map.containsKey(argName) && argument.optional() && argument.getDefaultValue() != null) {
                map.put(argName, argument.getDefaultValue());
            }
        }


        if (lexer.positional().size() > positionalIndex) {
            throw new ParseException("Too many positional arguments.");
        }

        return map;
    }
}