package group50.coupletones.util;

import android.content.Context;
import group50.coupletones.CoupleTones;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Joseph
 * @since 5/30/16
 */
public class VibeToneTest {

  CoupleTones app;
  Context mockContext;

  @Before
  public void setup()
  {
    app = CoupleTones.global().app();

    mockContext = mock(Context.class);
    when(app.getApplicationContext()).thenReturn(mockContext);
  }

  @Test
  public void testVibeToneEquality()
  {
    VibeTone vib1 = VibeTone.getTone();
    VibeTone vib2 = VibeTone.getTone(0);
    VibeTone vib3 = VibeTone.getTone(1);
    assertThat(vib1).isEqualTo(vib2);
    assertThat(vib1).isNotEqualTo(vib3);
  }

  @Test
  public void testVibeTonePlay()
  {
    VibeTone tone = VibeTone.getTone();
    tone.playArrival();
    //TODO: Consider rewriting test so it tests more.
  }

}
