package group50.coupletones.controller.tab.partnerslocations;

/**
 * @author Joanne Cho
 * @since 5/5/16
 */

/**
 * A class that creates a PartnerLocation object
 * that contains a favorite location name, address, and time visited.
 * Includes methods that get and set name, address, and time visited.
 */
public class PartnerLocation {
  private String name, address, timeValue, vibeTone;
  private int iconId;

  /**
   * Get Location's name
   * @return Location name string
   */
  public String getName() {
    return name;
  }

  /**
   * Get Location's address
   * @return Location address string
   */
  public String getAddress() {
    return address;
  }

  /**
   * Get Icon ID
   * @return Icon Id
   */
  public int getIconId() {
    return iconId;
  }

  /**
   * Get Location's time visited
   * @return Location's time visited string
   */
  public String getTime() {
    return timeValue;
  }

  /**
   * Get Location's VibeTone
   * @return Location's VibeTone
   */
  public String getVibeTone() {
    return vibeTone;
  }


  /**
   * Set Location's name
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set Location's address
   * @param address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Set Icon ID
   * @param iconId
   */
  public void setIconId(int iconId) {
    this.iconId = iconId;
  }

  /**
   * Set Location's visited time
   * @param time
   */
  public void setTime(String time) { this.timeValue = time; }

  /**
   * Set Location's VibeTone
   * @param tone
   */
  public void setVibeTone(String tone) { this.vibeTone = tone; }

}

