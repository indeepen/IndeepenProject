package com.release.indeepen.create.selectMedia;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.R;
import com.release.indeepen.content.art.ContentMusicData;
import com.release.indeepen.create.BlogData;
import com.release.indeepen.create.CreateHeader;
import com.release.indeepen.login.PropertyManager;
import com.release.indeepen.management.musicManager.MusicManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.ArtController;
import com.release.indeepen.management.networkManager.netArt.POSTMusicContentRequest;
import com.squareup.picasso.Picasso;

import java.io.File;

public class CreateMusicContentActivity extends AppCompatActivity implements View.OnClickListener  {
    ImageView vBackground, vForeground;
    SeekBar vSeek;
    MusicManager playerManager;
    String sPath;
    ImageView vImgBlog;
    TextView vTextBlog;
    EditText vInput_Content;
    PopupSelectBlog popupSelectBlog;
    BlogData mBlogData;
    RadioGroup vGruop;
    CreateHeader vHeader;

    ContentMusicData mCreateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_music_content);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_create));
        getSupportActionBar().setTitle("추가 정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vHeader = (CreateHeader) findViewById(R.id.header_create_music);
        vSeek = (SeekBar) findViewById(R.id.seekBar_create_music);
        vBackground = (ImageView) findViewById(R.id.img_create_music);
        vForeground = (ImageView) findViewById(R.id.img_create_foreground);

        vBackground.setOnClickListener(this);
        vForeground.setOnClickListener(this);

        vInput_Content = (EditText) vHeader.findViewById(R.id.input_create_content);
        vImgBlog = (ImageView) vHeader.findViewById(R.id.img_create_selected_blog);
        vTextBlog = (TextView) vHeader.findViewById(R.id.text_create_selected_blog);
        vGruop = (RadioGroup) vHeader.findViewById(R.id.group_emo);

        vImgBlog.setOnClickListener(this);
        vTextBlog.setOnClickListener(this);

        vGruop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int nEmo = 0;
                switch (checkedId) {
                    case R.id.img_create_emo_none: {
                        nEmo = 0;
                        break;
                    }
                    case R.id.img_create_emo_happy: {
                        nEmo = 1;
                        break;
                    }
                    case R.id.img_create_emo_love: {
                        nEmo = 2;
                        break;
                    }
                    case R.id.img_create_emo_sad: {
                        nEmo = 3;
                        break;
                    }
                    case R.id.img_create_emo_angry: {
                        nEmo = 4;
                        break;
                    }
                }
                mCreateData.nEmotion = nEmo;
            }
        });
        if(null != savedInstanceState){
            Bundle bundle = savedInstanceState.getBundle(DefineContentType.CREATE_SAVE);
            mCreateData =(ContentMusicData) bundle.getSerializable(DefineContentType.CREATE_MUSIC_DATA);
            bundle.putSerializable(DefineContentType.CREATE_MUSIC_DATA, mCreateData);

            //복원 작업
        }


        init();
    }

    private void init(){


        mCreateData =(ContentMusicData) (getIntent().getSerializableExtra(DefineContentType.BUNDLE_DATA_REQUEST));
        setBlogs();
        sPath = mCreateData.sMusicPath;
        vForeground.setVisibility(View.VISIBLE);
        playerManager = MusicManager.getMusicManager();
        Uri video_uri = Uri.fromFile(new File(sPath));
        playerManager.setMyURI(video_uri);
        playerManager.init(vSeek);
        vSeek.setProgress(0);
        //ImageLoader.getInstance().displayImage(Uri.fromFile(new File(mCreateData.sMusicBackIMG)).toString(), vBackground);
        Picasso.with(this).load(new File(mCreateData.sMusicBackIMG)).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).into(vBackground);

        // Uri uri = Uri.fromFile(new File("android.resource://com.release.indeepen/drawable/dog"));
        /*Uri uri = Uri.parse("android.resource://com.release.indeepen/drawable/"+R.drawable.dog);*/
        //String uri ="drawable://" + R.drawable.emo_happy_;
        //ImageLoader.getInstance().displayImage(uri.toString(), vImgBlog);

    }

    private void setBlogs(){
        popupSelectBlog = new PopupSelectBlog(this);
        mCreateData.sBlogKey = PropertyManager.getInstance().mUser.sBlogKey;
        mCreateData.mUserData.sArtist = PropertyManager.getInstance().mUser.sArtist;
        Picasso.with(this).load(R.drawable.emo_happy_).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vImgBlog);
        vTextBlog.setText(PropertyManager.getInstance().mUser.sArtist);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_create_music:
            case R.id.img_create_foreground: {
                if (playerManager.getState() == MusicManager.PlayState.STARTED) {
                    playerManager.pause();
                    vForeground.setVisibility(View.VISIBLE);
                } else {
                    Uri video_uri = Uri.parse(mCreateData.sMusicPath);
                    playerManager.setMyURI(video_uri);
                    //playerManager.init(vSeek);
                    playerManager.play();
                    playerManager.seekbarReset(vSeek);
                    vForeground.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case R.id.layout_select_blog:
            case R.id.img_create_selected_blog:
            case R.id.text_create_selected_blog: {
                onPopupSelectBlog(findViewById(R.id.layout_select_blog));
                break;
            }
        }
    }

    private void onPopupSelectBlog(View view) {

        popupSelectBlog.setOutsideTouchable(true);
        popupSelectBlog.showAsDropDown(view);
        popupSelectBlog.vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBlogData = (BlogData) popupSelectBlog.mAdapter.getItem(position);
                mCreateData.sBlogKey = "563ef1ca401ae00c19a15832";
                mCreateData.mUserData.sArtist = "나님";
                vTextBlog.setText(mBlogData.sName);
                if (!TextUtils.isEmpty(mBlogData.sIMGPath)) {
                    //ImageLoader.getInstance().displayImage(mBlogData.sIMGPath, vImgBlog);
                    Picasso.with(CreateMusicContentActivity.this).load(mBlogData.sIMGPath).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vImgBlog);
                }
                popupSelectBlog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                super.onBackPressed();
                break;
            }
            case R.id.action_create: {
                mCreateData.sText = vInput_Content.getText().toString();

                POSTMusicContentRequest request = new POSTMusicContentRequest();
                request.setData(mCreateData);
                ArtController.getInstance().putFilePOST(request, new NetworkProcess.OnResultListener<String>() {
                    @Override
                    public void onSuccess(NetworkRequest<String> request, String result) {
                        if (result.equalsIgnoreCase(DefineNetwork.RESULT_SUCCESS)) {
                            MusicChoiceActivity.musicChoiceActivity.finish();
                            MediaSingleChoiceActivity.mediaSingleChoiceActivity.finish();
                            finish();
                        } else {
                            Toast.makeText(CreateMusicContentActivity.this, DefineNetwork.RESULT_FAIL, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<String> request, int code) {
                        Toast.makeText(CreateMusicContentActivity.this, DefineNetwork.RESULT_FAIL, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerManager.pause();
        vForeground.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerManager.pause();
        vForeground.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerManager.pause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mCreateData.sText = vInput_Content.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DefineContentType.CREATE_MUSIC_DATA, mCreateData);
        outState.putBundle(DefineContentType.CREATE_SAVE, bundle);
    }

    @Override
    public void onBackPressed() {
        if (null != popupSelectBlog && popupSelectBlog.isShowing()) {
            popupSelectBlog.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
