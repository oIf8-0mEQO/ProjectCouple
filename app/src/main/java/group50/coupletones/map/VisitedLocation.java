package group50.coupletones.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Joseph on 5/3/2016.
 */
public class VisitedLocation implements Location {

  private String name;
  private LatLng position;
  private Date time;

  public VisitedLocation(FavoriteLocation location, Date time) {
    this.name = location.getName();
    this.position = location.getPosition();
    this.time = time;
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
