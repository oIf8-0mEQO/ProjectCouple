package group50.coupletones.controller.tab.favoritelocations.map;

import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocation;

/**
 * Created by Joseph on 5/1/2016.
 */
public interface ProximityObserver {
  void onEnterLocation(VisitedLocation location);
}
