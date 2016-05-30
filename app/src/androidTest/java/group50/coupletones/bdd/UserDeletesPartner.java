package group50.coupletones.bdd;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.Partner;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Sharmaine Manalo
 * @since 5/28/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserDeletesPartner {

  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class){
    @Override
    protected void beforeActivityLaunched() {
      super.beforeActivityLaunched();
    }
  };
  private UserTestUtil userMocker = new ConcreteUserTestUtil();

  private Partner originalPartner;

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

  private void givenThatTheUserHasPartner() {
    userMocker
      .mockProperty("name", "Henry")
      .mockProperty("email", "henry@email.com");

    userMocker
      .injectUserWithId("sharmaine", user -> {
        user.getProperties().property("name").set("Sharmaine");
        user.getProperties().property("email").set("sharmaine@email.com");
        return user;
      })
      .injectPartner("sharmaine");

    originalPartner = userMocker.getPartner();
    assertThat(originalPartner).isNotNull();
  }

  private void whenUserClicksRemovePartnerOnSettingsPage() {
    onView(withId(R.id.settings)).perform(click());

    onView(withId(R.id.disconnect_button))
      .perform(ViewActions.scrollTo())
      .perform(click());
  }

  private void thenUserPartnerIsRemoved() {
    // Verify that "No Partner" is displayed on Partner Profile card
    onView(withId(R.id.null_partner)).check(ViewAssertions.matches(isDisplayed()));
    assertThat(userMocker.getPartner()).isNull();
  }

  private void andUserIsRemovedAsPartnersPartner() {
    verify(originalPartner, times(1)).setPartnerId(null);
  }

  @Test
  public void userDeletesPartner() {
    givenThatTheUserHasPartner();
    whenUserClicksRemovePartnerOnSettingsPage();
    thenUserPartnerIsRemoved();
    andUserIsRemovedAsPartnersPartner();
  }
}
