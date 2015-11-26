package com.release.indeepen.blog;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.R;
import com.release.indeepen.blog.simpleList.TabsAdapter;
import com.release.indeepen.culture.CultureListFragment;

public class BlogInCultureActivity extends AppCompatActivity {


    TabHost tabHost;
    ViewPager pager;
    TabsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_in_culture);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        pager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager);

        Intent intent = getIntent();

        Bundle bMyCulture = new Bundle();
        bMyCulture.putString(DefineNetwork.REQUEST_URL, String.format(DefineNetwork.MY_CULTURE_LIST, intent.getStringExtra(DefineNetwork.BLOG_KEY)));
        bMyCulture.putString(DefineNetwork.REQUEST_URL_MORE, String.format(DefineNetwork.MY_CULTURE_LIST_MORE, intent.getStringExtra(DefineNetwork.BLOG_KEY)));
        bMyCulture.putInt(DefineNetwork.REQUEST_TYPE, DefineNetwork.MY_CULTURE);

        Bundle bMyFavoriteCulture = new Bundle();
        bMyFavoriteCulture.putString(DefineNetwork.REQUEST_URL, String.format(DefineNetwork.MY_FAVORITE_CULTURE_LIT, intent.getStringExtra(DefineNetwork.BLOG_KEY)));
        bMyFavoriteCulture.putString(DefineNetwork.REQUEST_URL_MORE, String.format(DefineNetwork.MY_FAVORITE_CULTURE_LIT_MORE, intent.getStringExtra(DefineNetwork.BLOG_KEY)));
        bMyCulture.putInt(DefineNetwork.REQUEST_TYPE, DefineNetwork.MY_FAVORITE_CULTURE);


        mAdapter.addTab(tabHost.newTabSpec(DefineContentType.BLOG_MY_CULTURE).setIndicator(DefineContentType.BLOG_MY_CULTURE), CultureListFragment.class, bMyCulture);
        mAdapter.addTab(tabHost.newTabSpec(DefineContentType.BLOG_LIKE_CULTURE).setIndicator(DefineContentType.BLOG_LIKE_CULTURE), CultureListFragment.class, bMyFavoriteCulture);

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
