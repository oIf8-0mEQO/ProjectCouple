package group50.coupletones.controller.tab.partnerslocations;

import group50.coupletones.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joannecho on 5/5/16.
 */
public class PartnerLocationsData {
  private static final String[] locationNames = {"Tamarack Apartments", "Disneyland", "Pepper Canyon", "Kogi BBQ", "Balboa Park", "UCSD", "Zion Market", "Kung Fu Tea", "TPumps"};
  private static final String[] locationAddresses = {"La Jolla, CA", "Anaheim, CA", "La Jolla, CA", "San Diego, CA", "San Diego, CA", "La Jolla, CA", "San Diego, CA", "San Diego, CA", "Cupertino, CA"};
  private static final int[] icons = {R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon};
  private static final String[] locationTimes = {"10:00 PM", "9:30 PM", "8:45 PM", "6:15 PM", "4:07 PM", "3:15 PM", "2:57PM", "11:15 AM", "9:30 AM"};

  public static List<PartnerLocation> getPartnerLocations() {
    List<PartnerLocation> data = new ArrayList<>();

    for (int i = 0; i < locationNames.length; i++) {
      PartnerLocation location = new PartnerLocation();
      location.setIconId(icons[i]);
      location.setName(locationNames[i]);
      location.setTime(locationTimes[i]);
      location.setAddress(locationAddresses[i]);
      data.add(location);
    }

    return data;
  }
}

