package group50.coupletones.map;

/**
 * Created by Joseph on 5/28/2016.
 */
public interface ProximityHandler {

  public void onNearby(FavoriteLocation nearbyLocation);

  public void register(NotificationObserver observer);

}
