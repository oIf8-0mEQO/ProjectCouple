package group50.coupletones.network.fcm;

/**
 * @author Sharmaine Manalo
 * @since 5/4/16
 */

import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import group50.coupletones.network.fcm.message.Message;
import group50.coupletones.util.Taggable;
import org.json.JSONObject;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import javax.inject.Inject;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * GCM Manager for the app
 */
public class FcmManager implements NetworkManager, Taggable {

  /**
   * FCM key
   */
  private static final String ENDPOINT = "https://fcm.googleapis.com/fcm/send";
  private static final String KEY = "AIzaSyCyMy2k4cpGNsqqvcilg2GyFjXQSAZ_qbE";
  private static final String AUTH_HEADER = "key=" + KEY;

  /**
   * Subject that is observed by objects that want to listen to incoming messages
   */
  final PublishSubject<RemoteMessage> incomingStream;

  /**
   * Subject that is observed by objects that want to listen to outgoing messages
   */
  final PublishSubject<Message> outgoingStream;

  @Inject
  public FcmManager() {
    this(Schedulers.newThread());
  }

  public FcmManager(Scheduler scheduler) {
    incomingStream = PublishSubject.create();
    outgoingStream = PublishSubject.create();

    // Register the default sending behavior
    outgoingStream
      .observeOn(scheduler)
      .subscribe(this::sendMessage);
  }

  /**
   * Handles sending outgoing messages
   * @param message The outgoing message. Must have a destination fcm token to send to.
   */
  public void sendMessage(Message message) {
    if (message.getTo() == null) {
      Log.e(getTag(), "Message must have a destination.");
      return;
    }

    try {
      URL url = new URL(ENDPOINT);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setInstanceFollowRedirects(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Authorization", AUTH_HEADER);
      conn.setUseCaches(false);

      DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
      String payload = new JSONObject(message.getPayload()).toString();
      wr.write(payload.getBytes());
      wr.close();
      int responseCode = conn.getResponseCode();
      Log.v(getTag(), "Sending FCM message to: " + message.getTo() + " with payload: " + payload);

      if (responseCode != 200) {
        Log.e(getTag(), "Error sending FCM POST Request (" + conn.getResponseCode() + "): " + conn.getResponseMessage());
      }
    } catch (Exception e) {
      Log.e(getTag(), "Unable to send FCM message.");
      e.printStackTrace();
    }
  }

  @Override
  public Observable<RemoteMessage> getIncomingStream() {
    return incomingStream;
  }

  @Override
  public Subject<Message, Message> getOutgoingStream() {
    return outgoingStream;
  }
}