package com.release.indeepen.management.networkManager.netMyBlog.data;

import com.google.gson.annotations.SerializedName;
import com.release.indeepen.management.networkManager.netArt.data.Resources;
import com.release.indeepen.management.networkManager.netArt.data.Work;

import java.util.List;

/**
 * Created by lyo on 2015-11-21.
 */
public class BlogContent {
    @SerializedName("_id")
    public String sContentKey;

    @SerializedName("resources")
    public List<Resources> arrResources;

    @SerializedName("work")
    public Work mWork;

}
