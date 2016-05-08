package group50.coupletones.controller.tab.favoritelocations.map;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocation;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Joseph
 * @since 5/28/2016.
 */
public class ProximityManagerTest {

  private FavoriteLocation favLocation;
  private Location loc;
  private MapProximityManager proximity;
  private ProximityObserver mockObserver;

  @Before
  public void setup() {
    // Setup DI
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );

    favLocation = new FavoriteLocation("", new LatLng(32.880315, -117.236288));
    loc = new Location("");

    // Stub the user
    LocalUser localUser = mock(LocalUser.class);
    // Make the user always return favLocation when getFavoriteLocations is called
    when(localUser.getFavoriteLocations()).thenReturn(Collections.singletonList(favLocation));

    // Stub getLocalUser to always return the mock user above
    when(CoupleTones.global().app().getLocalUser()).thenReturn(localUser);
    when(CoupleTones.global().app().isLoggedIn()).thenReturn(true);

    proximity = new MapProximityManager(CoupleTones.global().app());
    mockObserver = mock(ProximityObserver.class);
    proximity.register(mockObserver);
  }

  //TODO: Organize this test into edge case, normal case, error case
  //list.get(1).setLatitude(32.880292);
  //list.get(1).setLongitude(-117.237329);
  //list.get(2).setLatitude(32.880233);
  //list.get(2).setLongitude(-117.238316);

  @Test
  public void testLocationListenerFarAway() {
    //Normal case - should not pass
    loc.setLatitude(10.0);
    loc.setLongitude(10.0);
    proximity.onLocationChanged(loc);
    verify(mockObserver, never()).onEnterLocation(anyObject());
  }

  @Test
  public void testNotificationNearby() {
    //Normal case - should pass
    loc.setLatitude(32.880351);
    loc.setLongitude(-117.236578);
    proximity.onLocationChanged(loc);
    verify(mockObserver).onEnterLocation(anyObject());
  }

  @Test
  public void testNotificationTimer() {
    ProximityObserver observer = new ProximityObserver() {
      int count = 0;

      @Override
      public void onEnterLocation(VisitedLocation location) {
        assert (count == 0);
        count++;
      }
    };
    proximity.register(observer);
    FavoriteLocation shouldNotifyOnce = new FavoriteLocation();
    proximity.onEnterLocation(shouldNotifyOnce);
    proximity.onEnterLocation(shouldNotifyOnce);
  }
}
