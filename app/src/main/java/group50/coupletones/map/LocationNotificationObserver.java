package group50.coupletones.map;

import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.network.message.OutgoingMessage;

import javax.inject.Inject;

/**
 * @author Sharmaine Manalo
 * @since 5/5/16
 */
public class LocationNotificationObserver implements ProximityObserver {
  private NetworkManager network;

  @Inject
  public LocationNotificationObserver(NetworkManager network) {
    this.network = network;
  }

  @Override
  public void onEnterLocation(VisitedLocation location) {
    network.send((OutgoingMessage) new OutgoingMessage(MessageType.SEND_LOCATION_NOTIFICATION.value)
        .setString("name",location.getName())
        .setDouble("lat", location.getPosition().latitude)
        .setDouble("long", location.getPosition().longitude)
    );
  }

}
