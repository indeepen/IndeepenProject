package com.release.indeepen.comment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.release.indeepen.management.networkManager.netComment.data.Comments;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lady on 2015. 11. 2..
 */
public class CommentAdapter  extends BaseAdapter implements  CommentItemView.OnImageClickListener {
    List<Comments> items = new ArrayList<Comments>();
    OnAdapterImageListener mListener;
    CommentItemView.OnImageClickListener mImageClickListener;

    public void setOnAdapterImageListener(OnAdapterImageListener listener) {
        mListener = listener;
    }

    public void setOnImageClickListener(CommentItemView.OnImageClickListener listener) {
        mImageClickListener = listener;
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }

    public void add(Comments item) {
        items.add(0, item);
        //items.add(item);
        notifyDataSetChanged();
    }

    public void setList(List commList){
        items.addAll(0, commList);
        notifyDataSetChanged();
    }

    public void clear(){
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
        CommentItemView view;
        if (convertView == null) {
            view =  new CommentItemView(parent.getContext());
            view.setOnImageClickListener(this);
        } else {
            view = (CommentItemView) convertView;
        }
        view.setItemData(items.get(position));
        return view;
    }

    @Override
    public void onImageClick(CommentItemView view, Comments data) {
        if (mListener != null) {
            mListener.onAdapterImageClick(this, view, data);
        }
    }

    public interface OnAdapterImageListener {
        void onAdapterImageClick(CommentAdapter adapter, CommentItemView view, Comments data);
    }
}

