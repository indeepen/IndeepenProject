package com.release.indeepen.create.selectMedia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.R;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.content.art.ContentYoutubeData;
import com.release.indeepen.content.art.detail.SimpleImageAdapter;
import com.release.indeepen.create.BlogData;
import com.release.indeepen.create.BlogListAdapter;
import com.release.indeepen.create.CreateHeader;
import com.release.indeepen.create.HorizontalListView;
import com.release.indeepen.login.PropertyManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.ArtController;
import com.release.indeepen.management.networkManager.netArt.POSTImageContentRequest;
import com.release.indeepen.management.networkManager.netArt.POSTYoutubeContentRequest;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogController;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogInfoListRequest;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfo;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfoListResult;
import com.release.indeepen.youtube.DeveloperKey;
import com.release.indeepen.youtube.uploadManager.YoutubeUploadActivity;
import com.squareup.picasso.Picasso;

import java.io.File;

public class CreateYoutubeContentActivity extends AppCompatActivity/* implements View.OnClickListener*/{

    CreateHeader vHeader;
    ImageView vImgBlog, showName;
    TextView vTextBlog;
    EditText vInput_Content;
    PopupSelectBlog popupSelectBlog;
    BlogData mBlogData;
    RadioGroup vGruop;
    ContentYoutubeData mCreateData;
    YouTubePlayer mYouTubePlayer;

    Boolean tag = false;

