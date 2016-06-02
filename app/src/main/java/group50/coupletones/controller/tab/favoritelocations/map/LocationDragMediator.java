package group50.coupletones.controller.tab.favoritelocations.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.LinkedList;
import java.util.List;

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

  private List<Marker> markers;

  public LocationDragMediator()
  {
    CoupleTones.global().inject(this);
    markers = new LinkedList<>();
    locations = new LinkedList<>();
  }

  public void bindPair(Marker marker, FavoriteLocation location)
  {
    markers.add(marker);
    locations.add(location);
    locations.
  }

  @Override
  public void onMarkerDragEnd(Marker marker)
  {

  }

  private class Pair
  {

    private Marker marker;
    private FavoriteLocation location;

  }

}
