package group50.coupletones.network.sync;

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
  protected void buildSubjects(Class<?> scanClass) {
    super.buildSubjects(scanClass);
    listenFirebase();
  }

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
