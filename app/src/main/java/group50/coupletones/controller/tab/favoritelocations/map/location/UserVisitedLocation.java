package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import group50.coupletones.util.storage.Storable;
import group50.coupletones.util.storage.Storage;

/**
 * @Author Joseph
 * @Since 5/21/16
 */
public class UserVisitedLocation implements Location, Storable {

  private ConcreteLocation location;
  private Date timeVisited;

  //Should only be used when loading.
  public UserVisitedLocation()
  {
    location = new ConcreteLocation();
  }

  public UserVisitedLocation(String name, LatLng position, Date timeVisited)
  {
    location = new ConcreteLocation(name, position);
    this.timeVisited = timeVisited;
  }

  /**
   *Constructs a new visited location from the passed favorite location.
   */
  public UserVisitedLocation(UserFavoriteLocation favoriteLocation, Date timeVisited)
  {
    location = new ConcreteLocation(favoriteLocation.getName(), favoriteLocation.getPosition());
    this.timeVisited = timeVisited;
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

  public Date getTimeVisited()
  {
    return timeVisited;
  }

  @Override
  public void save(Storage storage)
  {
    location.save(storage);
    storage.setLong("date", timeVisited.getTime());
  }

  @Override
  public void load(Storage storage)
  {
    location.load(storage);
    timeVisited = new Date(storage.getLong("date"));
  }

  public boolean equals(UserVisitedLocation other)
  {
    if (!location.equals(other.location)) return false;
    if (!timeVisited.equals(other.timeVisited)) return false;
    return true;
  }

}
