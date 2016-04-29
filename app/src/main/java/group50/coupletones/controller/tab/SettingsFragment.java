package group50.coupletones.controller.tab;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.w3c.dom.Text;

import javax.inject.Inject;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;

/**
 * A simple {@link Fragment} subclass for the Favorite Locations tab.
 * Activities that contain this fragment must implement the {@link Listener} interface to handle interaction events.
 * Use the {@link SettingsFragment#build} factory class to create an instance of this fragment.
 */
public class SettingsFragment extends TabFragment<SettingsFragment.Listener> {

  @Inject
  CoupleTones app;

  public SettingsFragment() {
    super(Listener.class);
  }

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

    CoupleTones.component().inject(this);

    if (getArguments() != null) {
      //TODO: Read arguments
    }
  }

  /**
   * A method that sets the font of each textview/string on the Settings Fragment.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_settings, container, false);
    Typeface pierSans = Typeface.createFromAsset(getActivity().getAssets(),
      getString(R.string.pier_sans));

    TextView yourProfileText = (TextView) v.findViewById(R.id.my_profile_header);
    TextView yourNameText = (TextView) v.findViewById(R.id.your_name_header);
    String yourName = app.getLocalUser().getName();
    TextView yourAccountText = (TextView) v.findViewById(R.id.your_account_header);
    String yourAccount = app.getLocalUser().getEmail();
    v.findViewById(R.id.your_email);
    TextView partnersProfileText = (TextView) v.findViewById(R.id.partners_profile_text);
    TextView partnerNameText = (TextView) v.findViewById(R.id.partner_name_header);

    //TODO: change partnerName/partnerAccount to get keys from server?
    TextView partnerName = (TextView) v.findViewById(R.id.partner_name);
    TextView partnerAccountText = (TextView) v.findViewById(R.id.partner_account_header);
    TextView partnerAccount = (TextView) v.findViewById(R.id.partner_email);
    //TextView editProfileButton = (TextView) v.findViewById(R.id.edit_profile_button);
    TextView logoutButton = (TextView) v.findViewById(R.id.logout_button);
    //TextView removePartnerButton = (TextView) v.findViewById(R.id.remove_partner_button);
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
    //editProfileButton.setTypeface(pierSans);
    logoutButton.setTypeface(pierSans);
    //removePartnerButton.setTypeface(pierSans);

    setupLogoutButton(inflater, container);
    setupEditPartnerButton(inflater, container);

    return v;
  }


  private void setupLogoutButton(LayoutInflater inflater, ViewGroup container) {
    View v = inflater.inflate(R.layout.fragment_settings, container, false);

    Button logoutButton = (Button) v.findViewById(R.id.logout_button);
    logoutButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        switch (v.getId()) {
          // ...
          case R.id.logout_button:
            signOut();
            break;
          // ...
        }
      }
    });
  }

  private void signOut() {
    Auth.GoogleSignInApi.signOut(this::updateUI).setResultCallback(
      new ResultCallback<Status>() {
        @Override
        public void onResult(AsyncTask.Status status) {
          // [START_EXCLUDE]
          updateUI(false);
          // [END_EXCLUDE]
        }
      });
  }

  private void updateUI(boolean signedIn) {
    TextView StatusTextView = (TextView) getActivity().findViewById(R.id.status);
    if (signedIn) {
      getActivity().findViewById(R.id.sign_in_button).setVisibility(View.GONE);
      getActivity().findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
    } else {
      StatusTextView.setText(R.string.signed_out);

      getActivity().findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
      getActivity().findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
    }
  }


  private void setupEditPartnerButton(LayoutInflater inflater, ViewGroup container) {
    View v = inflater.inflate(R.layout.fragment_settings, container, false);

    Button editPartnerButton = (Button) v.findViewById(R.id.edit_partner_button);
    editPartnerButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        switch (v.getId()) {
          // ...
          case R.id.edit_partner_button:
            //pop-up dialog fragment;
            break;
          // ...
        }
      }
    });
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
