package group50.coupletones.controller.tab.favoritelocations.map;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.controller.tab.favoritelocations.map.location.UserFavoriteLocation;

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
public class UserFavoriteLocationTest {

  @Test
  public void testCoolDown()
  {
    UserFavoriteLocation location1 = new UserFavoriteLocation("", null, 0);
    assert (!location1.isOnCooldown());
    UserFavoriteLocation location2 = new UserFavoriteLocation("", null, System.currentTimeMillis());
    assert (location2.isOnCooldown());
  }

}
