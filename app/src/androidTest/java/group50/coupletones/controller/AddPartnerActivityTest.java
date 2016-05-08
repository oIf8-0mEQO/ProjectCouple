package group50.coupletones.controller;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.MockLocalUser;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Calvin on 5/7/2016.
 */
@RunWith(AndroidJUnit4.class)
public class AddPartnerActivityTest extends ActivityInstrumentationTestCase2<AddPartnerActivity> {

    private AddPartnerActivity activity;

    public AddPartnerActivityTest() {
        super(AddPartnerActivity.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        CoupleTones.setComponent(
                DaggerMockAppComponent
                        .builder()
                        .mockProximityModule(new MockProximityModule())
                        .build());

        // Stub getLocalUser method
        when(CoupleTones.component().app().getLocalUser())
                .thenReturn(new MockLocalUser());

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }

    /**
     * Test the login button click and if it calls sign in for the authenticator
     */
    @Test
    public void testOnClick() {
        activity = getActivity();

        activity.runOnUiThread(() -> {
            Button button = (Button) activity.findViewById(R.id.connect_button);
            button.performClick();
            // Verify sign in is called
            verify(activity.auth).signIn();
        });

        activity.runOnUiThread(() -> {
            Button button = (Button) activity.findViewById(R.id.skip_button);
            button.performClick();
            // Verify sign in is called
            verify(activity.auth).signIn();
        });
    }
}
