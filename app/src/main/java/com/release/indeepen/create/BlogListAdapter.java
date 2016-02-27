package com.release.indeepen.create;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-11-08.
 */
public class BlogListAdapter extends BaseAdapter {

    List<BlogInfo> items = new ArrayList<BlogInfo>();

    public void add(BlogInfo data){
        items.add(data);
        notifyDataSetChanged();
    }

    public void addList(List<BlogInfo> list){
        items.addAll(list);
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
        view.setData((BlogInfo)getItem(position));
        return view;
    }
}