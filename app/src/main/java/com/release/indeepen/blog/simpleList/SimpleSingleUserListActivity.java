package com.release.indeepen.blog.simpleList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogController;
import com.release.indeepen.management.networkManager.netMyBlog.UserListRequest;
import com.release.indeepen.management.networkManager.netMyBlog.data.WriterResultList;
import com.release.indeepen.user.UserData;

import java.util.List;

public class SimpleSingleUserListActivity extends AppCompatActivity {

    ListView vList;
    SimpleUserListAdapter mAdapter;
    String sBlogKey;
    boolean isStart, isLastItem;
    int nRequestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_single_user_list);

        SimpleUserListFragment simpleUserListFragment = new SimpleUserListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(DefineNetwork.USER_LIST_REQUEST, getIntent().getIntExtra(DefineNetwork.USER_LIST_REQUEST, -1));
        bundle.putString(DefineNetwork.BLOG_KEY, getIntent().getStringExtra(DefineNetwork.BLOG_KEY));
        simpleUserListFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_user_list, simpleUserListFragment).commit();
    }


}
