package com.release.indeepen.management.networkManager.netMyBlog;

import android.text.TextUtils;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.art.ContentYoutubeData;
import com.release.indeepen.management.networkManager.NetworkManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.data.Resources;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogContent;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogContentList;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfoListResult;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfoResult;
import com.release.indeepen.management.networkManager.netMyBlog.data.ImageResult;
import com.release.indeepen.management.networkManager.netMyBlog.data.UserProfileResult;
import com.release.indeepen.management.networkManager.netMyBlog.data.WriteResult;
import com.release.indeepen.management.networkManager.netMyBlog.data.WriterResultList;
import com.release.indeepen.user.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lyo on 2015-11-10.
 */
public class MyBlogController {

    private static MyBlogController instance;
    private ThreadPoolExecutor mExecutor;

    public static MyBlogController getInstance() {
        if (instance == null) {
            instance = new MyBlogController();
        }
        return instance;
    }

    private MyBlogController() {
        mExecutor = NetworkManager.getInstance().getExecutor();
    }


    public void getMyBlogInfo(NetworkRequest<BlogInfoResult> request, NetworkProcess.OnResultListener<BlogInfoResult> listener) {
        mExecutor.execute(new NetworkProcess<BlogInfoResult>(request, listener));
    }

    public void getMyBlogProfile(NetworkRequest<UserProfileResult> request, NetworkProcess.OnResultListener<UserProfileResult> listener) {
        mExecutor.execute(new NetworkProcess<UserProfileResult>(request, listener));
    }

    public void putMyBlogProfile(NetworkRequest<String> request, NetworkProcess.OnResultListener<String> listener) {
        mExecutor.execute(new NetworkProcess<String>(request, listener));
    }

    public void getMyBlogContent(NetworkRequest<BlogContentList> request, NetworkProcess.OnResultListener<BlogContentList> listener) {
        mExecutor.execute(new NetworkProcess<BlogContentList>(request, listener));
    }

    public void getWriterList(NetworkRequest<WriterResultList> request, NetworkProcess.OnResultListener<WriterResultList> listener) {
        mExecutor.execute(new NetworkProcess<WriterResultList>(request, listener));
    }

    public void putFilePOST(NetworkRequest<String> request, NetworkProcess.OnResultListener<String> listener) {
        mExecutor.execute(new NetworkProcess<String>(request, listener));
    }

    public void getProfileImage(NetworkRequest<ImageResult> request, NetworkProcess.OnResultListener<ImageResult> listener) {
        mExecutor.execute(new NetworkProcess<ImageResult>(request, listener));
    }

    public void putFan(NetworkRequest<String> request, NetworkProcess.OnResultListener<String> listener) {
        mExecutor.execute(new NetworkProcess<String>(request, listener));
    }

    public void postIMissYou(NetworkRequest<String> request, NetworkProcess.OnResultListener<String> listener) {
        mExecutor.execute(new NetworkProcess<String>(request, listener));
    }

    public void getMyBlogInfoList(NetworkRequest<BlogInfoListResult> request, NetworkProcess.OnResultListener<BlogInfoListResult> listener) {
        mExecutor.execute(new NetworkProcess<BlogInfoListResult>(request, listener));
    }

    public void putChangeBlog(NetworkRequest<String> request, NetworkProcess.OnResultListener<String> listener) {
        mExecutor.execute(new NetworkProcess<String>(request, listener));
    }


    public List<UserData> getUserList(WriterResultList arrResult) {
        List<UserData> list = new ArrayList<UserData>();
        for (WriteResult writeResult : arrResult.arrWriteResult) {
            if(null != writeResult.mWriter) {
                UserData mData = new UserData();

                mData.sBlogKey = writeResult.mWriter.sBlogKey;
                mData.sArtist = writeResult.mWriter.sArtist;
                mData.sUserkey = writeResult.mWriter.sUserkey;
                mData.thProfile = writeResult.mWriter.thProfile;

                if (null != mData) {
                    list.add(mData);
                }
            }
        }
        return list;
    }


    public List<ContentData> getContentList(BlogContentList arrResult) {
        List<ContentData> list = new ArrayList<ContentData>();
        for (BlogContent contens : arrResult.arrBogContents) {
            ContentData mData = getContent(contens);
            if (null != mData) {
                list.add(mData);
            }
        }
        return list;
    }

    public ContentData getContent(BlogContent contens) {
        if (null == contens || null == contens.mWork)
            return null;
        ContentData mData = null;



        if(null != contens.arrResources) {
            mData = new ContentData();
            for (Resources resources : contens.arrResources) {
                if (resources.sFileType.contains("image") && !TextUtils.isEmpty(resources.sThumb)) {
                    // mData.thIMG = Uri.parse(resources.sPath).toString();
                    mData.sThumb = resources.sThumb;
                }
            }
        }



        if(DefineContentType.SINGLE_ART_TYPE_YOUTUBE == contens.mWork.nArtType || DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO == contens.mWork.nArtType){
             mData = new ContentYoutubeData();
            ((ContentYoutubeData)mData).sYouTubePath = contens.sYouTubePath;
        }

        if(null != mData) {
            mData.nArtType = contens.mWork.nArtType;
            mData.sContentKey = contens.sContentKey;
        }
        return mData;
    }

}
