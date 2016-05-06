package group50.coupletones.map;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;

import javax.inject.Inject;

public class Map extends SupportMapFragment implements OnMapReadyCallback {

  @Inject
  public CoupleTones app;

  @Inject
  public ProximityManager proximityManager;

  private GoogleMap mMap;

  private GoogleMap.OnMapClickListener clickListener = new GoogleMap.OnMapClickListener() {
    @Override
    public void onMapClick(LatLng latLng) {
      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
      builder.setTitle(R.string.location_name_box);
      String name = mockMethod2();//TODO: properly implement this method call
      FavoriteLocation clickedLocation = new FavoriteLocation(name, latLng);
      app.getLocalUser().getFavoriteLocations().add(clickedLocation);
      CameraUpdate update = CameraUpdateFactory.newLatLng(clickedLocation.getPosition());
      mMap.moveCamera(update);
      populateMap();
    }
  };

  /**
   * Use this factory method to create a new instance of Map.
   */
  public static Map build() {
    Map fragment = new Map();
    Bundle args = new Bundle();
    // TODO: Set arguments
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CoupleTones.component().inject(this);
    getMapAsync(this);
    //geocoder = new Geocoder(getActivity().getApplicationContext());
  }


  /**
   * Manipulates the map once available.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.getUiSettings().setZoomControlsEnabled(true);
    LocationManager locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
    String locationProvider = LocationManager.GPS_PROVIDER;
    if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this.getActivity(),
        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
        100);
      Log.d("test1", "ins");
      return;
    } else if (mMap != null) {
      Log.d("test2", "outs");
      mMap.setMyLocationEnabled(true);
    }
    locationManager.requestLocationUpdates(locationProvider, 0, 0, new MovementListener(proximityManager, app.getLocalUser().getFavoriteLocations()));
    this.populateMap();
    mMap.setOnMapClickListener(clickListener);
  }

  public void registerNotificationObserver(ProximityObserver observer) {
    proximityManager.register(observer);
  }

  /**
   * Draws all of the favorited locations as markers on the map.
   */
  private void populateMap() {
    mMap.clear();
    MarkerOptions markerSettings = new MarkerOptions();
    markerSettings.draggable(false);
    for (FavoriteLocation i : app.getLocalUser().getFavoriteLocations()) {
      markerSettings.position(i.getPosition());
      markerSettings.title(i.getName());
      mMap.addMarker(markerSettings);
    }
  }

  /*public List<Address> search(String nameLocation) {
    try {
     List<Address> locations = geocoder.getFromLocationName(nameLocation, 10);
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

  public void addLocation(FavoriteLocation location) {
    app.getLocalUser().getFavoriteLocations().add(location);
    CameraUpdate update = CameraUpdateFactory.newLatLng(location.getPosition());
    mMap.moveCamera(update);
  }


  private String mockMethod2() {
    return "test name";
  }

}
