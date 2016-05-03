package group50.coupletones.util.network;

import group50.coupletones.util.function.Function;

/**
 * Created by sharmaine on 5/2/16.
 */
public interface MessageNetwork {
    void send(String message);
    void onReceive(Function callBack);
}