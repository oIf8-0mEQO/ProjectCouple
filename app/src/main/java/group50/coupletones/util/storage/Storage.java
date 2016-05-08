package group50.coupletones.util.storage;

import android.content.SharedPreferences;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ranlin Huang
 * @since 4/24/16
 */

/**
 * Storage class
 */
public class Storage {

  /**
   * Name of the user preference file
   */
  public static final String PREF_FILE_USER = "user";

  private final SharedPreferences preference;
  private final String suffix;

  public Storage(SharedPreferences preferences, String suffix) {
    this.preference = preferences;
    this.suffix = suffix;
  }

  /**
   * Storage
   * @param preferences
   */
  public Storage(SharedPreferences preferences) {
    this(preferences, "");
  }

  /**
   * Checks if it contains name data
   * @param name
   * @return
   */
  public boolean contains(String name) {
    return preference.contains(name + suffix);
  }

  /**
   * Gets the int for name data
   * @param name
   * @return
   */
  public int getInt(String name) {
    return preference.getInt(name + suffix, 0);
  }

  /**
   * Gets the float for name data
   * @param name
   * @return
   */
  public float getFloat(String name) {
    return preference.getFloat(name + suffix, 0);
  }

  /**
   * Gets the string for name data
   * @param name
   * @return
   */
  public String getString(String name) {
    return preference.getString(name + suffix, null);
  }

  /**
   * Gets the boolean for name data
   * @param name
   * @return
   */
  public boolean getBoolean(String name) {
    return preference.getBoolean(name + suffix, false);
  }

  /**
   * getCollection
   * @param name
   * @param type
   * @param <T>
   * @return list
   */
  public <T extends Storable> List<T> getCollection(String name, Class<T> type) {
    List<T> list = new LinkedList<>();

    if (contains(name)) {
      int collectionLength = getInt(name + suffix);

      try {
        T object = type.newInstance();
        for (int i = 0; i < collectionLength; i++) {
          Storage arrStorage = new Storage(preference, suffix + i);
          object.load(arrStorage);
          list.add(object);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  /**
   * Sets the int
   * @param name
   * @param value
   */
  public void setInt(String name, int value) {
    preference
      .edit()
      .putInt(name + suffix, value)
      .apply();
  }

  /**
   * Sets the float
   * @param name
   * @param value
   */
  public void setFloat(String name, float value) {
    preference
      .edit()
      .putFloat(name + suffix, value)
      .apply();
  }

  /**
   * Sets the string
   * @param name
   * @param value
   */
  public void setString(String name, String value) {
    preference
      .edit().putString(name + suffix, value)
      .apply();
  }

  /**
   * Sets the boolean
   * @param name
   * @param value
   */
  public void setBoolean(String name, boolean value) {
    preference
      .edit()
      .putBoolean(name + suffix, value)
      .apply();
  }

  /**
   * Sets the collection
   * @param name
   * @param collection
   */
  public void setCollection(String name, List<? extends Storable> collection) {
    int i = 0;
    for (Storable obj : collection) {
      obj.save(new Storage(preference, suffix + i));
      i++;
    }
    setInt(name + suffix, i);
  }

  /**
   * Deletes the name
   * @param name
   */
  public void delete(String name) {
    preference
      .edit()
      .remove(name + suffix)
      .apply();

  }
}