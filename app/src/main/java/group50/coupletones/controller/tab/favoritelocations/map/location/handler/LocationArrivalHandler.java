package group50.coupletones.controller.tab.favoritelocations.map.location.handler;

import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.network.fcm.NotificationBuilder;
import group50.coupletones.util.sound.VibeTone;

/**
 * Handles receiving location notification and plays VibeTones.
 *
 * @author Henry Mao
 * @since 6/2/16
 */
public class LocationArrivalHandler extends AbstractLocationHandler {
  public void onReceive(RemoteMessage msg) {
    super.onReceive(msg);
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

  @Override
  protected void handleNotifyFor(FavoriteLocation favoriteLocation) {
    VibeTone vibetone = favoriteLocation.getVibetone();
    vibetone.playArrival();
  }
}
