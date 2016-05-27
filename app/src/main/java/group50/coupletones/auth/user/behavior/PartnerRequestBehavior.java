package group50.coupletones.auth.user.behavior;

import group50.coupletones.auth.user.User;
import group50.coupletones.util.observer.ObservableProvider;
import group50.coupletones.util.observer.Properties;
import group50.coupletones.util.observer.Watch;
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
  private final Properties sync;

  /**
   * A list of all partner Ids who is trying to request partnership
   * with this user.
   */
  @Watch
  private List<String> partnerRequests = new LinkedList<>();

  public PartnerRequestBehavior(Properties sync) {
    this.sync = sync.set(this).subscribeAll();
  }

  /**
   * Requests to partner with this user.
   * @param requester The user sending the request
   */
  public void requestPartner(User requester) {
    partnerRequests.add(0, requester.getId());
    sync.publish("partnerRequests");
  }

  void removeRequest(String requesterId) {
    partnerRequests.remove(requesterId);
    sync.publish("partnerRequests");

  }

  public <T> Observable<T> getObservable(String name) {
    return sync.getObservable(name);
  }
}
