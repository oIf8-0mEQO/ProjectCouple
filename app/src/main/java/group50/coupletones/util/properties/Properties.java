package group50.coupletones.util.properties;

import rx.Observable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A properties is a collection of setters and getters
 * that hold an observable. One can watch an observable
 * for change.
 *
 * @author Henry Mao
 * @since 5/24/16
 */
public interface Properties {

  /**
   * Shorthand for property
   */
  default Property<Object> property(String name) {
    return property(name, Object.class);
  }

  /**
   * Returns the property associated with a given name
   *
   * @param name Name of the property
   * @param type Type of the property (optional)
   * @return The property
   */
  <T> Property<T> property(String name, Class<T> type);

  Collection<Property<?>> all();

  /**
   * @return All the observables in all properties.
   */
  default Collection<Observable<?>> allObservables() {
    List<Observable<?>> observables = new LinkedList<>();
    Collection<Property<?>> all = all();
    for (Property property : all) {
      observables.add(property.observable());
    }

    return observables;
  }
}
