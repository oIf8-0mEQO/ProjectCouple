package group50.coupletones.network.fcm.message;

import java.util.HashMap;
import java.util.Map;

/**
 * A concrete message object
 * @author Sharmaine Manalo
 * @since 5/4/16
 */
public class ConcreteMessage implements Message {

  /**
   * The data to send to the server.
   */
  private final Map<String, Object> data;

  public ConcreteMessage() {
    this(new HashMap<>());
  }

  public ConcreteMessage(Map<String, Object> data) {
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
}

