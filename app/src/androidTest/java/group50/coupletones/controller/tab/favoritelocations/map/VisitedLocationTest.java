package group50.coupletones.controller.tab.favoritelocations.map;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.Date;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

/**
 * @author Joseph Cox
 * @since 5/8/2016
 */

/**
 * Test for Visited Locations
 */
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
