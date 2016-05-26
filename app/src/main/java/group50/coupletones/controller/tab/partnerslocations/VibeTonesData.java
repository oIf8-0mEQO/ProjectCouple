package group50.coupletones.controller.tab.partnerslocations;

import java.util.ArrayList;
import java.util.List;

import group50.coupletones.R;

/**
 * @author joannecho
 * @since 5/25/16.
 */
public class VibeTonesData {
  private static final String[] vibeTones = {"VibeTone 1", "VibeTone 2", "VibeTone 3", "VibeTone 4", "VibeTone 5", "VibeTone 6", "VibeTone 7", "VibeTone 8", "VibeTone 9", "VibeTone 10"};

  /**
   * getVibeTones
   * @return - List of VibeTones
   */
  public static List<PartnerLocation> getVibeTones() {
    List<PartnerLocation> data = new ArrayList<>();

    for (int i = 0; i < vibeTones.length; i++) {
      PartnerLocation location = new PartnerLocation();
      location.setName(vibeTones[i]);
      data.add(location);
    }

    return data;
  }
}
