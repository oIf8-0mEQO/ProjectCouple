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
import group50.coupletones.map.NearbyLocationHandler;
import group50.coupletones.map.NotificationObserver;
import group50.coupletones.map.ProximityHandler;
import group50.coupletones.map.VisitedLocation;

/**
 * Created by Joseph on 5/28/2016.
 */
public class MapTest {

    private List<FavoriteLocation> locations;

    @Before
    public void setUp()
    {
        locations = new ArrayList<>();
        locations.add(0, new FavoriteLocation("loc1", new LatLng(60, 60)));
        locations.add(1, new FavoriteLocation("loc2", new LatLng(-20, -20)));
    }

    @Test
    public void testLocationListener()
    {

        ProximityHandler mock = mock(ProximityHandler.class);
        MovementListener listener = new MovementListener(mock, locations);

        List<android.location.Location> list = new LinkedList<>();
        for (int i = 0; i < 8; i++)
        {
            list.add(new Location(""));
        }
        list.get(0).setLatitude(60.001);    list.get(0).setLongitude(60.001);
        list.get(1).setLatitude(-60.001);  list.get(1).setLongitude(60.001);
        list.get(2).setLatitude(60.001);   list.get(2).setLongitude(-60.001);
        list.get(3).setLatitude(-60.001);  list.get(3).setLongitude(-60.001);
        list.get(4).setLatitude(60.0015);  list.get(4).setLongitude(60.0015);
        list.get(5).setLatitude(-60.0015); list.get(5).setLongitude(60.0015);
        list.get(6).setLatitude(60.0015);  list.get(6).setLongitude(-60.0015);
        list.get(7).setLatitude(-60.0015); list.get(7).setLongitude(-60.0015);

        for (int i = 0; i < 8; i++)
        {
            listener.onLocationChanged(list.get(i));
        }

        verify(mock, times(4)).onNearby(locations.get(0));
        verify(mock, times(0)).onNearby(locations.get(1));
    }

    @Test
    public void testNotificationTimer()
    {
        NotificationObserver observer = new NotificationObserver() {
            int count = 0;
            @Override
            public void onEnterLocation(VisitedLocation location) {
                assert (count==0);
                count++;
            }
        };
        NotificationObserver mock = mock(NotificationObserver.class);
        ProximityHandler handler = new NearbyLocationHandler();
        handler.register(observer);
        FavoriteLocation shouldNotifyOnce = new FavoriteLocation();
        handler.onNearby(shouldNotifyOnce);
        handler.onNearby(shouldNotifyOnce);
    }

}
