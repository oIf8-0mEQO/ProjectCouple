package group50.coupletones.controller.tab.partnerslocations;

import group50.coupletones.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joanne Cho
 * @since 5/25/16
 */

// TODO - currently placeholder data
/**
 * Partner Location placeholder data of
 * location names, addresses, and visited times.
 */
public class PartnerFavoritesData {
  private static final String[] locationNames = {"Tamarack Apartments", "Disneyland", "Pepper Canyon", "Kogi BBQ", "Balboa Park", "Tasty Noodle House", "Zion Market", "Kung Fu Tea", "TPumps"};
  private static final String[] locationAddresses = {"La Jolla, CA", "Anaheim, CA", "La Jolla, CA", "San Diego, CA", "San Diego, CA", "San Diego, CA", "San Diego, CA", "San Diego, CA", "Cupertino, CA"};
  private static final int[] icons = {R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon};
  private static final String[] vibeTones = {"VibeTone 1", "VibeTone 2", "VibeTone 3", "VibeTone 4", "VibeTone 5", "VibeTone 6", "VibeTone 7", "VibeTone 8", "VibeTone 9"};

  /**
   * getPartnerLocations
   * @return - List of partner information
   */
  public static List<PartnerLocation> getPartnerLocations() {
    List<PartnerLocation> data = new ArrayList<>();

    for (int i = 0; i < locationNames.length; i++) {
      PartnerLocation location = new PartnerLocation();
      location.setIconId(icons[i]);
      location.setName(locationNames[i]);
      location.setVibeTone(vibeTones[i]);
      location.setAddress(locationAddresses[i]);
      data.add(location);
    }

    return data;
  }
}

