package group50.coupletones.controller.tab.favoritelocations.map;

/**
 * @author Joseph
 * @since 5/28/2016.
 */
public interface ProximityManager {

  /**
   * Registers a ProximityObserver, which will be notified when the
   * user enters a favorite location.
   *
   * @param observer The observer
   */
  void register(ProximityObserver observer);
}
