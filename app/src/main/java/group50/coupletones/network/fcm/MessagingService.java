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
 *
 * @author Henry Mao
 */
public class MessagingService extends FirebaseMessagingService implements Taggable {

  @Inject
  public NetworkManager network;
  @Inject
  public CoupleTones app;

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
  }
}
