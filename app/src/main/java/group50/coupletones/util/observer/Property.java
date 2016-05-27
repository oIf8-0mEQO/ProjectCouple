package group50.coupletones.util.observer;

import group50.coupletones.util.function.Consumer;
import group50.coupletones.util.function.Supplier;
import rx.Observable;

/**
 * @author Henry Mao
 * @since 5/27/16
 */
public interface Property<T> {

  String name();

  Property<T> setter(Consumer<T> setter);

  Property<T> getter(Supplier<T> getter);

  Properties bind();

  /**
   * @return An observable for this property
   */
  Observable<T> observable();
}
