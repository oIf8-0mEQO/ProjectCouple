package group50.coupletones.controller.tab.favoritelocations.map.location.handler;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.sound.VibeTone;

/**
 * Handles receiving location notification and plays VibeTones.
 *
 * @author Henry Mao
 * @since 6/2/16
 */
public class LocationDepartureHandler extends AbstractLocationHandler {

  @Override
  protected void handleNotifyFor(FavoriteLocation favoriteLocation) {
    VibeTone vibetone = favoriteLocation.getVibetone();
    vibetone.playDeparture();
  }
}
