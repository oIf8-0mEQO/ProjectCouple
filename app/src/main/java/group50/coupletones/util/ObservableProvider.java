package group50.coupletones.util;

import rx.Observable;

/**
 * A object can be subscribed with to watch for certain named observables.
 * @author Henry Mao
 */
public interface ObservableProvider {

  /**
   * @param event The name of the observable to look for.
   * @return The observable object.
   */
  <T> Observable<T> getObservable(String event);
}
