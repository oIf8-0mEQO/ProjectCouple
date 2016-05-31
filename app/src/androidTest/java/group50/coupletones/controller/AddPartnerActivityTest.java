package group50.coupletones.controller;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Calvin
 * @since 5/7/2016
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddPartnerActivityTest {
  @Rule
  public ActivityTestRule<AddPartnerActivity> rule = new ActivityTestRule<>(AddPartnerActivity.class);

  public CoupleTones app;
  private UserTestUtil testUtil;

  @Before
  public void setUp() {
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build());

    // Stub getLocalUser method
    testUtil =
      new ConcreteUserTestUtil()
        .injectLocalUser();

    testUtil.injectSpyPartner();
  }

  /**
   * Test the login button click and if it calls sign in for the authenticator
   */
  @Test
  public void testOnClick() throws Throwable {
    onView(withId(R.id.connect_button)).perform(click());
    verify(testUtil.getPartner(), times(1)).requestPartner(any());
  }
}
