package group50.coupletones.util;

import android.location.Address;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sharmaine Manalo
 * @since 5/30/16
 */
public class FormatUtility {

  /**
   * Formats date for syntax
   * @param date The date to format
   * @return Formatted string of the date.
   */
  public String formatDate(Date date) {
    Format formatter = new SimpleDateFormat("EEE, h:mm a");
    String s = formatter.format(date);
    return s;
  }

  /**
   * Formats address for display
   * @param address The address to format
   * @return Formatted string of the address.
   */
  public String formatAddress(Address address) {
    if (address != null) {
      if (address.getLocality() != null) {
        return address.getLocality() + ", " + address.getAdminArea();
      } else {
        return address.getAdminArea();
      }
    }
    return "";
  }
}
