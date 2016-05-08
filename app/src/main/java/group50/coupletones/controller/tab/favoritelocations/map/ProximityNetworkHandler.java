package group50.coupletones.controller.tab.favoritelocations.map;

import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocation;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.network.message.OutgoingMessage;

import javax.inject.Inject;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sharmaine Manalo
 * @since 5/5/16
 */
public class ProximityNetworkHandler implements ProximityObserver {
  private NetworkManager network;

  @Inject
  public ProximityNetworkHandler(NetworkManager network) {
    this.network = network;
  }

  /**
   *
   * @param location Visited Location
   */
  @Override
  public void onEnterLocation(VisitedLocation location) {
    Format formatter = new SimpleDateFormat("MM-dd-yyyy");
    Date date = location.getTime();
    String time = formatter.format(date);

    network.send((OutgoingMessage) new OutgoingMessage(MessageType.SEND_LOCATION_NOTIFICATION.value)
            .setString("name", location.getName())
            .setDouble("lat", location.getPosition().latitude)
            .setDouble("long", location.getPosition().longitude)
            .setString("time", time)
            .setString("partner", "hello@sharmaine.me")
    );
  }
}
