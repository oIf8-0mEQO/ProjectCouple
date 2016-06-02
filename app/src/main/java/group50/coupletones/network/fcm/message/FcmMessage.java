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
  private final Map<String, Object> data;

  public FcmMessage(String type) {
    this(new HashMap<>());
    setString("type", type);
  }

  public FcmMessage(Map<String, Object> data) {
    this.data = data;
  }

  /**
   * Gets type
   * @return - incoming message
   */
  @Override
  public String getType() {
    return (String) data.get("type");
  }

  /**
   * Gets data
   * @return - data of message
   */
  @Override
  public Map<String, Object> getData() {
    if (!data.containsKey("data")) {
      data.put("data", new HashMap<>());
    }
    return (Map) data.get("data");
  }

  private void initNotification() {
    if (!data.containsKey("notification")) {
      data.put("notification", new HashMap<>());
    }
  }

  public FcmMessage setTitle(String title) {
    initNotification();
    ((Map) data.get("notification")).put("title", title);
    return this;
  }

  public FcmMessage setBody(String body) {
    initNotification();
    ((Map) data.get("notification")).put("body", body);
    return this;
  }

  public FcmMessage setIcon(String icon) {
    initNotification();
    ((Map) data.get("notification")).put("icon", icon);
    return this;
  }

  @Override
  public String getTo() {
    return (String) data.get("to");
  }

  public FcmMessage setTo(String to) {
    initNotification();
    ((Map) data.get("notification")).put("to", to);
    return this;
  }
}

