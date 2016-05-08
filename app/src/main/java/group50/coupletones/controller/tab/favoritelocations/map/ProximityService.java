package group50.coupletones.controller.tab.favoritelocations.map;

import android.app.IntentService;
import android.content.Intent;
import android.location.LocationManager;
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
public class ProximityService extends IntentService implements Taggable {
  @Inject
  public ProximityManager listener;
  private LocationManager locationManager;

  public ProximityService() {
    super("ProximityService");
  }

  @Override
  public void onCreate() {
    super.onCreate();
    CoupleTones.component().inject(this);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Log.d(getTag(), "Handled intent!");
  }


/*
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(getTag(), "Start Service");
    // Register the proximity manager with Android LocationManager
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      Log.d(getTag(), "Registered listener: " + listener);
      locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, (MapProximityManager) listener);
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, (MapProximityManager) listener);
    } else {
      //TODO: Handle when location permission is not provided.
      Log.e(getTag(), "Location permission not granted");
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
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      locationManager.removeUpdates((MapProximityManager) listener);
    }
  }*/
}
