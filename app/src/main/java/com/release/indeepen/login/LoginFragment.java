package com.release.indeepen.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netLogin.LoginController;
import com.release.indeepen.management.networkManager.netLogin.POSTLoginRequest;
import com.release.indeepen.management.networkManager.netLogin.data.IndeepenLoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginFragment extends Fragment {

    public static final int MODE_CHECK = -1;
    public static final int MODE_PROFILE = 1;
    public static final int MODE_POST = 2;
    CallbackManager callbackManager = CallbackManager.Factory.create();
    ImageButton btn_login;
    EditText email, pw;
    String mFacebookEmail;
    LoginManager mLoginManager;
    ImageButton facebook;
    boolean isFill = true;
    AccessTokenTracker tracker;
    int mode = MODE_CHECK;

    public LoginFragment() {
        // Required empty public constructor
    }

    public final static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public final static boolean isValidPW(String pw) {
        return !TextUtils.isEmpty((pw));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        LoginManager manager = LoginManager.getInstance();
        email = (EditText) view.findViewById(R.id.text_email);
        pw = (EditText) view.findViewById(R.id.text_pw);
//test

        //  manager.registerCallback(callbackManager,  new FacebookCallback<LoginResult>() {

      /*  final LoginButton btn_facebook = (LoginButton) view.findViewById(R.id.btn_facebook_login);
        btn_facebook.setFragment(this);
        btn_facebook.setReadPermissions("email");

        //btn_facebook.setReadPermissions("name");
        btn_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                final AccessToken token = AccessToken.getCurrentAccessToken();

                NetworkManager.getInstance().loginFacebookToken(getContext(), token.getToken(), "OK",
                        new NetworkManager.OnResultListener<String>() {
                            @Override
                            public void onSuccess(String result) {
                                if (result.equals("OK")) {
                                    PropertyManager.getInstance().setFacebookId(token.getUserId());

                                    GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            try {
                                                mFacebookEmail = (String) response.getJSONObject().get("email");

                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    Bundle parameters = new Bundle();
                                    // parameters.putString(mFacebookEmail, mFacebookName);
                                    request.setParameters(parameters);
                                    request.executeAsync();
                                    //   Toast.makeText(getContext(), "id" + token.getUserId(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getContext(), MainActivity.class));

                                } else if (result.equals("NOT REGISTER")) {
                                    startActivity(new Intent(getContext(), MainActivity.class));
                                    Toast.makeText(getContext(), "asfasfasfsa", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFail(int code) {
                                Toast.makeText(getContext(), "다시확인", Toast.LENGTH_SHORT).show();

                            }
                        });

            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getContext(), "Login Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getContext(), "Problem connecting to Facebook", Toast.LENGTH_SHORT).show();
            }
        });

*/
        btn_login = (ImageButton) view.findViewById(R.id.btn_login);
        //btn_login.setEnabled(false);
/*
        if(!TextUtils.isEmpty(PropertyManager.getInstance().getId()) && !TextUtils.isEmpty(PropertyManager.getInstance().getPassword())){
            email.setText(PropertyManager.getInstance().getId());
            pw.setText(PropertyManager.getInstance().getPassword());
            netLogin();
        }
*/

      /*  email.setText("indeepen18@gmail.com");
        pw.setText("1234");*/
        //PropertyManager.getInstance().mUser.sArtist = "이여름";
        btn_login.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             netLogin();
                                             /*startActivity(new Intent(getContext(), MainActivity.class));
                                             getActivity().finish();*/

                                             /*final String id = email.getText().toString();
                                             final String password = pw.getText().toString();
                                             if(TextUtils.isEmpty(id)){
                                                 isFill = false;
                                                 AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                 builder.setMessage("아이디를 입력해 주세요.");

                                                 builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) {
                                                     }
                                                 });
                                                 builder.setCancelable(false);
                                                 builder.create().show();
                                             }
                                             else if(TextUtils.isEmpty(password)){
                                                 isFill = false;
                                                 AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                 builder.setMessage("비밀번호를 입력해 주세요.");

                                                 builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) {
                                                     }
                                                 });
                                                 builder.setCancelable(false);
                                                 builder.create().show();
                                             }
                                             if(isFill) {
                                                 POSTLoginRequest request = new POSTLoginRequest();
                                                 request.setURL(DefineNetwork.LOGIN);
                                                 request.setData(id, password);

                                                 LoginController.getInstance().login(request, new NetworkProcess.OnResultListener<IndeepenLoginResult>() {
                                                     @Override
                                                     public void onSuccess(NetworkRequest<IndeepenLoginResult> request, IndeepenLoginResult result) {
                                                         Toast.makeText(getContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                                         PropertyManager.getInstance().mUser.sUserkey = result.result.userKey;
                                                         PropertyManager.getInstance().mUser.sBlogKey = result.result.artistBlogKey;
                                                         PropertyManager.getInstance().mUser.sActiveBlogKey = result.result.activityBlogKey;
                                                         PropertyManager.getInstance().mUser.arrSpaceKeys = result.result.arrSpaceBlogKeys;
                                                         startActivity(new Intent(getContext(), MainActivity.class));
                                                         getActivity().finish();
                                                     }

                                                     @Override
                                                     public void onFail(NetworkRequest<IndeepenLoginResult> request, int code) {
                                                         AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                         //builder.setIcon(android.R.drawable.ic_dialog_alert);
                                                         //builder.setTitle("Alert Dialog");
                                                         builder.setMessage("로그인 정보를 다시 확인해 주세요");

                                                         builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                             @Override
                                                             public void onClick(DialogInterface dialog, int which) {
                                                                 Toast.makeText(getContext(), "확인", Toast.LENGTH_SHORT).show();
                                                             }
                                                         });
                                                         builder.setCancelable(false);
                                                         builder.create().show();
                                                     }
                                                 });
                                             }*/
                                         }
                                     }
        );

        facebook = (ImageButton) view.findViewById(R.id.facebook_login);

        setLabel();
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    login(null);
                    mode = MODE_PROFILE;
                } else {
                    mLoginManager.logOut();
                }
            }
        });
        tracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                //setLabel();
            }
        };

        return view;
    }

    private void netLogin(){
        final String id = email.getText().toString();
        final String password = pw.getText().toString();
        if(TextUtils.isEmpty(id)){
            isFill = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("아이디를 입력해 주세요.");

            builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        }
        else if(TextUtils.isEmpty(password)){
            isFill = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("비밀번호를 입력해 주세요.");

            builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        }
        if(isFill) {
            POSTLoginRequest request = new POSTLoginRequest();
            request.setURL(DefineNetwork.LOGIN);
            request.setData(id, password);

            LoginController.getInstance().login(request, new NetworkProcess.OnResultListener<IndeepenLoginResult>() {
                @Override
                public void onSuccess(NetworkRequest<IndeepenLoginResult> request, IndeepenLoginResult result) {
                    Toast.makeText(getContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();

                    PropertyManager.getInstance().setId(id);
                    PropertyManager.getInstance().setPassword(password);

                    PropertyManager.getInstance().mUser.sUserkey = result.result.userKey;
                    PropertyManager.getInstance().mUser.sBlogKey = result.result.artistBlogKey;
                    PropertyManager.getInstance().mUser.sActiveBlogKey = result.result.activityBlogKey;
                    PropertyManager.getInstance().mUser.arrSpaceKeys = result.result.arrSpaceBlogKeys;
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }

                @Override
                public void onFail(NetworkRequest<IndeepenLoginResult> request, int code) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    //builder.setIcon(android.R.drawable.ic_dialog_alert);
                    //builder.setTitle("Alert Dialog");
                    builder.setMessage("로그인 정보를 다시 확인해 주세요");

                    builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "확인", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setCancelable(false);
                    builder.create().show();
                }
            });
        }
    }

    private void postMessage() {
        String message = "facebook test message";
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        String graphPath = "/me/feed";
        Bundle parameters = new Bundle();
        parameters.putString("message", message);
        parameters.putString("link", "http://developers.facebook.com/docs/android");
        parameters.putString("picture", "https://raw.github.com/fbsamples/.../iossdk_logo.png");
        parameters.putString("name", "Hello Facebook");
        parameters.putString("description", "The 'Hello Facebook' sample  showcases simple …");
        GraphRequest request = new GraphRequest(accessToken, graphPath, parameters, HttpMethod.POST,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject data = response.getJSONObject();
                        String id = (data == null) ? null : data.optString("id");
                        if (id == null) {
                            Toast.makeText(getContext(), "error : " + response.getError().getErrorMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "post object id : " + id, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        request.executeAsync();
    }

    private void login(List<String> permissions) {
        login(permissions, true);
    }

    private void login(List<String> permissions, boolean isRead) {
        mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (mode == MODE_PROFILE) {
                    getProfile();

                    startActivity(new Intent(getContext(), MainActivity.class));
                    mode = MODE_CHECK;
                } else if (mode == MODE_POST) {
                    postMessage();
                }
                if (mode == MODE_CHECK) {
                    startActivity(new Intent(getContext(), MainActivity.class));

                }
            }

            @Override
            public void onCancel() {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }

            @Override
            public void onError(FacebookException error) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        if (isRead) {
            mLoginManager.logInWithReadPermissions(getActivity(), permissions);
        } else {
            mLoginManager.logInWithPublishPermissions(getActivity(), permissions);
        }
    }

    private void getProfile() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        GraphRequest request = new GraphRequest(token, "/me", null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                JSONObject object = response.getJSONObject();
                try {
                    mFacebookEmail = (String) object.get("email");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (object == null) {
                    String message = response.getError().getErrorMessage();
                    Toast.makeText(getContext(), "error : " + message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "profile : " + object.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tracker.stopTracking();
    }

    private void setLabel() {
        if (!isLogin()) {
            // facebook.setText("login");
        } else {
            //  facebook.setText("logout");
        }
    }

    private boolean isLogin() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        return token == null ? false : true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("myLog", "requestCode  " + requestCode);
        Log.d("myLog", "resultCode" + resultCode);
        Log.d("myLog", "data  " + data.toString());
    }
}
