package oop.project.library.scenarios;

import oop.project.library.command.Command;
import oop.project.library.lexer.Lexer;
import oop.project.library.parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        var lexMap = Lexer.parse("lex", arguments);
        return lexMap != null ? new Result.Success<>(lexMap) : new Result.Failure<>("");
    }

    private static Result<Map<String, Object>> add(String arguments) {
        //Note: For this part of the project, we're focused on lexing/parsing.
        //The implementation of these scenarios isn't going to look like a full
        //command, but rather some weird hodge-podge mix. For example:
        //var args = Lexer.parse(arguments);
        //var left = IntegerParser.parse(args.positional[0]);
        //This is fine - our goal right now is to implement this functionality
        //so we can build up the actual command system in Part 3.
        var obj = Command.execute("add", arguments);
        return obj != null ? new Result.Success<>(obj) : new Result.Failure<>("");
    }

    private static Result<Map<String, Object>> sub(String arguments) {
        var obj = Command.execute("sub", arguments);
        return obj != null ? new Result.Success<>(obj) : new Result.Failure<>("");
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        //Note: This is the first command your library may not support all the
        //functionality to implement yet. This is fine - parse the number like
        //normal, then check the range manually. The goal is to get a feel for
        //the validation involved even if it's not in the library yet.
        //var number = IntegerParser.parse(lexedArguments.get("number"));
        //if (number < 1 || number > 100) ...
        var obj = Command.execute("fizzbuzz", arguments);
        return obj != null ? new Result.Success<>(obj) : new Result.Failure<>("");
    }

    private static Result<Map<String, Object>> difficulty(String arguments) {
        var obj = Command.execute("difficulty", arguments);
        return obj != null ? new Result.Success<>(obj) : new Result.Failure<>("");
    }

    private static Result<Map<String, Object>> echo(String arguments) {
        var obj = Command.execute("echo", arguments);
        return obj != null ? new Result.Success<>(obj) : new Result.Failure<>("");
    }

    private static Result<Map<String, Object>> search(String arguments) {
        var obj = Command.execute("search", arguments);
        return obj != null ? new Result.Success<>(obj) : new Result.Failure<>("");
    }

    private static Result<Map<String, Object>> weekday(String arguments) {
        var obj = Command.execute("weekday", arguments);
        return obj != null ? new Result.Success<>(obj) : new Result.Failure<>("");
    }

}