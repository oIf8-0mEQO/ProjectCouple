package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import group50.coupletones.util.storage.Storable;
import group50.coupletones.util.storage.Storage;

/**
 * @Author Joseph
 * @Since 5/21/16
 */
public class UserFavoriteLocation implements Location, Storable {

  private ConcreteLocation location;
  private long timeLastVisited;
  private static int COOL_DOWN_TIME = 600000;

  //Should only be used when loading.
  public UserFavoriteLocation()
  {
    location = new ConcreteLocation();
  }

  public UserFavoriteLocation(String name, LatLng position, long timeLastVisited)
  {
    this.location = new ConcreteLocation(name, position);
    this.timeLastVisited = timeLastVisited;
  }

  /**
   * Recreates the previous location with a different name.
   */
  public UserFavoriteLocation(UserFavoriteLocation previous, String name)
  {
    this.location = new ConcreteLocation(name, previous.location.getPosition());
    this.timeLastVisited = previous.timeLastVisited;
  }

  /**
   * Recreates the previous location with a different position.
   */
  public UserFavoriteLocation(UserFavoriteLocation previous, LatLng position)
  {
    this.location = new ConcreteLocation(previous.location.getName(), position);
    this.timeLastVisited = previous.timeLastVisited;
  }

  /**
   * Recreates the previous location with a different time.
   */
  public UserFavoriteLocation(UserFavoriteLocation previous, long timeLastVisited)
  {
    this.location = new ConcreteLocation(previous.getName(), previous.getPosition());
    this.timeLastVisited = timeLastVisited;
  }

  public String getName()
  {
    return location.getName();
  }

  public LatLng getPosition()
  {
    return location.getPosition();
  }

  public Address getAddress()
  {
    return location.getAddress();
  }

  public long getTime()
  {
    return timeLastVisited;
  }

  /**
   * @return true if the location is on cooldown, otherwise false.
   */
  public boolean isOnCooldown()
  {
    return (System.currentTimeMillis() - timeLastVisited < COOL_DOWN_TIME);
  }

  /**
   * @param storage - storage to save to
   */
  @Override
  public void save(Storage storage)
  {
    location.save(storage);
    storage.setLong("time", timeLastVisited);
  }

  @Override
  public void load(Storage storage)
  {
    timeLastVisited = storage.getLong("time");
    location.load(storage);
  }

  public boolean equals(UserFavoriteLocation other)
  {
    if (!location.equals(other.location)) return false;
    if (timeLastVisited != other.timeLastVisited) return false;
    return true;
  }

}
