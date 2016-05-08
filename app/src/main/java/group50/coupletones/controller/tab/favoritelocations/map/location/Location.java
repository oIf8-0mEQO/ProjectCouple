package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Joseph on 6/25/2016.
 */
public interface Location {

  /**
   * @return a LatLng object representing the gps coordinates of the location.
   */
  LatLng getPosition();

  /**
   * @return the name of the favorite location given by the user.
   */
  String getName();

  /**
   * @return the official address of the location if the address exists, otherwise return null.
   */
  Address getAddress();
}