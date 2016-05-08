package group50.coupletones.controller.tab.favoritelocations.map;

import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocation;

/**
 * @author Joseph Cox
 * @since 5/1/2016
 */

/**
 * Proximity observer interface
 */
public interface ProximityObserver {
  void onEnterLocation(VisitedLocation location);
}
