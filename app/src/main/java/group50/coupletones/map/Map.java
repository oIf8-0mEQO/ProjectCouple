package group50.coupletones.map;

import android.Manifest;
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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import group50.coupletones.R;
import group50.coupletones.map.FavoriteLocation;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<FavoriteLocation> favLocations = new LinkedList<FavoriteLocation>();
    private Geocoder geocoder = new Geocoder(this);
    private ProximityHandler proximityHandler = new NearbyLocationHandler();

    GoogleMap.OnMapClickListener clickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            String name = mockMethod2();//TODO: properly implement this method call
            favLocations.add(new FavoriteLocation(name, latLng));
            populateMap();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
            Log.d("test1", "ins");
            return;
        }else if(mMap != null) {
            Log.d("test2", "outs");
            mMap.setMyLocationEnabled(true);
        }
        locationManager.requestLocationUpdates(locationProvider, 0, 0, new MovementListener(proximityHandler, favLocations));//TODO: create actual ProximityHandler class
        this.populateMap();
        mMap.setOnMapClickListener(clickListener);
    }

    public void registerNotificationObserver(NotificationObserver observer)
    {
        proximityHandler.register(observer);
    }

    /**Draws all of the favorited locations as markers on the map.*/
    private void populateMap()
    {
        mMap.clear();
        MarkerOptions markerSettings = new MarkerOptions();
        markerSettings.draggable(false);
        for (FavoriteLocation i : favLocations)
        {
            markerSettings.position(i.getPosition());
            markerSettings.title(i.getName());
            mMap.addMarker(markerSettings);
        }
    }

    public void search(String nameLocation)
    {
        try
        {
            List<Address> locations = geocoder.getFromLocationName(nameLocation, 10);
            Address pickedAddress = mockMethod1(locations);//TODO: properly implement this method
            FavoriteLocation newLocation = new FavoriteLocation(pickedAddress.getAddressLine(0), new LatLng(pickedAddress.getLatitude(), pickedAddress.getLongitude()));
            favLocations.add(newLocation);
            CameraUpdate update = CameraUpdateFactory.newLatLng(newLocation.getPosition());
            mMap.moveCamera(update);
        }
        catch(IOException e)
        {
            //TODO: write exception handling code
        }
    }

    //Mock Methods//
    private Address mockMethod1(List<Address> list)
    {
        return list.get(0);
    }
    private String mockMethod2()
    {
        return "test name";
    }

}
