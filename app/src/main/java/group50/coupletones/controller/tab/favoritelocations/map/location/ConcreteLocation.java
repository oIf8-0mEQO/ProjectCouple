package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import group50.coupletones.CoupleTones;
import group50.coupletones.controller.tab.favoritelocations.map.LocationClickHandler;
import group50.coupletones.util.storage.Storable;
import group50.coupletones.util.storage.Storage;

/**
 * @Author Joseph
 * @Since 5/21/16
 */
public class ConcreteLocation implements Location, Storable {

  @Inject
  public AddressProvider addressProvider;

  private String name;
  private LatLng position;

  //Should only be used when loading.
  public ConcreteLocation()
  {
    CoupleTones.global().inject(this);
  }

  public ConcreteLocation(String name, LatLng position)
  {
    this.name = name;
    this.position = position;

    CoupleTones.global().inject(this);
  }

  public LatLng getPosition()
  {
    return position;
  }

  public String getName()
  {
    return name;
  }

  public Address getAddress()
  {
    return addressProvider.getAddressFromPosition(position);
  }

  @Override
  public void save(Storage storage)
  {
    storage.setString("name", name);
    storage.setFloat("lat", (float) position.latitude);
    storage.setFloat("long", (float) position.longitude);
  }

  @Override
  public void load(Storage storage) {
    name = storage.getString("name");
    position = new LatLng(storage.getFloat("lat"), storage.getFloat("long"));
  }

  public boolean equals(ConcreteLocation other)
  {
    if (!name.equals(other.name)) return false;
    if (position.latitude != other.position.latitude) return false;
    if (position.longitude != other.position.longitude) return false;
    return true;
  }

}
