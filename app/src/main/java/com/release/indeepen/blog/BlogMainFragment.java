package com.release.indeepen.blog;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.blog.simpleList.SimpleSingleUserListActivity;
import com.release.indeepen.blog.simpleList.SimpleTabUserListActivity;
import com.release.indeepen.blog.tripleGrid.HeaderGridView;
import com.release.indeepen.blog.tripleGrid.TripleGridAdapter;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.art.detail.ExpandImageActivity;
import com.release.indeepen.content.art.singleList.ContentSingleListAdapter;
import com.release.indeepen.create.selectMedia.MediaSingleChoiceActivity;
import com.release.indeepen.login.PropertyManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogContentRequest;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogController;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogInfoRequest;
import com.release.indeepen.management.networkManager.netMyBlog.ProfileImageRequest;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogContentList;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfo;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfoResult;
import com.release.indeepen.management.networkManager.netMyBlog.data.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogMainFragment extends Fragment implements View.OnClickListener  /*implements CallbackListener.OnGoActivityListener*/ {
    HeaderGridView vTripleGrid;
    TripleGridAdapter mAdapter;
    ImageView vIMGProBack, vthPro;
    Button vBtnArt, vBtnFavorite, vBtnMyCulture, vBtnCollabo;
    BlogIntroView vHeader;
    boolean isClick = false;
    String sBlogKey;
    BlogInfo mBlogData;
    TextView vTextArtist, vTextFanCount, vTextIMissUCount;
    boolean isLastItem;
    boolean isStart;
    boolean isMyContent;
    int nSaveIdx = -1;
    int nSaveTop = 0;
    boolean isMe = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog_main, container, false);
        isStart = true;
        vTripleGrid = (HeaderGridView) view.findViewById(R.id.grid_item);
       // mAdapter = new TripleGridAdapter(getContext());
        vHeader = new BlogIntroView(getContext());
        vTripleGrid.addHeaderView(vHeader);
       // vTripleGrid.setAdapter(mAdapter);

        vIMGProBack = (ImageView) vHeader.findViewById(R.id.img_blog_background);
        vthPro = (ImageView) vHeader.findViewById(R.id.img_blog_thProfile);
        vBtnArt = (Button) vHeader.findViewById(R.id.btn_tab_art);
        vBtnFavorite = (Button) vHeader.findViewById(R.id.btn_tab_favorite);
        vBtnMyCulture = (Button) vHeader.findViewById(R.id.btn_tab_culture);
        vBtnCollabo = (Button) vHeader.findViewById(R.id.btn_tab_collabo);
        vTextArtist = (TextView) vHeader.findViewById(R.id.text_blog_artist);
        vTextFanCount = (TextView) vHeader.findViewById(R.id.text_fan_count);
        vTextIMissUCount = (TextView) vHeader.findViewById(R.id.text_imissu_count);

        vIMGProBack.setOnClickListener(this);
        vthPro.setOnClickListener(this);
        vBtnArt.setOnClickListener(this);
        vBtnFavorite.setOnClickListener(this);
        vBtnMyCulture.setOnClickListener(this);
        vBtnCollabo.setOnClickListener(this);

        Button btn = (Button) vHeader.findViewById(R.id.btn_fanList);
        btn.setOnClickListener(this);

        btn = (Button) vHeader.findViewById(R.id.btn_imissyou);
        btn.setOnClickListener(this);

        vTripleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_FAN_LIST);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_FROM, DefineContentType.FROM_BLOG);

                mIntent.putExtra(DefineNetwork.LIST_POSITION, (int) (vTripleGrid.getItemIdAtPosition(position)));

                if (isMyContent) {
                    mIntent.putExtra(DefineNetwork.REQUEST_URL, String.format(DefineNetwork.MY_BLOG_ART_SINGLE, mBlogData.sBlogKey));
                    mIntent.putExtra(DefineNetwork.REQUEST_URL_MORE, String.format(DefineNetwork.MY_BLOG_ART_SINGLE_MORE, mBlogData.sBlogKey));
                } else {
                    mIntent.putExtra(DefineNetwork.REQUEST_URL, String.format(DefineNetwork.MY_BLOG_FAVORITE_ART_SINGLE, mBlogData.sBlogKey));
                    mIntent.putExtra(DefineNetwork.REQUEST_URL_MORE, String.format(DefineNetwork.MY_BLOG_FAVORITE_ART_SINGLE_MORE, mBlogData.sBlogKey));
                }
                startActivity(mIntent);

            }
        });

        vTripleGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            int mLastFirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (isMyContent) {
                        getMyContent();
                    } else {
                        getFavoriteContent();
                    }

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)) {
                    isLastItem = true;
                } else {
                    isLastItem = false;
                }
            }
        });

        if (nSaveIdx != -1) {
            vTripleGrid.setAdapter(mAdapter);
            vTripleGrid.setSelection(nSaveIdx);
        }else{
            mAdapter = new TripleGridAdapter(getContext());
            vTripleGrid.setAdapter(mAdapter);

            init();
        }

        return view;
    }

    private void init() {
        isMyContent = true;
        if (null != getArguments()) {
            sBlogKey = getArguments().getString(DefineNetwork.BLOG_KEY);
            if (TextUtils.isEmpty(sBlogKey)) {
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }

            //getBlogInfo();
            getMyContent();

        }

    }

    private void setBlogData(BlogInfo data) {
        mBlogData = data;

        Picasso.with(getContext()).load(mBlogData.thProfile).placeholder(R.drawable.ic_empty)
                .error(R.drawable.ic_error).fit().into(vthPro);
        Picasso.with(getContext()).load(mBlogData.thBackIMG).placeholder(R.drawable.ic_empty)
                .error(R.drawable.ic_error).fit().into(vIMGProBack);

//        ImageLoader.getInstance().displayImage(mBlogData.thProfile, vthPro);
//        ImageLoader.getInstance().displayImage(mBlogData.thBackIMG, vIMGProBack);

        vTextArtist.setText(mBlogData.sArtist);
        vTextFanCount.setText(mBlogData.arrFans.size() + "");
        vTextIMissUCount.setText(mBlogData.arrIMissYous.size() + "");

    }

    public void onBackgroundDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setIcon(android.R.drawable.ic_dialog_alert);
        // builder.setTitle("List Dialog");
        builder.setItems(new String[]{"블로그 배경 등록"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent mIntent = new Intent(getContext(), MediaSingleChoiceActivity.class);
                mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.ACTIVITY_TYPE_PROFILE_BACKGROUND); // 사진 선택 구별을 위한 타입
                mIntent.putExtra(DefineNetwork.BLOG_KEY, mBlogData.sBlogKey);
                startActivity(mIntent);

            }
        });
        builder.create().show();
    }

    public void onProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setIcon(android.R.drawable.ic_dialog_alert);
        // builder.setTitle("List Dialog");
        if(isMe) {
            builder.setItems(new String[]{"프로필 수정", "프로필 사진 보기", "프로필 사진 등록"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Intent mIntent;
                    switch (which) {
                        case 0: {
                            mIntent = new Intent(getContext(), UserProfileActivity.class);
                            mIntent.putExtra(DefineNetwork.BLOG_KEY, mBlogData.sBlogKey);
                            mIntent.putExtra(DefineContentType.IS_ME, true);
                            startActivity(mIntent);
                            break;
                        }
                        case 1: {
                            ProfileImageRequest request = new ProfileImageRequest();

                            request.setURL(String.format(DefineNetwork.PROFILE_IMG, sBlogKey));

                            MyBlogController.getInstance().getProfileImage(request, new NetworkProcess.OnResultListener<ImageResult>() {
                                @Override
                                public void onSuccess(NetworkRequest<ImageResult> request, ImageResult result) {
                                    Intent mIntent;
                                    mIntent = new Intent(getContext(), ExpandImageActivity.class);
                                    mIntent.putExtra(DefineContentType.EXPAND_IMG, result.mChageIMGData.sChangePath);
                                    startActivity(mIntent);
                                }

                                @Override
                                public void onFail(NetworkRequest<ImageResult> request, int code) {

                                }
                            });


                            break;
                        }
                        case 2: {
                            mIntent = new Intent(getContext(), MediaSingleChoiceActivity.class);
                            mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.ACTIVITY_TYPE_PROFILE_IMG);
                            mIntent.putExtra(DefineNetwork.BLOG_KEY, mBlogData.sBlogKey);
                            startActivity(mIntent);
                            break;
                        }
                    }

                }
            });
        }else{
            builder.setItems(new String[]{"프로필 보기", "프로필 사진 보기"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Intent mIntent;
                    switch (which) {
                        case 0: {
                            mIntent = new Intent(getContext(), UserProfileActivity.class);
                            mIntent.putExtra(DefineNetwork.BLOG_KEY, mBlogData.sBlogKey);
                            mIntent.putExtra(DefineContentType.IS_ME, false);
                            startActivity(mIntent);
                            break;
                        }
                        case 1: {
                            ProfileImageRequest request = new ProfileImageRequest();

                            request.setURL(String.format(DefineNetwork.PROFILE_IMG, sBlogKey));

                            MyBlogController.getInstance().getProfileImage(request, new NetworkProcess.OnResultListener<ImageResult>() {
                                @Override
                                public void onSuccess(NetworkRequest<ImageResult> request, ImageResult result) {
                                    Intent mIntent;
                                    mIntent = new Intent(getContext(), ExpandImageActivity.class);
                                    mIntent.putExtra(DefineContentType.EXPAND_IMG, result.mChageIMGData.sChangePath);
                                    startActivity(mIntent);
                                }

                                @Override
                                public void onFail(NetworkRequest<ImageResult> request, int code) {

                                }
                            });

                            break;
                        }

                    }

                }
            });
        }
        builder.create().show();
    }

    public void onConfirmDialog(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("응원 하시겠습니까?");
        // builder.setMessage("응원 하시겠습니까?");
        builder.setPositiveButton("아니", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "안함", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNeutralButton("응원한다", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "사랑한다", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.create().show();

    }

    private void getMyContent() {
        MyBlogContentRequest contentRequest = new MyBlogContentRequest();
        if (isStart) {
            contentRequest.setURL(String.format(DefineNetwork.MY_BLOG_ART_TRIPLE, sBlogKey));

        } else {
            contentRequest.setURL(String.format(DefineNetwork.MY_BLOG_ART_TRIPLE_MORE, sBlogKey));
        }

        MyBlogController.getInstance().getMyBlogContent(contentRequest, new NetworkProcess.OnResultListener<BlogContentList>() {
            @Override
            public void onSuccess(NetworkRequest<BlogContentList> request, BlogContentList result) {
                if (isStart) {
                    if (0 < mAdapter.getCount()) {
                        mAdapter.clear();
                    }
                    isStart = false;
                }
                List<ContentData> arrData = MyBlogController.getInstance().getContentList(result);
                mAdapter.addList(arrData);
            }

            @Override
            public void onFail(NetworkRequest<BlogContentList> request, int code) {
                if (isStart) {
                    mAdapter.clear();
                    isStart = false;
                }
            }
        });
    }

    private void getFavoriteContent() {
        MyBlogContentRequest contentRequest = new MyBlogContentRequest();
        if (isStart) {
            contentRequest.setURL(String.format(DefineNetwork.MY_BLOG_FAVORITE_ART_TRIPLE, sBlogKey));

        } else {
            contentRequest.setURL(String.format(DefineNetwork.MY_BLOG_FAVORITE_ART_TRIPLE_MORE, sBlogKey));
        }

        MyBlogController.getInstance().getMyBlogContent(contentRequest, new NetworkProcess.OnResultListener<BlogContentList>() {
            @Override
            public void onSuccess(NetworkRequest<BlogContentList> request, BlogContentList result) {
                if (isStart) {
                    if (0 < mAdapter.getCount()) {
                        mAdapter.clear();
                        isStart = false;
                    }
                }
                List<ContentData> arrData = MyBlogController.getInstance().getContentList(result);
                mAdapter.addList(arrData);
            }

            @Override
            public void onFail(NetworkRequest<BlogContentList> request, int code) {
                if (isStart) {
                    mAdapter.clear();
                    isStart = false;
                }
            }
        });
    }

    private void getBlogInfo() {
        MyBlogInfoRequest request = new MyBlogInfoRequest();
        request.setURL(DefineNetwork.MY_BLOG_INFO + sBlogKey);
        MyBlogController.getInstance().getMyBlogInfo(request, new NetworkProcess.OnResultListener<BlogInfoResult>() {
            @Override
            public void onSuccess(NetworkRequest<BlogInfoResult> request, BlogInfoResult result) {
                setBlogData(result.mBlogInfo);
                isMe = result.mBlogInfo.sUserKey.equals(PropertyManager.getInstance().mUser.sUserkey);
            }

            @Override
            public void onFail(NetworkRequest<BlogInfoResult> request, int code) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_blog_thProfile:
                onProfileDialog();
                break;
            case R.id.img_blog_background:
                if(isMe) {
                    onBackgroundDialog();
                }
                break;
            case R.id.btn_tab_art:
                isMyContent = true;
                isStart = true;
                getMyContent();
                break;
            case R.id.btn_tab_favorite:
                isMyContent = false;
                isStart = true;
                getFavoriteContent();

                break;
            case R.id.btn_tab_culture: {
                Intent mIntent = new Intent(getContext(), BlogInCultureActivity.class);

                mIntent.putExtra(DefineNetwork.BLOG_KEY, mBlogData.sBlogKey);
                startActivity(mIntent);

                break;
            }
            case R.id.btn_tab_collabo: {
                //Toast.makeText(getContext(), "서비스 준비중입니다.", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_SPACE);

                startActivity(mIntent);
                break;
            }

            case R.id.btn_fanList: {
                if(isMe || mBlogData.arrFans.contains(PropertyManager.getInstance().mUser.sBlogKey)) {
                    Intent mIntent = new Intent(getContext(), SimpleTabUserListActivity.class);
                    mIntent.putExtra(DefineNetwork.BLOG_KEY, mBlogData.sBlogKey);
                    startActivity(mIntent);
                }else{
                    //+1
                }
                break;
            }
            case R.id.btn_imissyou: {
                if (isMe || mBlogData.arrFans.contains(PropertyManager.getInstance().mUser.sBlogKey)) {
                    Intent mIntent = new Intent(getContext(), SimpleSingleUserListActivity.class);
                    mIntent.putExtra(DefineNetwork.BLOG_KEY, mBlogData.sBlogKey);
                    mIntent.putExtra(DefineNetwork.USER_LIST_REQUEST, DefineNetwork.USER_LIST_TYPE_IMISSU);
                    startActivity(mIntent);
                } else {
                    //+1
                    //onConfirmDialog(v);
                }
                break;
            }
            default:
                return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        nSaveIdx = vTripleGrid.getFirstVisiblePosition();
        View v = vTripleGrid.getChildAt(0);
        nSaveTop = (v == null) ? 0 : (v.getTop() - vTripleGrid.getPaddingTop());
    }

    @Override
    public void onResume() {
        super.onResume();

        getBlogInfo();

    }
}