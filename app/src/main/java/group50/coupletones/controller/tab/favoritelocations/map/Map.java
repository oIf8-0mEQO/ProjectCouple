package group50.coupletones.controller.tab.favoritelocations.map;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.util.storage.Storage;

import javax.inject.Inject;

public class Map extends SupportMapFragment implements OnMapReadyCallback {

  @Inject
  public CoupleTones app;

  @Inject
  public ProximityManager proximityManager;

  private GoogleMap mMap;

  /**
   * Creates a OnMapClickListener that opens a dialog box asking the user for a name of a favorite location. When the user accepts the name
   * a new favorite location is created at the clicked spot with the submitted name.
   */
  private GoogleMap.OnMapClickListener clickListener = new GoogleMap.OnMapClickListener() {
    @Override
    public void onMapClick(LatLng latLng) {
      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
      builder.setTitle(R.string.location_name_box);
      final EditText input = new EditText(getContext());
      input.setInputType(InputType.TYPE_CLASS_TEXT);
      builder.setView(input);
      builder.setPositiveButton(R.string.location_name_accept, (dialog, which) -> {
        // Save the favorite location
          String name = input.getText().toString();
          FavoriteLocation clickedLocation = new FavoriteLocation(name, latLng);
          app.getLocalUser().getFavoriteLocations().add(clickedLocation);
        app.getLocalUser().save(new Storage(getActivity().getSharedPreferences(Storage.PREF_FILE_USER, Context.MODE_PRIVATE)));

        // Move the camera
          CameraUpdate update = CameraUpdateFactory.newLatLng(clickedLocation.getPosition());
          mMap.moveCamera(update);
          populateMap();
        }
      );
      builder.show();
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
  }


  /**
   * Manipulates the map once available.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.getUiSettings().setZoomControlsEnabled(true);
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
    this.populateMap();
    mMap.setOnMapClickListener(clickListener);
    mMap.setMyLocationEnabled(true);
    CameraUpdate initial = CameraUpdateFactory.newLatLngZoom(new LatLng(32.880234, -117.236106), 15);
    mMap.moveCamera(initial);

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


}
