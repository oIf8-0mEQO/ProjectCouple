package group50.coupletones.network.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import group50.coupletones.network.Message;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.util.Taggable;
import group50.coupletones.util.function.Function;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author sharmaine
 * @since 5/4/16
 */
public class GcmManager implements NetworkManager, Taggable {
  private String PROJECT_NUMBER = "794558589013";
  private GoogleCloudMessaging gcm;
  private String regid;

  @Inject
  public GcmManager() {
  }

  @Override
  public AsyncTask<Void, Void, Boolean> send(Message message) {
    return new AsyncTask<Void, Void, Boolean>() {

      @Override
      protected Boolean doInBackground(Void... params) {
        try {
          gcm.send(PROJECT_NUMBER + "@gcm.googleapis.com", message.getId(), message.getData());
        } catch (IOException ex) {
          ex.printStackTrace();
          return false;
        }
        return true;
      }
    }.execute(null, null, null);

  }

  @Override
  public void onReceive(Function callBack) {

  }

  public AsyncTask<Void, Void, Boolean> register(Context context) {
    return new AsyncTask<Void, Void, Boolean>() {

      @Override
      protected Boolean doInBackground(Void... params) {
        try {
          if (gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(context.getApplicationContext());
          }

          regid = gcm.register(PROJECT_NUMBER);
          Log.i("GCM", "!!!!! " + regid);

        } catch (IOException ex) {
          ex.printStackTrace();
          return false;
        }
        return true;
      }

    }.execute(null, null, null);
  }
}
