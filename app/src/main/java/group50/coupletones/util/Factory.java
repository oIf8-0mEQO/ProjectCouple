package group50.coupletones.util;

/**
 * A generic factory is responsible for building type T.
 * A factory defines how to build an object of type T.
 * @author Henry Mao
 */
public interface Factory<T> {

  /**
   * Builds a new instance of this type from this factory.
   * @return A new instance of the object.
   */
  T build();
}
