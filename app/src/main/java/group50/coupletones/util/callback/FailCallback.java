package group50.coupletones.util.callback;

import group50.coupletones.util.function.Function;

/**
 * An object that can handle a fail callback
 * @author Henry Mao
 */
public interface FailCallback<T, S> {
  /**
   * @param callback The callback function upon failure
   * @return This instance
   */
  T onFail(Function<S, S> callback);
}
