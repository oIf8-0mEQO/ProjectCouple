package group50.coupletones.auth.user.behavior;

import group50.coupletones.auth.user.User;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.network.sync.Syncable;
import group50.coupletones.util.ObservableProvider;
import rx.Observable;

import java.util.LinkedList;
import java.util.List;

/**
 * Provides the behavior for handling the local user's partner
 * @author Henry Mao
 */
public class PartnerRequestBehavior implements ObservableProvider {
  /**
   * Object responsible for syncing the object with database
   */
  private final Sync sync;

  /**
   * A list of all partner Ids who is trying to request partnership
   * with this user.
   */
  @Syncable
  List<String> partnerRequests = new LinkedList<>();

  public PartnerRequestBehavior(Sync sync) {
    this.sync = sync.watch(this).subscribeAll();
  }

  /**
   * Requests to partner with this user.
   * @param requester The user sending the request
   */
  public void requestPartner(User requester) {
    partnerRequests.add(0, requester.getId());
    sync.publish("partnerRequests");
  }

  public <T> Observable<T> getObservable(String name) {
    return sync.getObservable(name);
  }
}
