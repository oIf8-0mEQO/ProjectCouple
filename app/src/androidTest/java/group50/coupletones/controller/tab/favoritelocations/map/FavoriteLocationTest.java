package group50.coupletones.controller.tab.favoritelocations.map;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import org.junit.Test;

/**
 * @author Joseph Cox
 * @since 5/7/16
 */

/**
 * Test for Favorite locations
 */
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
