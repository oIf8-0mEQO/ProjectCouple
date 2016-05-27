package group50.coupletones.util.observer;

import java.util.Collection;

/**
 * An object managers observers an object's fields
 *
 * @author Henry Mao
 * @since 5/24/16
 */
public interface Properties {
  /**
   * Sets the Properties object to set a particular object
   *
   * @param obj The object to set
   * @return A new instance that watches the given object
   */
  Properties set(Object obj);

  default Property<Object> property(String name) {
    return property(name, Object.class);
  }

  <T> Property<T> property(String name, Class<T> type);

  Collection<Property<?>> all();
}
