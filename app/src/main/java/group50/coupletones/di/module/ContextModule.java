package group50.coupletones.di.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

/**
 * Dependency injection module to provide the context in the dependency graph
 * @author Henry Mao
 */
@Module
public class ContextModule {

  private final Context context;

  public ContextModule(Context context) {
    this.context = context;
  }

  @Provides
  Context context() {
    return context;
  }
}
