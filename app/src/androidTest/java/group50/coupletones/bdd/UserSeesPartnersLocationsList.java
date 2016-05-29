package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.assertj.core.api.Assertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Sharmaine Manalo
 * @since 5/27/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserSeesPartnersLocationsList {

  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
  private CoupleTones app;
  private List<VisitedLocationEvent> visitedLocations = new LinkedList<>();
  private LocalUser mockUser;
  private Partner mockPartner;

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
    mockPartner = mock(Partner.class);
    app = CoupleTones.global().app();
    when(app.isLoggedIn()).thenReturn(true);
    when(app.getLocalUser()).thenReturn(mockUser);
    when(app.getLocalUser().getPartner()).thenReturn(mockPartner);
    doAnswer(ans -> visitedLocations.add((VisitedLocationEvent) ans.getArguments()[0]))
        .when(mockUser)
        .addVisitedLocation(any());
    when(mockUser.getVisitedLocations()).then(ans -> Collections.unmodifiableList(visitedLocations));
  }

  private void givenUserHasPartner() {
    assertThat(app.getLocalUser().getPartner()).isNotNull();
  }

  private void whenPartnerVisitsZone() {
    mockUser.addVisitedLocation(any());
  }

  private void andUserNavigatesToHomePage() {
    onView(withId(R.id.partners_location_list)).check(matches(isDisplayed()));
  }

  private void thenUserWillSeeAllZonesPartnerHasBeenThatDay() {
    assertThat(mockUser.getVisitedLocations()).isNotEmpty();
  }

  private void andTheNewZonePartnerJustEntered() {
    assertThat(mockUser.getVisitedLocations()).hasSize(1);
  }

  private void whenPartnerDoesNotVisitNewZone() {
    assertThat(mockUser.getVisitedLocations()).hasSize(0);
  }

  @Test
  public void usersPartnerEntersZone() {
    givenUserHasPartner();
    whenPartnerVisitsZone();
    andUserNavigatesToHomePage();
    thenUserWillSeeAllZonesPartnerHasBeenThatDay();
    andTheNewZonePartnerJustEntered();
  }

  @Test
  public void usersPartnerDoesNotEnterZone() {
    givenUserHasPartner();
    whenPartnerDoesNotVisitNewZone();
    andUserNavigatesToHomePage();
    thenUserWillSeeAllZonesPartnerHasBeenThatDay();
  }
}
