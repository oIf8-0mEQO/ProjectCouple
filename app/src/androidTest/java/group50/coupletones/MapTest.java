package group50.coupletones;

import android.location.Address;
import android.location.Location;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import org.junit.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import group50.coupletones.map.FavoriteLocation;
import group50.coupletones.map.Map;
import group50.coupletones.map.MovementListener;
import group50.coupletones.map.ProximityHandler;

/**
 * Created by Joseph on 5/28/2016.
 */
public class MapTest {

    private List<FavoriteLocation> locations;

    @Before
    public void setUp()
    {
        locations = new ArrayList<>();
        locations.add(new FavoriteLocation("loc1", new LatLng(0, 0)));
        locations.add(new FavoriteLocation("loc2", new LatLng(60, 60)));
        locations.add(new FavoriteLocation("loc3", new LatLng(-20, -20)));
    }

    @Test
    public void testLocationListener()
    {

        ProximityHandler mock = mock(ProximityHandler.class);
        MovementListener listener = new MovementListener(mock, locations);

        List<android.location.Location> list = new LinkedList<>();
        for (int i = 0; i < 18; i++)
        {
            list.add(new Location(""));
        }
        list.get(0).setLatitude(0.001);     list.get(0).setLongitude(0.001);
        list.get(1).setLatitude(-0.001);    list.get(0).setLongitude(0.001);
        list.get(2).setLatitude(0.001);     list.get(0).setLongitude(-0.001);
        list.get(3).setLatitude(-0.001);    list.get(0).setLongitude(-0.001);
        list.get(4).setLatitude(0);         list.get(0).setLongitude(0);
        list.get(5).setLatitude(0.0012);    list.get(0).setLongitude(0.0012);
        list.get(6).setLatitude(-0.0012);   list.get(0).setLongitude(0.0012);
        list.get(7).setLatitude(0.0012);    list.get(0).setLongitude(-0.0012);
        list.get(8).setLatitude(-0.0012);   list.get(0).setLongitude(-0.0012);
        list.get(9).setLatitude(60.001);    list.get(0).setLongitude(60.001);
        list.get(10).setLatitude(-60.001);  list.get(0).setLongitude(60.001);
        list.get(11).setLatitude(60.001);   list.get(0).setLongitude(-60.001);
        list.get(12).setLatitude(-60.001);  list.get(0).setLongitude(-60.001);
        list.get(14).setLatitude(60.0015);  list.get(0).setLongitude(60.0015);
        list.get(15).setLatitude(-60.0015); list.get(0).setLongitude(60.0015);
        list.get(16).setLatitude(60.0015);  list.get(0).setLongitude(-60.0015);
        list.get(17).setLatitude(-60.0015); list.get(0).setLongitude(-60.0015);

        for (int i = 0; i < 18; i++)
        {
            listener.onLocationChanged(list.get(i));
        }

        verify(mock, times(5)).onNearby(locations.get(0));
        verify(mock, times(4)).onNearby(locations.get(1));
        verify(mock, times(0)).onNearby(locations.get(2));
    }

    @Test
    public void testSearch()
    {

    }

    /*@Test
    public void test()
    {

    }*/

}
