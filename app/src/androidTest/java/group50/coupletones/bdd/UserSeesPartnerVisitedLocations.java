package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
    testUtil.injectLocalUser();
  }


  private void givenUserHasAPartner() throws Throwable {
    rule.runOnUiThread(() -> testUtil.injectSpyPartner());
  }

  private void whenThePartnerVisitsAZone() {
    //Partner partner = testUtil.getPartner();
    // partner.getVisitedLocations();
    //TODO
  }

  private void andTheUserNavigatesToTheHomePage() {
    //onView(withId(R.id.partner_locations)).perform(click());
    //TODO
  }

  private void thenTheUserWillSeeAllZonesPartnerHas() {
    //TODO
  }

  private void thenTheUserWillSeeTheNewZonePartnerEntered() {
    //TODO
  }

  @Test
  public void userPartnerEntersLocation() throws Throwable {
    givenUserHasAPartner();
    whenThePartnerVisitsAZone();
    andTheUserNavigatesToTheHomePage();
    thenTheUserWillSeeAllZonesPartnerHas();
    thenTheUserWillSeeTheNewZonePartnerEntered();
  }
}
