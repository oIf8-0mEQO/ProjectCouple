package group50.coupletones.map;

/**
 * @author Joseph
 * @since 5/28/2016.
 */
public interface ProximityManager {
  void onNearby(FavoriteLocation nearbyLocation);

  void register(ProximityObserver observer);
}
