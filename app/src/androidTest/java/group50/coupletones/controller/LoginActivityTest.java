package group50.coupletones.controller;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * The LoginActivity test to test the LoginActivity
 *
 * @author Henry Mao
 * @since 4/27/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

  private LoginActivity activity;

  public LoginActivityTest() {
    super(LoginActivity.class);
  }

  @Before
  public void setUp() throws Exception {
    super.setUp();

    // Injecting the Instrumentation instance is required
    // for your test to run with AndroidJUnitRunner.
    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    activity = getActivity();
  }

  /**
   * Test the login
   */
  @Test
  public void testAuth() {

  }
}
