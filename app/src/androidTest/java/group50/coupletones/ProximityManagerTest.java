package group50.coupletones;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.auth.User;
import group50.coupletones.map.FavoriteLocation;
import group50.coupletones.map.MapProximityManager;
import group50.coupletones.map.ProximityObserver;
import group50.coupletones.map.VisitedLocation;
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
    locations.add(0, new FavoriteLocation("loc1", new LatLng(60, 60)));
    locations.add(1, new FavoriteLocation("loc2", new LatLng(-20, -20)));

    // Stub the user
    when(CoupleTones.component().app().getLocalUser())
      .thenReturn(new User() {
        @Override
        public String getId() {
          return "mockuser";
        }

        @Override
        public String getName() {
          return "Mock User";
        }

        @Override
        public String getEmail() {
          return "mock@mock.com";
        }

        @Override
        public List<FavoriteLocation> getFavoriteLocations() {
          return locations;
        }
      });

    MapProximityManager proximity = (MapProximityManager) CoupleTones.component().proximity();

    //TODO: Organize this test into edge case, normal case, error case
    List<android.location.Location> list = new LinkedList<>();
    for (int i = 0; i < 8; i++) {
      list.add(new Location(""));
    }
    list.get(0).setLatitude(60.001);
    list.get(0).setLongitude(60.001);
    list.get(1).setLatitude(-60.001);
    list.get(1).setLongitude(60.001);
    list.get(2).setLatitude(60.001);
    list.get(2).setLongitude(-60.001);
    list.get(3).setLatitude(-60.001);
    list.get(3).setLongitude(-60.001);
    list.get(4).setLatitude(60.0015);
    list.get(4).setLongitude(60.0015);
    list.get(5).setLatitude(-60.0015);
    list.get(5).setLongitude(60.0015);
    list.get(6).setLatitude(60.0015);
    list.get(6).setLongitude(-60.0015);
    list.get(7).setLatitude(-60.0015);
    list.get(7).setLongitude(-60.0015);

    for (int i = 0; i < 8; i++) {
      // Simulate location change
      proximity.onLocationChanged(list.get(i));
    }

    verify(proximity, times(4)).onEnterLocation(locations.get(0));
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
