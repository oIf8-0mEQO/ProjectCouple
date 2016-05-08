package group50.coupletones.controller.tab.favoritelocations.map;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;

import group50.coupletones.BuildConfig;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.MockLocalUser;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Joseph
 * @since 5/28/2016.
 */
public class ProximityManagerTest {

  FavoriteLocation favLocation;
  Location loc;
  MapProximityManager proximity;
  ProximityObserver mock;

  @Before
  public void setup()
  {
    LinkedList<FavoriteLocation> list = new LinkedList<>();
    list.add(new FavoriteLocation("", new LatLng(32.880315, -117.236288)));
    loc = new Location("");

    // Stub the user
    CoupleTones.component().app().setLocalUser(new MockLocalUser());
    when(CoupleTones.component().app().getLocalUser().getFavoriteLocations()).thenReturn(list);

    proximity = new MapProximityManager();
    mock = mock(ProximityObserver.class);
    proximity.register(mock);
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
    MapProximityManager proximity = (MapProximityManager) CoupleTones.component().proximity();
    proximity.register(observer);
    FavoriteLocation shouldNotifyOnce = new FavoriteLocation();
    proximity.onEnterLocation(shouldNotifyOnce);
    proximity.onEnterLocation(shouldNotifyOnce);
  }
}
