package com.release.indeepen.create.selectMedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.release.indeepen.R;
import com.release.indeepen.create.BlogData;
import com.release.indeepen.create.BlogListAdapter;
import com.release.indeepen.create.HorizontalListView;
import com.release.indeepen.login.PropertyManager;

/**
 * Created by lyo on 2015-11-08.
 */
public class PopupSelectBlog extends PopupWindow {
    Context mContext;
    public HorizontalListView vList;
    BlogListAdapter mAdapter;

    public PopupSelectBlog(Context context) {
        super(context);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_choice_blog, null);
        vList = (HorizontalListView) view.findViewById(R.id.list_create_header_blog);
        mAdapter = new BlogListAdapter();
        vList.setAdapter(mAdapter);

        init();

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void init() {
        BlogData data;
        data = new BlogData();
        data.sName =  PropertyManager.getInstance().mUser.sArtist;
        data.sBlogkey = PropertyManager.getInstance().mUser.sBlogKey;
        data.sIMGPath = "drawable://" + R.drawable.emo_sad;
        //mAdapter.add(data);

      /*  if( null != PropertyManager.getInstance().mUser.arrSpaceKeys||0 < PropertyManager.getInstance().mUser.arrSpaceKeys.size()) {
            for (int idx = 0; idx < PropertyManager.getInstance().mUser.arrSpaceKeys.size(); idx++) {
                data = new BlogData();
                data.sName = "블로그" + idx;
                data.sIMGPath = "drawable://" + R.drawable.emo_sad;
                data.sBlogkey = "adgadgds" + idx;
                mAdapter.add(data);
            }
        }*/
    }

    public void setData(BlogData data){

    }
}
