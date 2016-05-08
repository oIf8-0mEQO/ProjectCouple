package group50.coupletones.controller.tab.favoritelocations.map;

import org.junit.Test;

/**
 * Created by Joseph on 5/7/2016.
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
