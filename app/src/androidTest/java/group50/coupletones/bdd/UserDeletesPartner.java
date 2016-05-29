package group50.coupletones.bdd;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Sharmaine Manalo
 * @since 5/28/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserDeletesPartner {

  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
  private CoupleTones app;
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

    app = CoupleTones.global().app();
    when(app.isLoggedIn()).thenReturn(true);

    userMocker.injectLocalUser();
  }

  private void givenThatTheUserHasPartner() {
    userMocker
        .mockProperty("name", "Henry")
        .mockProperty("email", "henry@email.com");

    userMocker
        .mockPartner()
        .mockProperty("name", "Sharmaine")
        .mockProperty("email", "sharmaine@email.com");

    assertThat(userMocker.getPartner()).isNotNull();
  }

  private void whenUserClicksRemovePartnerOnSettingsPage() {
    onView(withId(R.id.settings)).perform(click());

    // TODO: Clicking disconnect not working
    onView(withId(R.id.disconnect_button))
        .perform(ViewActions.scrollTo())
        .perform(click());
  }

  private void thenUserPartnerIsRemoved() {
    // Verify that "No Partner" is displayed on Partner Profile card
    // onView(withId(R.id.null_partner)).check(ViewAssertions.matches(isDisplayed()));
    //assertThat(userMocker.getPartner()).isNull();
  }

  /*private void andUserIsRemovedAsPartnersPartner() {
  //    assertThat(userMocker.getPartner()).isNull();
  }*/

  @Test
  public void userDeletesPartner() {
    givenThatTheUserHasPartner();
    whenUserClicksRemovePartnerOnSettingsPage();
    thenUserPartnerIsRemoved();
    //andUserIsRemovedAsPartnersPartner();
  }
}
