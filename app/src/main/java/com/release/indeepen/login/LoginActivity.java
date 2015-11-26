package com.release.indeepen.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.release.indeepen.DefineContentType;
import com.release.indeepen.R;

public class LoginActivity extends AppCompatActivity {
    FragmentTabHost vTabHost;
    ImageView tab_image;
    View signin, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        vTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        signin = getLayoutInflater().inflate(R.layout.view_image, null);
        signup = getLayoutInflater().inflate(R.layout.view_image, null);

        tab_image = (ImageView) signin.findViewById(R.id.tab_image);
        tab_image.setBackgroundResource(R.drawable.tab_signin);
        tab_image = (ImageView) signup.findViewById(R.id.tab_image);
        tab_image.setBackgroundResource(R.drawable.tab_signup);

        vTabHost.addTab(vTabHost.newTabSpec(DefineContentType.LOGIN_TAB_LOGIN).setIndicator(signin), LoginFragment.class, null);
        vTabHost.addTab(vTabHost.newTabSpec(DefineContentType.LOGIN_TAB_SIGNUP).setIndicator(signup), SignupFragment.class, null);

    }
}
