package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.R;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.assertj.core.api.Assertions.assertThat;

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
      testUtil.injectSpyPartner();

      // Make fake favorite locations for partner
      Partner partner = testUtil.getPartner();
      partner
        .getProperties()
        .property("favoriteLocations")
        .set(Collections.singletonList(new FavoriteLocation("Test", new LatLng(0, 0), 0, 0)));

      super.beforeActivityLaunched();
    }
  };

  private void givenThatTheUserIsOnThePageOfPartnersListOfFavoriteLocations() {
    onView(withId(R.id.partners_list_button)).perform(click());
  }

  private void whenTheUserTapsOnOneOfTheirPartnersFavoriteLocations() {
    onView(withId(R.id.edit_location_icon)).perform(click());
  }

  private void thenTheUserWillBeTakenToAPageToChooseFromTenVibeTones() {
    // Ensure the user is on the VibeTonesFragment
    onView(withId(R.id.vibetone_recycler_view)).check(matches(isDisplayed()));
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
    List<FavoriteLocation> favoriteLocations = testUtil.getPartner().getFavoriteLocations();
    assertThat(favoriteLocations).hasSize(1);
    assertThat(favoriteLocations.get(0).getVibetone()).isEqualTo(VibeTone.getDefaultTone());
  }

  @Test
  public void userDoesNotCustomizeLocationVibeTone() {
    givenThatTheUserDoesNotTapOnALocationToCustomizeVibeTones();
    whenTheUserViewsThePartnersListOfFavoriteLocations();
    thenTheUserWillHaveTheDefaultVibeToneAssigned();
  }


  public void givenThatTheUserIsOnThePageToCustomizeVibeTones() {
    givenThatTheUserIsOnThePageOfPartnersListOfFavoriteLocations();
    whenTheUserTapsOnOneOfTheirPartnersFavoriteLocations();
  }

  public void whenTheUserTapsOnAVibeTone() throws Throwable {
    Thread.sleep(1000);
    onView(withId(R.id.vibetone_recycler_view))
      .perform(click());

  }

  public void thenThatVibeToneWillBeAssignedToTheLocationBeingEdited() {
    List<FavoriteLocation> favoriteLocations = testUtil.getPartner().getFavoriteLocations();
    assertThat(favoriteLocations).hasSize(1);
    assertThat(favoriteLocations.get(0).getVibetone()).isEqualTo(VibeTone.getTone(1));
  }

  @Test
  public void userSavesLocationToneAndVibration() throws Throwable {
    givenThatTheUserIsOnThePageToCustomizeVibeTones();
    whenTheUserTapsOnAVibeTone();
    thenThatVibeToneWillBeAssignedToTheLocationBeingEdited();
  }
}
