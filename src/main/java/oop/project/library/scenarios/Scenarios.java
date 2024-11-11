package oop.project.library.scenarios;

import oop.project.library.command.Command;
import oop.project.library.lexer.Lexer;
import oop.project.library.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        //Note: For ease of testing, this should use your Lexer implementation
        //directly rather and return those values.
        var obj = Command.execute(arguments);

        Map<String, Object> result = new HashMap<>();

        try {
            var positionalArgs = obj.getPositionalArgs();
            for (int i = 0; i < positionalArgs.size(); i++) {
                result.put(String.valueOf(i), positionalArgs.get(i));
            }
            var namedArgs = obj.getNamedArgs();
            result.putAll(namedArgs);
        } catch (NullPointerException e) {
            return new Result.Failure<>("Null pointer exception thrown in positionalArgs");
        }

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

        var obj = Command.execute(arguments);

        if (obj.getPositionalArgs().size() != 2)
            return new Result.Failure<>("Not enough arguments");

        Object left, right;
        try {
            left = Parser.parseInt(obj.getPositionalArgs().get(0));
            right = Parser.parseInt(obj.getPositionalArgs().get(1));
        } catch (Exception e) {
            return new Result.Failure<>("One of the arguments is not an integer");
        }


//        if (!(leftObj instanceof Integer left) || !(rightObj instanceof Integer right)) {
//            return new Result.Failure<>("One of the arguments is not an integer");
//        }

        Map<String, Object> result = new HashMap<>();

        result.put("left", left);
        result.put("right", right);

        return new Result.Success<>(result);
    }

    private static Result<Map<String, Object>> sub(String arguments) {
        String[] tokens = arguments.split(" ");
        if (tokens.length != 4) {
            return new Result.Failure<>("Not enough arguments");
        }

        Map<String, Object> result = new HashMap<>();

        for (int i = 0; i < tokens.length; i++) {
            String flag = "";

            switch (tokens[i]) {
                case "--left" -> flag = "left";
                case "--right" -> flag = "right";
                default -> {
                    return new Result.Failure<>("Undefined flag " + flag + ".");
                }
            }

            if (i + 1 < tokens.length) {
                Object obj;
                try {
                    obj = Parser.parseDouble(tokens[++i]);
                } catch (Exception e) {
                    return new Result.Failure<>("One of the arguments cannot be cast to a double.");
                }
                result.put(flag, obj);
//                if (obj instanceof Integer value) {
//                    result.put(flag, value.doubleValue());
//                } else if (obj instanceof Double value) {
//                    result.put(flag, value);
//                }
            } else {
                return new Result.Failure<>("TODO");
            }
        }

        if (!result.containsKey("left") || !result.containsKey("right")) {
            return new Result.Failure<>("Missing one or more values");
        }

        //var obj = Command.execute("sub", arguments);
        return new Result.Success<>(result);
        //return obj != null ? new Result.Success<>(obj) : new Result.Failure<>("");
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        //Note: This is the first command your library may not support all the
        //functionality to implement yet. This is fine - parse the number like
        //normal, then check the range manually. The goal is to get a feel for
        //the validation involved even if it's not in the library yet.
        //var number = IntegerParser.parse(lexedArguments.get("number"));
        //if (number < 1 || number > 100) ...
        var obj = Command.execute(arguments);

        var positionalArgs = obj.getPositionalArgs();

        if (positionalArgs.size() != 1) {
            return new Result.Failure<>("Not enough arguments");
        }

        Object object;
        try {
            object = Parser.parseInt(positionalArgs.getFirst());
        } catch (Exception e) {
            return new Result.Failure<>("Argument is not an integer");
        }

        if (!(object instanceof Integer number)) {
            return new Result.Failure<>("Argument is not an integer");
        }

        Map<String, Object> result = new HashMap<>();

        if (number >= 1 && number <= 100) {
            result.put("number", number);
            return new Result.Success<>(result);
        } else
            return new Result.Failure<>("Number out of range");
    }

    private static Result<Map<String, Object>> difficulty(String arguments) {
        var obj = Command.execute(arguments);

        var positionalArgs = obj.getPositionalArgs();

        if (positionalArgs.size() != 1) {
            return new Result.Failure<>("Not enough arguments");
        }

        Object object;
        try {
            object = Parser.parseString(positionalArgs.getFirst());
        } catch (Exception e) {
            return new Result.Failure<>("Argument cannot be used as a string");
        }

        Map<String, Object> result = new HashMap<>();

        if (object instanceof String difficulty) {
            if (difficulty.equals("easy") || difficulty.equals("normal")
                    || difficulty.equals("medium") || difficulty.equals("hard")) {
                result.put("difficulty", difficulty);
            } else
                return new Result.Failure<>("Unknown difficulty");
        }
        return new Result.Success<>(result);
    }

    private static Result<Map<String, Object>> echo(String arguments) {
        var obj = Command.execute(arguments);

        var positionalArgs = obj.getPositionalArgs();

        Map<String, Object> result = new HashMap<>();

        if (positionalArgs.isEmpty()) {
            result.put("message", "Echo, echo, echo!");
            return new Result.Success<>(result);
        }

        Object message;
        try {
            message = Parser.parseString(positionalArgs.getFirst());
        } catch (Exception e) {
            return new Result.Failure<>("Argument cannot be used as a string");
        }

        result.put("message", message);

        return new Result.Success<>(result);
    }

    private static Result<Map<String, Object>> search(String arguments) {
        // Ensure Command.execute() does not return null
        var obj = Command.execute(arguments);
        if (obj == null) {
            return new Result.Failure<>("No flag value was provided for case-insensitive");
        }

        var positionalArgs = obj.getPositionalArgs();
        if (positionalArgs.isEmpty()) {
            return new Result.Failure<>("Missing arguments");
        }
        if (positionalArgs.size() > 1) {
            return new Result.Failure<>("Too many positional arguments");
        }

        // Initialize result map
        Map<String, Object> result = new HashMap<>();
        var term = Parser.parseString(positionalArgs.getFirst());
        result.put("term", term);

        // Handle named arguments
        var namedArgs = obj.getNamedArgs();
        if (namedArgs.isEmpty()) {
            result.put("case-insensitive", false);
            return new Result.Success<>(result); // If no named args, return with default case-insensitive
        }

        if (namedArgs.size() > 1) {
            return new Result.Failure<>("Too many named arguments");
        }

        if (namedArgs.containsKey("case-insensitive")) {
            // Check for "case-insensitive" flag and set accordingly
            String caseInsensitive = Parser.parseString(namedArgs.get("case-insensitive"));
            if ("true".equalsIgnoreCase(caseInsensitive)) {
                result.put("case-insensitive", true);
            } else {
                result.put("case-insensitive", false);
            }
        }

        // Return the result map in a Success object
        return new Result.Success<>(result);
    }


    private static Result<Map<String, Object>> weekday(String arguments) {
        var obj = Command.execute(arguments);

        var positionalArgs = obj.getPositionalArgs();

        Object date = Parser.parseString(positionalArgs.getFirst());

        try {
            LocalDate localDate = LocalDate.parse(date.toString());
            Map<String, Object> result = new HashMap<>();
            result.put("date", localDate);
            return new Result.Success<>(result);
        } catch (DateTimeParseException e) {
            return new Result.Failure<>("Invalid date format");
        }
    }

}