package group50.coupletones.map;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.ContextWrapper;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.pm.PackageManager;
import android.content.Context;

import com.google.android.gms.fitness.data.Application;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.GoogleUser;
import group50.coupletones.map.FavoriteLocation;

public class Map extends SupportMapFragment implements OnMapReadyCallback {

  @Inject
  public CoupleTones app;

  private GoogleMap mMap;
  //private Geocoder geocoder;
  private ProximityHandler proximityHandler = new NearbyLocationHandler();

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

  GoogleMap.OnMapClickListener clickListener = new GoogleMap.OnMapClickListener() {
    @Override
    public void onMapClick(LatLng latLng) {
      String name = mockMethod2();//TODO: properly implement this method call
      FavoriteLocation clickedLocation = new FavoriteLocation(name, latLng);
      app.getLocalUser().getFavoriteLocations().add(clickedLocation);
      CameraUpdate update = CameraUpdateFactory.newLatLng(clickedLocation.getPosition());
      mMap.moveCamera(update);
      populateMap();
    }
  };

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
    locationManager.requestLocationUpdates(locationProvider, 0, 0, new MovementListener(proximityHandler, app.getLocalUser().getFavoriteLocations()));
    this.populateMap();
    mMap.setOnMapClickListener(clickListener);
  }

  public void registerNotificationObserver(NotificationObserver observer) {
    proximityHandler.register(observer);
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
