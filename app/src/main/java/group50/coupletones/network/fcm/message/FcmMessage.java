package group50.coupletones.network.fcm.message;

import java.util.HashMap;
import java.util.Map;

/**
 * A concrete message object
 * @author Sharmaine Manalo
 * @since 5/4/16
 */
public class FcmMessage implements Message {

  /**
   * The data to send to the server.
   */
  private final Map<String, Object> payload;

  public FcmMessage(String type) {
    this(new HashMap<>());
    payload.put("type", type);
  }

  public FcmMessage(Map<String, Object> payload) {
    this.payload = payload;
  }

  /**
   * Gets type
   * @return - incoming message
   */
  @Override
  public String getType() {
    return (String) payload.get("type");
  }

  /**
   * Gets data
   * @return - data of message
   */
  @Override
  public Map<String, Object> getPayload() {
    return payload;
  }

  @Override
  public Map<String, Object> getData() {
    if (!payload.containsKey("data")) {
      payload.put("data", new HashMap<>());
    }
    return (Map) payload.get("data");
  }

  @Override
  public Map<String, Object> getNotification() {
    if (!payload.containsKey("notification")) {
      payload.put("notification", new HashMap<>());
    }
    return (Map) payload.get("notification");
  }

  public FcmMessage setTitle(String title) {
    getNotification().put("title", title);
    return this;
  }

  public FcmMessage setBody(String body) {
    getNotification().put("body", body);
    return this;
  }

  public FcmMessage setIcon(String icon) {
    getNotification().put("icon", icon);
    return this;
  }

  public FcmMessage setString(String key, Object value)
  {
    payload.put(key, value);
    return this;
  }

  @Override
  public String getTo() {
    return (String) payload.get("to");
  }

  @Override
  public Message setTo(String to) {
    payload.put("to", to);
    return this;
  }
}

