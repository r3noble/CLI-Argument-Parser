package oop.project.library.scenarios;

import oop.project.library.command.*;
import oop.project.library.parser.*;
import oop.project.library.lexer.Lexer;

import java.time.LocalDate;
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
            case "test" -> testCommand(arguments);
            default -> throw new AssertionError("Undefined command " + base + ".");
        };
    }

    private static <T> Result<Map<String, Object>> testCommand(String arguments) {
        try {
            Command test = new Command(
                    "test",
                    List.of(
                            Argument.<Integer>build()
                                    .name("one")
                                    .parser(new IntegerParser())
                                    .optional()
                                    .build(),
                            Argument.<Integer>build()
                                    .name("two")
                                    .parser(new IntegerParser())
                                    .optional()
                                    .build(),
                            Argument.<Integer>build()
                                    .name("three")
                                    .parser(new IntegerParser())
                                    .optional()
                                    .build()
                    ),
                    Map.of(
                            "flag", Argument.<Boolean>build()
                                    .name("flag")
                                    .parser(new BooleanParser())
                                    .build()
                    )
            );

            Map<String, T> parsedArguments;
            parsedArguments = test.parse(arguments);
            T one = parsedArguments.get("one");
            T two = parsedArguments.get("two");
            T three = parsedArguments.get("three");
            T flag = parsedArguments.get("flag");
            Map<String, Object> result = new HashMap<>();
            result.put("one", one);
            result.put("two", two);
            result.put("three", three);
            result.put("flag", flag);
            return new Result.Success<>(result);
        } catch (RuntimeException | ParseException | BadArgumentException | BadCommandException e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> lex(String arguments) {
        Lexer.Data lexerData;
        try {
            lexerData = Lexer.parse(arguments);
        } catch (ParseException e) {
            return new Result.Failure<>("Error parsing arguments: " + e.getMessage());
        }

        Map<String, Object> result = new HashMap<>();

        List<String> positionalArgs = lexerData.positional();
        for (int i = 0; i < positionalArgs.size(); i++) {
            String arg = positionalArgs.get(i);
            result.put(String.valueOf(i), arg);
        }

        Map<String, String> namedArgs = lexerData.named();
        for (Map.Entry<String, String> entry : namedArgs.entrySet()) {
            String flag = entry.getKey();
            String value = entry.getValue();
            result.put(flag, value);
        }

        return new Result.Success<>(result);
    }

    private static <T> Result<Map<String, Object>> add(String arguments) {
        //Note: For this part of the project, we're focused on lexing/parsing.
        //The implementation of these scenarios isn't going to look like a full
        //command, but rather some weird hodge-podge mix. For example:
        //var args = Lexer.parse(arguments);
        //var left = IntegerParser.parse(args.positional[0]);
        //This is fine - our goal right now is to implement this functionality
        //so we can build up the actual command system in Part 3.
        try {
            var add = new Command(
                    "add",
                    List.of(
                            Argument.<Integer>build()
                                    .name("left")
                                    .parser(new IntegerParser())
                                    .build(),
                            Argument.<Integer>build()
                                    .name("right")
                                    .parser(new IntegerParser())
                                    .build()
                    ),
                    Map.of()
            );

            Map<String, T> parsedArguments;
            parsedArguments = add.parse(arguments);
            T left = parsedArguments.get("left");
            T right = parsedArguments.get("right");
            Map<String, Object> result = new HashMap<>();
            result.put("left", left);
            result.put("right", right);
            return new Result.Success<>(result);
        } catch (RuntimeException | ParseException | BadArgumentException | BadCommandException e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static <T> Result<Map<String, Object>> sub(String arguments) {
        try {
            var sub = new Command(
                    "sub",
                    List.of(),
                    Map.of(
                            "left", Argument.<Double>build()
                                    .name("left")
                                    .parser(new DoubleParser())
                                    .build(),
                            "right", Argument.<Double>build()
                                    .name("right")
                                    .parser(new DoubleParser())
                                    .build()
                    )
            );

            Map<String, T> parsedArguments;
            parsedArguments = sub.parse(arguments);
            T left = parsedArguments.get("left");
            T right = parsedArguments.get("right");
            Map<String, Object> result = new HashMap<>();
            result.put("left", left);
            result.put("right", right);
            return new Result.Success<>(result);
        } catch (RuntimeException | ParseException | BadArgumentException | BadCommandException e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static <T> Result<Map<String, Object>> fizzbuzz(String arguments) {
        //Note: This is the first command your library may not support all the
        //functionality to implement yet. This is fine - parse the number like
        //normal, then check the range manually. The goal is to get a feel for
        //the validation involved even if it's not in the library yet.
        //var number = IntegerParser.parse(lexedArguments.get("number"));
        //if (number < 1 || number > 100) ...
        try {
            var fizzbuzz = new Command(
                    "fizzbuzz",
                    List.of(
                            Argument.<Integer>build()
                                    .name("number")
                                    .parser(new IntegerParser())
                                    .addRestriction(value -> value > 0)
                                    .addRestriction(value -> value < 100)
                                    .build()
                    ),
                    Map.of()
            );

            Map<String, T> parsedArguments;
            parsedArguments = fizzbuzz.parse(arguments);
            T left = parsedArguments.get("number");
            Map<String, Object> result = new HashMap<>();
            result.put("number", left);
            return new Result.Success<>(result);
        } catch (RuntimeException | ParseException | BadArgumentException | BadCommandException e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static <T> Result<Map<String, Object>> difficulty(String arguments) {
        try {
            Command difficulty = new Command(
                    "difficulty",
                    List.of(
                            Argument.<String>build()
                                    .name("difficulty")
                                    .parser(new StringParser())
                                    .addRestriction(value -> value.equals("easy") || value.equals("normal") || value.equals("medium") || value.equals("hard"))
                                    .build()
                    ),
                    Map.of()
            );

            Map<String, T> parsedArguments;
            parsedArguments = difficulty.parse(arguments);
            T diff = parsedArguments.get("difficulty");
            Map<String, Object> result = new HashMap<>();
            result.put("difficulty", diff);
            return new Result.Success<>(result);
        } catch (RuntimeException | ParseException | BadArgumentException | BadCommandException e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static <T> Result<Map<String, Object>> echo(String arguments) {
        try {
            Command echo = new Command(
                    "echo",
                    List.of(
                            Argument.<String>build()
                                    .name("message")
                                    .parser(new StringParser())
                                    .optional()
                                    .defaultValue(() -> "Echo, echo, echo!")
                                    .build()
                    ),
                    Map.of()
            );

            Map<String, T> parsedArguments;
            parsedArguments = echo.parse(arguments);
            T message = parsedArguments.get("message");
            Map<String, Object> result = new HashMap<>();
            result.put("message", message);
            return new Result.Success<>(result);
        } catch (RuntimeException | ParseException | BadArgumentException | BadCommandException e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static <T> Result<Map<String, Object>> search(String arguments) {
        try {
            Command search = new Command(
                    "search",
                    List.of(
                            Argument.<String>build()
                                    .name("term")
                                    .parser(new StringParser())
                                    .defaultValue(() -> String.valueOf(false))
                                    .build()
                    ),
                    Map.of(
                            "case-insensitive", Argument.<Boolean>build()
                                    .name("case-insensitive")
                                    .parser(new BooleanParser())
                                    .optional()
                                    .defaultValue(() -> false)
                                    .build()
                    )
            );

            Map<String, T> parsedArguments;
            parsedArguments = search.parse(arguments);
            T term = parsedArguments.get("term");
            T flag = parsedArguments.get("case-insensitive");
            Map<String, Object> result = new HashMap<>();
            result.put("term", term);
            result.put("case-insensitive", flag);
            return new Result.Success<>(result);
        } catch (RuntimeException | ParseException | BadArgumentException | BadCommandException e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static <T> Result<Map<String, Object>> weekday(String arguments) {
        try {
            Command weekday = new Command(
                    "weekday",
                    List.of(
                            Argument.<LocalDate>build()
                                    .name("date")
                                    .parser(new LocalDateParser())
                                    .build()
                    ),
                    Map.of()
            );

            Map<String, T> parsedArguments;
            parsedArguments = weekday.parse(arguments);
            T date = parsedArguments.get("date");
            Map<String, Object> result = new HashMap<>();
            result.put("date", date);
            return new Result.Success<>(result);
        } catch (RuntimeException | ParseException | BadArgumentException | BadCommandException e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

}