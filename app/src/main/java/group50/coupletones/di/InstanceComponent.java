package group50.coupletones.di;

import android.content.Context;
import dagger.Component;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.user.User;
import group50.coupletones.di.module.AuthenticatorModule;
import group50.coupletones.di.module.ContextModule;

/**
 * The dependency injection global for instance based objects
 * @author Henry Mao
 */

@Component(
  modules = {
    ContextModule.class,
    AuthenticatorModule.class
  }
)
public interface InstanceComponent {

  Context context();

  Authenticator<User, String> auth();
}
