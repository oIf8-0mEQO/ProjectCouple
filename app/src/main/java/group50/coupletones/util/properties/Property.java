package group50.coupletones.util.properties;

import com.google.firebase.database.GenericTypeIndicator;
import group50.coupletones.util.function.Consumer;
import group50.coupletones.util.function.Supplier;
import rx.subjects.Subject;

/**
 * @author Henry Mao
 * @since 5/27/16
 */
public interface Property<T> {

  String name();

  Class<T> type();

  T get();

  void set(T value);

  Property<T> setter(Consumer<T> setter);

  Property<T> getter(Supplier<T> getter);

  Property<T> update();

  Property<T> mark(GenericTypeIndicator<?> indicator);

  GenericTypeIndicator<?> getIndicator();

  Properties bind();

  /**
   * Automatically binds an object to this property being set.
   * Internally reflection is used to perform get/set operation on a field
   * within the bind's class for the field with the property's name.
   * @param bind The object to bind
   * @return Self instance
   */
  Properties bind(Object bind);

  /**
   * An observable object for this property.
   * The observable object is responsible for notifying observers when this property changes.
   * When update() is called, subscribers to this property are notified.
   * @return The observable for this property
   */
  Subject<T, T> observable();
}
