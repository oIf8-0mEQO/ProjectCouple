package group50.coupletones.map;

import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Joseph on 6/25/2016.
 */
public class FavoriteLocation implements Location {

  @Inject
  public Geocoder geocoder;
  private String name;
  private LatLng position;
  private long time;

  public FavoriteLocation() {
    this("", new LatLng(0, 0), 0);
  }

  public FavoriteLocation(String name, LatLng position) {
    this(name, position, 0);
  }

  public FavoriteLocation(String name, LatLng position, long time) {
    //DI
    CoupleTones.component().inject(this);

    setName(name);
    setPosition(position);
    this.time = time;
  }

  @Override
  public LatLng getPosition() {
    return position;
  }

  public void setPosition(LatLng position) {
    this.position = position;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public void setCooldown() {
    time = System.currentTimeMillis();
  }

  /**
   * @Return true if the location is on cooldown, otherwise false.
   */
  public boolean isOnCooldown() {
    return (System.currentTimeMillis() - time > 600000);
  }

  /**
   * @Return the most recent time this location was visited as a long.
   */
  protected long getTime() {
    return time;
  }

}
