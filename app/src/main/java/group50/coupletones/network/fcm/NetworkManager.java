package group50.coupletones.network.fcm;

/**
 * @author Sharmaine Manalo
 * @since 2/5/16
 */

import group50.coupletones.network.fcm.message.Message;
import rx.Observable;
import rx.subjects.Subject;

/**
 * Network manager interface
 */
public interface NetworkManager {

  /**
   * Holds a stream of messages that can be subscribed to and
   * listened.
   * @return The incoming stream of messages from the server.
   */
  Observable<Message> getIncomingStream();

  /**
   * Holds a stream of outgoing messages that can be published
   * to, which, in effect, sends the message to the server.
   * @return The outgoing stream of messages to the server.
   */
  Subject<Message, Message> getOutgoingStream();
}