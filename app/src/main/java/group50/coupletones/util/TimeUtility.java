package group50.coupletones.util;

import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;

import java.util.Calendar;
import java.util.Date;

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
   * @return System time
   */
  public Date systemDate() {
    return new Date(systemTime());
  }

  /**
   * This function checks to see if the time of the visited location is before 3AM
   *
   * @param visitedLocation The location to check the time of
   * @return true if visited location time is before 3AM, otherwise false
   */
  public boolean isFromPreviousDay(VisitedLocationEvent visitedLocation) {
    // Get the time visited of visited location
    Date visitTime = visitedLocation.getTimeVisited();

    // Refer to 3AM of current day
    Calendar resetTime = Calendar.getInstance();
    resetTime.setTime(systemDate());

    // If it's between 0 AM and 3AM, backtrack one day.
    if (resetTime.get(Calendar.HOUR_OF_DAY) < 3) {
      resetTime.set(Calendar.DAY_OF_YEAR, resetTime.get(Calendar.DAY_OF_YEAR) - 1);
    }

    // Set to 3AM of the current day
    resetTime.set(Calendar.HOUR_OF_DAY, 3);
    resetTime.set(Calendar.MINUTE, 0);
    resetTime.set(Calendar.SECOND, 0);
    return visitTime.before(resetTime.getTime());
  }
}
