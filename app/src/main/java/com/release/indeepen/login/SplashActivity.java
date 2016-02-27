package com.release.indeepen.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.login.service.RegistrationIntentService;
import com.release.indeepen.management.networkManager.NetworkManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netLogin.LoginController;
import com.release.indeepen.management.networkManager.netLogin.POSTLoginRequest;
import com.release.indeepen.management.networkManager.netLogin.data.IndeepenLoginResult;

/*import com.release.indeepen.login.service.RegistrationIntentService;*/

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());
    CallbackManager callbackManager = CallbackManager.Factory.create();
    LoginManager mLoginManager = LoginManager.getInstance();
    AccessTokenTracker mTokenTracker;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //goMainActivity();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //doRealStart();
            }
        };
       // setUpIfNeeded();
        if(!TextUtils.isEmpty(PropertyManager.getInstance().getId()) && !TextUtils.isEmpty(PropertyManager.getInstance().getPassword())){
            final String id =PropertyManager.getInstance().getId();
            final String password = PropertyManager.getInstance().getPassword();


                POSTLoginRequest request = new POSTLoginRequest();
                request.setURL(DefineNetwork.LOGIN);
                request.setData(id, password);

                LoginController.getInstance().login(request, new NetworkProcess.OnResultListener<IndeepenLoginResult>() {
                    @Override
                    public void onSuccess(NetworkRequest<IndeepenLoginResult> request, IndeepenLoginResult result) {
                        Toast.makeText(SplashActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();

                        PropertyManager.getInstance().setId(id);
                        PropertyManager.getInstance().setPassword(password);

                        PropertyManager.getInstance().mUser.sUserkey = result.result.userKey;
                        PropertyManager.getInstance().mUser.sBlogKey = result.result.artistBlogKey;
                        PropertyManager.getInstance().mUser.sActiveBlogKey = result.result.activityBlogKey;
                        PropertyManager.getInstance().mUser.arrSpaceKeys = result.result.arrSpaceBlogKeys;
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFail(NetworkRequest<IndeepenLoginResult> request, int code) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                        //builder.setIcon(android.R.drawable.ic_dialog_alert);
                        //builder.setTitle("Alert Dialog");
                        builder.setMessage("로그인 정보를 다시 확인해 주세요");

                        builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(SplashActivity.this, "확인", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setCancelable(false);
                        builder.create().show();
                    }
                });

        }else{
            goLoginActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST &&
                resultCode == Activity.RESULT_OK) {
            setUpIfNeeded();
        }
    }
    private void setUpIfNeeded() {
        if (checkPlayServices()) {
            String regId = PropertyManager.getInstance().getRegistrationToken();
            if (!regId.equals("")) {
                doRealStart();
            } else {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private void doRealStart() {
        // activity start...
        final String id = PropertyManager.getInstance().getFaceBookId();
        if (!TextUtils.isEmpty(id)) {
            // facebook login
            mTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    AccessToken token = AccessToken.getCurrentAccessToken();
                    if (token != null) {
                        if (token.getUserId().equals(id)) {
                            NetworkManager.getInstance().loginFacebookToken(SplashActivity.this, token.getToken(), "OK", new NetworkManager.OnResultListener<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    if (result.equals("OK")) {
                                         goMainActivity();
                                    }
                                }

                                @Override
                                public void onFail(int code) {
                                    goLoginActivity();

                                }
                            });
                        } else {
                            Toast.makeText(SplashActivity.this, "facebook id change", Toast.LENGTH_SHORT).show();
                            mLoginManager.logOut();
                            goLoginActivity();
                        }
                    }
                }
            };
            mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    //goMainActivity();
                }

                @Override
                public void onCancel() {
                    goMainActivity();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(SplashActivity.this, "callback facebook login fail...", Toast.LENGTH_SHORT).show();
                    goLoginActivity();
                }
            });

            mLoginManager.logInWithReadPermissions(this, null);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goLoginActivity();
                }
            }, 1000);
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTokenTracker != null) {
            mTokenTracker.stopTracking();
        }
    }
    private void goMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void goLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
