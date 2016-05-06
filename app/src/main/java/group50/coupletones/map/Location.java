package group50.coupletones.map;

import android.location.Address;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Joseph on 6/25/2016.
 */
public interface Location {

  LatLng getPosition();

  String getName();

  Address getAddress();
}