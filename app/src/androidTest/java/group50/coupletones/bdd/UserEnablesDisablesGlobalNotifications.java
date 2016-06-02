package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Sharmaine Manalo
 * @since 6/1/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserEnablesDisablesGlobalNotifications {
  private UserTestUtil testUtil = new ConcreteUserTestUtil();

  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      testUtil.injectLocalUser();
      super.beforeActivityLaunched();
    }
  };

  /** Scenario 1: User turns on notifications
   Given that the user is on the Settings page
   And notifications are off,
   When the user toggles the notifications on
   And the user's partner goes to one of his/her favorite locations,
   Then the user will receive a notification.**/

  private void givenUserIsOnSettingsPage() {
    onView(withId(R.id.settings)).perform(click());
    onView(withId(R.id.settings)).check(matches(isDisplayed()));
  }

  private void andNotificationsAreOff() {
    onView(withId(R.id.notif_switch)).perform(scrollTo());
  }

  private void whenUserTogglesNotificationsOn() {
    onView(withId(R.id.notif_switch)).perform(click());
  }

  private void andUserPartnerGoesToFavoriteLocation() {
    // TODO
  }

  private void thenUserWillReceiveNotification() {
    // TODO
  }

  @Test
  public void userTurnsOnNotifications() {
    givenUserIsOnSettingsPage();
    andNotificationsAreOff();
    whenUserTogglesNotificationsOn();
    //andUserPartnerGoesToFavoriteLocation();
    //thenUserWillReceiveNotification();
  }

   /**Scenario 2: User turns off notifications
   Given that the user is on the Settings page
   And notifications are on,
   When the user toggles the notifications off
   And the user's partner goes to one of his/her favorite locations,
   Then the user will not receive a notification.*/

  private void andNotificationsAreOn() {
    onView(withId(R.id.notif_switch)).perform(scrollTo()).perform(click());
  }

  private void whenUserTogglesNotificationsOff() {
    onView(withId(R.id.notif_switch)).perform(scrollTo()).perform(click());
  }

  private void thenUserWillNotReceiveNotification() {
    // TODO
  }

  @Test
  public void userTurnsOffNotications() {
    givenUserIsOnSettingsPage();
    andNotificationsAreOn();
    whenUserTogglesNotificationsOff();
    //andUserPartnerGoesToFavoriteLocation();
    //thenUserWillNotReceiveNotification();
  }

   /**Scenario 3: User turns on sound notifications
   Given that the user is on the Settings page
   And sound notifications are off,
   When the user toggles the sound notifications on
   And the user's partner goes to one of his/her favorite locations,
   Then the user will receive a sound notification.*/

  private void andSoundNotificationsAreOff() {
  }

  private void whenUserTogglesSoundNotificationsOn() {
    onView(withId(R.id.notif_tones_switch)).perform(scrollTo()).perform(click());
  }

  private void thenUserWillReceiveSoundNotification() {
    // TODO
  }

  @Test
  public void userTurnsOnSoundNotifications() {
    givenUserIsOnSettingsPage();
    andSoundNotificationsAreOff();
    whenUserTogglesSoundNotificationsOn();
    //andUserPartnerGoesToFavoriteLocation();
    //thenUserWillReceiveSoundNotification();
  }

   /**Scenario 4: User turns off sound notifications
   Given that the user is on the Settings page
   And sound notifications are on,
   When the user toggles the sound notifications off
   And the user's partner goes to one of his/her favorite locations,
   Then the user will not receive a sound notification.*/

  private void andSoundNotificationsAreOn() {
    onView(withId(R.id.notif_tones_switch)).perform(scrollTo()).perform(click());
  }

  private void whenUserTogglesSoundNotificationsOff() {
    onView(withId(R.id.notif_tones_switch)).perform(scrollTo()).perform(click());
  }

  private void thenUserWillNotReceiveSoundNotification() {
    // TODO
  }

  @Test
  public void userTurnsOffSoundNotifications() {
    givenUserIsOnSettingsPage();
    andSoundNotificationsAreOn();
    whenUserTogglesSoundNotificationsOff();
    //andUserPartnerGoesToFavoriteLocation();
    //thenUserWillNotReceiveSoundNotification();
  }
   /**Scenario 5: User turns on vibration notifications
   Given that the user is on the Settings page
   And vibration notifications are off,
   When the user toggles the vibration notifications on
   And the user's partner goes to one of his/her favorite locations,
   Then the user will receive a vibration notification.*/

  private void andVibrationNotificationsAreOff() {
  }

  private void whenUserTogglesVibrationNotificationsOn() {
    onView(withId(R.id.notif_vibe_switch)).perform(scrollTo()).perform(click());
  }

  private void thenUserWillReceiveVibrationNotification() {
    // TODO
  }

  @Test
  public void userTurnsOnVibrationNotification() {
    givenUserIsOnSettingsPage();
    andVibrationNotificationsAreOff();
    whenUserTogglesVibrationNotificationsOn();
    //andUserPartnerGoesToFavoriteLocation();
    //thenUserWillReceiveVibrationNotification();
  }

   /**Scenario 6: User turns off vibration notifications
   Given that the user is on the Settings page
   And vibration notifications are on,
   When the user toggles the vibration notifications off
   And the partner goes to one of his/her favorite locations,
   Then the user will not receive a vibration notification.
   */

  private void andVibrationNoticationsAreOn() {
    onView(withId(R.id.notif_vibe_switch)).perform(scrollTo()).perform(click());
  }

  private void whenUserTogglesVibrationNotificationsOff() {
    onView(withId(R.id.notif_vibe_switch)).perform(scrollTo()).perform(click());
  }

  private void thenUserWillNotReceiveVibrationNotification() {
    // TODO
  }

  @Test
  public void userTurnsOffVibrationNotifications() {
    givenUserIsOnSettingsPage();
    andVibrationNoticationsAreOn();
    whenUserTogglesVibrationNotificationsOff();
    //andUserPartnerGoesToFavoriteLocation();
    //thenUserWillNotReceiveVibrationNotification();
  }
}
