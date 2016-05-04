package group50.coupletones;

import android.widget.ImageView;

/**
 * A class that creates a FaveLocations object that contains a favorite location name and address.
 */
public class FaveLocation {
   private String name, address;
   private int iconId;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getIconId() {
        return iconId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
