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
public class PartnerVisitedLocation implements Location, Storable {

  private ConcreteLocation location;
  private Date timeVisited;
  private VibeTone tone;

  //Should only be used when loading.
  public PartnerVisitedLocation()
  {
    location = new ConcreteLocation();
  }

  public PartnerVisitedLocation(String name, LatLng position, Date timeVisited, VibeTone tone)
  {
    location = new ConcreteLocation(name, position);
    this.timeVisited = timeVisited;
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

  public Date getTimeVisited()
  {
    return timeVisited;
  }

  public VibeTone getTone()
  {
    return tone;
  }

  @Override
  public void save(Storage storage)
  {
    location.save(storage);
    storage.setLong("date", timeVisited.getTime());
    //TODO: Implement save for VibeTone.
  }

  @Override
  public void load(Storage storage)
  {
    timeVisited = new Date(storage.getLong("date"));
    location.load(storage);
  }

  public boolean equals(PartnerVisitedLocation other)
  {
    if (!location.equals(other.location)) return false;
    if (!timeVisited.equals(other.timeVisited)) return false;
    if (!tone.equals(other.tone)) return false;
    return true;
  }

}