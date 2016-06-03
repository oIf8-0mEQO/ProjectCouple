package group50.coupletones.network.fcm.message;

import java.util.Map;

/**
 * Message interface represents the
 * message sent to partner on the app.
 *
 * Notifications must have:
 * title
 * body
 * icon
 * in order to notify the partner.
 * @author Sharmaine Manalo
 * @since 5/4/16
 */
public interface Message {
  /**
   * @return The type of the message
   */
  String getType();

  /**
   * @return The FCM Token of the device to send the message to
   */
  String getTo();

  /**
   * Set the recipient of the message
   * @param to The FCM Token of the device to send the message to
   * @return Self instance
   */
  Message setTo(String to);

  /**
   * The payload of the message
   * @return The payload object map
   */
  Map<String, Object> getPayload();

  /**
   * The data to send in the message
   * @return The data object map
   */
  Map<String, Object> getData();

  /**
   * The notification data to send in the message
   * @return The notification object map
   */
  Map<String, Object> getNotification();


}
