package oop.project.library.command;

import oop.project.library.lexer.Lexer;

public final class Command {

    private static final Lexer lexer = new Lexer();

    public static Lexer execute(String arguments) {
        return lexer.parse(arguments);
    }
}