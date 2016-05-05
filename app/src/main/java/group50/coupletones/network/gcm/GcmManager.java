package group50.coupletones.network.gcm;

/**
 * @author Sharmaine Manalo
 * @since 5/4/16.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import group50.coupletones.CoupleTones;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.message.IncomingMessage;
import group50.coupletones.network.message.MessageReceiver;
import group50.coupletones.network.message.OutgoingMessage;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;

public class GcmManager implements NetworkManager, Taggable {
  /**
   * A map of all message receivers
   */
  private final HashMap<String, MessageReceiver> receivers = new HashMap<>();
  /**
   * The app instance.
   */
  private final CoupleTones app;
  /**
   * Project number registered with Google API
   */
  private String PROJECT_NUMBER = "794558589013";
  /**
   * GCM instance
   */
  private GoogleCloudMessaging gcm;
  /**
   * The device registraton ID
   */
  private String regid;

  @Inject
  public GcmManager(CoupleTones app) {
    this.app = app;
  }

  @Override
  public AsyncTask<Void, Void, Boolean> send(OutgoingMessage message) {
    return new AsyncTask<Void, Void, Boolean>() {

      @Override
      protected Boolean doInBackground(Void... params) {
        try {
          gcm.send(PROJECT_NUMBER + "@gcm.googleapis.com", message.getId(), message.getData());
        } catch (IOException ex) {
          ex.printStackTrace();
          return false;
        }
        return true;
      }
    }.execute(null, null, null);

  }

  @Override
  public AsyncTask<Void, Void, Boolean> register(Context context) {
    return new AsyncTask<Void, Void, Boolean>() {

      @Override
      protected Boolean doInBackground(Void... params) {
        try {
          if (gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(context.getApplicationContext());
          }

          regid = gcm.register(PROJECT_NUMBER);
          Log.i("GCM", "Registered: " + regid);

          // Notify server of registration
          send(
            (OutgoingMessage) new OutgoingMessage("registration")
              .setString("email", app.getLocalUser().getEmail())
          );

        } catch (IOException ex) {
          ex.printStackTrace();
          return false;
        }
        return true;
      }

    }.execute(null, null, null);
  }

  @Override
  public void register(String type, MessageReceiver receiver) {
    if (receivers.containsKey(type)) {
      throw new RuntimeException("Attempt to register a GCM with duplicate type: " + type);
    } else {
      receivers.put(type, receiver);
    }
  }

  @Override
  public void unregister(String type) {
    if (receivers.containsKey(type)) {
      receivers.remove(type);
    } else {
      throw new RuntimeException("Attempt to unregister a GCM with invalid type: " + type);
    }
  }

  void handleReceive(Bundle extras) {
    IncomingMessage msg = new IncomingMessage(extras.getString("type"), extras);
    MessageReceiver messageReceiver = receivers.get(msg.getType());

    if (messageReceiver != null)
      messageReceiver.onReceive(msg);
    else
      throw new RuntimeException("Attempt to handle GCM of invalid type: " + extras.getString("type"));
  }
}
