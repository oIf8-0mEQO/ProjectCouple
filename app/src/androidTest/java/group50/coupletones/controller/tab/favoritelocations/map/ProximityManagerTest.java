package group50.coupletones.controller.tab.favoritelocations.map;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.MockLocalUser;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author Joseph
 * @since 5/28/2016.
 */
public class ProximityManagerTest {

  FavoriteLocation favLocation;
  Location loc;
  MapProximityManagerMock proximity;
  ProximityObserver mock;

  @Before
  public void setup() {
    // Setup DI
    CoupleTones.setComponent(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );

    favLocation = new FavoriteLocation("", new LatLng(32.880315, -117.236288));
    loc = new Location("");

    // Stub the user
    CoupleTones.component().app().setLocalUser(new MockLocalUser());

    proximity = new MapProximityManagerMock();
    mock = mock(ProximityObserver.class);
    proximity.register(mock);
    proximity.addFavoriteLocation(favLocation);
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
    verify(mock, never()).onEnterLocation(anyObject());
  }

  @Test
  public void testNotificationNearby() {
    //Normal case - should pass
    loc.setLatitude(32.880351);
    loc.setLongitude(-117.236578);
    proximity.onLocationChanged(loc);
    verify(mock).onEnterLocation(anyObject());
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
    MapProximityManager proximity = new MapProximityManager();
    proximity.register(observer);
    FavoriteLocation shouldNotifyOnce = new FavoriteLocation();
    proximity.onEnterLocation(shouldNotifyOnce);
    proximity.onEnterLocation(shouldNotifyOnce);
  }
}
