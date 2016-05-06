package group50.coupletones.controller.tab.partnerslocations;

/**
 * Created by joannecho on 5/5/16.
 */

/**
 * A class that creates a PartnerLocation object that contains a favorite location name and address.
 */
public class PartnerLocation {
  private String name, address;
  private int iconId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getIconId() {
    return iconId;
  }

  public void setIconId(int iconId) {
    this.iconId = iconId;
  }
}

