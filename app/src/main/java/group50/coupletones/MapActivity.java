package group50.coupletones;

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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import group50.coupletones.util.FavoriteLocation;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<FavoriteLocation> favLocations = new LinkedList<FavoriteLocation>();
    private Geocoder geocoder = new Geocoder(this);

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            for (FavoriteLocation i : favLocations)
            {
                if (distanceInMiles(i.getPosition(), new LatLng(location.getLatitude(), location.getLongitude())) < 0.1)
                {
                    System.out.println(i);//TODO: properly implement this method call
                }
            }
        }
        @Override
        public void onProviderDisabled (String provider) {}
        @Override
        public void onProviderEnabled (String provider) {}
        @Override
        public void onStatusChanged(String provider, int status, Bundle extra) {}
    };

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
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        this.populateMap();
        mMap.setOnMapClickListener(clickListener);
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

    /**Finds the distance in miles between two locations given by the gps.*/
    double distanceInMiles(LatLng location1, LatLng location2)
    {
        double distLatKilometers = (location1.latitude - location2.latitude)/110.54;
        double distLongKilometers = (location1.longitude - location2.longitude)/(111.32*Math.cos(location1.latitude - location2.latitude));
        double distKilometers = Math.sqrt(Math.pow(distLatKilometers, 2) + Math.pow(distLongKilometers, 2));
        double distMiles = 0.621371*distKilometers;
        return distMiles;
    }


    public void search(String nameLocation)
    {
        try
        {
            List<Address> locations = geocoder.getFromLocationName(nameLocation, 10);
            Address pickedAddress = mockMethod1(locations);//TODO: properly implement this method
            favLocations.add(new Location(pickedAddress.getAddressLine(0), new LatLong(pickedAddress.getLatitude(), pickedAddress.getLongitude())));
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
