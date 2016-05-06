package group50.coupletones.map;

import javax.inject.Inject;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joseph on 5/1/2016.
 */
public class MapProximityManager implements ProximityManager {

  private List<ProximityObserver> observers;

  @Inject
  public MapProximityManager() {
    observers = new LinkedList<>();
  }

  public void register(ProximityObserver observer) {
    observers.add(observer);
  }

  public void onNearby(FavoriteLocation nearbyLocation) {
    if (!nearbyLocation.isOnCooldown()) {
      for (ProximityObserver i : observers) {
        i.onEnterLocation(new VisitedLocation(nearbyLocation, new Date()));
      }
      nearbyLocation.setCooldown();
    }
  }

}
