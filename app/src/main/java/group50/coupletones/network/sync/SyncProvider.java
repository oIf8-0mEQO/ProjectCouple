package group50.coupletones.network.sync;

import group50.coupletones.util.observer.ObservableProvider;
import group50.coupletones.util.observer.Properties;
import rx.Observable;

/**
 * An object that composes a Properties object.
 * Properties uses this to recursively serializes data to child data. (Like a Tree)
 *
 * @author Henry Mao
 * @since 5/27/16
 */
public interface SyncProvider extends ObservableProvider {

  /**
   * Gets the sync object responsible for syncing this object
   *
   * @return The sync object.
   */
  Properties getSync();

  @Override
  default <T> Observable<T> getObservable(String name) {
    return getSync().getObservable(name);
  }
}
