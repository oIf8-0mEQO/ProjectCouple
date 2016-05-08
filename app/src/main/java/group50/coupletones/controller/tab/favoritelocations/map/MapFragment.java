package group50.coupletones.controller.tab.favoritelocations.map;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import group50.coupletones.CoupleTones;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.Location;

import javax.inject.Inject;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {

  public static final float PROXIMITY_RADIUS_METERS = 160.934f;

  public static final String TAG = "MapFragment";

  @Inject
  public CoupleTones app;

  @Inject
  public ProximityManager proximityManager;

  private GoogleApiClient apiClient;

  private GoogleMap mMap;

  private LocationManager locationManager;

  /**
   * Creates a OnMapClickListener that opens a dialog box asking the user for a name of a favorite location. When the user accepts the name
   * a new favorite location is created at the clicked spot with the submitted name.
   */
  private GoogleMap.OnMapClickListener clickListener = new LocationClickHandler(this);

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CoupleTones.global().inject(this);
    getMapAsync(this);

    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    apiClient = new GoogleApiClient.Builder(getActivity())
      .addApi(LocationServices.API)
      .addConnectionCallbacks(this)
      .build();

    // Register an observer that updates the map as the user moves
    proximityManager.register(location -> moveMap(location.getPosition()));
  }

  public void registerProximity(Location location) {
    Intent intent = new Intent(getContext(), ProximityService.class);
    intent.putExtra("lat", location.getPosition().latitude);
    intent.putExtra("long", location.getPosition().longitude);

    PendingIntent pendingIntent = PendingIntent.getActivity(
      getContext(),
      0 /* Request code */,
      intent,
      PendingIntent.FLAG_ONE_SHOT
    );

    // Add a proximity alert
    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      locationManager.addProximityAlert(
        location.getPosition().latitude,
        location.getPosition().longitude,
        PROXIMITY_RADIUS_METERS,
        -1,
        pendingIntent
      );
      Log.d(TAG, "Registered proximity alert");
    } else {
      Log.e(TAG, "Location permission not granted");
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    apiClient.connect();
  }

  @Override
  public void onStop() {
    super.onStop();
    apiClient.disconnect();
  }

  @Override
  public void onConnected(Bundle bundle) {
    Log.d(TAG, "Connected to Google API");
    LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, LocationRequest.create(), proximityManager);
  }

  @Override
  public void onConnectionSuspended(int i) {
    Log.d(TAG, "Connection suspended");
  }

  /**
   * Manipulates the map once available.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    Log.d(TAG, "Map Ready");
    mMap = googleMap;
    mMap.getUiSettings().setZoomControlsEnabled(true);
    if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(
        this.getActivity(),
        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
        100
      );
      Log.e(TAG, "Location permission not granted");
      return;
    }

    this.populateMap();
    mMap.setOnMapClickListener(clickListener);
    mMap.setMyLocationEnabled(true);
  }

  @Override
  public void onResume() {
    super.onResume();
    centerMap();
  }

  /**
   * Centers the map
   */
  public void centerMap() {
    moveMap(LocationServices.FusedLocationApi.getLastLocation(apiClient));
  }

  /**
   * Moves the map to a given location
   * @param loc The location to move to
   */
  public void moveMap(android.location.Location loc) {
    if (loc != null) {
      moveMap(new LatLng(loc.getLatitude(), loc.getLongitude()));
    }
  }

  /**
   * Moves the map to a given latlong position
   * @param position The position to move to
   */
  public void moveMap(LatLng position) {
    Log.d(TAG, "Moving map to: " + position);
    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 15);
    mMap.moveCamera(update);
  }

  /**
   * Draws all of the favorited locations as markers on the map.
   */
  public void populateMap() {
    mMap.clear();
    MarkerOptions markerSettings = new MarkerOptions();
    markerSettings.draggable(false);
    for (FavoriteLocation i : app.getLocalUser().getFavoriteLocations()) {
      markerSettings.position(i.getPosition());
      markerSettings.title(i.getName());
      mMap.addMarker(markerSettings);
      //TODO: Remove. This should be done in app initialization
      registerProximity(i);
    }
  }


  /*public List<Address> search(String nameLocation) {
    try {
     List<Address> locations = addressProvider.getFromLocationName(nameLocation, 10);
      if (locations.size() == 0) {
        return locations;
      }
      CameraUpdate update = CameraUpdateFactory.newLatLng(new LatLng(locations.get(0).getLatitude(), locations.get(0).getLongitude()));
      mMap.moveCamera(update);
      return locations;
    } catch (IOException e) {
      return null;
      //TODO: write exception handling code
    }
  }*/

}
