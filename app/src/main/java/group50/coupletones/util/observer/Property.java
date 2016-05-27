package group50.coupletones.util.observer;

import group50.coupletones.util.function.Consumer;
import group50.coupletones.util.function.Supplier;
import rx.subjects.Subject;

/**
 * @author Henry Mao
 * @since 5/27/16
 */
public interface Property<T> {

  String name();

  T get();

  void set(T value);

  Property<T> setter(Consumer<T> setter);

  Property<T> getter(Supplier<T> getter);

  default Property<T> update() {
    set(get());
    return this;
  }

  Properties bind();

  Properties bind(Object bind);

  /**
   * @return An observable for this property
   */
  Subject<T, T> observable();
}
