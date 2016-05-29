package group50.coupletones.mocker;

import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.User;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Henry Mao
 * @since 5/29/16
 */
public class MockUserTestUtil extends UserTestUtil {

  private Map<String, Object> propMock;

  public MockUserTestUtil() {
    this(mock(LocalUser.class));
  }

  public MockUserTestUtil(User user) {
    this.user = user;
  }

  @Override
  public UserTestUtil mockProperty(String property, Object value) {
    if (propMock == null) {
      propMock = new HashMap<>();
      when(user.observable(any()))
        .then(arg -> {
          String propertyName = (String) arg.getArguments()[0];
          return Observable.just(propMock.get(propertyName));
        });

      when(user.observable(any(), any()))
        .then(arg -> {
          String propertyName = (String) arg.getArguments()[0];
          return Observable.just(propMock.get(propertyName));
        });
    }

    propMock.put(property, value);
    return this;
  }
}
