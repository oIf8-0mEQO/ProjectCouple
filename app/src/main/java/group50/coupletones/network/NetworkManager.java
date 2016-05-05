package group50.coupletones.network;

/**
 * @author Sharmaine Manalo
 * @since 02-05-2016.
 */

import android.content.Context;
import android.os.AsyncTask;

import group50.coupletones.network.message.MessageReceiver;
import group50.coupletones.network.message.OutgoingMessage;
import group50.coupletones.util.Identifiable;

public interface NetworkManager {
  /**
   * The send function sends an outgoing message to the server.
   *
   * @param message - the outgoing message
   * @return AsyncTask
   */
  AsyncTask<Void, Void, Boolean> send(OutgoingMessage message);

  /**
   * The register function registers a receiver for each message
   * sent to the server without having a type specified.
   *
   * @param context - the context to register the manager with
   * @return AsyncTask
   */
  AsyncTask<Void, Void, Boolean> register(Context context);

  default void register(MessageReceiver receiver) {
    if (receiver instanceof Identifiable) {
      register(((Identifiable) receiver).getId(), receiver);
    } else {
      throw new RuntimeException("Receiver must implement Identifiable");
    }
  }

  /**
   * The register function registers a receiver for each message
   * sent to the server.
   *
   * @param type     - the type of receiver to register
   * @param receiver - the receiver to register
   */
  void register(String type, MessageReceiver receiver);

  /**
   * The unregister function unregisters a receiver for the type
   * of receiver passed in.
   *
   * @param type - the type of receiver to unregister
   */
  void unregister(String type);
}