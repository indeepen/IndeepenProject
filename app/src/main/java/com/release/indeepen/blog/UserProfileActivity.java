package com.release.indeepen.blog;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.R;
import com.release.indeepen.culture.OptionPopupWindow;
import com.release.indeepen.login.PropertyManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogController;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogInfoRequest;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogProfileRequest;
import com.release.indeepen.management.networkManager.netMyBlog.PUTMyBlogProfileRequest;
import com.release.indeepen.management.networkManager.netMyBlog.data.UserProfileResult;
import com.release.indeepen.user.UserData;

public class UserProfileActivity extends AppCompatActivity {
    EditText vInputArtist, vInputIntro, vInputUserName, vInputPhone, vInputEmail;
    UserData mUserData;
    ImageView vIMGPublic;
    PublicPopupWindow popup;
    boolean isMe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setTitle("프로필 정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vInputArtist = (EditText) findViewById(R.id.input_blog_artist);
        vInputIntro = (EditText) findViewById(R.id.input_blog_introduce);
        vInputUserName = (EditText) findViewById(R.id.input_blog_user_name);
        vInputPhone = (EditText) findViewById(R.id.input_blog_phone);
        vInputEmail = (EditText) findViewById(R.id.input_blog_email);
        vIMGPublic = (ImageView) findViewById(R.id.img_blog_public);

        vInputArtist.setKeyListener(null);

        vIMGPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetPublic(vIMGPublic);
            }
        });

        init();

    }

    private void init() {
        isMe = getIntent().getBooleanExtra(DefineContentType.IS_ME, false);
        if(isMe){
            setEdit();
        }
        getData();

    }

    private void getData(){
        MyBlogProfileRequest request = new MyBlogProfileRequest();
        request.setURL(String.format(DefineNetwork.MY_BLOG_PROFILE, getIntent().getStringExtra(DefineNetwork.BLOG_KEY)));

        MyBlogController.getInstance().getMyBlogProfile(request, new NetworkProcess.OnResultListener<UserProfileResult>() {
            @Override
            public void onSuccess(NetworkRequest<UserProfileResult> request, UserProfileResult result) {

               // mUserData = result.mUserProfile.mUserData;
                mUserData = result.mUserProfile;
                setData(mUserData);
                Toast.makeText(UserProfileActivity.this, "수정이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(NetworkRequest<UserProfileResult> request, int code) {
                Toast.makeText(UserProfileActivity.this, "수정에 실패하였습니다. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(UserData data) {
        vInputArtist.setText(data.sArtist);
        vInputIntro.setText(data.sArtist);

        if (data.isPublic) {
            vIMGPublic.setImageResource(R.drawable.emo_happy);
        } else{
            vIMGPublic.setImageResource(R.drawable.emo_angry);
        }

        if (data.isPublic || isMe) {
            vInputUserName.setText(data.sName);
            vInputPhone.setText(data.sPhoneNum);
            vInputEmail.setText(data.sEmail);
            vInputIntro.setText(data.sIntrodution);


        } else if(!data.isPublic){

            vInputUserName.setText("비공개");
            vInputPhone.setText("비공개");
            vInputEmail.setText("비공개");

        }
    }

    private void setEdit() {
        vInputArtist.setKeyListener(TextKeyListener.getInstance());
        vInputIntro.setEnabled(true);
        vInputUserName.setEnabled(true);
        vInputPhone.setEnabled(true);
        vInputEmail.setEnabled(true);

    }

    private void onSetPublic(View view) {

        popup = new PublicPopupWindow(this);
        popup.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popup.showAsDropDown(view, Gravity.RELATIVE_LAYOUT_DIRECTION, 16, 0);
        }

        popup.vBtnPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vIMGPublic.setImageResource(R.drawable.emo_happy);
                popup.dismiss();
                mUserData.isPublic = true;
            }
        });

        popup.vBtnPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vIMGPublic.setImageResource(R.drawable.emo_angry);
                popup.dismiss();
                mUserData.isPublic = false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(isMe) {
            getMenuInflater().inflate(R.menu.menu_user_edit, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.action_edit: {
                mUserData.sArtist = vInputArtist.getText().toString();
                mUserData.sIntrodution = vInputIntro.getText().toString();
                mUserData.sName = vInputUserName.getText().toString();
                mUserData.sPhoneNum = vInputPhone.getText().toString();
                mUserData.sEmail = vInputEmail.getText().toString();

                PUTMyBlogProfileRequest request = new PUTMyBlogProfileRequest();
                request.setURL(String.format(DefineNetwork.MY_BLOG_PROFILE, getIntent().getStringExtra(DefineNetwork.BLOG_KEY)));
                request.setData(mUserData);

                MyBlogController.getInstance().putMyBlogProfile(request, new NetworkProcess.OnResultListener<String>() {
                    @Override
                    public void onSuccess(NetworkRequest<String> request, String result) {
                        getData();
                    }

                    @Override
                    public void onFail(NetworkRequest<String> request, int code) {

                    }
                });
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (null != popup && popup.isShowing()) {
            popup.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
