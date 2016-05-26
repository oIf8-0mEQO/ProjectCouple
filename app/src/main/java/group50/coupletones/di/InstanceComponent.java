package group50.coupletones.di;

import android.content.Context;
import dagger.Component;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.user.User;
import group50.coupletones.auth.user.UserFactory;
import group50.coupletones.di.module.AuthenticatorModule;
import group50.coupletones.di.module.ContextModule;
import group50.coupletones.di.module.UserFactoryModule;

/**
 * The dependency injection global for instance based objects
 * @author Henry Mao
 */

@Component(
  modules = {
    ContextModule.class,
    AuthenticatorModule.class,
    UserFactoryModule.class
  }
)
public interface InstanceComponent {

  Context context();

  Authenticator<User, String> auth();

  UserFactory userFactory();
}
