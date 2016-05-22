package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import group50.coupletones.util.storage.Storable;
import group50.coupletones.util.storage.Storage;

/**
 * @Author Joseph
 * @Since 5/21/16
 */
public class PartnerFavoriteLocation implements Location, Storable {

  private ConcreteLocation location;
  private VibeTone tone;

  //Should only be used when loading.
  public PartnerFavoriteLocation()
  {
    location = new ConcreteLocation();
  }

  public PartnerFavoriteLocation(String name, LatLng position, VibeTone tone)
  {
    this.location = new ConcreteLocation(name, position);
    this.tone = tone;
  }

  /**
   * Recreates the previous location with a different tone.
   */
  public PartnerFavoriteLocation(PartnerFavoriteLocation previous, VibeTone tone)
  {
    location = previous.location;
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

  public VibeTone getTone()
  {
    return tone;
  }

  /**
   * @param storage - storage to save to
   */
  @Override
  public void save(Storage storage)
  {
    location.save(storage);
    //TODO: Implement saving of VibeTone.
  }

  @Override
  public void load(Storage storage)
  {
    location.load(storage);
  }

  public boolean equals(PartnerFavoriteLocation other)
  {
    if (!location.equals(other.location)) return false;
    if (!tone.equals(other.tone)) return false;
    return true;
  }

}
