package group50.coupletones.network.fcm.message;

import java.util.Map;

/**
 * @author Sharmaine Manalo
 * @since 5/4/16
 */

/**
 * Message interface represents the
 * message sent to partner on the app.
 */
public interface Message {
  /**
   * @return The type of the message
   */
  String getType();

  /**
   * @return The Firebase ID of the device to send the message to
   */
  String getTo();

  Map<String, Object> getData();

  /**
   * Short-hand functions to getCollection various data from the message.
   */
  default String getString(String key) {
    return (String) getData().get(key);
  }

  default float getFloat(String key) {
    return (float) getData().get(key);
  }

  default double getDouble(String key) {
    return (double) getData().get(key);
  }

  default boolean getBoolean(String key) {
    return (boolean) getData().get(key);
  }

  default int getInt(String key) {
    return (int) getData().get(key);
  }

  default Message setString(String key, String value) {
    getData().put(key, value);
    return this;
  }

  default Message setFloat(String key, float value) {
    getData().put(key, value);
    return this;
  }

  default Message setDouble(String key, double value) {
    getData().put(key, value);
    return this;
  }

  default Message setInt(String key, int value) {
    getData().put(key, value);
    return this;
  }

  default Message setBoolean(String key, boolean value) {
    getData().put(key, value);
    return this;
  }
}
