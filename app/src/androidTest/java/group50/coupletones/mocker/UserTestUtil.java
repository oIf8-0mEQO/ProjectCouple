package group50.coupletones.mocker;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.auth.user.User;
import group50.coupletones.auth.user.UserFactory;
import group50.coupletones.auth.user.concrete.ConcretePartner;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.util.function.Consumer;
import group50.coupletones.util.function.Function;
import group50.coupletones.util.function.Supplier;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Henry Mao
 * @since 5/28/16
 */
public abstract class UserTestUtil {
  // User being mocked
  protected User user;

  public User get() {
    return user;
  }

  public Partner getPartner() {
    return ((LocalUser) user).getPartner().toBlocking().next().iterator().next();
  }

  public UserTestUtil mockProperty(String property) {
    return mockProperty(property, null);
  }

  public abstract UserTestUtil mockProperty(String property, Object value);

  public UserTestUtil injectLocalUser() {
    when(CoupleTones.global().app().isLoggedIn())
      .thenReturn(true);
    when(CoupleTones.global().app().getLocalUser())
      .thenReturn((LocalUser) user);
    return this;
  }

  public UserTestUtil mockPartner() {
    when(((LocalUser) user).getPartner()).thenReturn(Observable.just(mock(Partner.class)));
    return new MockUserTestUtil(getPartner());
  }

  public UserTestUtil injectUserWithId(String id, Function<Partner, Partner> builder) {
    UserFactory userFactory = CoupleTones.global().userFactory();

    Function<Sync, Partner> ctor = sync -> {
      ConcretePartner spy = spy(new ConcretePartner(sync));
      // Immediately load
      doReturn(Observable.just(spy)).when(spy).load();
      return spy;
    };
    UserFactory.Buildable<Partner> buildable =
      userFactory.new Buildable<Partner>(ctor, mock(Sync.class)) {
        @Override
        public Partner build() {
          return builder.apply(super.build());
        }
      };
    doReturn(buildable).when(userFactory).withId(any());
    return this;
  }

  public UserTestUtil injectPartner(String id) {
    ((LocalUser) user).setPartner(id);
    return this;
  }

  public UserTestUtil mockNoPartner() {
    when(((LocalUser) user).getPartner()).thenReturn(Observable.just(null));
    return this;
  }

  public UserTestUtil mockFavoriteLocationsAdd(Consumer<FavoriteLocation> adder) {
    doAnswer(
      ans -> {
        adder.accept((FavoriteLocation) ans.getArguments()[0]);
        return null;
      })
      .when((LocalUser) user)
      .addFavoriteLocation(any());
    return this;
  }

  public UserTestUtil mockFavoriateLocations(Supplier<List<FavoriteLocation>> locationGetter) {
    when(user.getFavoriteLocations())
      .then(ans -> locationGetter.get());
    return this;
  }
}
