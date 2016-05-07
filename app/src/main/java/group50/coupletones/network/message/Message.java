package group50.coupletones.network.message;

import android.os.Bundle;

/**
 * Created by sharmaine on 5/4/16.
 */
public interface Message {
  String getType();

  Bundle getData();

  /**
   * Short-hand functions to getCollection various data from the message.
   */
  default String getString(String key) {
    return getData().getString(key);
  }

  default float getFloat(String key) {
    return getData().getFloat(key);
  }

  default double getDouble(String key) {
    return getData().getDouble(key);
  }

  default boolean getBoolean(String key) {
    return getData().getBoolean(key);
  }

  default int getInt(String key) {
    return getData().getInt(key);
  }

  default Message setString(String key, String value) {
    getData().putString(key, value);
    return this;
  }

  default Message setFloat(String key, float value) {
    getData().putFloat(key, value);
    return this;
  }

  default Message setDouble(String key, double value) {
    getData().putDouble(key, value);
    return this;
  }

  default Message setInt(String key, int value) {
    getData().putInt(key, value);
    return this;
  }

  default Message setBoolean(String key, boolean value) {
    getData().putBoolean(key, value);
    return this;
  }
}
