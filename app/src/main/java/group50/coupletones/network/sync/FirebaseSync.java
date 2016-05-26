package group50.coupletones.network.sync;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import rx.subjects.BehaviorSubject;

import java.util.Map;

/**
 * @author Henry Mao
 */
public class FirebaseSync extends FieldSync {
  public FirebaseSync() {
  }

  public FirebaseSync(Object obj) {
    super(obj);
  }

  @Override
  public FieldSync watch(Object obj) {
    return new FirebaseSync(obj).setRef(ref);
  }

  @Override
  protected void buildSubjects(Class<?> scanClass) {
    super.buildSubjects(scanClass);
    listenFirebase();
  }

  //TODO: Need to dispose listeners
  private void listenFirebase() {
    for (Map.Entry<String, BehaviorSubject<?>> entry : observables.entrySet()) {
      String name = entry.getKey();
      BehaviorSubject subject = entry.getValue();

      ref.child(name)
        .addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            subject.onNext(dataSnapshot.getValue());
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
            throw databaseError.toException();
          }
        });
    }
  }
}
