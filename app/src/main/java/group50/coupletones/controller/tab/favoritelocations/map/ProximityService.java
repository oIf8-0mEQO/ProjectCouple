package group50.coupletones.controller.tab.favoritelocations.map;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import group50.coupletones.CoupleTones;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;

/**
 * A background service that handles location updates.
 *
 * @author Henry Mao
 * @since 5/6/16
 */
public class ProximityService extends Service implements Taggable {

  private static final int REQUEST_RESOLVE = 1001;

  @Inject
  public ProximityManager listener;
  private LocationManager locationManager;

  @Override
  public void onCreate() {
    super.onCreate();
    CoupleTones.global().inject(this);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(getTag(), "Start Service");

    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      Log.d(getTag(), "Request location updates");
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
    } else {
      Log.e(getTag(), "Invalid location permission");
    }
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(getTag(), "End Service");
    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      locationManager.removeUpdates(listener);
    } else {
      Log.e(getTag(), "Invalid location permission");
    }
  }
}
