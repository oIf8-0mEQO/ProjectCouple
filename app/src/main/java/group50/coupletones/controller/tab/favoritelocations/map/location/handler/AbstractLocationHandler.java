package group50.coupletones.controller.tab.favoritelocations.map.location.handler;

import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import group50.coupletones.CoupleTones;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * A class responsible for handling location notification and VibeTones
 *
 * @author Henry Mao
 * @since 6/2/16
 */
public abstract class AbstractLocationHandler implements Taggable {
  @Inject
  public CoupleTones app;

  public AbstractLocationHandler() {
    CoupleTones.global().inject(this);
  }

  public void onReceive(RemoteMessage msg) {
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
              handleNotifyFor(favoriteLocation);
            } else {
              Log.e(getTag(), "Invalid location size");
            }
          });
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

  /**
   * Handles the sound and vibration notification for a favorite location
   *
   * @param favoriteLocation A partner's favorite location
   */
  protected abstract void handleNotifyFor(FavoriteLocation favoriteLocation);
}
