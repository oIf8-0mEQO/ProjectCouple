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
import group50.coupletones.util.TimeUtility;
import rx.Observable;
import rx.subjects.PublishSubject;

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
  private final PublishSubject<VisitedLocationEvent> enterSubject = PublishSubject.create();
  private final PublishSubject<VisitedLocationEvent> exitSubject = PublishSubject.create();
  private CoupleTones app;
  private TimeUtility timeUtility;
  private List<FavoriteLocation> currentlyIn;

  /**
   * Map Proximity Manager
   *
   * @param app - CoupleTones app
   */
  @Inject
  public MapProximityManager(CoupleTones app, TimeUtility timeUtility) {
    this.app = app;
    this.timeUtility = timeUtility;
    currentlyIn = new LinkedList<>();
  }

  /**
   * Finds the distance in miles between two locations given by the gps.
   */
  private static double distanceInMiles(LatLng location1, LatLng location2) {
    return (conversion * SphericalUtil.computeDistanceBetween(location1, location2));
  }

  public Observable<VisitedLocationEvent> getEnterSubject() {
    return enterSubject;
  }

  public Observable<VisitedLocationEvent> getExitSubject() {
    return exitSubject;
  }

  /**
   * Called when a user enters a favorite location.
   * Notifies all observers.
   *
   * @param location The favorite location entered
   */
  public void onEnterLocation(FavoriteLocation location) {
    Log.d(getTag(), "Entering location: " + location.getName() + " cooldown = " + location.isOnCooldown());
    if (!location.isOnCooldown() && !currentlyIn.contains(location)) {
      VisitedLocationEvent newLoc = new VisitedLocationEvent(location, new Date());

      // Update cool down
      List<FavoriteLocation> favoriteLocations = app.getLocalUser().getFavoriteLocations();
      int i = favoriteLocations.indexOf(location);
      if (i >= 0 && i < favoriteLocations.size()) {
        location.setTime(timeUtility.systemTime());
        app.getLocalUser().setFavoriteLocation(i, location);
      }

      enterSubject.onNext(newLoc);
      currentlyIn.add(location);
    }
  }

  public void onLeaveLocation(FavoriteLocation location) {
    Log.d(getTag(), "Departing location: " + location.getName() + " cooldown = " + location.isOnCooldown());
    currentlyIn.remove(location);
    VisitedLocationEvent loc = null;
    for (VisitedLocationEvent i : app.getLocalUser().getVisitedLocations()) {
      if (location.equals(i.getLocation()) && i.getLeaveTime() == -1)
        loc = i;
    }
    if (loc != null) {
      exitSubject.onNext(loc);
    } else {
      Log.e(getTag(), "Invalid visited location! (May have been removed)");
    }
  }

  /**
   * Handles the location change event
   *
   * @param location - The changed location
   */
  @Override
  public void onLocationChanged(Location location) {
    Log.d(getTag(), "Location changed: " + location);
    // Make sure the user is logged in
    if (app.isLoggedIn()) {
      List<FavoriteLocation> favoriteLocations = app.getLocalUser().getFavoriteLocations();
      for (FavoriteLocation loc : favoriteLocations) {
        // Check distance
        if (distanceInMiles(loc.getPosition(), new LatLng(location.getLatitude(), location.getLongitude())) < 0.1) {
          onEnterLocation(loc);
        }
      }
      for (FavoriteLocation loc : currentlyIn) {
        if (distanceInMiles(new LatLng(location.getLatitude(), location.getLongitude()), loc.getPosition()) > .1) {
          onLeaveLocation(loc);
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
