package com.release.indeepen.space;

import com.google.gson.annotations.SerializedName;
import com.release.indeepen.management.networkManager.netSearch.data.Search;

import java.util.List;

/**
 * Created by lyo on 2015-11-24.
 */
public class SpaceInfo  implements Search {

    @SerializedName("_id")
    public String sBlogKey;

    @SerializedName("nick")
    public String sSpaceName;

    @SerializedName("profilePhoto")
    public String thProfile;

    @SerializedName("bgPhoto")
    public String thBackIMG;

    @SerializedName("type")
    public int nBlogType;

}
