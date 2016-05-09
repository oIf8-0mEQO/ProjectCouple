package group50.coupletones.util.function;

/**
 * Java 8 Functional Interface copied from Java 8 library
 * Represents a supplier of results.
 * <p>
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #get()}.
 *
 * @param <T> the type of results supplied by this supplier
 * @since 1.8
 */
public interface Supplier<T> {

  /**
   * Gets a result.
   *
   * @return a result
   */
  T get();
}
