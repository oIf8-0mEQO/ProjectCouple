package group50.coupletones.controller.tab.favoritelocations.map;

import android.util.Log;
import group50.coupletones.CoupleTones;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.network.fcm.NetworkManager;
import group50.coupletones.network.fcm.message.FcmMessage;
import group50.coupletones.network.fcm.message.MessageType;
import group50.coupletones.util.FormatUtility;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;

/**
 * @author Sharmaine Manalo
 * @since 5/5/16
 */
public class LocationNotificationMediator implements Taggable {

  private static final String NOTIFY_TITLE = "%1$s visited %2$s";
  private static final String NOTIFY_ICON = "icon";

  @Inject
  public CoupleTones app;
  @Inject
  public NetworkManager network;
  @Inject
  public FormatUtility formatUtility;

  /**
   * Proximity Network Handler
   */
  public LocationNotificationMediator() {
    CoupleTones.global().inject(this);
  }

  /**
   * onEnterLocation
   * @param location - Visited Location
   */
  public void onEnterLocation(VisitedLocationEvent location) {
    if (app.getLocalUser().getPartner() != null) {
      // Adds the location as a visited location
      app.getLocalUser().addVisitedLocation(location);

      // Send notification to partner about location visit
      app.getLocalUser().getPartner()
        .filter(partner -> partner != null)
        .subscribe(partner -> {
          network
            .getOutgoingStream()
            .onNext(
              new FcmMessage(MessageType.LOCATION_NOTIFICATION.value)
                .setTitle(String.format(NOTIFY_TITLE, app.getLocalUser().getName(), location.getName()))
                .setBody(formatUtility.formatDate(location.getTimeVisited()))
                .setIcon(NOTIFY_ICON)
                .setTo(partner.getFcmToken())
            );
        });
    } else {
      Log.e(getTag(), "Attempt to send location notification to null partner.");
    }
  }

  public void onLeaveLocation(VisitedLocationEvent location) {
    //TODO: Remove?
  }
}
