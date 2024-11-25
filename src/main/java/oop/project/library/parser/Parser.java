package oop.project.library.parser;

@FunctionalInterface
public interface Parser<T> {

    T parse(String value) throws ParseException;
}