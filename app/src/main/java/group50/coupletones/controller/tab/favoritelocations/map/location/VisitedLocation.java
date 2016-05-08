package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author Joseph Cox
 * @since 5/3/2016
 */

/**
 * Visited Location class represents
 * a location a User has visited on the app.
 */
public class VisitedLocation implements Location {

  @Inject
  public AddressProvider addressProvider;
  private String name;
  private LatLng position;
  private Date time;

  /**
   *
   * @param location Visited location
   * @param time Time the location was visited
   */
  public VisitedLocation(FavoriteLocation location, Date time) {
    //DI
    CoupleTones.global().inject(this);

    this.name = location.getName();
    this.position = location.getPosition();
    this.time = time;
  }


  @Override

  /**
   * @return Visited Location Address
   */
  public Address getAddress() {
    return addressProvider.getAddressFromPosition(position);
  }

  /**
   *
   * @return Latitude-Longitude of Position
   */
  public LatLng getPosition() {
    return position;
  }

  /**
   *
   * @return Name of Visited Location
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @return Time the Location was visited
   */
  public Date getTime() {
    return time;
  }

  @Override
  public boolean equals(Object other) {
    try {

      if (((VisitedLocation) other).getPosition().equals(getPosition())) return true;
      else return false;
    } catch (ClassCastException e) {
      return false;
    }
  }

}
