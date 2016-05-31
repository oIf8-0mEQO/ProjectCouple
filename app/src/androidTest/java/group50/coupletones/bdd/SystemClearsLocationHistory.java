package group50.coupletones.bdd;

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

import java.util.Calendar;
import java.util.Date;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * @author Sharmaine Manalo
 * @since 5/30/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest

public class SystemClearsLocationHistory {
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
  private UserTestUtil userMocker = new ConcreteUserTestUtil();
  TimeUtility timeUtility;
  List<VisitedLocationEvent> visitedLocations;
  VisitedLocationEvent visitedLocation;

  @Before
  public void setup() {
    FavoriteLocation partnerFavorite = new FavoriteLocation("name", new LatLng(10, 10), 0, VibeTone.getTone());
    timeUtility = CoupleTones.global().timeUtility();

    Date date = new Date(1464480000000L);
    doReturn(date).when(timeUtility).systemDate();

    visitedLocation = new VisitedLocationEvent(partnerFavorite, timeUtility.systemDate(), timeUtility.systemDate());
    visitedLocations = new LinkedList<>();
    visitedLocations.add(visitedLocation);

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
        .mockProperty("email", "henry@email.com");

    userMocker.injectUserWithId("sharmaine", user -> {
      user.getProperties().property("name").set("Sharmaine");
      user.getProperties().property("email").set("sharmaine@email.com");
      user.getProperties().property("visitedLocations").set(visitedLocations);
      return user;
    });

    userMocker.injectPartner("sharmaine");

    assertThat(userMocker.getPartner()).isNotNull();

    assertThat(userMocker.getPartner().getProperties().property("visitedLocations", List.class).get()).hasSize(1);
    assertThat(userMocker.getPartner().getProperties().property("visitedLocations", List.class).get()).contains(visitedLocation);

    doReturn(1464480000000L).when(timeUtility).systemTime();
  }

  private void whenUserViewsPartnersVisitedLocations() {
    onView(withId(R.id.partner_locations)).perform(click());
  }

  private void thenAppDisplaysListofPartnersVisitedLocationsSinceLast3AM() throws Throwable{
    assertThat(userMocker.getPartner().getVisitedLocations()).hasSize(1);
    assertThat(userMocker.getPartner().getVisitedLocations()).contains(visitedLocation);

    onView(withId(R.id.partner_locations)).check(ViewAssertions.matches(isDisplayed()));
    onView(withId(R.id.partners_location_list)).check(ViewAssertions.matches(isDisplayed()));
  }

  @Test
  public void userViewsPartnersVisitedLocationsBefore3AM() throws Throwable{
    givenThatUserHasPartnerAndViewsListBefore3AM();
    whenUserViewsPartnersVisitedLocations();
    thenAppDisplaysListofPartnersVisitedLocationsSinceLast3AM();
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

  /*@Test
  public void userViewsPartnersVisitedLocationsAfter3AM() {

  }*/


}
