package oop.project.library.command;

public class BadArgumentException extends Exception {
    public BadArgumentException(String message) {
        super(message);
    }
}