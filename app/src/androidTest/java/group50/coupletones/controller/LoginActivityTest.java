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

    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
  }

  /**
   * Test the LoginActivity initialization for calls to Authenticator
   */
  @Test
  public void testOnCreate() {
    activity = getActivity();

    // Verify some Authenticator methods were called
    verify(activity.auth).bind(activity);
    verify(activity.auth).onSuccess(any());
    verify(activity.auth).autoSignIn();
  }

  /**
   * Test that auto sign in works when the user is already logged in
   */
  @Test
  public void testAutoSignIn() throws Exception {
    Authenticator<User, String> auth = CoupleTones.component().auth();

    final Wrapper<Function<User, User>> successWrapper = new Wrapper<>();

    // When the activity binds a success function, call it immediately.
    when(auth.onSuccess(any()))
      .then(invocation -> {
        successWrapper.obj = (Function) invocation.getArguments()[0];
        return invocation;
      });

    /*
     * When autoSignIn is called, automatically call the success function
     * to simulate user "already logged in" to app.
     */
    when(auth.autoSignIn())
      .then(invocation -> {
        assertThat(successWrapper.obj).isNotNull();
        successWrapper.obj.apply(mock(User.class));
        return auth;
      });

    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
      .addMonitor(MainActivity.class.getName(), null, false);

    activity = getActivity();

    // Activity should have auto signed in

    //Watch for the timeout 5 seconds, if the expected activity was created.
    Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
    // next activity is opened and captured.
    assertThat(nextActivity).isInstanceOf(MainActivity.class);
    nextActivity.finish();
  }

  /**
   * Test the login button click and if it calls sign in for the authenticator
   */
  @Test
  public void testOnClick() {
    activity = getActivity();

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
    // Bind stub methods
    Authenticator<User, String> auth = CoupleTones.component().auth();
    final Wrapper<Function<User, User>> successWrapper = new Wrapper<>();

    // When the activity binds a success function, call it immediately.
    when(auth.onSuccess(any())).then(invocation -> {
      successWrapper.obj = (Function) invocation.getArguments()[0];
      return invocation;
    });

    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
      .addMonitor(MainActivity.class.getName(), null, false);

    activity = getActivity();

    successWrapper.obj.apply(mock(User.class));

    //Watch for the timeout 5 seconds, if the expected activity was created.
    Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
    // next activity is opened and captured.
    assertThat(nextActivity).isInstanceOf(MainActivity.class);
    nextActivity.finish();
  }

  /**
   * Test that when login is not successful, MainActivity is not launched.
   */
  @Test
  public void testOnUserLoginFailure() {

    Authenticator<User, String> auth = CoupleTones.component().auth();
    final Wrapper<Function<User, User>> successWrapper = new Wrapper<>();

    // When the activity binds a success function, call it immediately.
    when(auth.onSuccess(any())).then(invocation -> {
      successWrapper.obj = (Function) invocation.getArguments()[0];
      return invocation;
    });

    // Disable auto sign in
    //TODO: Why is this set to begin with?
    when(auth.autoSignIn()).then(invocation -> auth);

    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
      .addMonitor(MainActivity.class.getName(), null, false);

    activity = getActivity();

    assertThat(successWrapper.obj).isNotNull();
    activity.runOnUiThread(() -> successWrapper.obj.apply(null));

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

  /**
   * A simple class that wraps an object. Used for tests with lambda expressions where values must be effectively final.
   *
   * @param <T>
   */
  class Wrapper<T> {
    T obj;
  }
}
