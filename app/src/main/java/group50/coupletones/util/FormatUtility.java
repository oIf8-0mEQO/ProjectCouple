package group50.coupletones.util;

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
}
