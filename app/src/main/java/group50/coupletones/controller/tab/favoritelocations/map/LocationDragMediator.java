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

  private List<Pair> pairs;
  private Marker currentMarker;

  public LocationDragMediator()
  {
    CoupleTones.global().inject(this);
    pairs = new LinkedList<>();
  }

  public void bindPair(Marker marker, FavoriteLocation location)
  {
    pairs.add(new Pair(marker, location));
  }

  @Override
  public void onMarkerDragEnd(Marker marker)
  {
    FavoriteLocation location = null;
    for (Pair p : pairs)
    {
      if (p.marker.equals(currentMarker))
      {
        location = p.location;
        break;
      }
    }
    int index = app.getLocalUser().getFavoriteLocations().indexOf(location);
    location.setLat(marker.getPosition().latitude);
    location.setLong(marker.getPosition().longitude);
    app.getLocalUser().setFavoriteLocation(index, location);
  }

  @Override
  public void onMarkerDragStart(Marker marker)
  {
    currentMarker = marker;
  }

  @Override
  public void onMarkerDrag(Marker marker)
  {

  }

  private class Pair
  {

    public Pair(Marker marker, FavoriteLocation location)
    {
      this.marker = marker;
      this.location = location;
    }

    private Marker marker;
    private FavoriteLocation location;

  }

}
