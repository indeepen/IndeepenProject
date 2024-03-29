package com.release.indeepen.culture;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.release.indeepen.content.ContentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lady on 2015. 11. 2..
 */
public class CultureAdapter  extends BaseAdapter{
    List<CultureItemData> items = new ArrayList<CultureItemData>();
    CultureListFragment fragment;

    public void setFragment(CultureListFragment fragment){
        this.fragment = fragment;
    }
    public void changeData(int position, CultureItemData data) {
        items.set(position, data);
    }
    public void removeData(int position) {
        items.remove(position);
    }

    public void add(CultureItemData item) {
        items.add(item);
        notifyDataSetChanged();
    }
    public void addList(List<CultureItemData> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
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
        CultureItemView view;

        if (convertView == null) {
            view =  new CultureItemView(parent.getContext());
        } else {
            view = (CultureItemView) convertView;
        }
        view.setItemData(items.get(position), fragment, position);


        //view.setTag(position);
        return view;
    }
}