    public HorizontalListView vList;
    BlogListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_youtube_content);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_create));
        getSupportActionBar().setTitle("추가 정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        vHeader = (CreateHeader) findViewById(R.id.header_create_youtube);
        vInput_Content = (EditText) vHeader.findViewById(R.id.input_create_content);
        vImgBlog = (ImageView) vHeader.findViewById(R.id.img_create_selected_blog);
        vTextBlog = (TextView) vHeader.findViewById(R.id.text_create_selected_blog);
        vGruop = (RadioGroup) vHeader.findViewById(R.id.group_emo);

        /*vImgBlog.setOnClickListener(this);
        vTextBlog.setOnClickListener(this);*/

        vList = (HorizontalListView) vHeader.findViewById(R.id.list_create_header_blog);
        mAdapter = new BlogListAdapter();
        vList.setAdapter(mAdapter);


        showName = (ImageView)  vHeader.findViewById(R.id.btn_show_name);
        final LinearLayout setName = (LinearLayout)  vHeader.findViewById(R.id.set_name);
        RelativeLayout name = (RelativeLayout)  vHeader.findViewById(R.id.layout_select_blog);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == false) {
                    setName.setVisibility(View.VISIBLE);
                    showName.setImageResource(R.drawable.btn_top);
                    tag = true;
                } else {
                    setName.setVisibility(View.GONE);
                    showName.setImageResource(R.drawable.btn_bottom);
                    tag = false;
                }

            }
        });

        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (tag == false) {
                    setName.setVisibility(View.VISIBLE);
                    showName.setImageResource(R.drawable.btn_top);
                    tag = true;
                } else {
                    setName.setVisibility(View.GONE);
                    showName.setImageResource(R.drawable.btn_bottom);
                    tag = false;
                }

                BlogInfo mBlogData = (BlogInfo) mAdapter.getItem(position);
                mCreateData.sBlogKey = mBlogData.sBlogKey;

                vTextBlog.setText(mBlogData.sArtist);
                if (!TextUtils.isEmpty(mBlogData.thProfile)) {
                    // ImageLoader.getInstance().displayImage(mBlogData.sIMGPath, vImgBlog);
                    Picasso.with(CreateYoutubeContentActivity.this).load(mBlogData.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vImgBlog);
                }
            }
        });

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

        if (null != savedInstanceState) {
            Bundle bundle = savedInstanceState.getBundle(DefineContentType.CREATE_SAVE);
            mCreateData = (ContentYoutubeData) bundle.getSerializable(DefineContentType.CREATE_IMAGE_DATA);
            bundle.putSerializable(DefineContentType.CREATE_IMAGE_DATA, mCreateData);

            //복원 작업
        }

        init();
    }

    private void init() {
        mCreateData = (ContentYoutubeData) getIntent().getSerializableExtra(DefineContentType.BUNDLE_DATA_REQUEST);

        YouTubePlayerSupportFragment playerFragment;

        Fragment f = getSupportFragmentManager().findFragmentByTag("YouTubePlayerFragment");
        if (f == null) {
            playerFragment = YouTubePlayerSupportFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.create_youtube_container, playerFragment, "YouTubePlayerFragment").commit();
        } else {
            playerFragment = (YouTubePlayerSupportFragment) f;
        }

        playerFragment.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                mYouTubePlayer = youTubePlayer;
                youTubePlayer.cueVideo(mCreateData.sYouTubePath);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        setBlogs();


        //Text
        // Uri uri = Uri.fromFile(new File("android.resource://com.release.indeepen/drawable/dog"));
        /*Uri uri = Uri.parse("android.resource://com.release.indeepen/drawable/"+R.drawable.dog);*/
        //String uri = "drawable://" + R.drawable.emo_love;
        //ImageLoader.getInstance().displayImage(uri.toString(), vImgBlog);

    }

    private void setBlogs(){

        MyBlogInfoListRequest request = new MyBlogInfoListRequest();
        request.setURL(DefineNetwork.MY_BLOG_INFO_LIST);

        MyBlogController.getInstance().getMyBlogInfoList(request, new NetworkProcess.OnResultListener<BlogInfoListResult>() {
            @Override
            public void onSuccess(NetworkRequest<BlogInfoListResult> request, BlogInfoListResult result) {
                //popupSelectBlog.setData(result.arrBlogs);
                mAdapter.addList(result.arrBlogs);
                for (BlogInfo blog : result.arrBlogs) {
                    if (blog.isActivated) {
                        mCreateData.sBlogKey = blog.sBlogKey;
                        mCreateData.mUserData.sArtist = blog.sArtist;
                        Picasso.with(CreateYoutubeContentActivity.this).load(blog.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vImgBlog);
                        vTextBlog.setText(blog.sArtist);
                        break;
                    }
                }
            }

            @Override
            public void onFail(NetworkRequest<BlogInfoListResult> request, int code) {
                mCreateData.sBlogKey = PropertyManager.getInstance().mUser.sActiveBlogKey;
                //Picasso.with(CreateImageContentActivity.this).load(R.drawable.backsample).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vImgBlog);
                //vTextBlog.setText("");
                Toast.makeText(CreateYoutubeContentActivity.this, "블로그 정보를 불러오는데 실패 하였습니다. 현재 활동 중인 블로그에 글이 게시 됩니다. -" + code, Toast.LENGTH_SHORT).show();
            }
        });

    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_select_blog:
            case R.id.img_create_selected_blog:
            case R.id.text_create_selected_blog: {
                onPopupSelectBlog(findViewById(R.id.layout_select_blog));
                break;
            }
        }
    }*/

    private void onPopupSelectBlog(View view) {
        popupSelectBlog = new PopupSelectBlog(this);
        popupSelectBlog.setOutsideTouchable(true);
        popupSelectBlog.showAsDropDown(view);
        popupSelectBlog.vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBlogData = (BlogData) popupSelectBlog.mAdapter.getItem(position);
                mCreateData.sBlogKey = "564a926b29c7cf6416be1118";
                mCreateData.mUserData.sArtist = "나님";
                vTextBlog.setText(mBlogData.sName);
                if (!TextUtils.isEmpty(mBlogData.sIMGPath)) {
                    // ImageLoader.getInstance().displayImage(mBlogData.sIMGPath, vImgBlog);
                    Picasso.with(CreateYoutubeContentActivity.this).load(mBlogData.sIMGPath).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vImgBlog);
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
                onBackPressed();
                break;
            }
            case R.id.action_create: {
                mCreateData.nArtType = getIntent().getIntExtra(DefineContentType.BUNDLE_DATA_TYPE, -1);
                mCreateData.sText = vInput_Content.getText().toString();

                if (-1 != mCreateData.nArtType) {
                    POSTYoutubeContentRequest request = new POSTYoutubeContentRequest();
                    request.setData(mCreateData);
                    request.setURL(DefineNetwork.CONTENT);
                    ArtController.getInstance().putFilePOST(request, new NetworkProcess.OnResultListener<String>() {
                        @Override
                        public void onSuccess(NetworkRequest<String> request, String result) {
                            if (result.equalsIgnoreCase(DefineNetwork.RESULT_SUCCESS)) {
                                YoutubeUploadActivity.youtubeUploadActivity.finish();

                                finish();
                            } else {
                                Toast.makeText(CreateYoutubeContentActivity.this, DefineNetwork.RESULT_FAIL, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFail(NetworkRequest<String> request, int code) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateYoutubeContentActivity.this);
                            builder.setIcon(android.R.drawable.ic_dialog_alert);
                            builder.setTitle("업로드에 실패 하였습니다. 네트워크 연결 확인 후 다시 시도해 주십시오.");
                            builder.setNeutralButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    YoutubeUploadActivity.youtubeUploadActivity.finish();
                                    finish();
                                }
                            });
                            builder.setCancelable(false);
                            builder.create().show();
                        }
                    });
                }
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mCreateData.sText = vInput_Content.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DefineContentType.CREATE_IMAGE_DATA, mCreateData);
        outState.putBundle(DefineContentType.CREATE_SAVE, bundle);
    }

    @Override
    public void onBackPressed() {
        /*if (null != popupSelectBlog && popupSelectBlog.isShowing()) {
            popupSelectBlog.dismiss();
        } else {
            super.onBackPressed();
        }*/
        super.onBackPressed();
    }
}
