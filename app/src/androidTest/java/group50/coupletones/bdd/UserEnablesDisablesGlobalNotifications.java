package group50.coupletones.bdd;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.runner.RunWith;

/**
 * @author Sharmaine Manalo
 * @since 6/1/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserEnablesDisablesGlobalNotifications {
  /** Scenario 1: User turns on notifications
   Given that the user is on the Settings page
   And notifications are off,
   When the user toggles the notifications on
   And the user's partner goes to one of his/her favorite locations,
   Then the user will receive a notification.

   Scenario 2: User turns off notifications
   Given that the user is on the Settings page
   And notifications are on,
   When the user toggles the notifications off
   And the user's partner goes to one of his/her favorite locations,
   Then the user will not receive a notification.

   Scenario 3: User turns on sound notifications
   Given that the user is on the Settings page
   And sound notifications are off,
   When the user toggles the sound notifications on
   And the user's partner goes to one of his/her favorite locations,
   Then the user will receive a sound notification.

   Scenario 4: User turns off sound notifications
   Given that the user is on the Settings page
   And sound notifications are on,
   When the user toggles the sound notifications off
   And the user's partner goes to one of his/her favorite locations,
   Then the user will not receive a sound notification.

   Scenario 5: User turns on vibration notifications
   Given that the user is on the Settings page
   And vibration notifications are off,
   When the user toggles the vibration notifications on
   And the user's partner goes to one of his/her favorite locations,
   Then the user will receive a vibration notification.

   Scenario 6: User turns off vibration notifications
   Given that the user is on the Settings page
   And vibration notifications are on,
   When the user toggles the vibration notifications off
   And the partner goes to one of his/her favorite locations,
   Then the user will not receive a vibration notification.

   */
}
