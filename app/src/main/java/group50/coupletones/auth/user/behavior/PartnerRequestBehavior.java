package group50.coupletones.auth.user.behavior;

import group50.coupletones.auth.user.User;
import group50.coupletones.util.properties.Properties;

import java.util.LinkedList;
import java.util.List;

/**
 * Provides the behavior for handling the local user's partner
 * @author Henry Mao
 */
public class PartnerRequestBehavior {
  /**
   * Object responsible for syncing the object with database
   */
  private final Properties properties;

  /**
   * A list of all partner Ids who is trying to request partnership
   * with this user.
   */
  private List<String> partnerRequests;

  public PartnerRequestBehavior(Properties properties) {
    this.properties = properties
      .property("partnerRequests")
      .bind(this);
  }

  /**
   * Requests to partner with this user.
   * @param requester The user sending the request
   */
  public void requestPartner(User requester) {
    if (partnerRequests == null) {
      partnerRequests = new LinkedList<>();
    }
    partnerRequests.add(0, requester.getId());
    properties
      .property("partnerRequests")
      .update();
  }

  void removeRequest(String requesterId) {
    if (partnerRequests != null) {
      partnerRequests.remove(requesterId);
    }
    properties
      .property("partnerRequests")
      .update();
  }
}
