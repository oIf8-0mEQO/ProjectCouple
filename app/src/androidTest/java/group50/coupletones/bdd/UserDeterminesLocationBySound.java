package group50.coupletones.bdd;

import org.junit.Before;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;

import static org.mockito.Mockito.*;

/**
 * Created by Joseph on 6/1/2016.
 */
public class UserDeterminesLocationBySound {

  CoupleTones app;
  LocalUser user;

  @Before
  public void setup()
  {
    user = mock(LocalUser.class);
    when(app.getLocalUser()).thenReturn(user);
  }

}
