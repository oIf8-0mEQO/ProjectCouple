package group50.coupletones.controller.tab.favoritelocations.map;


import android.location.LocationListener;

import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import rx.subjects.BehaviorSubject;

/**
 * @author Joseph Cox
 * @since 5/28/2016
 */
public interface ProximityManager extends LocationListener {

  BehaviorSubject<VisitedLocationEvent> getEnterSubject();
  BehaviorSubject<VisitedLocationEvent> getExitSubject();

}
