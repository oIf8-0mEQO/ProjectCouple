package group50.coupletones.util.observer;

import java.util.Collection;

/**
 * An object managers observers an object's fields
 *
 * @author Henry Mao
 * @since 5/24/16
 */
public interface Properties {

  default Property<Object> property(String name) {
    return property(name, Object.class);
  }

  <T> Property<T> property(String name, Class<T> type);

  Collection<Property<?>> all();
}
