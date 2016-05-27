package group50.coupletones.util.properties;

import rx.Observable;

/**
 * @author Henry Mao
 * @since 5/27/16
 */
public interface PropertiesProvider {

  /**
   * Shorthand methods
   *
   * @param name Name of the property
   * @param type Type of the property
   * @return An observable for the given property
   */
  default <T> Observable<T> observable(String name, Class<T> type) {
    return getProperties().property(name, type).observable();
  }

  /**
   * Shorthand methods
   *
   * @param name Name of the property
   * @return An observable for the given property
   */
  default Observable<Object> observable(String name) {
    return getProperties().property(name).observable();
  }

  Properties getProperties();
}
