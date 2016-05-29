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

  private static VibeTone[] tones = {//TODO: Collect sounds and implement this.
    new VibeTone("", new long[] {0, 2000}),//Index 0 / default tone.
    new VibeTone("", new long[] {0, 400, 400, 400, 400, 400}),//Index 1.
    new VibeTone("", new long[] {0, 800, 400, 800}),//Index 2.
    new VibeTone("", new long[] {0, 1200, 400, 400}),//Index 3.
    new VibeTone("", new long[] {0, 400, 400, 1200}),//Index 4.
    new VibeTone("", new long[] {0, 800, 400, 200, 400, 200}),//Index 5.
    new VibeTone("", new long[] {0, 200, 400, 800, 400, 200}),//Index 6.
    new VibeTone("", new long[] {0, 200, 400, 200, 400, 800}),//Index 7.
  };

  @Inject
  public CoupleTones app;

  private Ringtone sound;
  private long[] vibration;

  private static Ringtone arrivalSound;
  private static Ringtone departureSound;
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
      vib.vibrate(arrivalVibration, 0);
      arrivalSound.play();
      wait(delay);
      vib.vibrate(vibration, 0);
      sound.play();
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
      vib.vibrate(departureVibration, 0);
      departureSound.play();
      wait(delay);
      vib.vibrate(vibration, 0);
      sound.play();
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  public boolean equals(VibeTone other)
  {
    if (!sound.equals(other.sound)) return false;
    if (!vibration.equals(other.vibration)) return false;
    return true;
  }

}
