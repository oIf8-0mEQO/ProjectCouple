package group50.coupletones.network;

import android.os.Bundle;

import org.hashids.Hashids;

/**
 * Created by sharmaine on 5/4/16.
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
   * The id of the message type
   */
  private final String type;
  /**
   * The data to send to the server.
   */
  private Bundle data;

  public OutgoingMessage(String type) {
    this.type = type;
    this.id = hashId.encode(currentId++);
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public Bundle getData() {
    return data;
  }
}
