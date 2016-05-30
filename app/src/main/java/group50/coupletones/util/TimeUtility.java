package group50.coupletones.util;

import java.util.Calendar;
import java.util.Date;

import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;

/**
 * @author Sharmaine Manalo
 * @since 5/30/16
 */
public class TimeUtility {

  /**
   * @return System time
   */
  public Long systemTime() {
    return System.currentTimeMillis();
  }

  /**
   * This function checks to see if the time of the visited location is before 3AM
   * @param visitedLocation The location to check the time of
   * @return true if visited location time is before 3AM, otherwise false
   */
  public boolean checkTime(VisitedLocationEvent visitedLocation) {

    // Get the time visited of visited location
    Date currentTime = visitedLocation.getTimeVisited();

    // Refer to 3AM of current day
    Calendar reset = Calendar.getInstance();
    reset.set(Calendar.HOUR_OF_DAY, 3);
    reset.set(Calendar.MINUTE, 00);
    Date resetTime = reset.getTime();

    boolean b = false;

    if (currentTime.before(resetTime)) {
      b = true;
    }
    return b;
  }
}
