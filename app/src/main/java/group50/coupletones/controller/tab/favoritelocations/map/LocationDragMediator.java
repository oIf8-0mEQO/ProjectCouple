package group50.coupletones.controller.tab.favoritelocations.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import javax.inject.Inject;

import group50.coupletones.CoupleTones;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;

/**
 * Manages changes between a marker and the Favorite Location it represents.
 * @author joseph
 * @since 6/2/16
 */
public class LocationDragMediator implements GoogleMap.OnMarkerDragListener {

  @Inject
  CoupleTones app;

  private FavoriteLocation location;
  private 

  public LocationDragMediator(FavoriteLocation location, Marker marker)
  {
    this.location = location;
    CoupleTones.global().inject(this);
  }

  @Override
  public void onMarkerDragEnd(Marker marker)
  {

  }

}
