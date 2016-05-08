package group50.coupletones.controller.tab;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.AddPartnerActivity;
import group50.coupletones.controller.LoginActivity;
import group50.coupletones.di.InstanceComponent;
import group50.coupletones.di.module.ContextModule;
import group50.coupletones.util.storage.Storage;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

/**
 * A {@link Fragment} subclass for the Settings tab.
 */
public class SettingsFragment extends TabFragment<Object> {

  @Inject
  public CoupleTones app;

  private Authenticator<User, String> auth;

  private InstanceComponent component;

  private TextView yourProfileText;
  private TextView yourNameText;
  private TextView yourName;
  private TextView yourAccountText;
  private TextView yourAccount;
  private TextView null_partner;
  private TextView partnersProfileText;
  private TextView partnerNameText;
  private TextView partnerName;
  private TextView partnerAccountText;
  private TextView partnerAccount;
  private ImageButton add_partner_button;

  public SettingsFragment() {
    super(Object.class);
  }

  /**
   * getResourceID
   * @return - Settings fragment
   */
  @Override
  protected int getResourceId() {
    return R.layout.fragment_settings;
  }

  /**
   * onCreate
   * @param savedInstanceState - Bundle
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CoupleTones.global().inject(this);
    component = CoupleTones
      .instanceComponentBuilder()
      .contextModule(new ContextModule(getContext()))
      .build();
    auth = component.auth();
  }

  /**
   * A method that sets the font of each TextView on the Settings Fragment.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_settings, container, false);
    Typeface pierSans = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.pier_sans));

    // User's Profile CardView
    yourProfileText = (TextView) v.findViewById(R.id.my_profile_header);
    yourNameText = (TextView) v.findViewById(R.id.your_name_header);
    yourName = (TextView) v.findViewById(R.id.your_name);
    yourAccountText = (TextView) v.findViewById(R.id.your_account_header);
    yourAccount = (TextView) v.findViewById(R.id.your_email);
    null_partner = (TextView) v.findViewById(R.id.null_partner);

    // Partner's Profile CardView
    partnersProfileText = (TextView) v.findViewById(R.id.partners_profile_text);
    partnerNameText = (TextView) v.findViewById(R.id.partner_name_header);
    partnerName = (TextView) v.findViewById(R.id.partner_name);
    partnerAccountText = (TextView) v.findViewById(R.id.partner_account_header);
    partnerAccount = (TextView) v.findViewById(R.id.partner_email);

    add_partner_button = (ImageButton) v.findViewById(R.id.add_partner_button);

    // Logout/Disconnect Buttons at Bottom
    TextView logoutButton = (TextView) v.findViewById(R.id.logout_button);
    TextView disconnectButton = (TextView) v.findViewById(R.id.disconnect_button);

    // Set fonts
    yourProfileText.setTypeface(pierSans);
    yourNameText.setTypeface(pierSans);
    yourName.setTypeface(pierSans);
    yourAccountText.setTypeface(pierSans);
    yourAccount.setTypeface(pierSans);
    null_partner.setTypeface(pierSans);
    partnersProfileText.setTypeface(pierSans);
    partnerNameText.setTypeface(pierSans);
    partnerName.setTypeface(pierSans);
    partnerAccountText.setTypeface(pierSans);
    partnerAccount.setTypeface(pierSans);

    // Set local user data
    yourName.setText(app.getLocalUser().getName());
    yourAccount.setText(app.getLocalUser().getEmail());

    // Control visibility and customizability of Partner Name and Partner Email
    if (app.getLocalUser().getPartner() != null) {
      updateUI(true);
    } else {
      updateUI(false);
    }


    logoutButton.setTypeface(pierSans);
    disconnectButton.setTypeface(pierSans);

    // Register button click handlers. After add partner, take the user to Add Partner page.
    v.findViewById(R.id.add_partner_button)
      .setOnClickListener(evt -> startActivity(new Intent(getContext(), AddPartnerActivity.class)));

    // Logout button handler. After logout, take the user to Login page.
    v.findViewById(R.id.logout_button)
      .setOnClickListener(evt -> auth.signOut(status -> startActivity(new Intent(getContext(), LoginActivity.class))));

    // Disconnect with partner button handler
    v.findViewById(R.id.disconnect_button)
      .setOnClickListener(evt -> {
          app.getLocalUser().setPartner(null);
          app.getLocalUser().save(new Storage(getActivity()
            .getSharedPreferences("user", MODE_PRIVATE)));
          updateUI(false);
        }
      );

    return v;
  }

  /**
   * Updates the UI based on whether
   * user is connected to a partner.
   * @param hasPartner - if user is connected with partner
   *
   * @param hasPartner Does the user have a partner?
   */
  private void updateUI(boolean hasPartner) {
    if (hasPartner) {
      partnerName.setText(app.getLocalUser().getPartner().getName());
      partnerAccount.setText(app.getLocalUser().getPartner().getEmail());
      partnerName.setVisibility(View.VISIBLE);
      partnerNameText.setVisibility(View.VISIBLE);
      partnerAccount.setVisibility(View.VISIBLE);
      partnerAccountText.setVisibility(View.VISIBLE);
      null_partner.setVisibility(View.INVISIBLE);
      add_partner_button.setVisibility(View.INVISIBLE);
    } else {
      add_partner_button.setVisibility(View.VISIBLE);
      partnerNameText.setVisibility(View.INVISIBLE);
      partnerName.setVisibility(View.INVISIBLE);
      partnerAccountText.setVisibility(View.INVISIBLE);
      partnerAccount.setVisibility(View.INVISIBLE);
      null_partner.setVisibility(View.VISIBLE);
    }
  }

  /**
   * onStart starts and connects
   */
  @Override
  public void onStart() {
    super.onStart();
    auth.connect();
  }

  /**
   * onStop stop and disconnects
   */
  @Override
  public void onStop() {
    super.onStop();
    auth.disconnect();
  }

  /**
   * onResume updates UI depending
   * on if user has a partner or not
   */
  @Override
  public void onResume() {
    super.onResume();
    if (app.getLocalUser().getPartner() != null) {
      updateUI(true);
    } else {
      updateUI(false);
    }
  }
}
