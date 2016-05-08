package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by Joseph on 5/3/2016.
 */
public class VisitedLocation implements Location {

  @Inject
  public AddressProvider addressProvider;
  private String name;
  private LatLng position;
  private Date time;

  public VisitedLocation(FavoriteLocation location, Date time) {
    //DI
    CoupleTones.component().inject(this);

    this.name = location.getName();
    this.position = location.getPosition();
    this.time = time;
  }

  @Override
  public Address getAddress() {
    return addressProvider.getAddressFromPosition(position);
  }

  public LatLng getPosition() {
    return position;
  }

  public String getName() {
    return name;
  }

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
