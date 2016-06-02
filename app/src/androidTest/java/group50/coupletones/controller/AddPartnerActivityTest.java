package group50.coupletones.controller;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.assertj.core.api.Assertions.assertThat;
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
  public CoupleTones app;
  private Partner cachePartner;
  private UserTestUtil testUtil = new ConcreteUserTestUtil();
  @Rule
  public ActivityTestRule<AddPartnerActivity> rule = new ActivityTestRule(AddPartnerActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      // Stub getLocalUser method
      testUtil
        .injectLocalUser()
        // Fake a user in the database
        .injectUserWithEmail("sharmaine@email.com", user -> {
          user.getProperties().property("name").set("Sharmaine");
          user.getProperties().property("email").set("sharmaine@email.com");
          cachePartner = user;
          return user;
        });
      super.beforeActivityLaunched();
    }
  };

  /**
   * Test the login button click and if it calls sign in for the authenticator
   */
  @Test
  public void testOnClick() throws Throwable {
    onView(withId(R.id.email_address)).perform(typeText("sharmaine@email.com"));
    // Close the keyboard
    Espresso.closeSoftKeyboard();
    onView(withId(R.id.connect_button)).perform(click());
    verify(cachePartner, times(1)).requestPartner(any());
    assertThat(rule.getActivity().isFinishing()).isTrue();
  }
}
