package com.release.indeepen.create;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-11-08.
 */
public class BlogListAdapter extends BaseAdapter {

    List<BlogData> items = new ArrayList<BlogData>();

    public void add(BlogData data){
        items.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CreateChoiceBlogView view = (CreateChoiceBlogView)convertView;
        if(null == view){
            view = new CreateChoiceBlogView(parent.getContext());
        }
        view.setData((BlogData)getItem(position));
        return view;
    }
}
