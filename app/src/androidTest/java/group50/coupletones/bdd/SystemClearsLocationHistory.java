package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.MainActivity;

/**
 * @author Sharmaine Manalo
 * @since 5/30/16
 */
public class SystemClearsLocationHistory {

  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
  private CoupleTones app;
  private LocalUser mockUser;
  /**
   * Scenario 1: User's partner enters zone
   * Given that the user has a partner
   * When the partner visits a zone
   * And the user navigates to the home page
   * Then the user will see all zones the partner has been and the new zone the partner just entered
   *
   * Scenario 2: User's partner does not enter zone
   * Given that the user has a partner
   * When the partner does not visit a new zone
   * And the user navigates to the home page
   * Then the user will see all zones the partner has previously been that day
   */

}
