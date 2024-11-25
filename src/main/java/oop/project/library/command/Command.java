package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.ParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public record Command (
    String name,
    List<Argument> positional
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

        // Process named arguments
        for (var entry : lexer.named().entrySet()) {
            String argName = entry.getKey();
            String rawValue = entry.getValue();

            var argument = positional.stream()
                    .filter(arg -> arg.name().equals(argName))
                    .findFirst()
                    .orElseThrow(() -> new ParseException("Unexpected named argument: " + argName));

            var parsed = argument.parser().parse(rawValue);
            map.put(argName, parsed);
        }


        // Apply default values for any missing named arguments (if they are optional)
        for (Argument argument : positional) {
            // Only set default value if it's optional and not already present
            if (!map.containsKey(argument.name()) && argument.optional() && argument.getDefaultValue() != null) {
                map.put(argument.name(), argument.getDefaultValue());
            }
        }

        if (lexer.positional().size() > positionalIndex) {
            throw new ParseException("Too many positional arguments.");
        }

        return map;
    }
}