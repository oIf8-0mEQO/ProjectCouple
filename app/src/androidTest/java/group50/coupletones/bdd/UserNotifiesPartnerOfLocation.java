package group50.coupletones.bdd;

import android.location.LocationManager;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.MapProximityManager;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityManager;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityNetworkHandler;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * BDD style test for user notifies partner of location
 *
 * @author Henry Mao
 * @since 5/8/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserNotifiesPartnerOfLocation {

  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
  private CoupleTones app;
  private LocalUser mockUser;
  private ProximityManager proximityManager;
  private LatLng zoneLatLng = new LatLng(32.882, -117.233);
  private FavoriteLocation zone = new FavoriteLocation("Home", zoneLatLng, 0, VibeTone.getTone());

  @Before
  public void setup() {
    // Mock DI
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );

    // Mock the user
    mockUser = mock(LocalUser.class);
    app = CoupleTones.global().app();

    // Create an instance of MapProxManager and register a network handler
    proximityManager = new MapProximityManager(app);
    proximityManager.register(new ProximityNetworkHandler(app, CoupleTones.global().network()));

    //List of the user's favorite locations.
    List<FavoriteLocation> list = new LinkedList<>();
    list.add(zone);

    when(app.isLoggedIn()).thenReturn(true);
    when(app.getLocalUser()).thenReturn(mockUser);
    when(mockUser.getFavoriteLocations()).thenReturn(list);
    User mock = mock(User.class);
    when(mock.getName()).thenReturn("Henry");
    when(mock.getEmail()).thenReturn("henry@email.com");
    when(mockUser.getPartner()).thenReturn(mock);

  }

  private android.location.Location givenIWasNotPreviousInTheZone() {
    android.location.Location mockLocation = new android.location.Location(LocationManager.GPS_PROVIDER);
    mockLocation.setLatitude(30);
    mockLocation.setLongitude(117);
    mockLocation.setAltitude(0);
    mockLocation.setAccuracy(1);
    mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
    mockLocation.setTime(System.currentTimeMillis());
    proximityManager.onLocationChanged(mockLocation);
    return mockLocation;
  }

  private android.location.Location whenIEnterTheZone() {
    android.location.Location mockLocation = new android.location.Location(LocationManager.GPS_PROVIDER);
    mockLocation.setLatitude(zoneLatLng.latitude);
    mockLocation.setLongitude(zoneLatLng.longitude);
    mockLocation.setAltitude(0);
    mockLocation.setAccuracy(1);
    mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
    mockLocation.setTime(System.currentTimeMillis());
    proximityManager.onLocationChanged(mockLocation);
    return mockLocation;
  }

  private void thenMyPartnerIsNotified() {
    // Check message sent to server
    verify(CoupleTones.global().network(), times(1)).send(any());
  }

  @Test
  public void userEntersZone() throws Exception {
    givenIWasNotPreviousInTheZone();
    whenIEnterTheZone();
    thenMyPartnerIsNotified();
  }
}
