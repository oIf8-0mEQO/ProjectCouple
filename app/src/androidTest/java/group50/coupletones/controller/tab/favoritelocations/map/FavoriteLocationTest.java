package group50.coupletones.controller.tab.favoritelocations.map;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joseph Cox
 * @since 5/7/16
 */

/**
 * Test for Favorite locations
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FavoriteLocationTest {

  FavoriteLocation original;

  @Before
  public void setup() {
    original = new FavoriteLocation("name", new LatLng(10, 10), 50, VibeTone.getTone());
  }

  @Test
  public void testCoolDown() {
    FavoriteLocation location1 = new FavoriteLocation("", new LatLng(0, 0), 0, VibeTone.getTone());
    assert (!location1.isOnCooldown());
    FavoriteLocation location2 = new FavoriteLocation("", new LatLng(0, 0), System.currentTimeMillis(), VibeTone.getTone());
    assert (location2.isOnCooldown());
  }

  @Test
  public void testChangeName() {
    FavoriteLocation testLoc = new FavoriteLocation(original, "name2");
    FavoriteLocation refLoc = new FavoriteLocation("name2", new LatLng(10, 10), 50, VibeTone.getTone());
    assert (testLoc.equals(refLoc));
  }

  @Test
  public void testChangePosition() {
    FavoriteLocation testLoc = new FavoriteLocation(original, new LatLng(15, 15));
    FavoriteLocation refLoc = new FavoriteLocation("name2", new LatLng(15, 15), 50, VibeTone.getTone());
    assert (testLoc.equals(refLoc));
  }

  @Test
  public void testChangeTime() {
    FavoriteLocation testLoc = new FavoriteLocation(original, 100);
    FavoriteLocation refLoc = new FavoriteLocation("name2", new LatLng(10, 10), 100, VibeTone.getTone());
    assert (testLoc.equals(refLoc));
  }

}
