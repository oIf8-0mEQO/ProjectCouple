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
import group50.coupletones.util.properties.Property;
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
  public CoupleTones app;
  private List<FavoriteLocation> currentlyIn;

  /**
   * Map Proximity Manager
   * @param app - CoupleTones app
   */
  @Inject
  public MapProximityManager(CoupleTones app) {
    this.app = app;
    currentlyIn = new LinkedList<>();
  }

  /**
   * Finds the distance in miles between two locations given by the gps.
   */
  private static double distanceInMiles(LatLng location1, LatLng location2) {
    return (conversion * SphericalUtil.computeDistanceBetween(location1, location2));
  }

  public Observable<VisitedLocationEvent> getEnterSubject()
  {
    return enterSubject;
  }

  public Observable<VisitedLocationEvent> getExitSubject()
  {
    return exitSubject;
  }

  /**
   * Called when a user enters a favorite location.
   * Notifies all observers.
   *
   * @param favoriteLocation The favorite location entered
   */
  public void onEnterLocation(FavoriteLocation favoriteLocation) {
    Log.d(getTag(), "Entering location: " + favoriteLocation.getName() + " cooldown = " + favoriteLocation.isOnCooldown());
    if (!favoriteLocation.isOnCooldown() && !currentlyIn.contains(favoriteLocation)) {
      VisitedLocationEvent newLoc = new VisitedLocationEvent(favoriteLocation, new Date());
      app.getLocalUser().addVisitedLocation(newLoc);
      app.getLocalUser().updateCooldownOfFavorite(favoriteLocation);
      enterSubject.onNext(newLoc);
      currentlyIn.add(favoriteLocation);
    }
  }

  public void onLeaveLocation(FavoriteLocation location)
  {
    currentlyIn.remove(location);
    VisitedLocationEvent newLoc = new VisitedLocationEvent(location, new Date(location.getTime()), new Date());
    app.getLocalUser().addVisitedLocation(newLoc);
    exitSubject.onNext(newLoc);
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
      for (FavoriteLocation loc : currentlyIn)
      {
        if (distanceInMiles(new LatLng(location.getLatitude(), location.getLongitude()), loc.getPosition()) > .1)
        {
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
