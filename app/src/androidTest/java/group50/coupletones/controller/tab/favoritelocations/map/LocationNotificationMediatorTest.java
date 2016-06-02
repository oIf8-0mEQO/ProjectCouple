package group50.coupletones.controller.tab.favoritelocations.map;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.network.fcm.NetworkManager;
import group50.coupletones.network.fcm.message.Message;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import rx.subjects.Subject;

import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Henry Mao
 * @since 5/29/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocationNotificationMediatorTest {
  private LocationNotificationMediator mediator;
  private UserTestUtil testUtil = new ConcreteUserTestUtil();

  @Before
  public void setup() {
    testUtil.injectLocalUser();
    testUtil.injectSpyPartner();
    mediator = new LocationNotificationMediator();
  }

  @Test
  public void testEnterLocation() {
    // Make sure FCM message is sent
    NetworkManager network = CoupleTones.global().network();
    Subject<Message, Message> spySubject = spy(Subject.class);
    doReturn(spySubject).when(network).getOutgoingStream();

    VisitedLocationEvent location = new VisitedLocationEvent("Home", new LatLng(0, 0), new Date(), new Date(), null);
    mediator.onEnterLocation(location);

    // Verify the GCM message has exactly the correct arguments.
    verify(spySubject, times(1)).onNext(argThat(new Matcher<Message>() {
      @Override
      public boolean matches(Object item) {
        if (item instanceof Message) {
          Message msg = (Message) item;
          Map<String, Object> notification = msg.getNotification();
          return "Henry visited Home".equals(notification.get("title")) &&
            mediator.formatUtility.formatDate(location.getTimeVisited()).equals(notification.get("body")) &&
            !((String) notification.get("icon")).isEmpty();
        }
        return false;
      }

      @Override
      public void describeMismatch(Object item, Description mismatchDescription) {
        if (item instanceof Message) {
          Message msg = (Message) item;
          Map<String, Object> notification = msg.getNotification();
          mismatchDescription.appendText((String) notification.get("title"));
          mismatchDescription.appendText((String) notification.get("body"));
          mismatchDescription.appendText((String) notification.get("icon"));
        }
      }

      @Override
      public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

      }

      @Override
      public void describeTo(Description description) {

      }
    }));

    // Make sure the visited location is added.
    LocalUser localUser = CoupleTones.global().app().getLocalUser();
    verify(localUser, times(1)).addVisitedLocation(location);
    assertThat(localUser.getVisitedLocations()).hasSize(1);
    assertThat(localUser.getVisitedLocations()).contains(location);
  }
}
