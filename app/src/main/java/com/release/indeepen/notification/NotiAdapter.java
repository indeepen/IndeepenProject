package com.release.indeepen.notification;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.release.indeepen.DefineContentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-10-31.
 */
public class NotiAdapter extends BaseAdapter {

    List<PushData> items = new ArrayList<PushData>();

    public void add(PushData data) {
        items.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return DefineContentType.NOTI_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {

        switch (((PushData) getItem(position)).nPushType) {
            case DefineContentType.NOTI_TYPE_LIKE_CULTURE:
            case DefineContentType.NOTI_TYPE_LIKE_ART:
            case DefineContentType.NOTI_TYPE_COMM_CULTURE:
            case DefineContentType.NOTI_TYPE_COMM_ART:
            case DefineContentType.NOTI_TYPE_TAG:
                return DefineContentType.NOTI_LIST_TEXT;

            case DefineContentType.NOTI_TYPE_FAN:
            case DefineContentType.NOTI_TYPE_MISSU:
            case DefineContentType.NOTI_TYPE_FAN_SPACE:
            case DefineContentType.NOTI_TYPE_MISSU_SPACE:
            default:
                return DefineContentType.NOTI_LIST_IMG;
        }
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


        if (DefineContentType.NOTI_LIST_IMG == getItemViewType(position)) {
            NotiImageView view;
            switch (((PushData) getItem(position)).nPushType) {
                case DefineContentType.NOTI_TYPE_LIKE_CULTURE:
                case DefineContentType.NOTI_TYPE_LIKE_ART:
                case DefineContentType.NOTI_TYPE_COMM_CULTURE:
                case DefineContentType.NOTI_TYPE_COMM_ART:
                case DefineContentType.NOTI_TYPE_TAG:
                default:
                    view = (NotiImageView) convertView;
                    if (null == view) {
                        view = new NotiImageView(parent.getContext());
                    }
                    view.setData((PushData) getItem(position));
                    return view;
            }
        } else {
            NotiTextView view;
            switch (((PushData) getItem(position)).nPushType) {
                case DefineContentType.NOTI_TYPE_FAN:
                case DefineContentType.NOTI_TYPE_MISSU:
                case DefineContentType.NOTI_TYPE_FAN_SPACE:
                case DefineContentType.NOTI_TYPE_MISSU_SPACE:
                default:
                    view = (NotiTextView) convertView;
                    if (null == view) {
                        view = new NotiTextView(parent.getContext());
                    }
                    view.setData((PushData) getItem(position));
                    return view;
            }
        }
    }
}
