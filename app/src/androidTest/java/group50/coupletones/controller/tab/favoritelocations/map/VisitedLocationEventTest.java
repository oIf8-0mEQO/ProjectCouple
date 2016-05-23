package group50.coupletones.controller.tab.favoritelocations.map;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.util.sound.VibeTone;

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
public class VisitedLocationEventTest {

  @Test
  public void testVisitedLocationCreation()
  {
    FavoriteLocation favLocation = new FavoriteLocation("Name", new LatLng(10, 15), 100, VibeTone.getTone());
    VisitedLocationEvent visLocation = new VisitedLocationEvent(favLocation, new Date());
    VisitedLocationEvent mock = mock(VisitedLocationEvent.class);
    when(mock.getPosition()).thenReturn(new LatLng(10, 15));
    when(mock.getName()).thenReturn("Name");
    assert (visLocation.equals(mock));
  }

  @Test
  public void testEquality()
  {
    VisitedLocationEvent locRef = new VisitedLocationEvent("name", new LatLng(10, 10), new Date(100), VibeTone.getTone());
    VisitedLocationEvent locTest1 = new VisitedLocationEvent("name", new LatLng(10, 10), new Date(100), VibeTone.getTone());
    VisitedLocationEvent locTest2 = new VisitedLocationEvent("name", new LatLng(10, 10), new Date(250), VibeTone.getTone());
    assert (locRef.equals(locTest1));
    assert (!locRef.equals(locTest2));
  }

}
