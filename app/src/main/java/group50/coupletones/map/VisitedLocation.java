package group50.coupletones.map;

import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by Joseph on 5/3/2016.
 */
public class VisitedLocation implements Location {

  @Inject
  public Geocoder geocoder;
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
    try {
      List<Address> fromLocations = geocoder.getFromLocation(position.latitude, position.longitude, 1);

      if (fromLocations.size() > 0)
        return fromLocations.get(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
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

}
