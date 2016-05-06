package group50.coupletones;

import java.util.ArrayList;
import java.util.List;

import group50.coupletones.FaveLocation;
import group50.coupletones.R;

/**
 * Created by sharmaine on 5/1/16.
 */
public class FaveLocationsData {
  private static final String[] locationNames = {"Tapex", "Disneyland", "UCSD", "Tapex", "Disneyland", "UCSD", "Tapex", "Disneyland", "UCSD"};
  private static final String[] locationAddresses = {"La Jolla, CA", "Anaheim, CA", "La Jolla, CA", "La Jolla, CA", "Anaheim, CA", "La Jolla, CA", "La Jolla, CA", "Anaheim, CA", "La Jolla, CA"};
  private static final int[] icons = {R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon, R.drawable.target_icon};

  public static List<FaveLocation> getFaveLocations() {
    List<FaveLocation> data = new ArrayList<>();

    for (int i = 0; i < locationNames.length; i++) {
      FaveLocation location = new FaveLocation();
      location.setIconId(icons[i]);
      location.setName(locationNames[i]);
      location.setAddress(locationAddresses[i]);
      data.add(location);
    }

    return data;
  }
}
