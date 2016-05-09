package group50.coupletones.bdd;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.Button;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.GoogleAuthenticator;
import group50.coupletones.auth.user.MockLocalUser;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.LoginActivity;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.di.DaggerInstanceComponent;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.di.module.AuthenticatorModule;
import group50.coupletones.util.function.Function;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests the User Logs Into CoupleTones story
 *
 * @author Henry Mao
 * @since 4/27/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserLogsIntoCoupleTones extends ActivityInstrumentationTestCase2<LoginActivity> {

  // Time out for activities
  private final int TIMEOUT = 5000;

  // The LoginActivity
  private LoginActivity activity;

  // The mock authenticator
  private Authenticator<User, String> authenticator;

  public UserLogsIntoCoupleTones() {
    super(LoginActivity.class);
  }

  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();

    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );

    // Inject mock authenticator
    authenticator = mock(Authenticator.class);

    CoupleTones.setInstanceComponentBuilder(
      DaggerInstanceComponent
        .builder()
        .authenticatorModule(new AuthenticatorModule() {
          //TODO: Extra method
          @Override
          protected Authenticator<User, String> provideAuth(GoogleAuthenticator auth) {
            return authenticator;
          }
        })
    );

    // Stub getLocalUser method
    when(CoupleTones.global().app().getLocalUser())
      .thenReturn(new MockLocalUser());

    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
  }

  private void whenISeeTheLoginPage() {
    activity = getActivity();
  }

  private void givenThatIAmNotLoggedIn() {
    when(CoupleTones.global().app().getLocalUser()).thenReturn(null);
    when(CoupleTones.global().app().isLoggedIn()).thenReturn(false);
  }

  // See only the login page
  private void thenIWillSeeTheLoginPage(Instrumentation.ActivityMonitor activityMonitor) {
    //Watch for the timeout
    //example values 5000 if in ms, or 5 if it's in seconds.
    Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT);
    // next activity is opened and captured.
    assertThat(nextActivity).isNull();
  }

  /**
   * Test the LoginActivity initialization for calls to Authenticator
   */
  @Test
  public void userNotLoggedIntoCoupleTones() {
    givenThatIAmNotLoggedIn();
    whenISeeTheLoginPage();
    // Verify some Authenticator methods were called
    verify(activity.auth).onSuccess(any());
    verify(activity.auth).autoSignIn();
  }

  private void thenICanOpenMyGoogleCredentials() {
    activity.runOnUiThread(() -> {
      Button button = (Button) activity.findViewById(R.id.sign_in_button);
      button.performClick();
      // Verify sign in is called
      verify(activity.auth).signIn(activity);
    });
/*
    onView(withId(R.id.sign_in_button)).perform(click());
    verify(activity.auth).signIn(activity);
  */
  }

  /**
   * Test the login button click and if it calls sign in for the authenticator
   */
  @Test
  public void userTriesToLogin() {
    givenThatIAmLoggedIn();
    whenISeeTheLoginPage();
    thenICanOpenMyGoogleCredentials();
  }


  private void givenThatIAmLoggedIn() {
    Authenticator<User, String> auth = authenticator;

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
  }

  private void thenIWillNotSeeTheLoginPage(Instrumentation.ActivityMonitor activityMonitor) {
    //Watch for the timeout 5 seconds, if the expected activity was created.
    Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT);
    // next activity is opened and captured.
    assertThat(nextActivity).isInstanceOf(MainActivity.class);
    nextActivity.finish();
  }

  /**
   * Test that auto sign in works when the user is already logged in
   */
  @Test
  public void userIsLoggedIntoCoupleTones() throws Exception {
    givenThatIAmLoggedIn();

    // Silent sign in is called upon onCreate, so monitor must be setup before getActivity()
    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
      .addMonitor(MainActivity.class.getName(), null, false);

    whenISeeTheLoginPage();

    // Activity should have auto signed in
    thenIWillNotSeeTheLoginPage(activityMonitor);
  }

  /**
   * Test that when login is successful, MainActivity is launched.
   */
  @Test
  public void testOnUserLoginSuccess() {
    // Bind stub methods
    Authenticator<User, String> auth = authenticator;
    final Wrapper<Function<User, User>> successWrapper = new Wrapper<>();

    // When the activity binds a success function, call it immediately.
    when(auth.onSuccess(any())).then(invocation -> {
      successWrapper.obj = (Function) invocation.getArguments()[0];
      return invocation;
    });

    activity = getActivity();

    // Monitor must be setup after activity
    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
      .addMonitor(MainActivity.class.getName(), null, false);

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
    Authenticator<User, String> auth = authenticator;
    final Wrapper<Function<User, User>> successWrapper = new Wrapper<>();

    // When the activity binds a success function, call it immediately.
    when(auth.onSuccess(any())).then(invocation -> {
      successWrapper.obj = (Function) invocation.getArguments()[0];
      return invocation;
    });

    this.activity = getActivity();

    // Monitor must be setup after activity
    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
      .addMonitor(MainActivity.class.getName(), null, false);

    assertThat(successWrapper.obj).isNotNull();
    this.activity.runOnUiThread(() -> successWrapper.obj.apply(null));

    thenIWillSeeTheLoginPage(activityMonitor);
  }

  /**
   * A simple class that wraps an object.
   * Used for tests with lambda expressions where values must be effectively final.
   *
   * @param <T>
   */
  class Wrapper<T> {
    T obj;
  }
}
