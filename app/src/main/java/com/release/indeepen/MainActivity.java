package com.release.indeepen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.release.indeepen.blog.BlogFragment;
import com.release.indeepen.blog.BlogMainFragment;
import com.release.indeepen.content.art.detail.ContentDetailImageFragment;
import com.release.indeepen.create.CreateFragment;
import com.release.indeepen.culture.CultureFragment;
import com.release.indeepen.culture.CultureListFragment;
import com.release.indeepen.culture.CultureLocalFragment;
import com.release.indeepen.fan.FanFragment;
import com.release.indeepen.fan.FanMainFragment;
import com.release.indeepen.login.PropertyManager;
import com.release.indeepen.management.musicManager.MusicManager;
import com.release.indeepen.menu.MenuActivity;
import com.release.indeepen.notification.NotificationFragment;
import com.release.indeepen.notification.NotificationMainFragment;
import com.release.indeepen.search.SearchActivity;
import com.release.indeepen.search.SearchTripleFragment;
import com.release.indeepen.space.SpaceFragment;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    FragmentTabHost vTabHost;
    FrameLayout vContainer;
    FragmentManager mFM;
    boolean isClose = false;
    boolean isFirst = true;
    private OnKeyBackPressedListener mOnKeyBackPressedListener;
    ImageView actionRealSearch;
    ImageView actionSearch;
    View culture, fan, create, notification, myblog = null;
    ImageView tab_image;
    Bundle bUserBlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null != savedInstanceState) {

        }
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionSearch = (ImageView) findViewById(R.id.img_main_search);
        actionSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceSearch();
            }
        });
        actionRealSearch = (ImageView) findViewById(R.id.img_main_real_search);
        actionRealSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


        culture = getLayoutInflater().inflate(R.layout.view_image, null);
        fan = getLayoutInflater().inflate(R.layout.view_image, null);
        create = getLayoutInflater().inflate(R.layout.view_image, null);
        notification = getLayoutInflater().inflate(R.layout.view_image, null);
        myblog = getLayoutInflater().inflate(R.layout.view_image, null);

        tab_image = (ImageView) culture.findViewById(R.id.tab_image);
        tab_image.setBackgroundResource(R.drawable.navigation_culture);
        tab_image = (ImageView) fan.findViewById(R.id.tab_image);
        tab_image.setBackgroundResource(R.drawable.navigation_fan);
        tab_image = (ImageView) create.findViewById(R.id.tab_image);
        tab_image.setBackgroundResource(R.drawable.navigation_create);
        tab_image = (ImageView) notification.findViewById(R.id.tab_image);
        tab_image.setBackgroundResource(R.drawable.navigation_notification);
        tab_image = (ImageView) myblog.findViewById(R.id.tab_image);
        tab_image.setBackgroundResource(R.drawable.navigation_blog);


        vContainer = (FrameLayout) findViewById(R.id.realtabcontent);
        mFM = getSupportFragmentManager();
        vTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        vTabHost.setup(this, mFM, R.id.realtabcontent);

        bUserBlog = new Bundle();
        bUserBlog.putString(DefineNetwork.BLOG_KEY, PropertyManager.getInstance().mUser.sBlogKey);

        vTabHost.addTab(vTabHost.newTabSpec(DefineContentType.MAIN_TAB_CULTURE).setIndicator(culture), CultureFragment.class, null);
        vTabHost.addTab(vTabHost.newTabSpec(DefineContentType.MAIN_TAB_FAN).setIndicator(fan), FanFragment.class, null);
        vTabHost.addTab(vTabHost.newTabSpec(DefineContentType.MAIN_TAB_CREATE).setIndicator(create), CreateFragment.class, null);
        vTabHost.addTab(vTabHost.newTabSpec(DefineContentType.MAIN_TAB_NOTIFICATION).setIndicator(notification), NotificationFragment.class, null);
        vTabHost.addTab(vTabHost.newTabSpec(DefineContentType.MAIN_TAB_MYBLOG).setIndicator(myblog), BlogFragment.class, bUserBlog);

        vTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(final String tabId) {
                changeSearchFake();
                //mFM.popBackStackImmediate(null, mFM.POP_BACK_STACK_INCLUSIVE);
                isClose = false;

                if (MusicManager.PlayState.STARTED == MusicManager.getMusicManager().getState()) {
                    MusicManager.getMusicManager().pause();
                }

                vTabHost.getCurrentTabView().setOnTouchListener(

                        new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                    if (tabId.equalsIgnoreCase(DefineContentType.MAIN_TAB_CULTURE)) {
                                        if (vTabHost.getCurrentTabTag().equalsIgnoreCase(DefineContentType.MAIN_TAB_CULTURE)) {
                                            Fragment fragment = mFM.findFragmentByTag(DefineContentType.MAIN_TAB_CULTURE);
                                            CultureListFragment cultureListFragment = new CultureListFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putInt(DefineContentType.KEY_ON_NEW_FROM, DefineContentType.FROM_CULTURE);
                                            cultureListFragment.setArguments(bundle);
                                            fragment.getChildFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                            fragment.getChildFragmentManager().beginTransaction().commit();
                                            fragment.getChildFragmentManager().beginTransaction().replace(((MainTab) fragment).getContainer(), cultureListFragment, DefineContentType.MAIN_TAB_CULTURE).commitAllowingStateLoss();
                                        }
                                    } else if (tabId.equalsIgnoreCase(DefineContentType.MAIN_TAB_FAN)) {
                                        if (vTabHost.getCurrentTabTag().equalsIgnoreCase(DefineContentType.MAIN_TAB_FAN)) {
                                            Fragment fragment = mFM.findFragmentByTag(DefineContentType.MAIN_TAB_FAN);
                                            FanMainFragment fanMainFragment = new FanMainFragment();
                                            Bundle b = new Bundle();
                                            b.putInt(DefineContentType.KEY_ON_NEW_FROM, DefineContentType.FROM_FAN);
                                            fanMainFragment.setArguments(b);
                                            fragment.getChildFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                            fragment.getChildFragmentManager().beginTransaction().commit();
                                            fragment.getChildFragmentManager().beginTransaction().replace(((MainTab) fragment).getContainer(), fanMainFragment, DefineContentType.MAIN_TAB_FAN).commitAllowingStateLoss();
                                        }
                                    } else if (tabId.equalsIgnoreCase(DefineContentType.MAIN_TAB_NOTIFICATION)) {
                                        if (vTabHost.getCurrentTabTag().equalsIgnoreCase(DefineContentType.MAIN_TAB_NOTIFICATION)) {
                                            Fragment fragment = mFM.findFragmentByTag(DefineContentType.MAIN_TAB_NOTIFICATION);
                                            NotificationMainFragment notificationMainFragment = new NotificationMainFragment();
                                            fragment.getChildFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                            fragment.getChildFragmentManager().beginTransaction().commit();
                                            fragment.getChildFragmentManager().beginTransaction().replace(((MainTab) fragment).getContainer(), notificationMainFragment, DefineContentType.MAIN_TAB_NOTIFICATION).commitAllowingStateLoss();
                                        }
                                    } else if (tabId.equalsIgnoreCase(DefineContentType.MAIN_TAB_MYBLOG)) {
                                        if (vTabHost.getCurrentTabTag().equalsIgnoreCase(DefineContentType.MAIN_TAB_MYBLOG)) {
                                            Fragment fragment = mFM.findFragmentByTag(DefineContentType.MAIN_TAB_MYBLOG);
                                            BlogMainFragment blogMainFragment = new BlogMainFragment();
                                            blogMainFragment.setArguments(bUserBlog);
                                            fragment.getChildFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                            fragment.getChildFragmentManager().beginTransaction().commit();
                                            fragment.getChildFragmentManager().beginTransaction().replace(((MainTab) fragment).getContainer(), blogMainFragment, DefineContentType.MAIN_TAB_MYBLOG).commitAllowingStateLoss();
                                        }
                                    }
                                }
                                return false; // returning false seems do the
                                // trick <img src="http://www.anddev.org/images/smilies/smile.png" alt=":)" title="Smile" />
                            }
                        });
            }
        });

    }

    private void replaceSearch() {
        if (MusicManager.PlayState.STARTED == MusicManager.getMusicManager().getState()) {
            MusicManager.getMusicManager().pause();
        }
        FanFragment fanFragment = (FanFragment) mFM.findFragmentByTag(DefineContentType.MAIN_TAB_FAN);
        if (null == fanFragment) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(40);
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }).start();

            vTabHost.setCurrentTabByTag(DefineContentType.MAIN_TAB_FAN);
            mHandleSeach.postDelayed(mRunSearch, 30);
        } else {
            vTabHost.setCurrentTabByTag(DefineContentType.MAIN_TAB_FAN);
            //fanFragment.getChildFragmentManager().beginTransaction().addToBackStack(null).replace((fanFragment).getContainer(), new SearchTripleFragment(), DefineContentType.FRAGMENT_TAG_SEARCH).commitAllowingStateLoss();
            fanFragment.getChildFragmentManager().beginTransaction().replace((fanFragment).getContainer(), new SearchTripleFragment(), DefineContentType.FRAGMENT_TAG_SEARCH).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (null != intent) {

            switch (intent.getIntExtra(DefineContentType.KEY_ON_NEW_REQUEST, -1)) {
                case DefineContentType.TYPE_ON_NEW_REPLACE: { // 리플레이스 요청
                    goFragment(intent);
                    break;
                }
                case DefineContentType.TYPE_ON_NEW_ACTIVITY: { // 스타트 액티비티 요청
                    //goActivity(intent.getSerializableExtra(DefineContentType.KEY_ON_NEW_ACTIVITY_DATA));
                    break;
                }
                case DefineContentType.ACTIVITY_TYPE_PROFILE_BACKGROUND: { //


                    break;
                }
                case DefineContentType.ACTIVITY_TYPE_FIXD_INFO: { //
                    Toast.makeText(MainActivity.this, "프로필수정처리", Toast.LENGTH_SHORT).show();
                    break;
                }
                case DefineContentType.ACTIVITY_TYPE_PROFILE_IMG: { //


                    break;
                }
                case DefineContentType.TO_CULTURE_TAB: {
                    vTabHost.setCurrentTabByTag(DefineContentType.MAIN_TAB_CULTURE);
                }
            }
        }
    }

    private void goActivity(Intent intent, Serializable data) {
        switch (intent.getIntExtra(DefineContentType.KEY_ON_NEW_WHERE, -1)) {
        }

    }

    private void goFragment(Intent intent) {
        Fragment fragment = mFM.findFragmentByTag(vTabHost.getCurrentTabTag());
        if (MusicManager.PlayState.STARTED == MusicManager.getMusicManager().getState()) {
            MusicManager.getMusicManager().pause();
        }

        switch (intent.getIntExtra(DefineContentType.KEY_ON_NEW_WHERE, -1)) {
            case DefineContentType.TO_FAN_LIST:
                FanMainFragment fanMainFragment = new FanMainFragment();
                fanMainFragment.setArguments(intent.getExtras());
                fragment.getChildFragmentManager().beginTransaction().addToBackStack(null).replace(((MainTab) fragment).getContainer(), fanMainFragment, DefineContentType.FRAGMENT_TAG_FAN).commitAllowingStateLoss();
                break;
            case DefineContentType.TO_BLOG: {
                BlogMainFragment blogMainFragment = new BlogMainFragment();
                blogMainFragment.setArguments(intent.getExtras());
                fragment.getChildFragmentManager().beginTransaction().addToBackStack(null).replace(((MainTab) fragment).getContainer(), blogMainFragment,  DefineContentType.FRAGMENT_TAG_MY_BLOG).commitAllowingStateLoss();
                break;
            }
            case DefineContentType.TO_DETAIL_IMGAE: {
                fragment.getChildFragmentManager().beginTransaction().addToBackStack(null).replace(((MainTab) fragment).getContainer(), new ContentDetailImageFragment()).commitAllowingStateLoss();
                break;
            }
            case DefineContentType.TO_DETAIL_MUSIC_VIDEO: {
                break;
            }
            case DefineContentType.TO_DETAIL_MUSIC: {
                break;
            }
            case DefineContentType.TO_DETAIL_YOUTUBE: {
                break;
            }
            case DefineContentType.TO_DETAIL_CULTURE: {
                break;
            }
            case DefineContentType.TO_SPACE: {
                SpaceFragment spaceFragment = new SpaceFragment();
                spaceFragment.setArguments(intent.getExtras());
                fragment.getChildFragmentManager().beginTransaction().addToBackStack(null).replace(((MainTab) fragment).getContainer(), spaceFragment , DefineContentType.FRAGMENT_TAG_SPACE).commitAllowingStateLoss();
                break;
            }
            case DefineContentType.TO_CULTURE_LOCAL: {
                fragment.getChildFragmentManager().beginTransaction().addToBackStack(null).replace(((MainTab) fragment).getContainer(), new CultureLocalFragment(), DefineContentType.FRAGMENT_TAG_CULTURE).commitAllowingStateLoss();
                break;
            }
            case DefineContentType.TO_SEARCH_TRIPLE:{
                SearchTripleFragment searchTripleFragment = new SearchTripleFragment();
                searchTripleFragment.setArguments(intent.getExtras());
                fragment.getChildFragmentManager().beginTransaction().addToBackStack(null).replace(((MainTab) fragment).getContainer(), searchTripleFragment).commitAllowingStateLoss();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            case R.id.action_settings: {
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    Handler mHandleSeach = new Handler(Looper.getMainLooper());

    Runnable mRunSearch = new Runnable() {
        @Override
        public void run() {
            FanFragment fanFragment = (FanFragment) mFM.findFragmentByTag(DefineContentType.MAIN_TAB_FAN);
            fanFragment.getChildFragmentManager().beginTransaction().addToBackStack(null).replace((fanFragment).getContainer(), new SearchTripleFragment(), DefineContentType.FRAGMENT_TAG_SEARCH).commitAllowingStateLoss();
        }
    };

    private void changeSearchFake() {
        actionRealSearch.setVisibility(View.GONE);
        actionSearch.setVisibility(View.VISIBLE);
    }

    /*private void changSearchReal() {
        actionRealSearch.setVisibility(View.VISIBLE);
        actionSearch.setVisibility(View.GONE);
    }*/

    @Override
    public void onBackPressed() {

        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBack();
            return;
        }
        changeSearchFake();
        if (mFM.getBackStackEntryCount() > 0) {
            mFM.popBackStack();
        } else if (mFM.getBackStackEntryCount() == 0) {
            if (isClose) {
                MusicManager.getMusicManager().pause();
                super.onBackPressed();
            } else {
                vTabHost.setCurrentTabByTag(DefineContentType.MAIN_TAB_CULTURE);
                isClose = true;
            }

        }
    }

    public OnKeyBackPressedListener getOnKeyBackPressedListener() {
        return mOnKeyBackPressedListener;
    }

    public void setOnKeyBackPressedListener(OnKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public interface OnKeyBackPressedListener {
        void onBack();
    }

}
