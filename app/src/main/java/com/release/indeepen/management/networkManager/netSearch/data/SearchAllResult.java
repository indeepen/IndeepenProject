package com.release.indeepen.management.networkManager.netSearch.data;

import com.google.gson.annotations.SerializedName;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfo;
import com.release.indeepen.space.SpaceInfo;

import java.util.List;

/**
 * Created by lyo on 2015-11-24.
 */
public class SearchAllResult{
    @SerializedName("hashTags")
    public List<HashTagResult> arrHashTag;
    @SerializedName("artists")
    public List<BlogInfo> arrArtist;
    @SerializedName("spaces")
    public List<SpaceInfo> arrSpace;
}
