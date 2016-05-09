package group50.coupletones.controller.tab.favoritelocations.map;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
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

  @Test
  public void testCoolDown()
  {
    FavoriteLocation location = new FavoriteLocation("", null, 0);
    assert (!location.isOnCooldown());
    location.setCooldown();
    assert (location.isOnCooldown());
  }

}
