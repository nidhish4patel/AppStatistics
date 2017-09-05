package com.nidhi.as;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nidhi.as.common.AppPreferences;
import com.nidhi.as.common.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private Context context = LoginActivity.this;
    private Button bt_signIn, bt_Register, bt_Facebook;
    private EditText et_username, et_password;
    ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private SignInButton signInButton;
    private static final int RC_SIGN_IN = 007;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;



    /* ButterKnife annotations:

    @BindView(R.id.et_username) EditText et_username;
    @BindView(R.id.editText_password) EditText et_password;
    @BindView(R.id.button_signIn) Button bt_signIn;
    @BindView(R.id.button_Register) Button bt_Register;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        et_username = (EditText) findViewById(R.id.et_username);

        et_username.setFilters(new InputFilter[]{Utils.filter});

        et_password = (EditText) findViewById(R.id.editText_password);
        et_password.setFilters(new InputFilter[]{Utils.filter});


        bt_Register = (Button) findViewById(R.id.button_Register);
        bt_signIn = (Button) findViewById(R.id.button_signIn);

        bt_Register.setOnClickListener(this);
        bt_signIn.setOnClickListener(this);

        bt_Facebook = (Button) findViewById(R.id.login_facebook);
        bt_Facebook.setOnClickListener(this);

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        //ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code

                Toast.makeText(context, currentProfile.getName(), Toast.LENGTH_SHORT).show();
                AppPreferences.getInstance(context).addStringToStore("name", currentProfile.getName());
                Toast.makeText(context, currentProfile.getProfilePictureUri(150, 150).toString(), Toast.LENGTH_SHORT).show();
                AppPreferences.getInstance(context).addStringToStore("profpic", currentProfile.getProfilePictureUri(150, 150).toString());
                launchHome();
            }
        };


        // Google signIn

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,null).
                addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


        if (!AppPreferences.getInstance(context).getStringFromStore("name").trim().equalsIgnoreCase("")) {

           launchHome();

            return;

        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                if (acct.getDisplayName() != null)
                    AppPreferences.getInstance(context).addStringToStore("name", acct.getDisplayName());
                if (acct.getPhotoUrl() != null)
                    AppPreferences.getInstance(context).addStringToStore("profpic", acct.getPhotoUrl().toString());

               launchHome();
            }
        }
    }


    //@OnClick(R.id.button_signIn) public void OnClicksignIn() {}
    //@OnClick(R.id.button_Register) public void OnClickRegister(){}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_Register:
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                break;

            case R.id.button_signIn:
                //progressDialog.setMessage("Logging in...please wait");
                //progressDialog.setCancelable(false);
                //progressDialog.show();

                //validations

                if (et_username.length() > 0) {

                    if (et_username.length() >= 6) {

                        if (et_password.length() > 0) {

                            if (et_password.length() >= 6) {


                                String username = et_username.getText().toString();
                                String password = et_password.getText().toString();


                                AppPreferences.getInstance(context).addStringToStore("name", username);
                                //AppPreferences.getInstance(context).getStringFromStore("uname");

                                Toast.makeText(context, "login successful", Toast.LENGTH_SHORT).show();

                                launchHome();


                            } else {
                                Toast.makeText(context, "Password must be minimum six characters in length", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(context, "Password cannot be blank ", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, "Username must be minimum of six characters in length", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Username cannot be blank", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_facebook:

                break;

            case R.id.sign_in_button:
                googleSignIn();
                break;


        }

    }


    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    private void launchHome(){
        startActivity(new Intent(context, AppStaticsActivity.class));
        finish();
    }


}
