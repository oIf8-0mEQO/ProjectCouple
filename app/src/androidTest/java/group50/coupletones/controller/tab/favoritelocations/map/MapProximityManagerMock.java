package group50.coupletones.controller.tab.favoritelocations.map;

/**
 * Created by Joseph on 5/7/2016.
 */

import android.location.*;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;

public class MapProximityManagerMock implements ProximityManager, LocationListener {

  //Meters to Miles conversion ratio
  private static final double conversion = (1.0) / (1609.0);
  /**
   * A list of observers that subscribe to changes in location.
   */
  private final List<ProximityObserver> observers;
  private List<FavoriteLocation> locations;

  @Inject
  public CoupleTones app;

  @Inject
  public MapProximityManagerMock() {
    observers = new LinkedList<>();
    locations = new LinkedList<>();
  }

  /**
   * Finds the distance in miles between two locations given by the gps.
   */
  public static double distanceInMiles(LatLng location1, LatLng location2) {
    return (conversion * SphericalUtil.computeDistanceBetween(location1, location2));
  }

  public void register(ProximityObserver observer) {
    observers.add(observer);
  }

  /**
   * Called when a user enters a favorite location.
   * Notifies all observers.
   *
   * @param favoriteLocation The favorite location entered
   */
  public void onEnterLocation(FavoriteLocation favoriteLocation) {
    if (!favoriteLocation.isOnCooldown()) {
      for (ProximityObserver i : observers) {
        i.onEnterLocation(new VisitedLocation(favoriteLocation, new Date()));
      }
      favoriteLocation.setCooldown();
    }
  }

  /**
   * Handles the location change event
   *
   * @param location The location
   */
  @Override
  public void onLocationChanged(android.location.Location location) {
    // Make sure the user is logged in
    if (true) {
      for (FavoriteLocation loc : locations) {
        // Check distance
        double distance = distanceInMiles(loc.getPosition(), new LatLng(location.getLatitude(), location.getLongitude()));
        if (distanceInMiles(loc.getPosition(), new LatLng(location.getLatitude(), location.getLongitude())) < 0.1) {
          onEnterLocation(loc);
        }
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

  public void addFavoriteLocation(FavoriteLocation location)
  {
    locations.add(location);
  }
}
