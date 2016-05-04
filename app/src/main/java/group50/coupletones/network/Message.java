package group50.coupletones.network;

import android.os.Bundle;

/**
 * Created by sharmaine on 5/4/16.
 */
public interface Message {
    String getId();
    String getType();
    Bundle getData();
}
