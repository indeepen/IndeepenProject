package com.release.indeepen.content.art.detail;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-11-01.
 */
public class SimpleImageAdapter extends BaseAdapter {

    List<String> items = new ArrayList<String>();


    public void add(String data) {
        items.add(data);
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
        SimpleImageView view = (SimpleImageView) convertView;
        if (null == view) {
            view = new SimpleImageView(parent.getContext());
        }
        view.setData((String) getItem(position));

        return view;
    }
}
