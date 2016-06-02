package group50.coupletones.network.fcm;

/**
 * @author Sharmaine Manalo
 * @since 5/4/16
 */

import android.util.Log;
import group50.coupletones.network.fcm.message.Message;
import group50.coupletones.util.Taggable;
import org.json.JSONObject;
import rx.subjects.PublishSubject;

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
  final PublishSubject<Message> incomingStream;

  /**
   * Subject that is observed by objects that want to listen to outgoing messages
   */
  final PublishSubject<Message> outgoingStream;

  @Inject
  public FcmManager() {
    incomingStream = PublishSubject.create();
    outgoingStream = PublishSubject.create();

    // Register the default sending behavior
    outgoingStream.subscribe(this::onSendMessage);
  }

  private void onSendMessage(Message message) {
    try {
      URL url = new URL(ENDPOINT);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setInstanceFollowRedirects(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Authorization", AUTH_HEADER);
      conn.setUseCaches(false);

      JSONObject jsonObject = new JSONObject(message.getData());

      try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
        wr.write(jsonObject.toString().getBytes());
      }

    } catch (Exception e) {
      Log.e(getTag(), "Unable to send FCM message.");
      e.printStackTrace();
    }
  }

  @Override
  public PublishSubject<Message> getIncomingStream() {
    return incomingStream;
  }

  @Override
  public PublishSubject<Message> getOutgoingStream() {
    return outgoingStream;
  }
}