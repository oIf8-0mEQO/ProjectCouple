package group50.coupletones.controller.tab.favoritelocations.map;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import group50.coupletones.CoupleTones;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * The map proximity manager
 *
 * @author Joseph Cox
 * @since 5/1/2016
 */
public class MapProximityManager implements ProximityManager, Taggable {

  //Meters to Miles conversion ratio
  private static final double conversion = (1.0) / (1609.0);
  /**
   * A list of observers that subscribe to changes in location.
   */
  private final List<ProximityObserver> observers;

  public CoupleTones app;

  /**
   * Map Proximity Manager
   * @param app - CoupleTones app
   */
  @Inject
  public MapProximityManager(CoupleTones app) {
    observers = new LinkedList<>();
    this.app = app;
  }

  /**
   * Finds the distance in miles between two locations given by the gps.
   */
  private static double distanceInMiles(LatLng location1, LatLng location2) {
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
    Log.d(getTag(), "Entering location: " + favoriteLocation.getName() + " cooldown = " + favoriteLocation.isOnCooldown());
    if (!favoriteLocation.isOnCooldown()) {
      for (ProximityObserver i : observers) {
        i.onEnterLocation(new VisitedLocationEvent(favoriteLocation, new Date()));
      }
      app.getLocalUser().getFavoriteLocations().remove(favoriteLocation);
      app.getLocalUser().getFavoriteLocations().add(new FavoriteLocation(favoriteLocation, System.currentTimeMillis()));
      app.getLocalUser().getVisitedLocations().add(new VisitedLocationEvent(favoriteLocation, new Date()));
    }
  }

  /**
   * Handles the location change event
   * @param location - The changed location
   */
  @Override
  public void onLocationChanged(Location location) {
    Log.d(getTag(), "Location changed: " + location);
    // Make sure the user is logged in
    if (app.isLoggedIn()) {
      for (FavoriteLocation loc : app.getLocalUser().getFavoriteLocations()) {
        // Check distance
        if (distanceInMiles(loc.getPosition(), new LatLng(location.getLatitude(), location.getLongitude())) < 0.1) {
          onEnterLocation(loc);
        }
      }
    }
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(String provider) {

  }

  @Override
  public void onProviderDisabled(String provider) {

  }
}
