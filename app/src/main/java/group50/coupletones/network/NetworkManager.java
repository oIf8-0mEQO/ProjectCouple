package group50.coupletones.network;

import android.os.AsyncTask;

import group50.coupletones.util.function.Function;

/**
 * Created by sharmaine on 5/2/16.
 */
public interface NetworkManager {
  AsyncTask<Void, Void, Boolean> send(Message message);
  void onReceive(Function callBack);
}