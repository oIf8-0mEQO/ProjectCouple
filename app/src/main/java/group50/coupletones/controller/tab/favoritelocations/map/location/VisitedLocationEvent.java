package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import group50.coupletones.util.sound.VibeTone;

import java.util.Date;

/**
 * @Author Joseph
 * @Since 5/21/16
 */
//TODO: Add properties
public class VisitedLocationEvent {

  private FavoriteLocation location;
  private Date timeEnter;
  private Date timeLeft;

  //Should only be used when loading.
  public VisitedLocationEvent() {
    location = new FavoriteLocation();
  }

  public VisitedLocationEvent(String name, LatLng position, Date timeEnter, Date timeLeft, int tone) {
    location = new FavoriteLocation(name, position, 0, tone);
    this.timeEnter = timeEnter;
    this.timeLeft = timeLeft;
  }

  /**
   * Constructs a new visited location from the passed favorite location.
   */
  public VisitedLocationEvent(FavoriteLocation favoriteLocation, Date timeEnter) {
    this.location = favoriteLocation;
    this.timeEnter = timeEnter;
  }

  /**
   * Constructs a new visited location with the given enter and leave time.
   */
  public VisitedLocationEvent(FavoriteLocation favoriteLocation, Date timeEnter, Date timeLeft) {
    this.location = favoriteLocation;
    this.timeEnter = timeEnter;
    this.timeLeft = timeLeft;
  }

  @Exclude
  public String getName() {
    return location.getName();
  }

  @Exclude
  public LatLng getPosition() {
    return location.getPosition();
  }

  @Exclude
  public Address getAddress() {
    return location.getAddress();
  }

  @Exclude
  public VibeTone getVibeTone() {
    return location.getVibetone();
  }

  @Exclude
  public Date getTimeVisited() {
    return timeEnter;
  }

  @Exclude
  public Date getTimeLeft() {
    return timeLeft;
  }

  @Exclude
  public boolean getArrival()
  {
    return (timeLeft == null);
  }

  /**
   * Firebase serialization
   */
  public FavoriteLocation getLocation() {
    return location;
  }

  public void setLocation(FavoriteLocation location) {
    this.location = location;
  }

  public long getEnterTime() {
    return timeEnter.getTime();
  }

  public void setEnterTime(long timeVisited) {
    this.timeEnter = new Date(timeVisited);
  }

  public long getLeaveTime() {
    if (timeLeft != null) return timeLeft.getTime();
    else return -1;
  }

  public void setLeaveTime(long timeLeft) {
    this.timeLeft = new Date(timeLeft);
  }

  @Override
  public boolean equals(Object object) {
    try {
      VisitedLocationEvent other = (VisitedLocationEvent) object;
      if (!location.equals(other.location)) return false;
      if (!timeEnter.equals(other.timeEnter)) return false;
      if (!timeLeft.equals(other.timeLeft)) return false;
      return true;
    } catch (ClassCastException e) {
      return false;
    }
  }

}
