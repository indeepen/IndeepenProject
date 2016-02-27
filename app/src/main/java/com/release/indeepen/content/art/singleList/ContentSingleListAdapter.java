package com.release.indeepen.content.art.singleList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.release.indeepen.DefineContentType;
import com.release.indeepen.R;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.content.art.ContentMusicData;
import com.release.indeepen.content.art.ContentYoutubeData;
import com.release.indeepen.culture.CultureItemData;
import com.release.indeepen.culture.CultureItemView;
import com.release.indeepen.fan.FanMainFragment;
import com.release.indeepen.management.musicManager.MusicManager;
import com.release.indeepen.youtube.DeveloperKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyo on 2015-11-01.
 */
public class ContentSingleListAdapter extends BaseAdapter {

    List<ContentData> items = new ArrayList<ContentData>();
    FanMainFragment fanMainFragment;

    Map<View, YouTubeThumbnailLoader> loaders = new HashMap<View, YouTubeThumbnailLoader>();

    public void setFragment(FanMainFragment fragment){
        fanMainFragment = fragment;
    }

    public void changeData(int position, ContentData data) {
        items.set(position, data);
    }
    public void removeData(int position) {
        items.remove(position);
    }

    public void add(ContentData data) {
        items.add(data);
        notifyDataSetChanged();
    }

    public void addList(List<ContentData> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }


    public void clear() {
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
        View view = convertView;

        MusicManager.getMusicManager().reset();

        switch (nType) {
            case DefineContentType.SINGLE_IMAGE: {

                if (null == view) {
                    view = new SingleImageView(parent.getContext());
                }
                ((SingleImageView)view).setData((ContentImageData) items.get(position), fanMainFragment, position);

                break;
            }
            case DefineContentType.SINGLE_MUSIC: {
                if (null == view) {
                    view = new SingleMusicView(parent.getContext());
                } /*else {
                    MusicManager.getMusicManager().pause();
                }*/
                ((SingleMusicView)view).setData((ContentMusicData) items.get(position), fanMainFragment, position);
                break;
            }
            case DefineContentType.SINGLE_YOUTUBE: {

                if (null == view) {
                    view = new SingleYoutubeView(parent.getContext());
                }

                ((SingleYoutubeView)view).setData((ContentYoutubeData) items.get(position), fanMainFragment, position);
                break;
            }
            case DefineContentType.SINGLE_CULTURE: {
                if (null == view) {
                    view = new CultureItemView(parent.getContext());
                }
                ((CultureItemView)view).setItemData((CultureItemData) items.get(position), fanMainFragment, position);
                break;
            }

            default:
                break;

        }

        return view;

    }
    YouTubeThumbnailView.OnInitializedListener listener =  new YouTubeThumbnailView.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
            loaders.put(youTubeThumbnailView, youTubeThumbnailLoader);
            youTubeThumbnailLoader.setVideo((String) youTubeThumbnailView.getTag());
        }

        @Override
        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

        }
    };
}
