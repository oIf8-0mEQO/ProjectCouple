package group50.coupletones.util.function;

/**
 * Java 8 Functional Interface copied from Java 8 library
 *
 * Represents a function that has one input and one output
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @author Henry Mao
 */
public interface Function<T, R> {
  R apply(T input);
}
