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
  public String formatDate(Date date) {
    Format formatter = new SimpleDateFormat("EEE, h:mm a");
    String s = formatter.format(date);
    return s;
  }

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
