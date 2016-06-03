package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import group50.coupletones.CoupleTones;
import group50.coupletones.network.fcm.NotificationBuilder;
import group50.coupletones.util.Taggable;
import group50.coupletones.util.sound.VibeTone;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Handles receiving location notification and plays VibeTones.
 *
 * @author Henry Mao
 * @since 6/2/16
 */
public class LocationArrivalHandler implements Taggable {
  @Inject
  public CoupleTones app;

  public LocationArrivalHandler() {
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

    // Try to retrieve the favorite location, and play a VibeTone
    Map<String, String> data = msg.getData();

    if (data.containsKey("locationIndex")) {
      try {
        int favoriteLocationIndex = Integer.parseInt(data.get("locationIndex"));

        app.getLocalUser()
          .getPartner()
          .filter(partner -> partner != null)
          .subscribe(partner -> {
            List<FavoriteLocation> favoriteLocations = partner.getFavoriteLocations();
            if (favoriteLocationIndex < favoriteLocations.size()) {
              FavoriteLocation favoriteLocation = favoriteLocations.get(favoriteLocationIndex);
              VibeTone vibetone = favoriteLocation.getVibetone();
              vibetone.playArrival();
            } else {
              Log.e(getTag(), "Invalid location size");
            }
          });
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }
}
