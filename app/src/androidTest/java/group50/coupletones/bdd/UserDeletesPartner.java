package group50.coupletones.bdd;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.R;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
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

  private UserTestUtil userMocker = new ConcreteUserTestUtil();
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      userMocker.injectLocalUser();
      super.beforeActivityLaunched();
    }
  };
  private Partner originalPartner;

  private void givenThatTheUserHasPartner() throws Throwable {
    rule.runOnUiThread(() -> userMocker.injectSpyPartner());

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
  public void userDeletesPartner() throws Throwable {
    givenThatTheUserHasPartner();
    whenUserClicksRemovePartnerOnSettingsPage();
    thenUserPartnerIsRemoved();
    andUserIsRemovedAsPartnersPartner();
  }
}
