package group50.coupletones.util.storage;

import android.content.SharedPreferences;
import group50.coupletones.util.function.Supplier;

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

  public Storage(SharedPreferences preferences) {
    this(preferences, "");
  }


  public boolean contains(String name) {
    return preference.contains(name + suffix);
  }


  public int getInt(String name) {
    return preference.getInt(name + suffix, 0);
  }


  public float getFloat(String name) {
    return preference.getFloat(name + suffix, 0);
  }


  public String getString(String name) {
    return preference.getString(name + suffix, null);
  }


  public boolean getBoolean(String name) {
    return preference.getBoolean(name + suffix, false);
  }


  public <T extends Storable> List<T> getCollection(String name, Supplier<T> ctor) {
    if (suffix.isEmpty() && name.contains("_"))
      throw new RuntimeException("Name cannot contain underscore");

    List<T> list = new LinkedList<>();

    if (contains(name)) {
      int collectionLength = getInt(name + suffix);

      for (int i = 0; i < collectionLength; i++) {
        T object = ctor.get();
        Storage arrStorage = new Storage(preference, suffix + "_" + i);
        object.load(arrStorage);
        list.add(object);
      }
    }
    return list;
  }


  public void setInt(String name, int value) {
    preference
      .edit()
      .putInt(name + suffix, value)
      .apply();
  }


  public void setFloat(String name, float value) {
    preference
      .edit()
      .putFloat(name + suffix, value)
      .apply();
  }

  public void setString(String name, String value) {
    preference
      .edit().putString(name + suffix, value)
      .apply();
  }

  public void setBoolean(String name, boolean value) {
    preference
      .edit()
      .putBoolean(name + suffix, value)
      .apply();
  }


  public void setCollection(String name, List<? extends Storable> collection) {
    if (suffix.isEmpty() && name.contains("_"))
      throw new RuntimeException("Name cannot contain underscore");
    int i = 0;
    for (Storable obj : collection) {
      obj.save(new Storage(preference, "_" + suffix + i));
      i++;
    }
    setInt(name + suffix, i);
  }

  public void delete(String name) {
    preference
      .edit()
      .remove(name + suffix)
      .apply();

  }
}