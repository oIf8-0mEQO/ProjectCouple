package group50.coupletones.network.message;

/**
 * Represents an object that can handle a message reception
 * event. The event will only be received if it is registered
 * with the {NetworkManager} and the message received will
 * always have the same type as registered.
 *
 * @author Henry Mao
 * @since 5/4/16
 */
public interface MessageReceiver {
  /**
   * Called when the message receiver receives a particular message of the type
   * the receiver registered with.
   *
   * @param message - The incoming message object that contains data from the server
   */
  void onReceive(Message message);
}
