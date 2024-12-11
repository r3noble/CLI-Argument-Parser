package oop.project.library.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * A registry for managing and accessing parsers for various types.
 *
 * <p>The {@code ParserRegistry} provides a centralized mechanism to register and retrieve parsers
 * for specific types. It comes with pre-registered parsers for common types like {@code Integer},
 * {@code Double}, {@code Boolean}, and {@code String}.
 */
public class ParserRegistry {
    private static final Map<Class<?>, Parser<?>> registry = new HashMap<>();

    // Static block to pre-register common parsers
    static {
        registerParser(Integer.class, new IntegerParser());
        registerParser(Double.class, new DoubleParser());
        registerParser(Boolean.class, new BooleanParser());
        registerParser(String.class, new StringParser());
    }

    /**
     * Registers a parser for a specific type.
     *
     * @param <T>    the type of objects the parser handles
     * @param type   the class of the type to register
     * @param parser the parser instance to associate with the specified type
     * @throws IllegalStateException if a parser for the specified type is already registered
     */
    public static <T> void registerParser(Class<T> type, Parser<T> parser) {
        if (registry.containsKey(type)) {
            throw new IllegalStateException("A parser for type " + type.getName() + " is already registered.");
        }
        registry.put(type, parser);
    }

    /**
     * Retrieves a parser for a specific type.
     *
     * @param <T>  the type of objects the parser handles
     * @param type the class of the type to retrieve the parser for
     * @return the parser associated with the specified type
     * @throws IllegalStateException if no parser is registered for the specified type
     */
    @SuppressWarnings("unchecked")
    public static <T> Parser<T> getParser(Class<T> type) {
        Parser<?> parser = registry.get(type);
        if (parser == null) {
            throw new IllegalStateException("No parser registered for type: " + type.getName());
        }
        return (Parser<T>) parser;
    }
}
