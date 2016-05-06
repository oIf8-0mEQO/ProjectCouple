package group50.coupletones.controller.tab.favoritelocations.map;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import group50.coupletones.CoupleTones;

import javax.inject.Inject;

/**
 * A background service that handles location updates.
 *
 * @author Henry Mao
 * @since 5/6/16
 */
public class LocationService extends Service {
  @Inject
  public MapProximityManager listener;
  private LocationManager locationManager;

  public static Thread performOnBackgroundThread(final Runnable runnable) {
    final Thread t = new Thread() {
      @Override
      public void run() {
        try {
          runnable.run();
        } finally {

        }
      }
    };
    t.start();
    return t;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    CoupleTones.component().inject(this);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    // Register the proximity manager with Android LocationManager
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
    } else {
      //TODO: Handle when location permission is not provided.
    }

    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onDestroy() {
    // handler.removeCallbacks(sendUpdatesToUI);
    super.onDestroy();
    Log.v("STOP_SERVICE", "DONE");
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      locationManager.removeUpdates(listener);
    }
  }


}
