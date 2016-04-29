package group50.coupletones.controller;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import dagger.Component;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.User;
import group50.coupletones.di.AppComponent;
import group50.coupletones.di.MockApplicationModule;
import group50.coupletones.di.MockAuthenticatorModule;
import group50.coupletones.util.function.Function;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Singleton;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * The LoginActivity test to test the LoginActivity
 *
 * @author Henry Mao
 * @since 4/27/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

  // The LoginActivity
  private LoginActivity activity;
  private Function<User, String> successCallback;

  public LoginActivityTest() {
    super(LoginActivity.class);
  }

  @Before
  public void setUp() throws Exception {
    super.setUp();

    CoupleTones.setComponent(
      DaggerLoginActivityTest_TestAppComponent
        .builder()
        .build());

    // Bind stub methods
    Authenticator<User, String> auth = CoupleTones.component().auth();
    when(auth.onSuccess(any())).then(invocation -> {
      successCallback = (Function) invocation.getArguments()[0];
      return invocation;
    });

    // Injecting the Instrumentation instance is required
    // for your test to run with AndroidJUnitRunner.
    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    activity = getActivity();
  }

  /**
   * Test the LoginActivity initialization for calls to Authenticator
   */
  @Test
  public void testOnCreate() {
    // Verify some Authenticator methods were called
    verify(activity.auth).bind(activity);
    verify(activity.auth).autoSignIn();
  }

  /**
   * Test the login button click and if it calls sign in for the authenticator
   */
  @Test
  public void testOnClick() {
    activity.runOnUiThread(() -> {
      Button button = (Button) activity.findViewById(R.id.sign_in_button);
      button.performClick();
      // Verify sign in is called
      verify(activity.auth).signIn();
    });
  }

  /**
   * Test that when login is successful, MainActivity is launched.
   */
  @Test
  public void testOnUserLoginSuccess() {
    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
      .addMonitor(MainActivity.class.getName(), null, false);

    User mockUser = mock(User.class);
    successCallback.apply(mockUser);

    //Watch for the timeout
    //example values 5000 if in ms, or 5 if it's in seconds.
    Activity nextActivity = (MainActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
    // next activity is opened and captured.
    assertThat(nextActivity).isInstanceOf(MainActivity.class);
    nextActivity.finish();
  }

  /**
   * Test that when login is not successful, MainActivity is not launched.
   */
  @Test
  public void testOnUserLoginFailure() {
    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
      .addMonitor(MainActivity.class.getName(), null, false);

    activity.runOnUiThread(() -> successCallback.apply(null));

    //Watch for the timeout
    //example values 5000 if in ms, or 5 if it's in seconds.
    Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
    // next activity is opened and captured.
    assertThat(nextActivity).isNull();
  }

  /**
   * The dependency injection component for the entire app using mocks.
   *
   * @author Henry Mao
   * @since 28/4/2016
   */
  @Singleton
  @Component(
    modules = {
      MockAuthenticatorModule.class,
      MockApplicationModule.class
    }
  )
  public static interface TestAppComponent extends AppComponent {
  }
}
