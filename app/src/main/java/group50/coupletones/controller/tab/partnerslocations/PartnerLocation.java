package group50.coupletones.controller.tab.partnerslocations;

/**
 * @author Joanne Cho
 * @since 5/5/16
 */

/**
 * A class that creates a PartnerLocation object
 * that contains a favorite location name, address, and time visited.
 */
public class PartnerLocation {
  private String name, address, timeValue;
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

  public String getTime() {
    return timeValue;
  }

  public void setTime(String time) { this.timeValue = time; }
}

