package group50.coupletones.network.fcm.message;

/**
 * Contains all types of messages used in the app
 *
 * @author Henry Mao
 * @since 5/5/16
 */
public enum MessageType {

  LOCATION_ARRIVAL("locationArrival"),
  LOCATION_DEPARTURE("locationDeparture");

  public final String value;

  MessageType(String value) {
    this.value = value;
  }
}
