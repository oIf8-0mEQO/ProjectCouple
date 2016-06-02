package group50.coupletones.network.gcm;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.network.fcm.FcmManager;
import group50.coupletones.network.fcm.message.MessageReceiver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Henry Mao
 * @since 5/7/16
 */

/**
 * GCM Manager test
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FcmManagerTest {

  private FcmManager manager;

  @Before
  public void setUp() throws Exception {
    manager = new FcmManager();
  }

  @Test(expected = RuntimeException.class)
  public void failRegister() throws Exception {
    MessageReceiver mockReceiver = mock(MessageReceiver.class);
    manager.register(mockReceiver);
  }

  @Test
  public void successHandle() throws Exception {
    MessageReceiver mockReceiver = mock(MessageReceiver.class);
    manager.register("request", mockReceiver);

    Bundle bundle = new Bundle();
    bundle.putString("type", "request");
    manager.handleReceive(bundle);

    verify(mockReceiver).onReceive(any());
  }

  @Test(expected = RuntimeException.class)
  public void failHandle() throws Exception {
    MessageReceiver mockReceiver = mock(MessageReceiver.class);
    manager.register("request", mockReceiver);

    Bundle bundle = new Bundle();
    bundle.putString("type", "request2");
    manager.handleReceive(bundle);
  }

  @Test(expected = RuntimeException.class)
  public void unregister() throws Exception {
    MessageReceiver mockReceiver = mock(MessageReceiver.class);
    manager.register("request", mockReceiver);
    manager.unregister("request");

    Bundle bundle = new Bundle();
    bundle.putString("type", "request2");
    manager.handleReceive(bundle);
  }

  @Test(expected = RuntimeException.class)
  public void registerDuplicate() throws Exception {
    MessageReceiver mockReceiver = mock(MessageReceiver.class);
    manager.register("request", mockReceiver);
    manager.register("request", mockReceiver);
  }

  @Test
  public void handleOther() throws Exception {
    MessageReceiver receiver1 = mock(MessageReceiver.class);
    manager.register("request1", receiver1);
    MessageReceiver receiver2 = mock(MessageReceiver.class);
    manager.register("request2", receiver2);

    Bundle bundle = new Bundle();
    bundle.putString("type", "request1");
    manager.handleReceive(bundle);

    verify(receiver1, times(1)).onReceive(any());
    verify(receiver2, times(0)).onReceive(any());

    Bundle bundle2 = new Bundle();
    bundle2.putString("type", "request2");
    manager.handleReceive(bundle2);

    verify(receiver1, times(1)).onReceive(any());
    verify(receiver2, times(1)).onReceive(any());
  }
}
