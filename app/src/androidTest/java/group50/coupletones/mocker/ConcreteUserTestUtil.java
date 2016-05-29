package group50.coupletones.mocker;

import group50.coupletones.auth.user.concrete.ConcreteLocalUser;
import group50.coupletones.network.sync.Sync;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 * @author Henry Mao
 * @since 5/29/16
 */
public class ConcreteUserTestUtil extends UserTestUtil {

  private Sync syncMock;

  public ConcreteUserTestUtil() {
    syncMock = mock(Sync.class);
    this.user = spy(new ConcreteLocalUser(syncMock));
  }

  public Sync getSync() {
    return syncMock;
  }

  @Override
  public UserTestUtil mockProperty(String property, Object value) {
    user
      .getProperties()
      .property(property)
      .set(value);
    return this;
  }
}
