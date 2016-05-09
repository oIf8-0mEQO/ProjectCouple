package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;
import com.google.android.gms.maps.model.LatLng;

/**
 * An object that is capable of providing an address for a geolocation
 *
 * @author Henry Mao
 * @since 5/7/16
 */
public interface AddressProvider {

  /**
   * @param position The position of the latlong
   * @return The address of the location
   */
  Address getAddressFromPosition(LatLng position);
}
