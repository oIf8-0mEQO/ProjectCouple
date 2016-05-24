package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import group50.coupletones.util.sound.VibeTone;
import group50.coupletones.util.storage.Storable;
import group50.coupletones.util.storage.Storage;

/**
 * @Author Joseph
 * @Since 5/21/16
 */
public class FavoriteLocation implements Location, Storable {

  private ConcreteLocation location;
  private VibeTone tone;
  private long timeLastVisited;
  private static int COOL_DOWN_TIME = 600000;

  //Should only be used when loading.
  public FavoriteLocation()
  {
    location = new ConcreteLocation();
  }

  public FavoriteLocation(String name, LatLng position, long timeLastVisited, VibeTone tone)
  {
    this.location = new ConcreteLocation(name, position);
    this.timeLastVisited = timeLastVisited;
    this.tone = tone;
  }

  /**
   * Recreates the previous location with a different name.
   */
  public FavoriteLocation(FavoriteLocation previous, String name)
  {
    this.location = new ConcreteLocation(name, previous.location.getPosition());
    this.timeLastVisited = previous.timeLastVisited;
    this.tone = previous.tone;
  }

  /**
   * Recreates the previous location with a different position.
   */
  public FavoriteLocation(FavoriteLocation previous, LatLng position)
  {
    this.location = new ConcreteLocation(previous.location.getName(), position);
    this.timeLastVisited = previous.timeLastVisited;
    this.tone = previous.tone;
  }

  /**
   * Recreates the previous location with a different time.
   */
  public FavoriteLocation(FavoriteLocation previous, long timeLastVisited)
  {
    this.location = new ConcreteLocation(previous.getName(), previous.getPosition());
    this.timeLastVisited = timeLastVisited;
    this.tone = previous.tone;
  }

  /**
   * Recreates the previous location with a different tone.
   */
  public FavoriteLocation(FavoriteLocation previous, VibeTone tone)
  {
    this.location = new ConcreteLocation(previous.getName(), previous.getPosition());
    timeLastVisited = previous.timeLastVisited;
    this.tone = tone;
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

  public VibeTone getTone()
  {
    return tone;
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

  public boolean equals(FavoriteLocation other)
  {
    if (!location.equals(other.location)) return false;
    if (timeLastVisited != other.timeLastVisited) return false;
    if (!tone.equals(other.tone)) return false;
    return true;
  }

}
