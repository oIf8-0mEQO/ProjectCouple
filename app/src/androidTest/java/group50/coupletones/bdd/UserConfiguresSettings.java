package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * BDD style test for settings fragment
 *
 * @author Henry Mao
 * @since 5/8/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserConfiguresSettings {
  @Rule
  public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
  private CoupleTones app;
  private LocalUser mockUser;
  private Partner samplePartner;

  @Before
  public void setup() {
    // Mock DI
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );

    // Mock the user
    mockUser = mock(LocalUser.class);
    app = CoupleTones.global().app();
    when(app.isLoggedIn()).thenReturn(true);
    when(app.getLocalUser()).thenReturn(mockUser);
  }

  private void givenUserNotConnectedToPartner() {
    when(mockUser.getPartner()).thenReturn(null);
  }

  private void givenUserConnectedToPartner() {
    samplePartner = new Partner("Henry", "henry@calclavia.com");
    when(mockUser.getPartner()).thenReturn(samplePartner);
  }

  private void whenOpenSettingsPage() {
    onView(withId(R.id.settings)).perform(click());
  }

  @Test
  public void userAddsPartner() {
    givenUserNotConnectedToPartner();
    whenOpenSettingsPage();

    // I should see a button that allows me to add partner
    onView(withId(R.id.add_partner_button)).perform(click());
  }

  @Test
  public void userSeesPartner() {
    givenUserConnectedToPartner();
    whenOpenSettingsPage();

    // I should see my partner's info
    onView(withId(R.id.partner_name)).check(matches(withText(samplePartner.getName())));
    onView(withId(R.id.partner_email)).check(matches(withText(samplePartner.getEmail())));
    onView(withId(R.id.partner_name)).check(matches(isDisplayed()));
    onView(withId(R.id.partner_email)).check(matches(isDisplayed()));
  }
}
