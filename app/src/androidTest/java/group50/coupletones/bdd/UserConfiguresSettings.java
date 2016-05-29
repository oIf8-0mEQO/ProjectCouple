package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
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
  private UserTestUtil userMocker = new ConcreteUserTestUtil();

  @Before
  public void setup() {
    // Mock DI
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );

    userMocker.injectLocalUser();
  }

  private void givenUserNotConnectedToPartner() {
    userMocker.mockNoPartner();
    userMocker.mockProperty("name");
    userMocker.mockProperty("email");
  }

  private void givenUserConnectedToPartner() {
    userMocker
      .mockProperty("name", "Henry")
      .mockProperty("email", "henry@email.com");

    userMocker
      .mockPartner()
      .mockProperty("name", "Sharmaine")
      .mockProperty("email", "sharmaine@email.com");
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
  public void userAddsPartner() {
    givenUserNotConnectedToPartner();
    whenOpenSettingsPage();
    thenUserSeesButtonToAddPartner();
  }

  @Test
  public void userSeesPartner() {
    givenUserConnectedToPartner();
    whenOpenSettingsPage();
    thenUserSeesPartnerName();
  }
}
