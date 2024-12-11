package oop.project.library.command;

public class BadCommandException extends Exception {
    public BadCommandException(String message) {
        super(message);
    }
}