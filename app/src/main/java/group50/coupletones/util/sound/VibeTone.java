package group50.coupletones.util.sound;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * @Author Joseph
 * @Since 5/21/16
 */
public class VibeTone {

  private VibeTone() {
    //TODO: Implement this.
  }

  /**
   * @return the default vibetone.
   */
  public static VibeTone getTone()
  {
    return new VibeTone();
  }

  /**
   * @return the ith vibetone.
   */
  public static VibeTone getTone(int which)
  {
    return new VibeTone();
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof VibeTone;//TODO: Implement this.
  }

}
