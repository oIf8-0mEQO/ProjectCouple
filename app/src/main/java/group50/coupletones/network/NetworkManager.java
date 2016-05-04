package group50.coupletones.network;

import android.content.Context;
import android.os.AsyncTask;

import group50.coupletones.network.receiver.MessageReceiver;
import group50.coupletones.util.function.Function;

/**
 * @author sharmaine
 * @since 5/2/16
 */
public interface NetworkManager {
  AsyncTask<Void, Void, Boolean> send(Message message);

  void onReceive(Function callBack);

  AsyncTask<Void, Void, Boolean> register(Context context);

  void register(String type, MessageReceiver receiver);

  void unregister(String type);
}