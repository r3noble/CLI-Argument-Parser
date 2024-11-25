package oop.project.library.command;

import oop.project.library.parser.Parser;

import java.util.function.Predicate;
import java.util.List;
import java.util.function.Supplier;

public record Argument (
    String name,
    Parser<?> parser,
    boolean optional,
    List<Predicate<Object>> restrictions,
    Supplier<Object> defaultValue
) {

    public Argument(String name, Parser<?> parser) {
        this(name, parser, false, List.of(), null);
    }

    public Argument(String name, Parser<?> parser, boolean optional) {
        this(name, parser, optional, List.of(), null);
    }

    public Argument(String name, Parser<?> parser, boolean optional, Predicate<Object> restriction) {
        this(name, parser, optional, List.of(restriction), null);
    }

    public Argument (String name, Parser<?> parser, boolean optional, Predicate<Object> restriction, Supplier<Object> defaultValue) {
        this(name, parser, optional, List.of(restriction), defaultValue);
    }

    public Object getDefaultValue() {
        return defaultValue.get();
    }
}
