package group50.coupletones.util.sound;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import group50.coupletones.CoupleTones;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author Joseph
 * @Since 5/21/16
 */
public class VibeTone {

  public static final int MAX_VIBETONE_COUNT = 10;
  public static final int ARRIVAL_VIBETONE = 10;
  public static final int DEPARTURE_VIBETONE = 11;
  private static VibeTone[] tones = {
    new VibeTone("/assets/tones/vibetone1.mps", new long[]{0, 2000}, "Pikachu"),//Index 0 / default tone.
    new VibeTone("/assets/tones/vibetone2.mps", new long[]{0, 400, 400, 400, 400, 400}, "Coin"),//Index 1.
    new VibeTone("/assets/tones/vibetone3.mps", new long[]{0, 800, 400, 800}, "Super Mario"),//Index 2.
    new VibeTone("/assets/tones/vibetone4.mps", new long[]{0, 1200, 400, 400}, "Pokemon Battle"),//Index 3.
    new VibeTone("/assets/tones/vibetone5.mps", new long[]{0, 400, 400, 1200}, "Kim Possible"),//Index 4.
    new VibeTone("/assets/tones/vibetone6.mps", new long[]{0, 800, 400, 200, 400, 200}, "Kakao"),//Index 5.
    new VibeTone("/assets/tones/vibetone7.mps", new long[]{0, 200, 400, 800, 400, 200}, "Bling"),//Index 6.
    new VibeTone("/assets/tones/vibetone8.mps", new long[]{0, 200, 400, 200, 400, 800}, "1 Up"),//Index 7.
    new VibeTone("/assets/tones/vibetone9.mps", new long[]{0, 800, 400, 800, 400, 800}, "Windows"),//Index 8.
    new VibeTone("/assets/tones/vibetone10.mps", new long[]{0, 800, 400, 800, 400, 800, 400, 800}, "Zelda"),//Index 9.
    new VibeTone("/assets/tones/arrivaltone.mp3", new long[]{0, 1000}, "Arrival"),//Index 10 / Arrival.
    new VibeTone("/assets/tones/departuretone.mps", new long[]{0, 400, 200, 400}, "Departure"),//Index 11 / Departure.
  };
  private static long delay = 1500;//The amount of time in milliseconds to wait between arrival/departure global sound and specific sound.
  @Inject
  public CoupleTones app;
  private Ringtone sound;
  private long[] vibration;
  private String name;


  private VibeTone(String filePath, long[] vibration, String name) {
    CoupleTones.global().inject(this);
    this.vibration = vibration;
    this.name = name;
    Uri file = Uri.fromFile(new File(filePath));
    RingtoneManager.getRingtone(app.getApplicationContext(), file);
  }

  /**
   * @return the default vibetone.
   */
  public static VibeTone getTone() {
    return tones[0];
  }

  /**
   * @return the ith vibetone.
   */
  public static VibeTone getTone(int which) {
    if (which < 0 || which >= MAX_VIBETONE_COUNT) return tones[0];
    return tones[which];
  }

  public static List<VibeTone> getVibeTones() {
    return Collections.unmodifiableList(Arrays.asList(tones));
  }

  public void playArrival() {
    try {
      if (app.getLocalUser().getVibrationSetting()) tones[ARRIVAL_VIBETONE].playVibrate();
      if (app.getLocalUser().getTonesSetting()) tones[ARRIVAL_VIBETONE].playSound();
      wait(delay);
      if (app.getLocalUser().getVibrationSetting()) playVibrate();
      if (app.getLocalUser().getTonesSetting()) playSound();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void playVibrate() {
    Vibrator vib = (Vibrator) app.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
    vib.vibrate(vibration, 0);
  }

  public void playSound() {
    sound.play();
  }

  public void playDeparture() {
    try {
      Vibrator vib = (Vibrator) app.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
      if (app.getLocalUser().getVibrationSetting()) tones[DEPARTURE_VIBETONE].playVibrate();
      if (app.getLocalUser().getTonesSetting()) tones[DEPARTURE_VIBETONE].playSound();
      wait(delay);
      if (app.getLocalUser().getVibrationSetting()) playVibrate();
      if (app.getLocalUser().getTonesSetting()) playSound();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public String getName() {
    return name;
  }

  public int getIndex() {
    for (int i = 0; i < tones.length; i++) {
      if (tones[i].equals(this)) return i;
    }
    throw new RuntimeException("VibeTones should only exist as one of the elements contained in tones.");
  }

  @Override
  public int hashCode() {
    return getIndex();
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof VibeTone) {
      VibeTone other = (VibeTone) object;
      if (!sound.equals(other.sound)) return false;
      if (!Arrays.equals(vibration, other.vibration)) return false;
      return true;
    }
    return false;
  }

}
