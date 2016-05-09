package group50.coupletones.controller;

import static org.assertj.core.api.Assertions.*;
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
import group50.coupletones.auth.user.Partner;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.message.OutgoingMessage;
import group50.coupletones.util.storage.Storage;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Calvin on 5/8/2016.
 */
@RunWith(AndroidJUnit4.class)
public class PartnerResponseActivityTest extends ActivityInstrumentationTestCase2<AddPartnerActivity> {
    private PartnerResponseActivity activity;

    public NetworkManager network;

    public CoupleTones app;

    public PartnerResponseActivityTest() {
            super(AddPartnerActivity.class);
        }

    private OutgoingMessage mockMessage;

    private Partner partner;

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
    public void testSendResponse() {
        activity = new PartnerResponseActivity();

        activity.runOnUiThread(() -> {
            mockMessage = mock(OutgoingMessage.class);
            when(mockMessage.getString("partner")).thenReturn("rah005@ucsd.edu");
            //when(mockMessage.getString("partner")).thenReturn("rah005@ucsd.edu");
            network = CoupleTones.component().network();

            Button button = (Button) activity.findViewById(R.id.accept_button);
            button.performClick();

            // Verify sign in is called
            assertThat(app.getLocalUser().getName()).isEqualTo("partner");
            assertThat(app.getLocalUser().getEmail()).isEqualTo("rah005@ucsd.edu");

        });

        activity.runOnUiThread(() -> {
            mockMessage = mock(OutgoingMessage.class);
            when(mockMessage.getString("partner")).thenReturn("rah005@ucsd.edu");
            network = CoupleTones.component().network();

            Button button = (Button) activity.findViewById(R.id.reject_button);
            button.performClick();

            // Verify sign in is called
            verify(network).send(any());

            assertThat(app.getLocalUser().getName()).isEqualTo(null);
            assertThat(app.getLocalUser().getEmail()).isEqualTo(null);
        });
    }
}

