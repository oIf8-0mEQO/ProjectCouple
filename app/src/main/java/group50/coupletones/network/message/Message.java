package group50.coupletones.network.message;

import android.os.Bundle;

/**
 * Created by sharmaine on 5/4/16.
 */
public interface Message {
  String getType();

  Bundle getData();

  /**
   * Short-hand functions to get various data from the message.
   */
  default String getString(String key) {
    return getData().getString(key);
  }

  default float getFloat(String key) {
    return getData().getFloat(key);
  }

  default int getInt(String key) {
    return getData().getInt(key);
  }

  default Message setString(String key, String value) {
    getData().putString(key, value);
    return this;
  }

  default Message setFloat(String key, float value) {
    getData().getFloat(key, value);
    return this;
  }

  default Message setInt(String key, int value) {
    getData().getInt(key, value);
    return this;
  }
}
