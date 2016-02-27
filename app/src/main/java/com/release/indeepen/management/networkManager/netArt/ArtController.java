package com.release.indeepen.management.networkManager.netArt;

import android.net.Uri;
import android.text.TextUtils;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.content.art.ContentMusicData;
import com.release.indeepen.content.art.ContentYoutubeData;
import com.release.indeepen.culture.CultureItemData;
import com.release.indeepen.management.networkManager.NetworkManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.data.ContentResult;
import com.release.indeepen.management.networkManager.netArt.data.ContentResultList;
import com.release.indeepen.management.networkManager.netArt.data.Resources;
import com.release.indeepen.management.networkManager.netArt.data.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lyo on 2015-11-10.
 */
public class ArtController {

    private static ArtController instance;
    private ThreadPoolExecutor mExecutor;

    public static ArtController getInstance() {
        if (instance == null) {
            instance = new ArtController();
        }
        return instance;
    }

    private ArtController() {
        mExecutor = NetworkManager.getInstance().getExecutor();
    }


    public void getArtList(NetworkRequest<ContentResultList> request, NetworkProcess.OnResultListener<ContentResultList> listener) {
        mExecutor.execute(new NetworkProcess<ContentResultList>(request, listener));
    }

    public void getDetailContent(NetworkRequest<ContentResult> request, NetworkProcess.OnResultListener<ContentResult> listener) {
        mExecutor.execute(new NetworkProcess<ContentResult>(request, listener));
    }

    public void putFilePOST(NetworkRequest<String> request, NetworkProcess.OnResultListener<String> listener) {
        mExecutor.execute(new NetworkProcess<String>(request, listener));
    }




    public List<ContentData> getContentList(ContentResultList arrResult) {
        List<ContentData> list = new ArrayList<ContentData>();
        for (Result contens : arrResult.arrArts) {
            ContentData mData = getContent(contens);
            if (null != mData) {
                list.add(mData);
            }
        }
        return list;
    }

    public ContentData getContent(Result contens) {
        if (null == contens || null == contens.mPostinfo)
            return null;
        ContentData mData = null;


        if (DefineContentType.POST_TYPE_CULTURE == contens.mPostinfo.nPostType) {
            mData = new CultureItemData();

            mData.nArtType = DefineContentType.SINGLE_ART_TYPE_CULTURE;

            for (Resources resources : contens.mPostinfo.arrResources) {
                if (resources.sFileType.contains("image")) {
                    ((CultureItemData) mData).arrIMGs.add(resources.sPath);
                    ((CultureItemData) mData).arrThumbs.add(resources.sThumb);
                }
            }

            ((CultureItemData) mData).sTitle = contens.mPostinfo.mShow.sTitle;
            ((CultureItemData) mData).dStartDate = contens.mPostinfo.mShow.dStartDate;
            ((CultureItemData) mData).dEndDate = contens.mPostinfo.mShow.dEndDate;
            ((CultureItemData) mData).dStartTime = contens.mPostinfo.mShow.dStartTime;
            ((CultureItemData) mData).dEndTime = contens.mPostinfo.mShow.dEndTime;
            ((CultureItemData) mData).nFee = contens.mPostinfo.mShow.nFee;
            if (null != contens.mPostinfo.mShow.mLocation) {
                ((CultureItemData) mData).sAddress = contens.mPostinfo.mShow.mLocation.sAddress;
            }
            ((CultureItemData) mData).arrTags = contens.mPostinfo.mShow.arrTags;

        } else if (DefineContentType.POST_TYPE_ART == contens.mPostinfo.nPostType){

            switch (contens.mPostinfo.mWork.nArtType) {
                case DefineContentType.SINGLE_ART_TYPE_PAINT:
                case DefineContentType.SINGLE_ART_TYPE_PICTURE:
                case DefineContentType.SINGLE_ART_TYPE_MUSIC_PICTURE: {
                    mData = new ContentImageData();
                    //((ContentImageData) mData).arrIMGs = new ArrayList<String>();
                    if(0 == contens.mPostinfo.arrResources.size()){
                        return null;
                    }
                    for (Resources resources : contens.mPostinfo.arrResources) {
                        if (resources.sFileType.contains("image")) {
                            ((ContentImageData) mData).arrIMGs.add(resources.sPath);
                            ((ContentImageData) mData).arrThumbs.add(resources.sThumb);
                        }
                    }
                    break;
                }
                /*case DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO: {
                    mData = new ContentVideoData();
                    *//*((ContentVideoData)mData).*//*
                    break;
                }*/
                case DefineContentType.SINGLE_ART_TYPE_MUSIC:
                {
                    mData = new ContentMusicData();

                    if (null == contens.mPostinfo.arrResources) return null;

                    for (Resources resources : contens.mPostinfo.arrResources) {
                        if (TextUtils.isEmpty(resources.sFileType)) break;

                        if (resources.sFileType.contains("image")) {
                            if(null!= resources.sThumb) {
                                ((ContentMusicData) mData).sMusicBackThumb = Uri.parse(resources.sThumb).toString();
                            }else{
                                ((ContentMusicData) mData).sMusicBackIMG = Uri.parse(resources.sPath).toString();
                            }
                        } else if (resources.sFileType.contains("audio")) {
                            if(null!= resources.sThumb) {
                                ((ContentMusicData) mData).sMusicPath = Uri.parse(resources.sThumb).toString();
                            }else{
                                ((ContentMusicData) mData).sMusicPath = Uri.parse(resources.sPath).toString();
                            }
                            if (TextUtils.isEmpty(((ContentMusicData) mData).sMusicPath)) continue;
                        }
                    }
                    break;
                }
                case DefineContentType.SINGLE_ART_TYPE_YOUTUBE:
                case DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO: {
                    mData = new ContentYoutubeData();
                    ((ContentYoutubeData) mData).sYouTubePath = contens.mPostinfo.sYouTubePath;
                    break;
                }

            }
            mData.nArtType = contens.mPostinfo.mWork.nArtType;
            mData.nEmotion = contens.mPostinfo.mWork.nEmotion;
        }

        mData.sContentKey = contens.mPostinfo.sContentKey;
        mData.nCommentCount = contens.nCommentCnt;
        mData.sText = contens.mPostinfo.sContent;
        if(null != contens.mPostinfo.mWriter) {
            mData.mUserData.sArtist = contens.mPostinfo.mWriter.sArtist;
            mData.sBlogKey = contens.mPostinfo.mWriter.sBlogKey;
            mData.nBlogType = contens.mPostinfo.mWriter.nBlogType;
            mData.mUserData.sUserkey = contens.mPostinfo.mWriter.sUserkey;
            mData.mUserData.thProfile = contens.mPostinfo.mWriter.thProfile;
        }else{
           // return null;
        }

        mData.sWriteDate = contens.mPostinfo.dCreateAt;
        mData.arrLikes = contens.mPostinfo.arrLikes;
        mData.nLikeCount = contens.mPostinfo.arrLikes.size();
        mData.arrComment = contens.arrComments;

        return mData;
    }
}
