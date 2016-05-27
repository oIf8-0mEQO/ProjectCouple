package group50.coupletones.util.properties;

import group50.coupletones.util.function.Consumer;
import group50.coupletones.util.function.Supplier;
import rx.Observable;
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
   * PreObservables will call the setter.
   *
   * @return An observable for this property that always notifies subscribers BEFORE obsevable().
   */
  Subject<T, T> preObservable();

  /**
   * Observable that will not call the setter
   *
   * @return An observable for this property that always notifies subscribers AFTER beforeObservable().
   */
  Observable<T> observable();
}
