package group50.coupletones.network.fcm.message;

import java.util.HashMap;
import java.util.Map;

/**
 * A concrete message object
 * @author Sharmaine Manalo
 * @since 5/4/16
 */
public class NotificationMessage implements Message {

  /**
   * The data to send to the server.
   */
  private final Map<String, Object> data;

  public NotificationMessage(String type) {
    this(new HashMap<>());
    setString("type", type);
  }

  public NotificationMessage(Map<String, Object> data) {
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
    return data;
  }

  private void initNotification() {
    if (!data.containsKey("notification")) {
      data.put("notification", new HashMap<>());
    }
  }

  public NotificationMessage setTitle(String title) {
    initNotification();
    ((Map) data.get("notification")).put("title", title);
    return this;
  }

  public NotificationMessage setBody(String body) {
    initNotification();
    ((Map) data.get("notification")).put("body", body);
    return this;
  }

  public NotificationMessage setIcon(String icon) {
    initNotification();
    ((Map) data.get("notification")).put("icon", icon);
    return this;
  }
}

