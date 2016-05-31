package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.TimeUtility;
import group50.coupletones.util.sound.VibeTone;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

/**
 * @author Sharmaine Manalo
 * @since 5/30/16
 */
public class SystemClearsLocationHistory {
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
  private UserTestUtil userMocker = new ConcreteUserTestUtil();
  TimeUtility timeUtility;

  @Before
  public void setup() {
    LatLng position = new LatLng(10, 10);
    /*VisitedLocationEvent visitedLocation =
    List<FavoriteLocation> locationList = new LinkedList<>();
    locationList.add(partnerFavorite);*/
    timeUtility = CoupleTones.global().timeUtility();
    userMocker.injectLocalUser();
  }

  /**
   * Scenario 1: User's views the partner's visited before 3AM
   * Given that the user has a partner and views the partner's visited locations list before 3AM
   * When the user views his/her partner's visited locations
   * Then the app will display the list of partner's visited locations since the last 3AM
   */

  private void givenThatUserHasPartnerAndViewsListBefore3AM() {
    userMocker
        .mockProperty("name", "Henry")
        .mockProperty("email", "henry@email.com")
        .mockProperty("visitedLocations", )

    userMocker
        .mockPartner()
        .mockProperty("name", "Sharmaine")
        .mockProperty("email", "sharmaine@email.com");

    assertThat(userMocker.getPartner()).isNotNull();

    doReturn(150).when(timeUtility).systemTime();
  }

  private void whenUserViewsPartnersVisitedLocations() {
    onView(withId(R.id.partner_locations)).perform(click());
  }

  private void thenAppDisplaysListofPartnersVisitedLocationsSinceLast3AM() {
    onView(withId(R.id.partner_locations)).perform(click());
  }

  @Test
  public void userViewsPartnersVisitedLocationsBefore3AM() {

  }

  /**
   * Scenario 2: User's views the partner's visited after 3AM
   * Given that the user has a partner and views the partner's visited locations list after 3AM
   * When the user views his/her partner's visited locations
   * Then the app will clear the previous day's visited locations
   * And display the list of partner's visited locations since the most recent 3AM
   */

  private void givenThatUserHasPartnerAndViewsListAfter3AM() {

  }

  private void thenAppWillClearPreviousDaysVisitedLocations() {

  }

  private void andDisplayListofPartnersVisitedLocationsSinceLast3AM() {

  }

  @Test
  public void userViewsPartnersVisitedLocationsAfter3AM() {

  }


}
