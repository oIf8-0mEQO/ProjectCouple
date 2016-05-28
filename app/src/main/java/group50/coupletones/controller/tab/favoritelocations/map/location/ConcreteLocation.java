package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import group50.coupletones.CoupleTones;

import javax.inject.Inject;

/**
 * @Author Joseph
 * @Since 5/21/16
 */
public class ConcreteLocation implements Location {

  @Inject
  public AddressProvider addressProvider;

  /**
   * Name of the location. May be null.
   */
  private String name;

  /**
   * Position of the location.
   */
  private double latitude;

  private double longitude;

  //Should only be used when loading.
  public ConcreteLocation() {
    this(null, new LatLng(0, 0));
  }

  public ConcreteLocation(String name, LatLng pos) {
    this.name = name;
    this.latitude = pos.latitude;
    this.longitude = pos.longitude;

    CoupleTones.global().inject(this);
  }

  @Exclude
  public LatLng getPosition() {
    return new LatLng(latitude, longitude);
  }

  public String getName() {
    return name;
  }

  public ConcreteLocation setName(String name) {
    this.name = name;
    return this;
  }

  public double getLatitude() {
    return latitude;
  }

  public ConcreteLocation setLatitude(double latitude) {
    this.latitude = latitude;
    return this;
  }

  public double getLongitude() {
    return longitude;
  }

  public ConcreteLocation setLongitude(double longitude) {
    this.longitude = longitude;
    return this;
  }

  @Exclude
  public Address getAddress() {
    return addressProvider.getAddressFromPosition(getPosition());
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ConcreteLocation)) {
      return false;
    }

    ConcreteLocation other = (ConcreteLocation) o;
    if (name != null && !name.equals(other.name)) {
      return false;
    }
    if (latitude != other.latitude) {
      return false;
    }
    if (longitude != other.longitude) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;
    hashCode = 31 * hashCode + (name != null ? name.hashCode() : 0);
    hashCode = 31 * hashCode + Double.valueOf(latitude).hashCode();
    hashCode = 31 * hashCode + Double.valueOf(longitude).hashCode();
    return hashCode;
  }
}
