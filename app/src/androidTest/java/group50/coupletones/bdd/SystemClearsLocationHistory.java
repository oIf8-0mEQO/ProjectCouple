package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import group50.coupletones.CoupleTones;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Sharmaine Manalo
 * @since 5/30/16
 */
public class SystemClearsLocationHistory {
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

    userMocker
        .mockPartner()
        .mockProperty("name", "Sharmaine")
        .mockProperty("email", "sharmaine@email.com");

    assertThat(userMocker.getPartner()).isNotNull();

  }

  private void whenUserViewsPartnersVisitedLocations() {

  }

  private void thenAppDisplaysListofPartnersVisitedLocationsSinceLast3AM() {

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
