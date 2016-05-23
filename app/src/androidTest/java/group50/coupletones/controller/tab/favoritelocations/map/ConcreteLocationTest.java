package group50.coupletones.controller.tab.favoritelocations.map;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import group50.coupletones.controller.tab.favoritelocations.map.location.ConcreteLocation;

/**
 * Created by Joseph on 5/22/2016.
 */
public class ConcreteLocationTest
{

  ConcreteLocation locRef;
  ConcreteLocation locTest1;
  ConcreteLocation locTest2;
  ConcreteLocation locTest3;
  ConcreteLocation locTest4;

  @Before
  public void setup()
  {
    locRef = new ConcreteLocation("name", new LatLng(10, 10));
    locTest1 = new ConcreteLocation("name", new LatLng(10, 10));//Should be equal
    locTest2 = new ConcreteLocation("", new LatLng(10, 10));//Not equal
    locTest3 = new ConcreteLocation("name", new LatLng(110, 110));//Not equal
    locTest4 = new ConcreteLocation("", new LatLng(110, 110));//Not equal
  }

  @Test
  public void testEquality()
  {
    assert (locRef.equals(locTest1));
    assert (!locRef.equals(locTest2));
    assert (!locRef.equals(locTest3));
    assert (!locRef.equals(locTest4));
  }

}
