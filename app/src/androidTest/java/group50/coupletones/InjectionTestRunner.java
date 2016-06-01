package group50.coupletones;

import android.app.Application;
import android.support.test.runner.AndroidJUnitRunner;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;

/**
 * Performs mock dependency injection before app is injected.
 */
public class InjectionTestRunner extends AndroidJUnitRunner {
  @Override
  public void callApplicationOnCreate(Application app) {
    // Mock DI
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );
    super.callApplicationOnCreate(app);
  }
}