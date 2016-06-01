package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.sql.Time;

import javax.inject.Inject;

import group50.coupletones.CoupleTones;
import group50.coupletones.util.TimeUtility;
import group50.coupletones.util.sound.VibeTone;

/**
 * @author Joseph
 * @since 5/21/16
 */
//TODO: Clean up all the constructors
@IgnoreExtraProperties
public class FavoriteLocation implements Location {
  @Inject
  @Exclude
  public TimeUtility timeUtility;

  private static int COOL_DOWN_TIME = 600000;

  private ConcreteLocation location;
  private VibeTone tone;
  private long timeLastVisited;

  //Should only be used when loading.
  public FavoriteLocation() {
    this(null, new LatLng(0, 0), 0, null);
    CoupleTones.global().inject(this);

  }

  public FavoriteLocation(String name, LatLng position, long timeLastVisited, VibeTone tone) {
    this.location = new ConcreteLocation(name, position);
    this.timeLastVisited = timeLastVisited;
    this.tone = tone;

    CoupleTones.global().inject(this);
  }

  /**
   * Recreates the previous location with a different name.
   */
  public FavoriteLocation(FavoriteLocation previous, String name) {
    this(name, previous.location.getPosition(), previous.timeLastVisited, previous.getTone());
  }

  /**
   * Recreates the previous location with a different position.
   */
  public FavoriteLocation(FavoriteLocation previous, LatLng position) {
    this(previous.location.getName(), position, previous.timeLastVisited, previous.tone);
  }

  /**
   * Recreates the previous location with a different time.
   */
  public FavoriteLocation(FavoriteLocation previous, long timeLastVisited) {
    this(previous.getName(), previous.getPosition(), timeLastVisited, previous.tone);
  }

  /**
   * Recreates the previous location with a different tone.
   */
  public FavoriteLocation(FavoriteLocation previous, VibeTone tone) {
    this(previous.getName(), previous.getPosition(), previous.timeLastVisited, tone);
  }

  public String getName() {
    return location.getName();
  }

  public void setName(String name) {
    location.setName(name);
  }

  @Exclude
  public LatLng getPosition() {
    return location.getPosition();
  }

  @Exclude
  public Address getAddress() {
    return location.getAddress();
  }

  public long getTime() {
    return timeLastVisited;
  }

  @Exclude
  public VibeTone getTone() {
    return tone;
  }

  public double getLat() {
    return location.getLatitude();
  }

  public void setLat(double value) {
    location.setLatitude(value);
  }

  public double getLong() {
    return location.getLongitude();
  }

  public void setLong(double value) {
    location.setLongitude(value);
  }

  public void setTimeLastVisited(long timeLastVisited) {
    this.timeLastVisited = timeLastVisited;
  }

  /**
   * @return true if the location is on cooldown, otherwise false.
   */
  public boolean isOnCooldown() {
    return (timeUtility.systemTime() - timeLastVisited < COOL_DOWN_TIME);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof FavoriteLocation)) {
      return false;
    }

    FavoriteLocation other = (FavoriteLocation) o;

    if (!location.equals(other.location)) {
      return false;
    }
    if (timeLastVisited != other.timeLastVisited) {
      return false;
    }
    if (tone != null && !tone.equals(other.tone)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;
    hashCode = 31 * hashCode + location.hashCode();
    hashCode = 31 * hashCode + Long.valueOf(timeLastVisited).hashCode();
    hashCode = 31 * hashCode + (tone != null ? tone.hashCode() : 0);
    return hashCode;
  }
}
