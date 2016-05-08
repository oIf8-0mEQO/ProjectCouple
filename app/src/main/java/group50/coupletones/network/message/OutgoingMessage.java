package group50.coupletones.network.message;

import android.os.Bundle;
import org.hashids.Hashids;

/**
 * @author Sharmaine Manalo
 * @since 5/4/16
 */

/**
 * Outgoing message class for the app
 * that handles all outgoing messages.
 */
public class OutgoingMessage implements Message {

  /**
   * The hash id and an int that enable randomly generated ids
   */
  private static final Hashids hashId = new Hashids();
  private static int currentId = 0;
  /**
   * The id of the message
   */
  private final String id;

  /**
   * The data to send to the server.
   */
  private final Bundle data;

  public OutgoingMessage(String type) {
    this.data = new Bundle();
    this.setString("type", type);
    this.id = hashId.encode(currentId++);
  }

  public String getId() {
    return id;
  }

  @Override
  public String getType() {
    return getString("type");
  }

  @Override
  public Bundle getData() {
    return data;
  }
}
