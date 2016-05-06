package group50.coupletones.util.storage;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Calvin on 4/24/2016.
 */

public class Storage {
  private SharedPreferences settings;
  SharedPreferences.Editor editor;
  String suffix;
  int collectionLength;

  public Storage(Activity activity) {
    settings = activity.getPreferences(0);
    editor = settings.edit();
    suffix = "";
    collectionLength = 0;
  }


  public boolean contain(String name) {
    return settings.contains(name + suffix);
  }


  public int getInt(String name) {
    return settings.getInt(name + suffix, 0);
  }


  public float getFloat(String name) {
    return settings.getFloat(name + suffix, 0);
  }


  public String getString(String name) {
    return settings.getString(name + suffix, "");
  }

  public boolean getBoolean(String name) {
    return settings.getBoolean(name + suffix, false);
  }


  public Collection<Storable> get(String name, Class<?> type) {
    Collection<Storable> list = new ArrayList<Storable>();
    try {
      Storable object = (Storable) type.newInstance();
      for (int i = 0; i < collectionLength; i++) {
        object.load(this);
        list.add(object);
        suffix = suffix.substring(0, suffix.length() - 1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }


  public void setString(String name, int value) {
    editor.putInt(name + suffix, value);
    editor.commit();
  }


  public void setString(String name, float value) {
    editor.putFloat(name + suffix, value);
    editor.commit();
  }


  public void setString(String name, String value) {
    editor.putString(name + suffix, value);
    editor.commit();
  }

  public void setBoolean(String name, boolean value) {
    editor.putBoolean(name + suffix, value);
    editor.commit();
  }


  public void setCollection(String name, Collection<Storable> list) {
    collectionLength = 0;
    for (Storable obj : list) {
      obj.save(this);
      suffix = suffix + "0";
      collectionLength++;
    }
  }
}