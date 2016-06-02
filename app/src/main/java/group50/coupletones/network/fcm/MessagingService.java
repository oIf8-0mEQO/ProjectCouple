package group50.coupletones.network.fcm;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import group50.coupletones.CoupleTones;
import group50.coupletones.util.Taggable;
import rx.subjects.Subject;

import javax.inject.Inject;

/**
 * FCM service
 * @author Henry Mao
 */
public class MessagingService extends FirebaseMessagingService implements Taggable {

  @Inject
  public NetworkManager network;

  public MessagingService() {
    CoupleTones.global().inject(this);
  }

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);

    Log.v(getTag(), "Received FCM message: " + remoteMessage.getMessageId());
    // Publish the received message
    ((Subject<RemoteMessage, RemoteMessage>) network.getIncomingStream())
      .onNext(remoteMessage);

    // Show the notification anyway (in foreground)
    RemoteMessage.Notification notification = remoteMessage.getNotification();
    if (notification != null) {
      Log.v(getTag(), "Generating notification.");
      new NotificationBuilder(this)
        .setTitle(notification.getTitle())
        .setMsg(notification.getBody())
        .show();
    }
  }
}
