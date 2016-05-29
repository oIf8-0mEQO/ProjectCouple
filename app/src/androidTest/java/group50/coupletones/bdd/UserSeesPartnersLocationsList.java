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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Sharmaine Manalo
 * @since 5/27/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserSeesPartnersLocationsList {
  @Rule
  public ActivityTestRule<MainActivity> notOwnListPage = new ActivityTestRule<>(MainActivity.class);

  @Before
  public void setup() {
    // Mock DI
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );
  }

  private void givenUserHasPartner() {
    new ConcreteUserTestUtil()
      .injectLocalUser()
      .mockPartner();
  }

  private void whenPartnerVisitsZone() {
    // TODO: Insert code that detects when partner visits zone
  }

  private void andUserNavigatesToHomePage() {
    onView(withId(R.id.partners_location_list)).check(matches(isDisplayed()));
  }

  private void thenUserWillSeeAllZonesPartnerHasBeenThatDay() {
    // TODO: Insert code that detects if user can view list of location notifications
  }

  private void andTheNewZonePartnerJustEntered() {
    // TODO: Insert code that detects if user can view new location
  }

  private void whenPartnerDoesNotVisitNewZone() {
    // TODO
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
    whenPartnerVisitsZone();
    andUserNavigatesToHomePage();
    thenUserWillSeeAllZonesPartnerHasBeenThatDay();
  }
}
