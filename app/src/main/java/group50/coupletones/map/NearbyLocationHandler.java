package group50.coupletones.map;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joseph on 5/1/2016.
 */
public class NearbyLocationHandler implements ProximityHandler {

    List<NotificationObserver> observers;

    public NearbyLocationHandler()
    {
        observers = new LinkedList<>();
    }

    public void register(NotificationObserver observer)
    {
        observers.add(observer);
    }

    public void onNearby(FavoriteLocation nearbyLocation)
    {
        if (!nearbyLocation.isOnCooldown())
        {
            for(NotificationObserver i : observers)
            {
                i.onEnterLocation(new VisitedLocation(nearbyLocation, new Date()));
            }
            nearbyLocation.setCooldown();
        }
    }

}
