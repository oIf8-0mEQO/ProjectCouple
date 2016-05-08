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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import group50.coupletones.CoupleTones;

import javax.inject.Inject;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

  public static final float PROXIMITY_RADIUS_METERS = 160.934f;

  public static final String TAG = "MapFragment";

  @Inject
  public CoupleTones app;

  @Inject
  public ProximityManager proximityManager;

  private GoogleMap mMap;

  private LocationManager locationManager;

  /**
   * Creates a OnMapClickListener that opens a dialog box asking the user for a name of a favorite location. When the user accepts the name
   * a new favorite location is created at the clicked spot with the submitted name.
   */
  private GoogleMap.OnMapClickListener clickListener = new LocationClickHandler(this);

  /**
   * Use this factory method to create a new instance of MapFragment.
   */
  public static MapFragment build() {
    return new MapFragment();
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
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CoupleTones.component().inject(this);
    getMapAsync(this);

    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
    Log.d(TAG, "Resume");
    centerMap();
  }

  /**
   * Centers the map
   */
  public void centerMap() {
    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      android.location.Location lastLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      if (lastLoc != null) {
        CameraUpdate initial = CameraUpdateFactory.newLatLngZoom(new LatLng(lastLoc.getLatitude(), lastLoc.getLongitude()), 15);
        mMap.moveCamera(initial);
      }
    } else {
      Log.e(TAG, "Location permission not granted");
    }
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

  public void moveMap(LatLng position) {
    CameraUpdate update = CameraUpdateFactory.newLatLng(position);
    mMap.moveCamera(update);
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
