package com.release.indeepen.search;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfo;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfoResult;
import com.release.indeepen.management.networkManager.netSearch.data.HashTagResult;
import com.release.indeepen.management.networkManager.netSearch.data.Search;
import com.release.indeepen.space.SpaceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-11-24.
 */
public class SearchAdapter extends BaseAdapter {

    final int TYPE_COUNT = 3;

    final int TYPE_HASHTAG = 0;
    final int TYPE_BLOG = 1;
    final int TYPE_SPACE = 2;

    List<Search> items = new ArrayList<Search>();

    public void add(Search item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addList(List<Search> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (getItem(position) instanceof HashTagResult) {
            return TYPE_HASHTAG;
        } else if (getItem(position) instanceof BlogInfo) {
            return TYPE_BLOG;
        } else if (getItem(position) instanceof SpaceInfo) {
            return TYPE_SPACE;
        }

        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
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
        View view = convertView;

        switch (getItemViewType(position)) {

            case TYPE_HASHTAG: {
                if (null == view || !(view instanceof SearchHashTagView)) {
                    view = new SearchHashTagView(parent.getContext());
                }
                ((SearchHashTagView)view).setData((HashTagResult) getItem(position));

                break;
            }
            case TYPE_BLOG: {

                if (null == view || !(view instanceof SearchArtistView)) {
                    view = new SearchArtistView(parent.getContext());
                }
                ((SearchArtistView)view).setData((BlogInfo) getItem(position));
                break;
            }
            case TYPE_SPACE: {
                if (null == view || !(view instanceof SearchSpaceView)) {
                    view = new SearchSpaceView(parent.getContext());
                }
                ((SearchSpaceView)view).setData((SpaceInfo) getItem(position));
                break;
            }
        }

        return view;
    }
}
