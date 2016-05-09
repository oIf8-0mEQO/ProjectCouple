package group50.coupletones.controller.tab.favoritelocations.map;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocation;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Joseph Cox
 * @since 5/8/2016
 */

/**
 * Test for Visited Locations
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class VisitedLocationTest {

  @Test
  public void testVisitedLocationCreation()
  {
    FavoriteLocation favLocation = new FavoriteLocation("Name", new LatLng(10, 15), 100);
    VisitedLocation visLocation = new VisitedLocation(favLocation, new Date());
    VisitedLocation mock = mock(VisitedLocation.class);
    when(mock.getPosition()).thenReturn(new LatLng(10, 15));
    when(mock.getName()).thenReturn("Name");
    assert (visLocation.equals(mock));
  }

}
