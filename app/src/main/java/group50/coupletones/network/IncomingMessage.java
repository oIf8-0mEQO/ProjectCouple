package group50.coupletones.network;

import android.os.Bundle;

import org.hashids.Hashids;

/**
 * Created by sharmaine on 5/4/16.
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

  @Override
  public String getType() {
    return type;
  }

  @Override
  public Bundle getData() {
    return data;
  }
}

