package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Henry Mao
 * @since 6/2/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserCustomizesLocationNotificationVibeTone {

  private UserTestUtil testUtil = new ConcreteUserTestUtil();
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      testUtil.injectLocalUser();
      super.beforeActivityLaunched();
    }
  };

  private void givenThatTheUserIsOnThePageOfPartnersListOfFavoriteLocations() {
    onView(withId(R.id.partners_static_list)).perform(click());
  }

  private void whenTheUserTapsOnOneOfTheirPartnersFavoriteLocations() {

  }

  private void thenTheUserWillBeTakenToAPageToChooseFromTenVibeTones() {

  }

  @Test
  public void userCustomizesLocationToneAndVibration() {
    givenThatTheUserIsOnThePageOfPartnersListOfFavoriteLocations();
    whenTheUserTapsOnOneOfTheirPartnersFavoriteLocations();
    thenTheUserWillBeTakenToAPageToChooseFromTenVibeTones();
  }

  private void givenThatTheUserDoesNotTapOnALocationToCustomizeVibeTones() {
    //Do nothing
  }

  private void whenTheUserViewsThePartnersListOfFavoriteLocations() {
    // Need to view the partner's favorite locations
    givenThatTheUserIsOnThePageOfPartnersListOfFavoriteLocations();
  }


  private void thenTheUserWillHaveTheDefaultVibeToneAssigned() {

  }

  @Test
  public void userDoesNotCustomizeLocationVibeTone() {
    givenThatTheUserDoesNotTapOnALocationToCustomizeVibeTones();
    whenTheUserViewsThePartnersListOfFavoriteLocations();
    thenTheUserWillHaveTheDefaultVibeToneAssigned();
  }
}
