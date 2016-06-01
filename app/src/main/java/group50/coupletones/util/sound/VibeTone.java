package group50.coupletones.util.sound;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import java.io.File;

import javax.inject.Inject;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * @Author Joseph
 * @Since 5/21/16
 */
public class VibeTone {

  private static VibeTone[] tones = {
    new VibeTone("/assets/tones/vibetone1.mps", new long[] {0, 2000}),//Index 0 / default tone.
    new VibeTone("/assets/tones/vibetone2.mps", new long[] {0, 400, 400, 400, 400, 400}),//Index 1.
    new VibeTone("/assets/tones/vibetone3.mps", new long[] {0, 800, 400, 800}),//Index 2.
    new VibeTone("/assets/tones/vibetone4.mps", new long[] {0, 1200, 400, 400}),//Index 3.
    new VibeTone("/assets/tones/vibetone5.mps", new long[] {0, 400, 400, 1200}),//Index 4.
    new VibeTone("/assets/tones/vibetone6.mps", new long[] {0, 800, 400, 200, 400, 200}),//Index 5.
    new VibeTone("/assets/tones/vibetone7.mps", new long[] {0, 200, 400, 800, 400, 200}),//Index 6.
    new VibeTone("/assets/tones/vibetone8.mps", new long[] {0, 200, 400, 200, 400, 800}),//Index 7.
  };

  @Inject
  public static CoupleTones app;

  private Ringtone sound;
  private long[] vibration;

  private static Ringtone arrivalSound = RingtoneManager.getRingtone(app.getApplicationContext(), Uri.fromFile(new File("/assets/tones/arrivaltone.mp3")));
  private static Ringtone departureSound = RingtoneManager.getRingtone(app.getApplicationContext(), Uri.fromFile(new File("/assets/tones/departuretone.mp3")));
  private static long[] arrivalVibration = new long[] {0, 1000};
  private static long[] departureVibration = new long[] {0, 400, 200, 400};
  private static long delay = 1500;//The amount of time in milliseconds to wait between arrival/departure global sound and specific sound.


  /**
   * @return the default vibetone.
   */
  public static VibeTone getTone()
  {
    return tones[0];
  }

  /**
   * @return the ith vibetone.
   */
  public static VibeTone getTone(int which)
  {
    if (which < 0 || which >  7) return tones[0];
    return tones[which];
  }

  private VibeTone(String filePath, long[] vibration)
  {
    this.vibration = vibration;
    Uri file = Uri.fromFile(new File(filePath));
    RingtoneManager.getRingtone(app.getApplicationContext(), file);
  }

  public void playArrival()
  {
    try
    {
      Vibrator vib = (Vibrator) app.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
      if (app.getLocalUser().vibrationEnabled()) vib.vibrate(arrivalVibration, 0);
      if (app.getLocalUser().soundEnabled()) arrivalSound.play();
      wait(delay);
      if (app.getLocalUser().vibrationEnabled()) vib.vibrate(vibration, 0);
      if (app.getLocalUser().soundEnabled()) arrivalSound.play();sound.play();
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  public void playDeparture()
  {
    try
    {
      Vibrator vib = (Vibrator) app.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
      if (app.getLocalUser().vibrationEnabled()) vib.vibrate(departureVibration, 0);
      if (app.getLocalUser().soundEnabled()) arrivalSound.play();departureSound.play();
      wait(delay);
      if (app.getLocalUser().vibrationEnabled()) vib.vibrate(vibration, 0);
      if (app.getLocalUser().soundEnabled()) arrivalSound.play();sound.play();
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public int hashCode()
  {
    for (int i = 0; i < tones.length; i++)
    {
      if (tones[i].equals(this)) return i;
    }
    throw new RuntimeException("VibeTones should only exist as one of the elements contained in tones.");
  }

  @Override
  public boolean equals(Object object)
  {
    try {
      VibeTone other = (VibeTone) object;
      if (!sound.equals(other.sound)) return false;
      if (!vibration.equals(other.vibration)) return false;
      return true;
    }
    catch(ClassCastException e)
    {
      return false;
    }
  }

}
