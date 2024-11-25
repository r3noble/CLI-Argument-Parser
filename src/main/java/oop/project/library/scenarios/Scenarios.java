package oop.project.library.scenarios;

import oop.project.library.command.Command;
import oop.project.library.command.Argument;
import oop.project.library.lexer.Lexer;
import oop.project.library.parser.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Scenarios {

    public static Result<Map<String, Object>> parse(String command) {
        //Note: Unlike argparse4j, our library will contain a lexer than can
        //support an arbitrary String (instead of requiring a String[] array).
        //We still need to split the base command from the actual arguments
        //string to know which scenario (aka command) we're trying to parse
        //arguments for. This sounds like something a library should handle...
        var split = command.split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "lex" -> lex(arguments);
            case "add" -> add(arguments);
            case "sub" -> sub(arguments);
            case "fizzbuzz" -> fizzbuzz(arguments);
            case "difficulty" -> difficulty(arguments);
            case "echo" -> echo(arguments);
            case "search" -> search(arguments);
            case "weekday" -> weekday(arguments);
            default -> throw new AssertionError("Undefined command " + base + ".");
        };
    }

    private static Result<Map<String, Object>> lex(String arguments) {
        Lexer.Data lexerData;
        try {
            lexerData = Lexer.parse(arguments);  // Lexer parses arguments into positional and named data
        } catch (ParseException e) {
            return new Result.Failure<>("Error parsing arguments: " + e.getMessage());
        }

        // Create a map to hold the parsed arguments
        Map<String, Object> result = new HashMap<>();

        // Process Positional Arguments
        List<String> positionalArgs = lexerData.positional();
        for (int i = 0; i < positionalArgs.size(); i++) {
            String arg = positionalArgs.get(i);
            result.put(String.valueOf(i), arg);  // You can modify the key as needed
        }

        // Process Named Arguments
        Map<String, String> namedArgs = lexerData.named();
        for (Map.Entry<String, String> entry : namedArgs.entrySet()) {
            String flag = entry.getKey();
            String value = entry.getValue();
            result.put(flag, value);
        }

        // Return the result as a successful outcome
        return new Result.Success<>(result);
    }

    private static Result<Map<String, Object>> add(String arguments) {
        //Note: For this part of the project, we're focused on lexing/parsing.
        //The implementation of these scenarios isn't going to look like a full
        //command, but rather some weird hodge-podge mix. For example:
        //var args = Lexer.parse(arguments);
        //var left = IntegerParser.parse(args.positional[0]);
        //This is fine - our goal right now is to implement this functionality
        //so we can build up the actual command system in Part 3.
        var add = new Command(
                "add",
                List.of(
                        new Argument("left", new IntegerParser(), false),
                        new Argument("right", new IntegerParser(), false)
                )
        );

        Map<String, Object> parsedArguments;
        try {
            parsedArguments = add.parse(arguments);
        } catch (ParseException e) {
            return new Result.Failure<>("Error parsing arguments: " + e.getMessage());
        }

        var left = parsedArguments.get("left");
        var right = parsedArguments.get("right");

        Map<String, Object> result = new HashMap<>();
        result.put("left", left);
        result.put("right", right);

        return new Result.Success<>(result);
    }

    private static Result<Map<String, Object>> sub(String arguments) {
        var sub = new Command(
                "sub",
                List.of(
                        new Argument("left", new DoubleParser(), false),
                        new Argument("right", new DoubleParser(), false)
                )
        );

        Map<String, Object> parsedArguments;
        try {
            parsedArguments = sub.parse(arguments);
        } catch (ParseException e) {
            return new Result.Failure<>("Error parsing arguments: " + e.getMessage());
        }

        var left = parsedArguments.get("left");
        var right = parsedArguments.get("right");

        Map<String, Object> result = new HashMap<>();
        result.put("left", left);
        result.put("right", right);

        return new Result.Success<>(result);
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        //Note: This is the first command your library may not support all the
        //functionality to implement yet. This is fine - parse the number like
        //normal, then check the range manually. The goal is to get a feel for
        //the validation involved even if it's not in the library yet.
        //var number = IntegerParser.parse(lexedArguments.get("number"));
        //if (number < 1 || number > 100) ...
        var fizzbuzz = new Command(
                "fizzbuzz",
                List.of(
                        new Argument("number", new IntegerParser(), false, value -> (int) value >= 1 && (int) value <= 100)
                )
        );

        Map<String, Object> parsedArguments;
        try {
            parsedArguments = fizzbuzz.parse(arguments);
        } catch (ParseException e) {
            return new Result.Failure<>("Error parsing arguments: " + e.getMessage());
        }

        var left = parsedArguments.get("number");

        Map<String, Object> result = new HashMap<>();
        result.put("number", left);

        return new Result.Success<>(result);
    }

    private static Result<Map<String, Object>> difficulty(String arguments) {
        Command difficulty = new Command(
                "difficulty",
                List.of(
                        new Argument("difficulty", new StringParser(), false, value -> value.equals("easy") || value.equals("normal") || value.equals("medium") || value.equals("hard"))
                )
        );

        Map<String, Object> parsedArguments;
        try {
            parsedArguments = difficulty.parse(arguments);
        } catch (ParseException e) {
            return new Result.Failure<>("Error parsing arguments: " + e.getMessage());
        }

        var diff = parsedArguments.get("difficulty");

        Map<String, Object> result = new HashMap<>();
        result.put("difficulty", diff);

        return new Result.Success<>(result);
    }

    private static Result<Map<String, Object>> echo(String arguments) {
        Command echo = new Command(
                "echo",
                List.of(
                        new Argument("message", new StringParser(), true, List.of(), () -> "Echo, echo, echo!" )
                )
        );

        Map<String, Object> parsedArguments;
        try {
            parsedArguments = echo.parse(arguments);
        } catch (ParseException e) {
            return new Result.Failure<>("Error parsing arguments: " + e.getMessage());
        }

        var message = parsedArguments.get("message");
        Map<String, Object> result = new HashMap<>();

        result.put("message", message);

        return new Result.Success<>(result);
    }

    private static Result<Map<String, Object>> search(String arguments) {
        Command search = new Command(
                "search",
                List.of(
                        new Argument("term", new StringParser(), false),
                        new Argument("case-insensitive", new BooleanParser(), true, List.of(), () -> false)
                )
        );

        Map<String, Object> parsedArguments;
        try {
            parsedArguments = search.parse(arguments);
        } catch (ParseException e) {
            return new Result.Failure<>("Error parsing arguments: " + e.getMessage());
        }

        var term = parsedArguments.get("term");
        var flag = parsedArguments.get("case-insensitive");

        Map<String, Object> result = new HashMap<>();

        result.put("term", term);
        result.put("case-insensitive", flag);

        return new Result.Success<>(result);
    }

    private static Result<Map<String, Object>> weekday(String arguments) {
        Command weekday = new Command(
                "weekday",
                List.of(
                        new Argument("date", new LocalDateParser())
                )
        );

        Map<String, Object> parsedArguments;
        try {
            parsedArguments = weekday.parse(arguments);
        } catch (ParseException e) {
            return new Result.Failure<>("Error parsing arguments: " + e.getMessage());
        }

        var date = parsedArguments.get("date");

        Map<String, Object> result = new HashMap<>();
        result.put("date", date);

        return new Result.Success<>(result);
    }

}