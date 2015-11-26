package com.release.indeepen.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.ACTIVITY_TYPE_FIXD_INFO);
        startActivity(mIntent);
    }
}
