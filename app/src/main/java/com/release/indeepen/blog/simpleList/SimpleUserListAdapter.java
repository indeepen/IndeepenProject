package com.release.indeepen.blog.simpleList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.release.indeepen.user.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-11-02.
 */
public class SimpleUserListAdapter extends BaseAdapter {

    List<UserData> items = new ArrayList<UserData>();

    public void add(UserData data) {
        items.add(data);
        notifyDataSetChanged();
    }

    public void addList(List<UserData> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }



    public void removeAll() {
        items.clear();
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

        SimpleUserView view = (SimpleUserView) convertView;

        if (null == view) {
            view = new SimpleUserView(parent.getContext());
        }

        view.setData((UserData) getItem(position));
        return view;
    }

}
