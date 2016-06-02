package group50.coupletones.controller.tab.favoritelocations.map;

import android.util.Log;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.network.fcm.NetworkManager;
import group50.coupletones.network.fcm.message.MessageType;
import group50.coupletones.network.fcm.message.FcmMessage;
import group50.coupletones.util.FormatUtility;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;

/**
 * @author Sharmaine Manalo
 * @since 5/5/16
 */
public class LocationNotificationMediator implements Taggable {
  private CoupleTones app;
  private NetworkManager network;
  private FormatUtility formatUtility;

  /**
   * Proximity Network Handler
   * @param app - CoupleTones app
   * @param network - Network Manager
   */
  @Inject
  public LocationNotificationMediator(CoupleTones app, NetworkManager network, FormatUtility formatUtility) {
    this.app = app;
    this.network = network;
    this.formatUtility = formatUtility;
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
                .setTo(partner.getFcmToken())
                .setTitle(
                  partner.getName() + " " +
                    app.getApplicationContext().getString(R.string.partner_visited_text) + " " +
                    location.getName()
                )
                .setBody(formatUtility.formatDate(location.getTimeVisited()))
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
