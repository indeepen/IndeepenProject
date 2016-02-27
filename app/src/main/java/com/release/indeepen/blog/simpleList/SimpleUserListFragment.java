package com.release.indeepen.blog.simpleList;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleUserListFragment extends Fragment {

    ListView vList;
    SimpleUserListAdapter mAdapter;
    String sBlogKey;
    boolean isStart, isLastItem;
    int nRequestType;

    public SimpleUserListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_simple_user_list, container, false);
        vList = (ListView) view.findViewById(R.id.list_simple_user);
        mAdapter = new SimpleUserListAdapter();
        vList.setAdapter(mAdapter);

        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_BLOG);
                mIntent.putExtra(DefineNetwork.BLOG_KEY, ((UserData) mAdapter.getItem((int) vList.getItemIdAtPosition(position))).sBlogKey);
                startActivity(mIntent);
                getActivity().finish();
            }
        });
        vList.setOnScrollListener(new AbsListView.OnScrollListener() {
            int mLastFirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    getUserList();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                isLastItem = totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
            }
        });

        init();
        return view;
    }


    private void init() {
        isStart = true;
        if (null != getArguments()) {
            sBlogKey = getArguments().getString(DefineNetwork.BLOG_KEY);
            nRequestType = getArguments().getInt(DefineNetwork.USER_LIST_REQUEST, -1);
            getUserList();
        }
    }

    private void getUserList(){

        UserListRequest contentRequest = new UserListRequest();
        switch (nRequestType){
            case DefineNetwork.USER_LIST_TYPE_MY_FAN:{
                if (isStart) {
                    contentRequest.setURL(String.format(DefineNetwork.MY_BLOG_FAN_LIST, sBlogKey));

                } else {
                    contentRequest.setURL(String.format(DefineNetwork.MY_BLOG_FAN_LIST_MORE, sBlogKey));
                }
                break;
            }
            case DefineNetwork.USER_LIST_TYPE_MY_ARTIST:{
                if (isStart) {
                    contentRequest.setURL(String.format(DefineNetwork.MY_BLOG_ARTIST_LIST, sBlogKey));

                } else {
                    contentRequest.setURL(String.format(DefineNetwork.MY_BLOG_ARTIST_LIST_MORE, sBlogKey));
                }
                break;
            }
            case DefineNetwork.USER_LIST_TYPE_IMISSU:{
                if (isStart) {
                    contentRequest.setURL(String.format(DefineNetwork.MY_BLOG_IMISSU_LIST, sBlogKey));

                } else {
                    contentRequest.setURL(String.format(DefineNetwork.MY_BLOG_IMISSU_LIST_MORE, sBlogKey));
                }
                break;
            }
        }


        MyBlogController.getInstance().getWriterList(contentRequest, new NetworkProcess.OnResultListener<WriterResultList>() {
            @Override
            public void onSuccess(NetworkRequest<WriterResultList> request, WriterResultList result) {
                if (isStart) {
                    if (0 < mAdapter.getCount()) {
                        mAdapter.clear();
                        isStart = false;
                    }
                }
                List<UserData> arrData = MyBlogController.getInstance().getUserList(result);
                mAdapter.addList(arrData);
            }

            @Override
            public void onFail(NetworkRequest<WriterResultList> request, int code) {
                if (isStart) {
                    mAdapter.clear();
                    isStart = false;
                }
            }
        });
    }


}
