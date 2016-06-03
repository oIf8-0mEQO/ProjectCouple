package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import group50.coupletones.CoupleTones;
import group50.coupletones.util.TimeUtility;
import group50.coupletones.util.sound.VibeTone;

import javax.inject.Inject;

/**
 * @author Joseph Cox
 * @since 5/21/16
 */
//TODO: Clean up all the constructors
@IgnoreExtraProperties
public class FavoriteLocation implements Location {
  /**
   * Time it takes to cooldown
   */
  private static int COOL_DOWN_TIME = 600000;

  @Inject
  @Exclude
  public TimeUtility timeUtility;

  /**
   * Reference to the concrete location object
   */
  private ConcreteLocation location;

  /**
   * ID of the vibeToneId
   */
  private int vibeToneId;

  /**
   * The last time the user visited this location
   */
  private long timeLastVisited;

  //Should only be used when loading.
  public FavoriteLocation() {
    this(null, new LatLng(0, 0), 0, 0);

  }

  public FavoriteLocation(String name, LatLng position, long timeLastVisited, int vibeTone) {
    this.location = new ConcreteLocation(name, position);
    this.timeLastVisited = timeLastVisited;
    this.vibeToneId = vibeTone;
    CoupleTones.global().inject(this);
  }

  //Methods accessing and modifying fields of FavoriteLocation
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
  public VibeTone getVibetone() {
    return VibeTone.getTone(vibeToneId);
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

  public int getVibeToneId() {
    return vibeToneId;
  }

  public void setVibeToneId(int vibeTone) {
    this.vibeToneId = vibeTone;
  }

  /**
   * @return true if the location is on cooldown, otherwise false.
   */
  @Exclude
  public boolean isOnCooldown() {
    return (timeUtility.systemTime() - timeLastVisited < COOL_DOWN_TIME);
  }

  /**
   * @param o FavoriteLocation to be compared
   * @return boolean value checking if equal
   */
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
    if (vibeToneId != other.vibeToneId) {
      return false;
    }
    return true;
  }

  /**
   * Generate hashCode for the location object
   * @return Hash code.
   */
  @Override
  public int hashCode() {
    int hashCode = 1;
    hashCode = 31 * hashCode + location.hashCode();
    hashCode = 31 * hashCode + Long.valueOf(timeLastVisited).hashCode();
    hashCode = 31 * hashCode + Integer.valueOf(vibeToneId).hashCode();
    return hashCode;
  }
}
