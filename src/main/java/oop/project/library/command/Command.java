package oop.project.library.command;

import oop.project.library.lexer.Lexer;

import java.util.Map;

public final class Command {
    public static Map<String, Object> execute(String command, String arguments) {
        return Lexer.parse(command, arguments);
    }
}