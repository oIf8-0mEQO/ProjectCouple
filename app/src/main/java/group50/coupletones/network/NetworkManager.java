package group50.coupletones.network;

import group50.coupletones.util.function.Function;

/**
 * Created by sharmaine on 5/2/16.
 */
public interface NetworkManager {
  void send(String message);

  void onReceive(Function callBack);
}