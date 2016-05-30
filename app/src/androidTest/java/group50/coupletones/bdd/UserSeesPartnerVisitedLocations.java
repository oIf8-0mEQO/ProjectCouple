package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Henry Mao
 * @since 5/29/16
 */
public class UserSeesPartnerVisitedLocations {

  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

  private UserTestUtil testUtil = new ConcreteUserTestUtil();

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


  private void givenUserHasAPartner() {
    testUtil
      .injectLocalUser()
      .mockPartner();
  }

  private void whenThePartnerVistsAZone() {
    Partner partner = testUtil.getPartner();
    // partner.getVisitedLocations();
  }

  private void andTheUserNavigatesToTheHomePage() {
    onView(withId(R.id.partner_locations)).perform(click());
  }

  private void thenTheUserWillSeeAllZonesPartnerHas() {
    //TODO
  }

  private void thenTheUserWillSeeTheNewZonePartnerEntered() {
    //TODO
  }

  @Test
  public void userPartnerEntersLocation() {
    givenUserHasAPartner();
    whenThePartnerVistsAZone();
    andTheUserNavigatesToTheHomePage();
    thenTheUserWillSeeAllZonesPartnerHas();
    thenTheUserWillSeeTheNewZonePartnerEntered();
  }
}
