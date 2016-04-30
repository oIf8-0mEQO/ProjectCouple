package group50.coupletones.controller.tab;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.w3c.dom.Text;

import javax.inject.Inject;

import group50.coupletones.R;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.GoogleUser;
import group50.coupletones.auth.User;
import group50.coupletones.controller.LoginActivity;
import group50.coupletones.controller.MainActivity;

/**
 * A simple {@link Fragment} subclass for the Settings tab.
 * Activities that contain this fragment must implement the {@link Listener} interface to handle interaction events.
 * Use the {@link SettingsFragment#build} factory class to create an instance of this fragment.
 */
public class SettingsFragment extends TabFragment<SettingsFragment.Listener> {

  public SettingsFragment() {
    super(Listener.class);
  }

  /**
   * The instance of the GoogleUser and Object for authentication.
   */
  @Inject
  GoogleUser user;
  public Authenticator<User, String> auth;

  /**
   * Use this factory method to create a new instance of SettingsFragment.
   */
  public static SettingsFragment build() {
    SettingsFragment fragment = new SettingsFragment();
    Bundle args = new Bundle();
    // TODO: Set arguments
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  protected int getResourceId() {
    return R.layout.fragment_settings;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      //TODO: Read arguments
    }
  }

  /**
   * A method that sets the font of each textview on the Settings Fragment.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_settings, container, false);
    Typeface pierSans = Typeface.createFromAsset(getActivity().getAssets(),
      getString(R.string.pier_sans));

    // User's Profile Cardview
    TextView yourProfileText = (TextView) v.findViewById(R.id.my_profile_header);
    TextView yourNameText = (TextView) v.findViewById(R.id.your_name_header);
    String yourName = user.getId();
    TextView yourAccountText = (TextView) v.findViewById(R.id.your_account_header);
    String yourAccount = user.getEmail();

    // Partner's Profile Cardview
    // TODO: Change partner's name/email to get keys from backend
    TextView partnersProfileText = (TextView) v.findViewById(R.id.partners_profile_text);
    TextView partnerNameText = (TextView) v.findViewById(R.id.partner_name_header);
    TextView partnerName = (TextView) v.findViewById(R.id.partner_name);
    TextView partnerAccountText = (TextView) v.findViewById(R.id.partner_account_header);
    TextView partnerAccount = (TextView) v.findViewById(R.id.partner_email);

    TextView logoutButton = (TextView) v.findViewById(R.id.logout_button);
    yourProfileText.setTypeface(pierSans);
    yourNameText.setTypeface(pierSans);
    yourNameText.setText(yourName);
    yourAccountText.setTypeface(pierSans);
    yourAccountText.setText(yourAccount);
    partnersProfileText.setTypeface(pierSans);
    partnerNameText.setTypeface(pierSans);
    partnerName.setTypeface(pierSans);
    partnerAccountText.setTypeface(pierSans);
    partnerAccount.setTypeface(pierSans);
    logoutButton.setTypeface(pierSans);
    return v;
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {

      case R.id.logout_button:
        auth.signOut();
        if (user.getId() == null)
          updateUI();
        else
          Toast.makeText(getContext(), "Sign Out Unsuccessful", Toast.LENGTH_SHORT).show();

        break;
    }
  }

  /**
   * After a successful signOut(), user will be taken to Login page.
   */
  private void updateUI() {
    Intent intent = new Intent(getContext(), LoginActivity.class);
    startActivity(intent);
    Log.d(getTag(), "Signed Out Successfully");
  }


  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface Listener {

    // TODO: Fill with interface methods
  }
}
