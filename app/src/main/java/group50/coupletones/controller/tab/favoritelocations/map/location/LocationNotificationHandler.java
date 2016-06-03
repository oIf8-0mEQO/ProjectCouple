package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import group50.coupletones.CoupleTones;
import group50.coupletones.network.fcm.NotificationBuilder;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;

/**
 * Handles receiving location notification and plays VibeTones.
 * @author Henry Mao
 * @since 6/2/16
 */
public class LocationNotificationHandler implements Taggable {
  @Inject
  public CoupleTones app;

  public LocationNotificationHandler() {
    CoupleTones.global().inject(this);
  }

  public void onReceive(RemoteMessage msg) {
    // Show the notification anyway (in foreground)
    if (app.getLocalUser().getGlobalNotificationsSetting()) {
      RemoteMessage.Notification notification = msg.getNotification();
      if (notification != null) {
        Log.v(getTag(), "Generating notification.");
        new NotificationBuilder(app.getApplicationContext())
          .setTitle(notification.getTitle())
          .setMsg(notification.getBody())
          .show();
      }
    }
  }
}
