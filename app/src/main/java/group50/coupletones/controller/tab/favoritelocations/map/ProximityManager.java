package group50.coupletones.controller.tab.favoritelocations.map;


import android.location.LocationListener;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import rx.Observable;

/**
 * @author Joseph Cox
 * @since 5/28/2016
 */
public interface ProximityManager extends LocationListener {

  Observable<VisitedLocationEvent> getEnterSubject();

  Observable<VisitedLocationEvent> getExitSubject();

}
