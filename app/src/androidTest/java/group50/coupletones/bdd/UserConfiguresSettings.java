package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * BDD style test for user configures settings story
 *
 * @author Henry Mao
 * @since 5/8/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserConfiguresSettings {
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
  private UserTestUtil testUtil = new ConcreteUserTestUtil();

  @Before
  public void setUp() throws Exception {
    testUtil.injectLocalUser();
  }

  private void givenUserNotConnectedToPartner() throws Throwable {
    rule.runOnUiThread(() -> testUtil.injectNoPartner());
  }

  private void givenUserConnectedToPartner() throws Throwable {
    rule.runOnUiThread(() -> testUtil.injectSpyPartner());
  }

  private void whenOpenSettingsPage() {
    onView(withId(R.id.settings)).perform(click());
  }

  private void thenUserSeesButtonToAddPartner() {
    // I should see a button that allows me to add partner
    onView(withId(R.id.add_partner_button)).perform(click());
  }

  private void thenUserSeesPartnerName() {
    // I should see my partner's info
    onView(withId(R.id.partner_name)).check(matches(withText("Sharmaine")));
    onView(withId(R.id.partner_email)).check(matches(withText("sharmaine@email.com")));
    onView(withId(R.id.partner_name)).check(matches(isDisplayed()));
    onView(withId(R.id.partner_email)).check(matches(isDisplayed()));
  }

  @Test
  public void userAddsPartner() throws Throwable {
    givenUserNotConnectedToPartner();
    whenOpenSettingsPage();
    thenUserSeesButtonToAddPartner();
  }

  @Test
  public void userSeesPartner() throws Throwable {
    givenUserConnectedToPartner();
    whenOpenSettingsPage();
    thenUserSeesPartnerName();
  }
}
