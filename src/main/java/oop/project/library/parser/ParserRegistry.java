package oop.project.library.parser;

import java.util.HashMap;
import java.util.Map;

public class ParserRegistry {
    private static final Map<Class<?>, Parser<?>> registry = new HashMap<>();

    // Static block to pre-register common parsers
    static {
        registerParser(Integer.class, new IntegerParser());
        registerParser(Double.class, new DoubleParser());
        registerParser(Boolean.class, new BooleanParser());
        registerParser(String.class, new StringParser());
    }

    public static <T> void registerParser(Class<T> type, Parser<T> parser) {
        registry.put(type, parser);
    }

    @SuppressWarnings("unchecked")
    public static <T> Parser<T> getParser(Class<T> type) throws ParseException {
        Parser<?> parser = registry.get(type);
        if (parser == null) {
            throw new ParseException("No parser registered for type: " + type.getName());
        }
        return (Parser<T>) parser;
    }
}
