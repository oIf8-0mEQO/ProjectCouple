package group50.coupletones.map;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.List;

public class MovementListener implements LocationListener {

  private ProximityManager handler;
  private List<FavoriteLocation> locations;


  public MovementListener(ProximityManager handler, List<FavoriteLocation> locations) {
    this.handler = handler;
    this.locations = locations;
  }

  @Override
  public void onLocationChanged(Location location) {
    for (FavoriteLocation i : locations) {
      if (this.distanceInMiles(i.getPosition(), new LatLng(location.getLatitude(), location.getLongitude())) < 0.1) {
        handler.onNearby(i);
      }
    }
  }

  @Override
  public void onProviderDisabled(String provider) {
  }

  @Override
  public void onProviderEnabled(String provider) {
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extra) {
  }

  /**
   * Finds the distance in miles between two locations given by the gps.
   */
  private double distanceInMiles(LatLng location1, LatLng location2) {
    return 0.000621371 * SphericalUtil.computeDistanceBetween(location1, location2);
  }

}
