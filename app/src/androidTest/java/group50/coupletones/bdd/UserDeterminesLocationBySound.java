package group50.coupletones.bdd;

import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.controller.tab.favoritelocations.map.MapProximityManager;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Before;

import java.util.LinkedList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 * Created by Joseph on 6/1/2016.
 */
public class UserDeterminesLocationBySound {

  private LinkedList<FavoriteLocation> list;
  private FavoriteLocation location;
  private VibeTone tone;
  private UserTestUtil testUtil;
  private MapProximityManager proximityManager;

  @Before
  public void setup() {
    tone = mock(VibeTone.class);
    list = new LinkedList<>();
    location = new FavoriteLocation("test location", new LatLng(10, 10), 0, tone.getIndex());
    list.add(location);

    testUtil.injectLocalUser();
    testUtil.mockFavoriteLocations(() -> list);
    testUtil.injectSpyPartner();

    proximityManager = spy(MapProximityManager.class);
  }
}
