package group50.coupletones.controller.tab.favoritelocations.map;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

/**
 * Created by Joseph on 5/8/2016.
 */
public class MapFragmentTest {

  @Test
  public void testMapMovement()
  {
    MapFragment map = new MapFragment();
    map.moveMap(new LatLng(10, 15));
    assert(map.getMap().getCameraPosition().equals(new LatLng(10, 15)));
  }

}
