package group50.coupletones.controller.tab.settings;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.AddPartnerActivity;
import group50.coupletones.controller.LoginActivity;
import group50.coupletones.controller.tab.TabFragment;
import group50.coupletones.di.InstanceComponent;
import group50.coupletones.di.module.ContextModule;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import javax.inject.Inject;

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
  private TextView name;
  private TextView yourAccountText;
  private TextView email;
  private TextView null_partner;
  private TextView partnersProfileText;
  private TextView partnerNameText;
  private TextView partnerName;
  private TextView partnerAccountText;
  private TextView partnerEmail;
  private ImageButton add_partner_button;

  private TextView logoutButton;
  private TextView disconnectButton;

  private CompositeSubscription subs;

  public SettingsFragment() {
    super(Object.class);
  }

  /**
   * getResourceID
   *
   * @return - Settings fragment
   */
  @Override
  protected int getResourceId() {
    return R.layout.fragment_settings;
  }

  /**
   * onCreate
   *
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
    subs = new CompositeSubscription();
  }

  /**
   * A method that sets the font of each TextView on the Settings Fragment.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_settings, container, false);

    setup(view);
    observeData();
    bindEvents(view);

    return view;
  }

  private void setup(View view) {
    // User's Profile CardView
    yourProfileText = (TextView) view.findViewById(R.id.my_profile_header);
    yourNameText = (TextView) view.findViewById(R.id.your_name_header);
    name = (TextView) view.findViewById(R.id.your_name);
    yourAccountText = (TextView) view.findViewById(R.id.your_account_header);
    email = (TextView) view.findViewById(R.id.your_email);
    null_partner = (TextView) view.findViewById(R.id.null_partner);

    // Partner's Profile CardView
    partnersProfileText = (TextView) view.findViewById(R.id.partners_profile_text);
    partnerNameText = (TextView) view.findViewById(R.id.partner_name_header);
    partnerName = (TextView) view.findViewById(R.id.partner_name);
    partnerAccountText = (TextView) view.findViewById(R.id.partner_account_header);
    partnerEmail = (TextView) view.findViewById(R.id.partner_email);

    add_partner_button = (ImageButton) view.findViewById(R.id.add_partner_button);

    // Logout/Disconnect Buttons at Bottom
    logoutButton = (TextView) view.findViewById(R.id.logout_button);
    disconnectButton = (TextView) view.findViewById(R.id.disconnect_button);

    Typeface pierSans = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.pier_sans));

    // Set fonts
    yourProfileText.setTypeface(pierSans);
    yourNameText.setTypeface(pierSans);
    name.setTypeface(pierSans);
    yourAccountText.setTypeface(pierSans);
    email.setTypeface(pierSans);
    null_partner.setTypeface(pierSans);
    partnersProfileText.setTypeface(pierSans);
    partnerNameText.setTypeface(pierSans);
    partnerName.setTypeface(pierSans);
    partnerAccountText.setTypeface(pierSans);
    partnerEmail.setTypeface(pierSans);
    logoutButton.setTypeface(pierSans);
    disconnectButton.setTypeface(pierSans);
  }

  /**
   * Observe user data, and change the view based on the model changes.
   */
  private void observeData() {
    // Set local user data
    LocalUser localUser = app.getLocalUser();

    subs.add(
      localUser
        .observable("name", String.class)
        .subscribe(name::setText)
    );

    subs.add(
      localUser
        .observable("email", String.class)
        .subscribe(email::setText)
    );

    CompositeSubscription partnerSubs = new CompositeSubscription();
    Observable<Partner> partnerObservable = localUser.getPartner();

    // Partner not null
    subs.add(
      partnerObservable
        .filter(partner -> partner != null)
        .subscribe(partner -> {
          partnerName.setVisibility(View.VISIBLE);
          partnerNameText.setVisibility(View.VISIBLE);
          partnerEmail.setVisibility(View.VISIBLE);
          partnerAccountText.setVisibility(View.VISIBLE);
          null_partner.setVisibility(View.INVISIBLE);
          add_partner_button.setVisibility(View.INVISIBLE);

          //Observe partner data
          Subscription nameChange = partner
            .observable("name", String.class)
            .subscribe(t -> {
              Log.d("Partner name: ", t);
              partnerName.setText(t);
            });

          Subscription emailChange = partner
            .observable("email", String.class)
            .subscribe(partnerEmail::setText);

          partnerSubs.add(nameChange);
          partnerSubs.add(emailChange);
          subs.add(nameChange);
          subs.add(emailChange);
        })
    );

    // Partner null
    subs.add(
      partnerObservable
        .filter(partner -> partner == null)
        .subscribe(partner -> {

          add_partner_button.setVisibility(View.VISIBLE);
          partnerNameText.setVisibility(View.INVISIBLE);
          partnerName.setVisibility(View.INVISIBLE);
          partnerAccountText.setVisibility(View.INVISIBLE);
          partnerEmail.setVisibility(View.INVISIBLE);
          null_partner.setVisibility(View.VISIBLE);

          // Clear partner subscriptions
          partnerSubs.unsubscribe();
          partnerSubs.clear();
        })
    );
  }

  private void bindEvents(View v) {
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
        }
      );
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

  @Override
  public void onDestroy() {
    super.onDestroy();
    subs.unsubscribe();
    subs.clear();
  }
}
