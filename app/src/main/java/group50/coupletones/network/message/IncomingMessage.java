package group50.coupletones.network.message;

import android.os.Bundle;

/**
 * @author Sharmaine Manalo
 * @since 5/4/16
 */

/**
 * Incoming message on the app.
 */
public class IncomingMessage implements Message {

  /**
   * The id of the message type
   */
  private final String type;
  /**
   * The data to send to the server.
   */
  private final Bundle data;

  public IncomingMessage(String type, Bundle data) {
    this.type = type;
    this.data = data;
  }

  /**
   * Gets type
   * @return - incoming message
   */
  @Override
  public String getType() {
    return type;
  }

  /**
   * Gets data
   * @return - data of message
   */
  @Override
  public Bundle getData() {
    return data;
  }
}

