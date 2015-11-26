package com.release.indeepen.search;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TabHost;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.R;
import com.release.indeepen.blog.simpleList.TabsAdapter;

public class SearchActivity extends AppCompatActivity {
    TabHost tabHost;
    ViewPager pager;
    TabsAdapter mAdapter;
    SearchView vSearchView;
    EditText vInputkeyword;
    String keyWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_search));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        pager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager);

        vSearchView = (SearchView) findViewById(R.id.view_search);
        vInputkeyword = (EditText) vSearchView.findViewById(R.id.input_search);

        Bundle bundle = new Bundle();
        bundle.putInt(DefineNetwork.REQUEST_TYPE, DefineNetwork.TYPE_SEARCH_ALL);
        mAdapter.addTab(tabHost.newTabSpec(DefineContentType.SEARCH_TAB_ALL).setIndicator(DefineContentType.SEARCH_TAB_ALL), SearchListFragment.class, bundle);
        bundle = new Bundle();
        bundle.putInt(DefineNetwork.REQUEST_TYPE, DefineNetwork.TYPE_SEARCH_HASHTAG);
        mAdapter.addTab(tabHost.newTabSpec(DefineContentType.SEARCH_TAB_TAG).setIndicator(DefineContentType.SEARCH_TAB_TAG), SearchListFragment.class, bundle);
        bundle = new Bundle();
        bundle.putInt(DefineNetwork.REQUEST_TYPE, DefineNetwork.TYPE_SEARCH_ARTIST);
        mAdapter.addTab(tabHost.newTabSpec(DefineContentType.SEARCH_TAB_ARTIST).setIndicator(DefineContentType.SEARCH_TAB_ARTIST), SearchListFragment.class, bundle);
        bundle = new Bundle();
        bundle.putInt(DefineNetwork.REQUEST_TYPE, DefineNetwork.TYPE_SEARCH_SPACE);
        mAdapter.addTab(tabHost.newTabSpec(DefineContentType.SEARCH_TAB_SPACE).setIndicator(DefineContentType.SEARCH_TAB_SPACE), SearchListFragment.class, bundle);


        vInputkeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyWord = s.toString();
                ((SearchListFragment) mAdapter.getCurrentTabFragment()).searchKeyword(keyWord);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAdapter.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                ((SearchListFragment) mAdapter.getCurrentTabFragment()).searchKeyword(keyWord);
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
