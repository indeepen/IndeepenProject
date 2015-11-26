package com.release.indeepen.menu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lady on 2015. 11. 16..
 */
public class MenuAdapter extends BaseAdapter {
    List<MenuItemData> items = new ArrayList<MenuItemData>();


    public void add(MenuItemData item) {
        items.add(item);
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
        MenuItemView view;
        if (convertView == null) {
            view = new MenuItemView(parent.getContext());
        } else {
            view = (MenuItemView) convertView;
        }
        view.setItemData(items.get(position));
        return view;
    }
}