package group50.coupletones;

import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.function.Consumer;
import group50.coupletones.util.function.Supplier;
import rx.Observable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Henry Mao
 * @since 5/28/16
 */
public class UserMocker {
  private User user;

  private Map<String, Object> propMock;


  public UserMocker(User user) {
    this.user = user;
  }

  public User get() {
    return user;
  }

  public Partner getPartner() {
    return ((LocalUser) user).getPartner().toBlocking().first();
  }

  public UserMocker mockProperty(String property) {
    return mockProperty(property, null);
  }

  public UserMocker mockProperty(String property, Object value) {
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

  public UserMocker injectLocalUser() {
    when(CoupleTones.global().app().isLoggedIn())
      .thenReturn(true);
    when(CoupleTones.global().app().getLocalUser())
      .thenReturn((LocalUser) user);
    return this;
  }

  public UserMocker mockPartner() {
    when(((LocalUser) user).getPartner()).thenReturn(Observable.just(mock(Partner.class)));
    return new UserMocker(getPartner());
  }

  public UserMocker mockNoPartner() {
    when(((LocalUser) user).getPartner()).thenReturn(Observable.just(null));
    return this;
  }

  public UserMocker mockFavoriteLocationsAdd(Consumer<FavoriteLocation> adder) {
    doAnswer(
      ans -> {
        adder.accept((FavoriteLocation) ans.getArguments()[0]);
        return null;
      })
      .when((LocalUser) user)
      .addFavoriteLocation(any());
    return this;
  }

  public UserMocker mockFavoriateLocations(Supplier<List<FavoriteLocation>> locationGetter) {
    when(user.getFavoriteLocations())
      .then(ans -> locationGetter.get());
    return this;
  }
}
