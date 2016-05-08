package group50.coupletones.controller.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A TabFragment represents an fragment that is meant to be a tab in
 * MainActivity.
 *
 * @author Henry Mao
 */
public abstract class TabFragment<L> extends Fragment {

  private final Class<L> listenerClass;
  protected L listener;

  public TabFragment(Class<L> listenerClass) {
    this.listenerClass = listenerClass;
  }

  /**
   * @return The ID of the XML layout for this fragment
   */
  protected abstract int getResourceId();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(getResourceId(), container, false);
  }

  /**
   * onAttach attaches listener if valid
   */
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    // Attempt to attach listener
    if (listenerClass.isAssignableFrom(context.getClass())) {
      listener = (L) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement " + listenerClass.getName());
    }
  }

  /**
   * onDetach detaches if the
   * listener is null
   */
  @Override
  public void onDetach() {
    super.onDetach();
    listener = null;
  }
}
