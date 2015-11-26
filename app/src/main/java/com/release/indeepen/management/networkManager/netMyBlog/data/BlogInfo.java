package com.release.indeepen.management.networkManager.netMyBlog.data;

import com.google.gson.annotations.SerializedName;
import com.release.indeepen.management.networkManager.netSearch.data.Search;

import java.util.List;

/**
 * Created by lyo on 2015-11-21.
 */
public class BlogInfo  implements Search {
    @SerializedName("_id")
    public String sBlogKey;

    @SerializedName("_user")
    public String sUserKey;

    @SerializedName("nick")
    public String sArtist;

    @SerializedName("profilePhoto")
    public String thProfile;

    @SerializedName("bgPhoto")
    public String thBackIMG;

    @SerializedName("type")
    public int nBlogType;

    @SerializedName("fans")
    public List<String> arrFans;

    @SerializedName("iMissYous")
    public List<String> arrIMissYous;

}
