package com.release.indeepen.content.art.singleList;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.R;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.content.art.ContentMusicData;
import com.release.indeepen.content.art.ContentYoutubeData;
import com.release.indeepen.culture.CultureItemData;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.ArtController;
import com.release.indeepen.management.networkManager.netArt.ArtListRequest;
import com.release.indeepen.management.networkManager.netArt.data.ContentResultList;
import com.release.indeepen.management.networkManager.netArt.data.Resources;
import com.release.indeepen.management.networkManager.netArt.data.Result;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentSingListFragment extends Fragment {

    ListView vList;
    ContentSingleListAdapter mAdapter;

    public ContentSingListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_sing_list, container, false);

        vList = (ListView) view.findViewById(R.id.list_single);

        mAdapter = new ContentSingleListAdapter();
        vList.setAdapter(mAdapter);
        init();
        return view;
    }

    private void addData(ContentResultList arrContentResultList) {

        for (Result contens : arrContentResultList.arrArts) {
            ContentData mData = null;
            if (0 == contens.mPostinfo.nPostType) {
                mData = new CultureItemData();
                mData.nArtType = DefineContentType.SINGLE_ART_TYPE_CULTURE;
            }

            switch (contens.mPostinfo.mWork.nArtType) {
                case DefineContentType.SINGLE_ART_TYPE_PAINT:
                case DefineContentType.SINGLE_ART_TYPE_PICTURE:
                case DefineContentType.SINGLE_ART_TYPE_MUSIC_PICTURE: {
                    mData = new ContentImageData();
                    ((ContentImageData) mData).arrIMGs = new ArrayList<String>();
                    for (Resources resources : contens.mPostinfo.arrResources) {
                        if (resources.sFileType.contains("image")) {
                            ((ContentImageData) mData).arrIMGs.add(Uri.parse(resources.sThumb).toString());
                        }
                    }
                    break;
                }
               /* case DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO: {
                    mData = new ContentVideoData();
                    break;
                }*/
                case DefineContentType.SINGLE_ART_TYPE_MUSIC: {
                    mData = new ContentMusicData();
                    break;
                }
                case DefineContentType.SINGLE_ART_TYPE_YOUTUBE: {
                    mData = new ContentYoutubeData();
                    break;
                }
            }

            mData.sContentKey = contens.mPostinfo.sContentKey;
            mData.nArtType = contens.mPostinfo.mWork.nArtType;
            mData.nCommentCount = contens.nCommentCnt;
            mData.sText = contens.mPostinfo.sContent;


            mData.mUserData.sArtist = contens.mPostinfo.mWriter.sArtist;
            mData.sBlogKey = contens.mPostinfo.mWriter.sBlogKey;
            mData.mUserData.sUserkey = contens.mPostinfo.mWriter.sUserkey;
            mData.mUserData.thProfile = Uri.parse(contens.mPostinfo.mWriter.thProfile).toString();
            mData.nEmotion = contens.mPostinfo.mWork.nEmotion;
            mData.sWriteDate = contens.mPostinfo.dCreateAt;
            mData.arrLikes = contens.mPostinfo.arrLikes;
            mData.nLikeCount = contens.mPostinfo.arrLikes.size();
            mData.arrComment = contens.arrComments;
        }
    }


    private void init() {

        ArtListRequest request = new ArtListRequest();
        ArtController.getInstance().getArtList(request, new NetworkProcess.OnResultListener<ContentResultList>() {
            @Override
            public void onSuccess(NetworkRequest<ContentResultList> request, ContentResultList result) {
                ContentResultList arrContentResultList;
                arrContentResultList = result;
                addData(arrContentResultList);
            }

            @Override
            public void onFail(NetworkRequest<ContentResultList> request, int code) {

            }
        });

        //test1();

    }

    private void test1() {


       /* ContentYoutubeData youtubeData = new ContentYoutubeData();
        youtubeData.nArtType = DefineContentType.SINGLE_ART_TYPE_YOUTUBE;
        youtubeData.thProfile = DefineTest.ARR_IMG2[3];
        youtubeData.sPath = "YQHsXMglC9A";
        mAdapter.add(youtubeData);


        ContentMusicData musicData = new ContentMusicData();
        musicData.nArtType = DefineContentType.SINGLE_ART_TYPE_MUSIC;
        musicData.thProfile = DefineTest.ARR_IMG2[3];
        musicData.sMusicBackIMG = DefineTest.ARR_IMG2[5];
        musicData.sMusicPath = "android.resource://com.release.indeepen/raw/sample";
        mAdapter.add(musicData);

        musicData = new ContentMusicData();
        musicData.nArtType = DefineContentType.SINGLE_ART_TYPE_MUSIC;
        musicData.thProfile = DefineTest.ARR_IMG2[3];
        musicData.sMusicBackIMG = DefineTest.ARR_IMG2[2];
        musicData.sMusicPath = "android.resource://com.release.indeepen/raw/holdon";
        mAdapter.add(musicData);

        ContentVideoData data = new ContentVideoData();
        data.nArtType = DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO;
        data.thProfile = DefineTest.ARR_IMG2[2];
       // data.videoData = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 7389);
        mAdapter.add(data);

        data = new ContentVideoData();
        data.nArtType = DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO;
        data.thProfile = DefineTest.ARR_IMG2[2];
       // data.videoData = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 7391);
        mAdapter.add(data);


        for (int idx = 0; idx < 11; idx++) {
            ContentImageData mData = new ContentImageData();
            mData.nArtType = DefineContentType.SINGLE_ART_TYPE_PAINT;
            mData.thProfile = DefineTest.ARR_IMG2[idx % 8];
            mData.arrIMGs = Arrays.asList(DefineTest.ARR_PATH);
            mAdapter.add(mData);
        }*/
    }

}
