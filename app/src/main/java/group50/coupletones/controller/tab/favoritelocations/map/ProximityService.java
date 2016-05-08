package group50.coupletones.controller.tab.favoritelocations.map;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import group50.coupletones.CoupleTones;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;

/**
 * A background service that handles location updates.
 * @author Henry Mao
 * @since 5/6/16
 */
public class ProximityService extends Service implements Taggable, GoogleApiClient.ConnectionCallbacks {
  @Inject
  public ProximityManager listener;
  private GoogleApiClient apiClient;

  @Override
  public void onCreate() {
    super.onCreate();
    CoupleTones.global().inject(this);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(getTag(), "Start Service");

    apiClient = new GoogleApiClient.Builder(this)
      .addApi(LocationServices.API)
      .addConnectionCallbacks(this)
      .build();

    apiClient.connect();
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onConnected(Bundle bundle) {
    Log.d(getTag(), "Requesting location updates");
    LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, LocationRequest.create(), listener);
  }

  @Override
  public void onConnectionSuspended(int i) {
    Log.d(getTag(), "Connection suspended");
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(getTag(), "End Service");
    LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, listener);
    apiClient.disconnect();
  }
}
