package group50.coupletones.network.fcm.message;

/**
 * Contains all types of messages used in the app
 *
 * @author Henry Mao
 * @since 5/5/16
 */
public enum MessageType {

  SEND_PARTNER_REQUEST("partner"),
  SEND_PARTNER_RESPONSE("partner-response"),
  SEND_LOCATION_NOTIFICATION("map"),
  RECEIVE_PARTNER_REQUEST("partner-request"),
  RECEIVE_PARTNER_RESPONSE("partner-response"),
  RECEIVE_PARTNER_ERROR("partner-error"),
  RECEIVE_MAP_NOTIFY("map-notify"),
  RECEIVE_MAP_REJECT("map-reject");

  public final String value;

  MessageType(String value) {
    this.value = value;
  }
}
