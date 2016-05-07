package group50.coupletones.network.message;

/**
 * Contains all types of messages used in the app
 *
 * @author Henry Mao
 * @since 5/5/16
 */
public enum MessageType {

  SEND_PARTNER_REQUEST("partner"),
  SEND_LOCATION_NOTIFICATION("map"),
  RECEIVE_PARTNER_REQUEST("partner-request"),
  RECEIVE_PARTNER_ACCEPT("partner-accept"),
  RECEIVE_PARTNER_REJECT("partner-reject"),
  RECEIVE_MAP_NOTIFY("map-notify"),
  RECEIVE_MAP_REJECT("map-reject");

  public final String value;

  MessageType(String value) {
    this.value = value;
  }
}
