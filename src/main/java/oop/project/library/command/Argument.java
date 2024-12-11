package oop.project.library.command;

import oop.project.library.parser.Parser;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.List;
import java.util.function.Supplier;

/**
 * Uses builder pattern to build argument objects accordingly
 * Can add individual parts to the argument as needed instead of providing all arguments.
 *
 * @param name Name for the argument in the command.
 * @param parser The Parser object that handles parsing the value stored in the argument.
 * @param optional False by default, must be set to true to make the argument required.
 * @param restrictions Uses Predicates to set limitations on the value passed into the argument.
 * @param defaultValue Null be default, can accept various types of defaults.
 * @param <T> Argument accepts generics to avoid unsafe type casting.
 */
public record Argument<T> (
        String name,
        Parser<T> parser,
        boolean optional,
        List<Predicate<T>> restrictions,
        Supplier<T> defaultValue
) {
    /**
     * Gets the default value.
     * @return returns the default value if present, else returns null.
     */
    public T getDefaultValue() {
        return defaultValue != null ? defaultValue.get() : null;
    }

    /**
     * @return Returns a new build instance of Argument
     * @param <T> Accepts Generics to avoid unsafe type casting.
     */
    public static <T> Builder<T> build() {
        return new Builder();
    }

    /**
     * Constructor to build the Argument object
     * @param <T> Accepts Generics to avoid unsafe type casting.
     */
    public static class Builder<T> {
        private String name;
        private Parser<T> parser;
        private boolean optional = false;
        private List<Predicate<T>> restrictions = new ArrayList<>();
        private Supplier<T> defaultValue;

        /**
         * Sets the name of an Argument
         * <p>
         *     Sets the name of the Argument to "term".
         *     <br>Usage - .name("term")
         * </p>
         * @param name Accepts a String.
         * @return Returns the Builder object.
         */
        public Builder<T> name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the specified Parser of the Argument
         * <p>
         *     Sets IntegerParser as the specified Parser.
         *     <br>Usage - .parser(new IntegerParser)
         *     <br>
         *     <br>Sets LocalDateParser as the specified Parser.
         *     <br>Usage - .parse(new LocalDateParser)
         * </p>
         * @param parser Accepts a Parser, either from the library i.e. IntegerParser, or a custom one i.e. LocalDateParser.
         * @return Returns the Builder object.
         */
        public Builder<T> parser(Parser<T> parser) {
            this.parser = parser;
            return this;
        }

        /**
         * Sets the argument to optional. False by default (required).
         * <p>
         *     Sets the Argument to optional.
         *     <br>Usage - .optional()
         * </p>
         * @return Returns the Builder object.
         */
        public Builder<T> optional() {
            this.optional = true;
            return this;
        }

        /**
         * Adds a restriction to the Arguments list of restrictions.
         * <p>
         *     Value in the argument must be greater than 10.
         *     <br>usage - .addRestriction(value -> value > 10)
         * </p>
         * @param restriction Accepts a Predicate that represents a restriction on the value of the argument
         * @return Returns the Builder object.
         */
        public Builder<T> addRestriction(Predicate<T> restriction) {
            if (this.restrictions == null) {
                this.restrictions = new ArrayList<>();
            }
            this.restrictions.add(restriction);
            return this;
        }

        /**
         * Sets the default value of the Argument if provided. Otherwise, it stays null.
         * <p>
         *     Sets the default value of some command that accepts a String.
         *     <br>Usage - .defaultValue(() -> "Echo, echo, echo!")
         *     <br>
         *     <br>Sets the default value of some command to true that accepts a Boolean.
         *     <br>Usage - .defaultValue(() -> true)
         *     <br>
         * </p>
         * Sets the default value of the Argument.
         * @param defaultValue Accepts a generic Supplier that represents the default value of the Argument.
         * @return Returns the Builder object.
         */
        public Builder<T> defaultValue(Supplier<T> defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        /**
         * Creates a new Argument object with the provided parameters.
         * <p>
         *     Must call this method last to create the Argument object.
         *     <br>Usage - .build()
         * </p>
         * @return Returns a new Argument object by using the Builder object.
         */
        public Argument<T> build() {
            if (name == null || parser == null) {
                throw new IllegalStateException("Name and parser must not be null.");
            }
            return new Argument<>(name, parser, optional, restrictions, defaultValue);
        }
    }
}