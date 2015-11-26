package com.release.indeepen.content.art.singleList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.content.art.ContentMusicData;
import com.release.indeepen.content.art.ContentYoutubeData;
import com.release.indeepen.culture.CultureItemData;
import com.release.indeepen.culture.CultureItemView;
import com.release.indeepen.management.musicManager.MusicManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-11-01.
 */
public class ContentSingleListAdapter extends BaseAdapter {

    List<ContentData> items = new ArrayList<ContentData>();

    public void add(ContentData data) {
        items.add(data);
        notifyDataSetChanged();
    }
    public void addList(List<ContentData> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }


    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        switch (((ContentData) getItem(position)).nArtType) {
            case DefineContentType.SINGLE_ART_TYPE_PAINT:
            case DefineContentType.SINGLE_ART_TYPE_PICTURE:
            case DefineContentType.SINGLE_ART_TYPE_MUSIC_PICTURE:
                return DefineContentType.SINGLE_IMAGE;

            case DefineContentType.SINGLE_ART_TYPE_MUSIC:
                return DefineContentType.SINGLE_MUSIC;

            case DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO:
                return DefineContentType.SINGLE_VIDEO;

            case DefineContentType.SINGLE_ART_TYPE_YOUTUBE:
                return DefineContentType.SINGLE_YOUTUBE;
            case DefineContentType.SINGLE_ART_TYPE_CULTURE:
                return DefineContentType.SINGLE_CULTURE;
            default:
                return -1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return DefineContentType.SINGLE_TYPE_COUNT;
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
        int nType = getItemViewType(position);

        switch (nType) {
            case DefineContentType.SINGLE_IMAGE:{
                SingleImageView view = (SingleImageView) convertView;
                if (null == view) {
                    view = new SingleImageView(parent.getContext());
                }
                view.setData((ContentImageData) items.get(position));

                return view;
            }
            /*case DefineContentType.SINGLE_VIDEO:{
                SingleVideoView view = (SingleVideoView) convertView;
                if (null == view) {
                    view = new SingleVideoView(parent.getContext());
                }
                view.setData((ContentVideoData)items.get(position));

                return view;

            }*/case DefineContentType.SINGLE_MUSIC:{
                SingleMusicView view = (SingleMusicView) convertView;
                if (null == view) {
                    view = new SingleMusicView(parent.getContext());
                }else{
                    //view.stop();
                    MusicManager.getMusicManager().pause();
                }
                view.setData((ContentMusicData)items.get(position));
                return view;
            }case DefineContentType.SINGLE_YOUTUBE:{
                SingleYoutubeView view = (SingleYoutubeView) convertView;
                if (null == view) {
                    view = new SingleYoutubeView(parent.getContext());
                }
                view.setData((ContentYoutubeData) items.get(position));

                return view;
            }case DefineContentType.SINGLE_CULTURE:{
                CultureItemView view = (CultureItemView) convertView;
                if (null == view) {
                    view = new CultureItemView(parent.getContext());
                }
                view.setItemData((CultureItemData) items.get(position));
                return view;
            }default: return null;


           /* case DefineContentType.SINGLE_YOUTUBE:{
                return;
            }*/
        }
    }
}
