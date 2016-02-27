package com.release.indeepen.management.networkManager.netCulture;

import android.net.Uri;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.culture.CultureItemData;
import com.release.indeepen.management.networkManager.NetworkManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.data.ContentResult;
import com.release.indeepen.management.networkManager.netArt.data.ContentResultList;
import com.release.indeepen.management.networkManager.netArt.data.Postinfo;
import com.release.indeepen.management.networkManager.netArt.data.Resources;
import com.release.indeepen.management.networkManager.netArt.data.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lyo on 2015-11-10.
 */
public class CultureController {

    private static CultureController instance;
    private ThreadPoolExecutor mExecutor;

    public static CultureController getInstance() {
        if (instance == null) {
            instance = new CultureController();
        }
        return instance;
    }

    private CultureController() {
        mExecutor = NetworkManager.getInstance().getExecutor();
    }


    public void getCultureList(NetworkRequest<ContentResultList> request, NetworkProcess.OnResultListener<ContentResultList> listener) {
        mExecutor.execute(new NetworkProcess<ContentResultList>(request, listener));
    }



    public void getDetailContent(NetworkRequest<ContentResult> request, NetworkProcess.OnResultListener<ContentResult> listener) {
        mExecutor.execute(new NetworkProcess<ContentResult>(request, listener));
    }


    public List<CultureItemData> getRealContentList(ContentResultList arrResult) {
        List<CultureItemData> list = new ArrayList<CultureItemData>();
        for (Result contens : arrResult.arrArts) {
            if (null == contens || null == contens.mPostinfo || null == contens.mPostinfo.mWriter) {
                continue;
            }

            CultureItemData mData = null;

            if (DefineContentType.POST_TYPE_CULTURE == contens.mPostinfo.nPostType) {
                mData = getRealContent(contens.mPostinfo);

                mData.nCommentCount = contens.nCommentCnt;
                mData.arrComment = contens.arrComments;
            }
            if (null != mData) {
                list.add(mData);
            }
        }
        return list;
    }

    public CultureItemData getRealContent(Postinfo mPostinfo) {


        CultureItemData mData = null;

        if (DefineContentType.POST_TYPE_CULTURE == mPostinfo.nPostType) {
            mData = new CultureItemData();
            mData.nArtType = DefineContentType.SINGLE_ART_TYPE_CULTURE;
            if (null != mPostinfo.arrResources) {
                for (Resources resources : mPostinfo.arrResources) {
                    if (resources.sFileType.contains("image")) {
                        mData.arrIMGs.add(resources.sPath);
                        mData.arrThumbs.add(resources.sThumb);
                    }
                }
            }
            mData.sTitle = mPostinfo.mShow.sTitle;
            mData.dStartDate = mPostinfo.mShow.dStartDate;
            mData.dEndDate = mPostinfo.mShow.dEndDate;
            mData.dStartTime = mPostinfo.mShow.dStartTime;
            mData.dEndTime = mPostinfo.mShow.dEndTime;
            mData.nFee = mPostinfo.mShow.nFee;
            if (null != mPostinfo.mShow.mLocation) {
                mData.sAddress = mPostinfo.mShow.mLocation.sAddress;
            }
            mData.arrTags = mPostinfo.mShow.arrTags;


            mData.sContentKey = mPostinfo.sContentKey;

            mData.sText = mPostinfo.sContent;


            mData.mUserData.sArtist = mPostinfo.mWriter.sArtist;
            mData.sBlogKey = mPostinfo.mWriter.sBlogKey;
            mData.mUserData.sUserkey = mPostinfo.mWriter.sUserkey;
            mData.mUserData.thProfile = mPostinfo.mWriter.thProfile;


            mData.sWriteDate = mPostinfo.dCreateAt;
            mData.arrLikes = mPostinfo.arrLikes;
            mData.nLikeCount = mPostinfo.arrLikes.size();

        }


        return mData;
    }

}
