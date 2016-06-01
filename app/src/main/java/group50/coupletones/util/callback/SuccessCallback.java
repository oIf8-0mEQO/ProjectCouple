package group50.coupletones.util.callback;

import group50.coupletones.util.function.Function;

/**
 * An object that can handle a success callback
 * @author Henry Mao
 */
//TODO: Change to Observable
public interface SuccessCallback<T, S> {
  /**
   * @param callback The callback function upon success
   * @return This instance
   */
  T onSuccess(Function<S, S> callback);
}
