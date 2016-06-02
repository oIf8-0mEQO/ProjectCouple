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

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

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

  public abstract UserTestUtil mockProperty(String property, Object value);

  public UserTestUtil injectLocalUser() {
    when(CoupleTones.global().app().isLoggedIn())
      .thenReturn(true);
    when(CoupleTones.global().app().getLocalUser())
      .thenReturn((LocalUser) user);

    // By default, inject a fake user name
    mockProperty("name", "Henry");
    mockProperty("email", "henry@email.com");
    return this;
  }

  /**
   * Shorthand for injecting partner.
   * @return Self instance
   */
  public UserTestUtil injectSpyPartner() {
    injectUserWithId("sharmaine", user -> {
      user.getProperties().property("name").set("Sharmaine");
      user.getProperties().property("email").set("sharmaine@email.com");
      return user;
    });
    injectPartner("sharmaine");
    return this;
  }

  /**
   * Injects a user to UserFactory, as if the user was "loaded" from database
   * @param id The ID of the user
   * @param builder The function for building the user object
   * @return Self instance
   */
  public UserTestUtil injectUserWithId(String id, Function<Partner, Partner> builder) {
    UserFactory userFactory = CoupleTones.global().userFactory();

    Function<Sync, Partner> ctor = getSyncPartnerFunction();
    UserFactory.Buildable<Partner> buildable = getPartnerBuildable(builder, userFactory, ctor);
    doReturn(buildable).when(userFactory).withId(id);
    return this;
  }

  public UserTestUtil injectUserWithEmail(String email, Function<Partner, Partner> builder) {
    UserFactory userFactory = CoupleTones.global().userFactory();
    Function<Sync, Partner> ctor = getSyncPartnerFunction();
    UserFactory.Buildable<Partner> buildable = getPartnerBuildable(builder, userFactory, ctor);
    doReturn(Observable.just(buildable)).when(userFactory).withEmail(email);
    return this;
  }

  private UserFactory.Buildable<Partner> getPartnerBuildable(
    final Function<Partner, Partner> builder,
    UserFactory userFactory,
    final Function<Sync, Partner> ctor) {
    return userFactory.new Buildable<Partner>(ctor, mock(Sync.class)) {
      @Override
      public Partner build() {
        return builder.apply(super.build());
      }
    };
  }

  private Function<Sync, Partner> getSyncPartnerFunction() {
    return sync -> {
      ConcretePartner spy = spy(new ConcretePartner(sync));
      // Immediately load
      doReturn(Observable.just(spy)).when(spy).load();
      return spy;
    };
  }

  public UserTestUtil injectPartner(String id) {
    ((LocalUser) user).setPartner(id);
    return this;
  }

  public UserTestUtil injectNoPartner() {
    return injectPartner(null);
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

  public UserTestUtil mockFavoriteLocations(Supplier<List<FavoriteLocation>> locationGetter) {
    when(user.getFavoriteLocations())
      .then(ans -> locationGetter.get());
    return this;
  }
}
