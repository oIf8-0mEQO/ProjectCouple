package group50.coupletones.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import group50.coupletones.R;

public class MovementListener implements LocationListener {

    private ProximityHandler handler;
    private List<FavoriteLocation> locations;


    public MovementListener(ProximityHandler handler, List<FavoriteLocation> locations)
    {
        this.handler = handler;
        this.locations = locations;
    }

    @Override
    public void onLocationChanged(Location location) {
        for (FavoriteLocation i : locations)
        {
            if (distanceInMiles(i.getPosition(), new LatLng(location.getLatitude(), location.getLongitude())) < 0.1)
            {
                handler.onNearby(i);
            }
        }
    }

    @Override
    public void onProviderDisabled (String provider) {}

    @Override
    public void onProviderEnabled (String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extra) {}

    /**Finds the distance in miles between two locations given by the gps.*/
    double distanceInMiles(LatLng location1, LatLng location2)
    {
        double distLatKilometers = (location1.latitude - location2.latitude)/110.54;
        double distLongKilometers = (location1.longitude - location2.longitude)/(111.32*Math.cos(location1.latitude - location2.latitude));
        double distKilometers = Math.sqrt(Math.pow(distLatKilometers, 2) + Math.pow(distLongKilometers, 2));
        double distMiles = 0.621371*distKilometers;
        return distMiles;
    }

}
