package com.release.indeepen.blog.simpleList;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.R;

public class SimpleTabUserListActivity extends AppCompatActivity {
    TabHost tabHost;
    ViewPager pager;
    TabsAdapter mAdapter;
    View myfan, myartist;
    ImageView tab_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab_user_list);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        pager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager);

        Bundle fanBundle = getIntent().getExtras();
        fanBundle.putInt(DefineNetwork.USER_LIST_REQUEST, DefineNetwork.USER_LIST_TYPE_MY_FAN);

        Bundle artistBundle = getIntent().getExtras();
        artistBundle.putInt(DefineNetwork.USER_LIST_REQUEST, DefineNetwork.USER_LIST_TYPE_MY_ARTIST);
        myfan = getLayoutInflater().inflate(R.layout.view_image, null);
        myartist = getLayoutInflater().inflate(R.layout.view_image, null);

        tab_image = (ImageView) myfan.findViewById(R.id.tab_image);
        tab_image.setBackgroundResource(R.drawable.blog_navi_fan);
        tab_image = (ImageView) myartist.findViewById(R.id.tab_image);
        tab_image.setBackgroundResource(R.drawable.blog_navi_artist);


        mAdapter.addTab(tabHost.newTabSpec(DefineContentType.SIMPLE_MY_FAN).setIndicator(myfan), SimpleUserListFragment.class, fanBundle);
        mAdapter.addTab(tabHost.newTabSpec(DefineContentType.SIMPLE_MY_ARTIST).setIndicator(myartist), SimpleUserListFragment.class, artistBundle);


        /*tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals(DefineContentType.SIMPLE_MY_FAN)){
                    ((SimpleUserListFragment)getSupportFragmentManager().findFragmentByTag(DefineContentType.SIMPLE_MY_FAN)).init();
                }else{
                    ((SimpleUserListFragment)getSupportFragmentManager().findFragmentByTag(DefineContentType.SIMPLE_MY_ARTIST)).init();
                }
            }
        });*/
        if (savedInstanceState != null) {
            tabHost.setCurrentTab(savedInstanceState.getInt("tabIndex"));
            mAdapter.onRestoreInstanceState(savedInstanceState);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("tabIndex", tabHost.getCurrentTab());
        mAdapter.onSaveInstanceState(outState);
    }
}
