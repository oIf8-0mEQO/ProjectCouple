package group50.coupletones.controller.tab.favoritelocations.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.User;

import javax.inject.Inject;

import static android.location.LocationManager.GPS_PROVIDER;

/**
 * Map Fragment Class
 */
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {

  public static final float PROXIMITY_RADIUS_METERS = 160.934f;

  public static final String TAG = "MapFragment";

  @Inject
  public CoupleTones app;

  @Inject
  public ProximityManager proximityManager;

  private GoogleMap mMap;

  private LocationManager locationManager;

  private boolean isUser = true;//True if map represents the user's data.

  /**
   * Creates a OnMapClickListener that opens a dialog box asking the user for a name of a favorite location. When the user accepts the name
   * a new favorite location is created at the clicked spot with the submitted name.
   */
  private LocationClickHandler clickListener = new LocationClickHandler(this);

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CoupleTones.global().inject(this);
    getMapAsync(this);

    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    // Register an observer that updates the map as the user moves
    proximityManager.register(location -> moveMap(location.getPosition()));
  }

  /**
   * onConnected
   *
   * @param bundle - Bundle
   */
  @Override
  public void onConnected(Bundle bundle) {
    Log.d(TAG, "Connected to Google API");
  }

  /**
   * onConnectionSuspended
   *
   * @param i - int
   */
  @Override
  public void onConnectionSuspended(int i) {
    Log.d(TAG, "Connection suspended");
  }

  /**
   * Manipulates the map once available.
   *
   * @param googleMap - Google Map
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    Log.d(TAG, "Map Ready");
    mMap = googleMap;
    mMap.getUiSettings().setZoomControlsEnabled(true);
    if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(
        this.getActivity(),
        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
        100
      );
      Log.e(TAG, "Location permission not granted");
      return;
    }

    this.populateMap();
    mMap.setOnMapClickListener(clickListener);
    mMap.setMyLocationEnabled(true);
  }

  /**
   * Centers the map. Must be called after map is ready.
   */
  public void centerMap() {
    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      Location lastKnownLocation = locationManager.getLastKnownLocation(GPS_PROVIDER);

      if (lastKnownLocation != null)
        moveMap(lastKnownLocation);
      else
        Log.d(getTag(), "Last known location is null");

      // Request a single publish to move the map to appropriate location
      locationManager.requestSingleUpdate(GPS_PROVIDER, new LocationListener() {

          /**
           * onLocationChanged
           *
           * @param location - changed location
           */
          @Override
          public void onLocationChanged(Location location) {
            Log.d(getTag(), "Got single publish");
            moveMap(location);
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
        },
        null);
    } else {
      Log.e(getTag(), "Invalid location permission");
    }
  }


  /**
   * @param user true if the map represents the users data, false if the map represents partner data.
   */
  public void setIsUser(boolean user)
  {
    this.isUser = user;
    clickListener.setIsDisabled(!user);
  }

  /**
   * Moves the map to a given location. Must be called after map is ready.
   *
   * @param loc - The location to move to
   */
  public void moveMap(android.location.Location loc) {
    moveMap(new LatLng(loc.getLatitude(), loc.getLongitude()));
  }

  /**
   * Moves the map to a given latlong position. Must be called after map is ready.
   *
   * @param position - The position to move to
   */
  public void moveMap(LatLng position) {
    Log.d(TAG, "Moving map to: " + position);
    if (mMap != null) {
      CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 15);
      mMap.moveCamera(update);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mMap != null)
      populateMap();
  }

  /**
   * Draws all of the favorited locations as markers on the map.
   */
  public void populateMap() {
    User user;
    if (isUser) user = app.getLocalUser();
    else user = app.getLocalUser().getPartner();
    mMap.clear();
    MarkerOptions markerSettings = new MarkerOptions();
    markerSettings.draggable(false);
    for (group50.coupletones.controller.tab.favoritelocations.map.location.Location i : user.getFavoriteLocations()) {
      markerSettings.position(i.getPosition());
      markerSettings.title(i.getName());
      mMap.addMarker(markerSettings);
    }
  }
}
