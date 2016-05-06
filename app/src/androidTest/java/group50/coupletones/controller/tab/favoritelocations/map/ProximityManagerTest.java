package group50.coupletones.controller.tab.favoritelocations.map;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.MockLocalUser;
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

  @Test
  public void testLocationListener() {
    List<FavoriteLocation> locations = new ArrayList<>();
    locations.add(0, new FavoriteLocation("loc1", new LatLng(32.880315, -117.236288)));
    locations.add(1, new FavoriteLocation("loc2", new LatLng(-20, -20)));

    // Stub the user
    when(CoupleTones.component().app().getLocalUser())
      .thenReturn(new MockLocalUser());

    MapProximityManager proximity = (MapProximityManager) CoupleTones.component().proximity();

    //TODO: Organize this test into edge case, normal case, error case
    List<android.location.Location> list = new LinkedList<>();
    for (int i = 0; i < 8; i++) {
      list.add(new Location(""));
    }
    list.get(0).setLatitude(32.880351);
    list.get(0).setLongitude(-117.236578);
    list.get(1).setLatitude(32.880292);
    list.get(1).setLongitude(-117.237329);
    list.get(2).setLatitude(32.880233);
    list.get(2).setLongitude(-117.238316);

    for (int i = 0; i < 8; i++) {
      // Simulate location change
      proximity.onLocationChanged(list.get(i));
    }

    verify(proximity, times(2)).onEnterLocation(locations.get(0));
    verify(proximity, times(0)).onEnterLocation(locations.get(1));
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
