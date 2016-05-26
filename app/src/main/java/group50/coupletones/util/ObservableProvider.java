package group50.coupletones.util;

import rx.Observable;

/**
 * A object can be subscribed with to watch for certain named observables.
 * @author Henry Mao
 */
public interface ObservableProvider {

  /**
   * @param name The name of the observable to look for.
   * @return An observable
   */
  <T> Observable<T> getObservable(String name);

  /**
   * Typed version of getObservable
   * @param name The name of the observable to look for.
   * @param type The class of the observable
   * @param <T> The type of the observable
   * @return An observable
   */
  default <T> Observable<T> getObservable(String name, Class<T> type) {
    return (Observable<T>) getObservable(name);
  }
}
