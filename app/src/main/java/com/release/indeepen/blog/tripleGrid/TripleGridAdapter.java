package com.release.indeepen.blog.tripleGrid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.R;
import com.release.indeepen.content.ContentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-10-31.
 */
public class TripleGridAdapter extends BaseAdapter {

    List<ContentData> items = new ArrayList<ContentData>();
    Context mContext;

    public TripleGridAdapter(Context context) {
        this.mContext = context;
    }

    public void add(ContentData data) {
        items.add(data);
        notifyDataSetChanged();
    }

    public void addList(List<ContentData> arrData) {
        items.addAll(arrData);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }


    public void removeAll() {
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
        ThumbImageView view = (ThumbImageView) convertView;
        if (null == view) {
            view = new ThumbImageView(parent.getContext());
        }

        if(DefineContentType.SINGLE_ART_TYPE_YOUTUBE == ((ContentData) getItem(position)).nArtType || DefineContentType.SINGLE_ART_TYPE_MUSIC == ((ContentData) getItem(position)).nArtType|| DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO == ((ContentData) getItem(position)).nArtType){
            view.findViewById(R.id.play_icon).setVisibility(View.VISIBLE);
        }else{
            view.findViewById(R.id.play_icon).setVisibility(View.GONE);
        }
        //view.setIMG(items.get(position).thIMG);

        view.setData(items.get(position));
        return view;
    }


}