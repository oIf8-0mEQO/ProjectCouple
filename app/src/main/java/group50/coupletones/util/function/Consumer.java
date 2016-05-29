package group50.coupletones.util.function;

/**
 * Java 8 Functional Interface copied from Java 8 library
 *
 * Represents a function that has one input and no output
 *
 * @param <T> the type of the input to the operation
 * @author Henry Mao
 */
public interface Consumer<T> {

  /**
   * Performs this operation on the given argument.
   * @param t the input argument
   */
  void accept(T t);
}
