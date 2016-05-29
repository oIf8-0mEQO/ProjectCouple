package group50.coupletones;

import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.User;
import group50.coupletones.auth.user.concrete.ConcreteLocalUser;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.function.Consumer;
import group50.coupletones.util.function.Supplier;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Henry Mao
 * @since 5/28/16
 */
public class UserMocker {
  User user;

  public UserMocker() {
  }

  public User get() {
    return user;
  }

  public UserMocker mockLocalUser() {
    user = mock(ConcreteLocalUser.class);
    when(CoupleTones.global().app().isLoggedIn())
      .thenReturn(true);
    when(CoupleTones.global().app().getLocalUser())
      .thenReturn((LocalUser) user);

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
