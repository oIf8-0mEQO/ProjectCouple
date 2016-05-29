package group50.coupletones.controller.tab.favoritelocations.map;

import android.util.Log;
import group50.coupletones.CoupleTones;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.network.message.OutgoingMessage;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Sharmaine Manalo
 * @since 5/5/16
 */
public class ProximityNetworkHandler implements Taggable {
  private CoupleTones app;
  private NetworkManager network;

  /**
   * Proximity Network Handler
   *
   * @param app     - CoupleTones app
   * @param network - Network Manager
   */
  @Inject
  public ProximityNetworkHandler(CoupleTones app, NetworkManager network) {
    this.app = app;
    this.network = network;
  }

  /**
   * onEnterLocation
   *
   * @param location - Visited Location
   */
  public void onEnterLocation(VisitedLocationEvent location) {
    if (app.getLocalUser().getPartner() != null) {
      app.getLocalUser().addVisitedLocation(location);
      Format formatter = new SimpleDateFormat("HH:mm", Locale.US);
      Date date = location.getTimeVisited();
      String time = formatter.format(date);

      app.getLocalUser().getPartner()
        .subscribe(partner -> {
          network.send((OutgoingMessage) new OutgoingMessage(MessageType.SEND_LOCATION_NOTIFICATION.value)
            .setString("name", location.getName())
            .setDouble("lat", location.getPosition().latitude)
            .setDouble("long", location.getPosition().longitude)
            .setString("time", time)
            .setString("partner", partner.getEmail())
          );
        });
    } else {
      Log.e(getTag(), "Attempt to send location notification to null partner.");
    }
  }

  public void onLeaveLocation(VisitedLocationEvent location)
  {
    if (app.getLocalUser().getPartner() != null)
    {
      app.getLocalUser().addVisitedLocation(location);
      //TODO: implement
    }
  }
}
